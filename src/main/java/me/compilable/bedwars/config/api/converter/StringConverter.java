/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.converter;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class StringConverter implements IValueConverter<String> {

	@Override
	public String convert( String value ) {
		return "\"" + value + "\"";
	}

	@Override
	public String revert( String serialized ) {
		return serialized;
	}
}
