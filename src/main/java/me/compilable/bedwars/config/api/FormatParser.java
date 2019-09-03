/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api;

import me.compilable.bedwars.config.api.ast.ASTCompoundNode;
import me.compilable.bedwars.config.api.ast.ASTListNode;
import me.compilable.bedwars.config.api.ast.ASTNode;
import me.compilable.bedwars.config.api.ast.ASTValueNode;
import me.compilable.bedwars.config.api.converter.ConverterFactory;
import me.compilable.bedwars.config.api.converter.IValueConverter;
import me.compilable.bedwars.config.api.converter.ReversionException;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author BlackyPaw
 * @version 1.0
 */
class FormatParser {

	private StreamTokenizer tokenizer;
	private int             token;

	public FormatParser( final Reader reader ) {
		this.tokenizer = new StreamTokenizer( reader );

		// Setup the tokenizer as we wish:
		this.tokenizer.eolIsSignificant( false );
		this.tokenizer.ordinaryChars( '0', '9' );
		this.tokenizer.ordinaryChars( '.', '.' );
		this.tokenizer.ordinaryChars( '_', '_' );
		this.tokenizer.ordinaryChars( '-', '-' );
		this.tokenizer.wordChars( 'a', 'b' );       // Allowed in names
		this.tokenizer.wordChars( 'A', 'B' );
		this.tokenizer.wordChars( '0', '9' );
		this.tokenizer.wordChars( '_', '_' );
		this.tokenizer.wordChars( '.', '.' );
		this.tokenizer.wordChars( '-', '-' );
		this.tokenizer.quoteChar( '"' );            // String escaping
		this.tokenizer.commentChar( '#' );          // Comments
		this.tokenizer.whitespaceChars( 0x00, 0x20 );
	}

	public <T extends SimpleConfig> void parse(T item ) throws IOException, ParseException, ReversionException {
		this.next();
		ASTCompoundNode root = this.parseCompound( null );

		try {
			this.applyAST( item, root );
		} catch ( ClassCastException e ) {
			// For invalid node casting:
			throw new ReversionException( "Failed to reverse configuration: Invalid node type not matching required field", e );
		}
	}

	// ----------------------------------------------------------- Utility Methods
	private int next() throws IOException, ParseException {
		this.token = this.tokenizer.nextToken();
		return this.token;
	}

	private int match( int c ) throws IOException, ParseException {
		if ( this.token == c ) {
			this.next();
		} else {
			throw new ParseException( String.format( "Unexpected token %c in line %d; expected %c", (char) this.token, this.tokenizer.lineno(), (char) c ), this.tokenizer.lineno() );
		}

		return this.token;
	}

	private String ensureWord() throws IOException, ParseException {
		if ( !( this.token == StreamTokenizer.TT_WORD || this.token == '\"' ) ) {
			throw new ParseException( String.format( "Unexpected token %c in line %d; expected identifier", (char) this.token, this.tokenizer.lineno() ), this.tokenizer.lineno() );
		}

		String identifier = this.tokenizer.sval;
		this.next();
		return identifier;
	}

	// ----------------------------------------------------------- Parsing

	private ASTCompoundNode parseCompound( String name ) throws IOException, ParseException {
		this.match( '{' );

		Map<String, ASTNode> fields = new HashMap<>();

		while ( this.token != '}' ) {
			String fieldName = this.ensureWord();
			this.match( ':' );
			ASTNode node = this.parseField( fieldName );
			fields.put( fieldName, node );
		}

		this.match( '}' );
		return new ASTCompoundNode( name, fields );
	}

	private ASTNode parseField( String fieldName ) throws IOException, ParseException {
		// Handle special escape sequences:
		switch ( this.token ) {
			case '[':
				return this.parseList( fieldName );
			case '{':
				return this.parseCompound( fieldName );
			default:
				return new ASTValueNode( fieldName, this.ensureWord() );
		}
	}

	private ASTListNode parseList(String fieldName ) throws IOException, ParseException {
		this.match( '[' );

		List<ASTNode> elements = new ArrayList<>();

		while ( this.token != ']' ) {
			ASTNode element = this.parseField( null );
			elements.add( element );

			if ( this.token != ',' ) {
				break;
			}

			this.next();
		}

		this.match( ']' );

		return new ASTListNode( fieldName, elements );
	}

