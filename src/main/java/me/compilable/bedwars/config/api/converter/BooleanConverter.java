/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.converter;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class BooleanConverter implements IValueConverter<Boolean> {

	@Override
	public String convert( Boolean value ) {
		return value.toString();
	}

	@Override
	public Boolean revert( String serialized ) {
		return Boolean.valueOf( serialized );
	}

}
