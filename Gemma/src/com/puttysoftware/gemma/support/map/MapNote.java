/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map;

import java.io.IOException;

import org.retropipes.diane.fileio.XDataReader;
import org.retropipes.diane.fileio.XDataWriter;

public class MapNote {
    // Fields
    private String contents;

    // Constructor
    public MapNote() {
	this.contents = "Empty Note";
    }

    public MapNote(final MapNote source) {
	this.contents = source.contents;
    }

    // Methods
    public String getContents() {
	return this.contents;
    }

    public void setContents(String newContents) {
	this.contents = newContents;
    }

    static MapNote readNote(XDataReader reader) throws IOException {
	MapNote mn = new MapNote();
	mn.contents = reader.readString();
	return mn;
    }

    void writeNote(XDataWriter writer) throws IOException {
	writer.writeString(this.contents);
    }
}
