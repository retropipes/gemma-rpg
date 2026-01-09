package com.puttysoftware.gemma.prefs;

import java.awt.desktop.PreferencesEvent;
import java.awt.desktop.PreferencesHandler;

public class PreferencesLauncher implements PreferencesHandler {
    @Override
    public void handlePreferences(PreferencesEvent e) {
	PreferencesManager.showPrefs();
    }
}
