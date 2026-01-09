package com.puttysoftware.gemma.game;

import java.io.IOException;

import org.retropipes.diane.fileio.XDataReader;
import org.retropipes.diane.fileio.XDataWriter;

import com.puttysoftware.gemma.support.creatures.PartyManager;

public class FileHooks {
    private FileHooks() {
	// Do nothing
    }

    public static void loadGameHook(XDataReader mapFile) throws IOException {
	PartyManager.loadGameHook(mapFile);
    }

    public static void saveGameHook(XDataWriter mapFile) throws IOException {
	PartyManager.saveGameHook(mapFile);
    }
}
