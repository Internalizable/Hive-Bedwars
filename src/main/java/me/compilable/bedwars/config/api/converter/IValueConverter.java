/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.converter;

/**
 * Value converts convert an object into a string ready to be written out into a configuration file.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public interface IValueConverter<T> {

	/**
	 * Converts the specified value to a string.
	 *
	 * @param value The value to convert.
	 *
	 * @return The converted value.
	 */
	String convert(T value) throws ConversionException;

	/**
	 * Reverts the conversion process.
	 *
	 * @param serialized The serialized string of the object.
	 *
	 * @return The de-serialized object.
	 */
	T revert(String serialized) throws ReversionException;

}
