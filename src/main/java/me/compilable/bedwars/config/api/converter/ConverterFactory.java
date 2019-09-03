/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */

package me.compilable.bedwars.config.api.converter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class ConverterFactory {

	private static Map<Class, IValueConverter> converterMap = new HashMap<>();

	// Default converter registration:
	static {
		registerConverter( Byte.TYPE, new ByteConverter() );
		registerConverter( Short.TYPE, new ShortConverter() );
		registerConverter( Integer.TYPE, new IntegerConverter() );
		registerConverter( Long.TYPE, new LongConverter() );
		registerConverter( Float.TYPE, new FloatConverter() );
		registerConverter( Double.TYPE, new DoubleConverter() );
		registerConverter( Boolean.TYPE, new BooleanConverter() );
		registerConverter( Character.TYPE, new CharConverter() );
		registerConverter( Byte.class, new ByteConverter() );
		registerConverter( Short.class, new ShortConverter() );
		registerConverter( Integer.class, new IntegerConverter() );
		registerConverter( Long.class, new LongConverter() );
		registerConverter( Float.class, new FloatConverter() );
		registerConverter( Double.class, new DoubleConverter() );
		registerConverter( Boolean.class, new BooleanConverter() );
		registerConverter( Character.class, new CharConverter() );
		registerConverter( String.class, new StringConverter() );
	}

	/**
	 * Gets the converter associated with the specified type.
	 *
	 * @param clazz The type to get a converter for.
	 * @param <T>   The type to get a convert for.
	 *
	 * @return An appropriate converter or null if none could be found.
	 */
	@SuppressWarnings( "unchecked" )
	public static <T> IValueConverter<T> getConverter( Class<T> clazz ) {
		return (IValueConverter<T>) converterMap.get( clazz );
	}

	/**
	 * Registers the specified converter to be used whenever the specified type is encountered.
	 *
	 * @param clazz     The type to register the converter for.
	 * @param converter The converter to be used for conversion back and forth.
	 * @param <T>       The type to register the converter for.
	 */
	public static <T> void registerConverter( Class<T> clazz, IValueConverter<T> converter ) {
		converterMap.put( clazz, converter );
	}

}
