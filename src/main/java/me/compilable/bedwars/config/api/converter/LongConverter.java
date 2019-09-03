/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.converter;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class LongConverter implements IValueConverter<Long> {

	@Override
	public String convert( Long value ) {
		return value.toString();
	}

	@Override
	public Long revert( String serialized ) {
		return Long.valueOf( serialized );
	}

}
