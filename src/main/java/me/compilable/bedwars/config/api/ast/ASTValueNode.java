/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.ast;

import lombok.Getter;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@Getter
public class ASTValueNode extends ASTNode {

	private final String value;

	public ASTValueNode( final String name, final String value ) {
		super( name );
		this.value = value;
	}

}
