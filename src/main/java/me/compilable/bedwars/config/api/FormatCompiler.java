/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api;

import lombok.Getter;
import lombok.Setter;
import me.compilable.bedwars.config.api.annotation.Comment;
import me.compilable.bedwars.config.api.converter.ConversionException;
import me.compilable.bedwars.config.api.converter.ConverterFactory;
import me.compilable.bedwars.config.api.converter.IValueConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

/**
 * @author BlackyPaw
 * @version 1.0
 */
class FormatCompiler {

	@Getter
	@Setter
	private String identation = "\t";

	private int identLevel;

	/**
	 * Compiles the specified object into a pretty-printed configuration string.
	 *
	 * @param item The item to compile.
	 * @param <T>  The type of the item to compile.
	 *
	 * @return The compiled string.
	 */
	public <T extends SimpleConfig> String compile(T item ) {
		StringBuilder builder = new StringBuilder();

		this.identLevel = 0;

		try {
			this.compileInstance( item, builder );
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return builder.toString();
	}

	@SuppressWarnings( "unchecked" )
	private <T extends SimpleConfig> void compileInstance( T item, StringBuilder builder ) throws Exception {
		builder.append( '{' );
		this.lineBreak( builder );

		this.identLevel++;

		for ( Field field : item.getClass().getDeclaredFields() ) {
			if ( Modifier.isStatic( field.getModifiers() ) ) {
				continue;
			}

			// Check for Comments
			if ( field.isAnnotationPresent( Comment.class ) ) {
				String commentContent = field.getAnnotation( Comment.class ).value();
				for ( String commentLine : commentContent.split( "\n" ) ) {
					this.identify( builder );
					builder.append( "# " ).append( commentLine );
					this.lineBreak( builder );
				}
			}

			field.setAccessible( true );
			Class<?> fieldType  = field.getType();
			Object   fieldValue = field.get( item );

			this.identify( builder );
			builder.append( field.getName() );
			builder.append( ": " );
			StringBuilder fieldBuilder = new StringBuilder();
			this.compileItem( field, fieldType, fieldValue, fieldBuilder );
			builder.append( fieldBuilder.toString() );
			this.lineBreak( builder );
		}

		this.identLevel--;

		this.identify( builder );
		builder.append( '}' );
	}

	@SuppressWarnings( "unchecked" )
	private void compileItem( Field field, Class<?> type, Object value, StringBuilder builder ) throws Exception {
		if ( value == null ) {
			builder.append( "null" );
			return;
		}

		// Special construct handling:
		boolean      isList        = false;
		Class<?>     componentType = null;
		List<Object> listElements  = null;

		if ( type.isArray() ) {
			isList = true;
			componentType = type.getComponentType();
			listElements = Arrays.asList( (Object[]) value );
		} else if ( List.class.isAssignableFrom( type ) ) {
			isList = true;

			// Try to get type by elements:
			ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
			componentType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
			listElements = (List) value;
		}

		if ( SimpleConfig.class.isAssignableFrom( type ) ) {
			this.compileInstance( (SimpleConfig) value, builder );
		} else if ( isList ) {
			// List value conversion:
			this.compileList( componentType, listElements, builder );
		} else {
			// Raw value conversion:
			this.compileValue( type, value, builder );
		}
	}

	@SuppressWarnings( { "unchecked" } )
	private void compileItem( Class<?> type, Object value, StringBuilder builder ) throws Exception {
		if ( value == null ) {
			builder.append( "null" );
			return;
		}

		// Special construct handling:
		boolean      isList        = false;
		Class<?>     componentType = null;
		List<Object> listElements  = null;

		if ( type.isArray() ) {
			isList = true;
			componentType = type.getComponentType();
			listElements = Arrays.asList( (Object[]) value );
		} else if ( List.class.isAssignableFrom( type ) ) {
			isList = true;

			// Try to get type by elements:
			listElements = (List) value;
			if ( listElements.size() > 0 ) {
				componentType = listElements.get( 0 ).getClass();
			}
		}

		if ( SimpleConfig.class.isAssignableFrom( type ) ) {
			this.compileInstance( (SimpleConfig) value, builder );
		} else if ( isList ) {
			// List value conversion:
			this.compileList( componentType, listElements, builder );
		} else {
			// Raw value conversion:
			this.compileValue( type, value, builder );
		}
	}

	private void compileList( Class<?> componentType, List<?> elements, StringBuilder builder ) throws Exception {
		builder.append( "[" );
		this.lineBreak( builder );
		this.identLevel++;

		for ( int i = 0; i < elements.size(); ++i ) {
			Object element = elements.get( i );

			this.identify( builder );
			this.compileItem( componentType, element, builder );
			if ( i + 1 < elements.size() ) {
				builder.append( ',' );
			}
			this.lineBreak( builder );
		}
		this.identLevel--;

		this.identify( builder );
		builder.append( "]" );
	}

	@SuppressWarnings( "unchecked" )
	private void compileValue( Class<?> type, Object value, StringBuilder builder ) throws Exception {
		IValueConverter converter = ConverterFactory.getConverter( type );
		if ( converter == null ) {
			throw new ConversionException( "Could not find appropriate converter for type: " + type.getName() );
		}

		builder.append( converter.convert( value ) );
	}

	private void identify( StringBuilder builder ) {
		for ( int i = 0; i < this.identLevel; ++i ) {
			builder.append( this.identation );
		}
	}

	private void lineBreak( StringBuilder builder ) {
		builder.append( '\n' );
	}

}
