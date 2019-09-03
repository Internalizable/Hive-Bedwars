/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.ast;

import java.util.Map;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class ASTCompoundNode extends ASTNode {

	private final Map<String, ASTNode> fields;

	public ASTCompoundNode( final String name, final Map<String, ASTNode> fields ) {
		super( name );
		this.fields = fields;
	}

	public ASTNode getField( String name ) {
		return this.fields.get( name );
	}

}
