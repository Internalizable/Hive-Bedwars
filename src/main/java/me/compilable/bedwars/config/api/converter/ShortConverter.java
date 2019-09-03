/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.converter;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class ShortConverter implements IValueConverter<Short> {

	@Override
	public String convert( Short value ) {
		return value.toString();
	}

	@Override
	public Short revert( String serialized ) {
		return Short.valueOf( serialized );
	}

}
