/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma;

import org.retropipes.diane.gui.dialog.CommonDialogs;
import org.retropipes.diane.integration.Integration;

import com.puttysoftware.gemma.prefs.PreferencesLauncher;
import com.puttysoftware.gemma.resourcemanagers.LogoManager;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.creatures.PartyManager;

public class Gemma {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "Gemma";
    public static final int GENERATOR_RANDOMNESS_MAX = 6;

    // Methods
    public static Application getApplication() {
	return Gemma.application;
    }

    public static void logError(final Throwable t) {
	Support.logError(t);
    }

    public static void logNonFatalError(final Throwable t) {
	Support.logNonFatalError(t);
    }

    public static String getProgramName() {
	return Gemma.PROGRAM_NAME;
    }

    public static void newScenario() {
	if (Support.getScenario() != null) {
	    Support.deleteScenario();
	    Gemma.application.getScenarioManager().setMap(null);
	}
	// Create scenario
	Support.createScenario();
	// Heal party
	PartyManager.revivePartyFully();
    }

    public static void main(final String[] args) {
	try {
	    String suffix;
	    if (Support.inDebugMode()) {
		suffix = " (DEBUG)";
	    } else {
		suffix = "";
	    }
	    Integration ni = Integration.integrate();
	    // Integrate with host platform
	    ni.configureLookAndFeel();
	    // Set defaults
	    CommonDialogs.setDefaultTitle(Gemma.PROGRAM_NAME + suffix);
	    CommonDialogs.setIcon(LogoManager.getMicroLogo());
	    // Initialization
	    Support.preInit();
	    Gemma.application = new Application();
	    Gemma.application.postConstruct();
	    Application.playLogoSound();
	    Gemma.application.getGUIManager().showGUI();
	    // Register platform hooks
	    ni.setAboutHandler(Gemma.application.getAboutDialog());
	    ni.setOpenFileHandler(Gemma.application.getScenarioManager());
	    ni.setPreferencesHandler(new PreferencesLauncher());
	    ni.setQuitHandler(Gemma.application.getGUIManager());
	} catch (Throwable t) {
	    Gemma.logError(t);
	}
    }
}
