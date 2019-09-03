/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.converter;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class ByteConverter implements IValueConverter<Byte> {

	@Override
	public String convert( Byte value ) {
		return value.toString();
	}

	@Override
	public Byte revert( String serialized ) {
		return Byte.valueOf( serialized );
	}

}
