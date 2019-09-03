/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.converter;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class FloatConverter implements IValueConverter<Float> {

	@Override
	public String convert( Float value ) {
		return value.toString();
	}

	@Override
	public Float revert( String serialized ) {
		return Float.valueOf( serialized );
	}

}
