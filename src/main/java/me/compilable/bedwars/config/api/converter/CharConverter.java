/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.converter;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class CharConverter implements IValueConverter<Character> {

	@Override
	public String convert( Character value ) {
		return value.toString();
	}

	@Override
	public Character revert( String serialized ) {
		return serialized.charAt( 0 );
	}

}
