/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.converter;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class IntegerConverter implements IValueConverter<Integer> {

	@Override
	public String convert( Integer value ) {
		return value.toString();
	}

	@Override
	public Integer revert( String serialized ) {
		return Integer.valueOf( serialized );
	}

}