	// ----------------------------------------------------------- AST Application

	private <T extends SimpleConfig> void applyAST( final T item, final ASTCompoundNode root ) throws ReversionException {
		Class<?> type = item.getClass();
		for ( Field field : type.getDeclaredFields() ) {
			if ( Modifier.isStatic( field.getModifiers() ) ) {
				continue;
			}

			field.setAccessible( true );

			ASTNode node = root.getField( field.getName() );
			if ( node == null ) {
				continue;
			}

			Object value = this.revertInstance( field, node );
			try {
				field.set( item, value );
			} catch ( IllegalAccessException e ) {
				throw new ReversionException( "Failed to reverse compound: could not set field " + field.getName() );
			}
		}
	}

	private Object revertInstance( Field field, ASTNode node ) throws ReversionException {
		if ( node instanceof ASTValueNode ) {
			if ( ( (ASTValueNode) node ).getValue().equals( "null" ) ) {
				return null;
			}
		}

		Class<?> type   = field.getType();
		boolean  isList = ( type.isArray() || List.class.isAssignableFrom( type ) );

		if ( isList ) {
			return this.revertList( field, (ASTListNode) node );
		} else {
			if ( SimpleConfig.class.isAssignableFrom( type ) ) {
				return this.revertCompound( type, (ASTCompoundNode) node );
			} else {
				return this.revertValue( type, (ASTValueNode) node );
			}
		}
	}

	private Object revertInstance( Class<?> type, ASTNode node ) throws ReversionException {
		boolean isList = ( type.isArray() || List.class.isAssignableFrom( type ) );

		if ( isList ) {
			if ( type.isArray() ) {
				return this.revertList( type.getComponentType(), (ASTListNode) node );
			} else {
				throw new ReversionException( "Cannot deduce generic component type for list: " + node.getName() );
			}
		} else {
			if ( SimpleConfig.class.isAssignableFrom( type ) ) {
				return this.revertCompound( type, (ASTCompoundNode) node );
			} else {
				return this.revertValue( type, (ASTValueNode) node );
			}
		}
	}

	private Object revertCompound( Class<?> type, ASTCompoundNode node ) throws ReversionException {
		Object instance;

		try {
			Constructor defaultConstructor = type.getDeclaredConstructor();
			defaultConstructor.setAccessible( true );
			instance = defaultConstructor.newInstance();
		} catch ( NoSuchMethodException e ) {
			throw new ReversionException( "Failed to reverse compound: no default constructor declared for type " + type.getName(), e );
		} catch ( InvocationTargetException | InstantiationException | IllegalAccessException e ) {
			throw new ReversionException( "Failed to reverse compound: Failed to invoke default constructor for type " + type.getName(), e );
		}

		this.applyAST( (SimpleConfig) instance, node );
		return instance;
	}

	private Object revertList( Field field, ASTListNode node ) throws ReversionException {
		boolean  asArray = true;
		Class<?> componentType;

		if ( List.class.isAssignableFrom( field.getType() ) ) {
			ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
			componentType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
			asArray = false;
		} else {
			// Is array:
			componentType = field.getType().getComponentType();
		}

		Object[]          array = new Object[node.size()];
		Iterator<ASTNode> it    = node.iterator();
		int               index = 0;
		while ( it.hasNext() ) {
			array[index] = this.revertInstance( componentType, it.next() );
			index++;
		}

		return ( asArray ? array : Arrays.asList( array ) );
	}

	private Object revertList( Class<?> componentType, ASTListNode node ) throws ReversionException {
		Object[]          array = new Object[node.size()];
		Iterator<ASTNode> it    = node.iterator();
		int               index = 0;
		while ( it.hasNext() ) {
			array[index] = this.revertInstance( componentType, it.next() );
			index++;
		}

		return ( array );
	}

	private Object revertValue( Class<?> type, ASTValueNode node ) throws ReversionException {
		IValueConverter converter = ConverterFactory.getConverter( type );
		if ( converter == null ) {
			throw new ReversionException( "Could not find appropriate converter for type: " + type.getName() );
		}

		return converter.revert( node.getValue() );
	}

}
