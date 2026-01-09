/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map;

import org.retropipes.diane.storage.ObjectStorage;

import com.puttysoftware.gemma.support.map.generic.MapObject;

class LowLevelDataStore extends ObjectStorage<MapObject> {
    // Constructor
    LowLevelDataStore(int... shape) {
	super(shape);
    }
    LowLevelDataStore(LowLevelDataStore source) {
	super(source);
    }

    // Methods
    public MapObject getMapCell(int... loc) {
	return this.getCell(loc);
    }

    public void setMapCell(MapObject obj, int... loc) {
	this.setCell(obj, loc);
    }
}
