/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map;

import org.retropipes.diane.storage.ObjectStorage;

class LowLevelNoteDataStore extends ObjectStorage<MapNote> {
    // Constructor
    LowLevelNoteDataStore(int... shape) {
	super(shape);
    }
    LowLevelNoteDataStore(LowLevelNoteDataStore source) {
	super(source);
    }

    // Methods
    public MapNote getNote(int... loc) {
	return this.getCell(loc);
    }

    public void setNote(MapNote obj, int... loc) {
	this.setCell(obj, loc);
    }
}
