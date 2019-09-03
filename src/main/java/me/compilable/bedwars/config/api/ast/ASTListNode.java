/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.ast;

import java.util.Iterator;
import java.util.List;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class ASTListNode extends ASTNode implements Iterable<ASTNode> {

	private final List<ASTNode> elements;

	public ASTListNode( final String name, final List<ASTNode> elements ) {
		super( name );
		this.elements = elements;
	}

	@Override
	public Iterator<ASTNode> iterator() {
		return this.elements.iterator();
	}

	public int size() {
		return this.elements.size();
	}

}
