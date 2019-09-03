/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.converter;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class DoubleConverter implements IValueConverter<Double> {

	@Override
	public String convert( Double value ) {
		return value.toString();
	}

	@Override
	public Double revert( String serialized ) {
		return Double.valueOf( serialized );
	}

}
