/*
 * Copyright (c) BlackyPaw - All Rights Reserved
 * You may find the complete license inside LICENSE file in the root directory of this source.
 */
package me.compilable.bedwars.config.api;

import me.compilable.bedwars.config.api.converter.ReversionException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.ParseException;

/**
 * Any class may extend SimpleConfig in order to easily provide basic configuration functionality. It may then be
 * exposed as a config file and imported again later.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class SimpleConfig {

	/**
	 * Initializes the configuration from the specified file. If the file does not exist, it will be created.
	 *
	 * @param file The file to load the configuration from.
	 *
	 * @throws IOException Thrown in case that any I/O error occurs.
	 */
	public void initialize( File file ) throws IOException {
		if ( !file.exists() ) {
			try ( Writer writer = new FileWriter( file ) ) {
				this.write( writer );
			}
		} else {
			try ( Reader reader = new FileReader( file ) ) {
				this.read( reader );
			}
		}
	}

	/**
	 * Writes the configuration to the specified output writer.
	 *
	 * @param writer The writer to write the configuration data to.
	 *
	 * @throws IOException Thrown in case that any I/O error occurs.
	 */
	public void write( Writer writer ) throws IOException {
		FormatCompiler compiler = new FormatCompiler();
		String         source   = compiler.compile( this );
		writer.write( source );
	}

	/**
	 * Reads the configuration from the specified input reader.
	 *
	 * @param reader The reader to read the configuration data from.
	 *
	 * @throws IOException Thrown in case that any I/O error occurs.
	 */
	public void read( Reader reader ) throws IOException {
		FormatParser parser = new FormatParser( reader );
		try {
			parser.parse( this );
		} catch ( ParseException e ) {
			throw new IOException( "Failed to parse configuration", e );
		} catch ( ReversionException e ) {
			throw new IOException( "Failed to revert configuration", e );
		}
	}

}
