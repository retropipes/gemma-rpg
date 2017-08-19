/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.gemma.prefs.PreferencesManager;
import com.puttysoftware.gemma.resourcemanagers.LogoManager;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.creatures.PartyManager;
import com.puttysoftware.platform.Platform;

public class Gemma {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "Gemma";
    public static final int GENERATOR_RANDOMNESS_MAX = 6;

    // Methods
    public static Application getApplication() {
        return Gemma.application;
    }

    public static ErrorLogger getErrorLogger() {
        return Support.getErrorLogger();
    }

    public static ErrorLogger getNonFatalLogger() {
        return Support.getNonFatalLogger();
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
            // Integrate with host platform
            Platform.hookLAF(Gemma.getProgramName());
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
            Platform.hookAbout(Gemma.application.getAboutDialog(),
                    Gemma.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            Platform.hookFileOpen(Gemma.application.getScenarioManager(),
                    Gemma.application.getScenarioManager().getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class),
                    s);
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(Gemma.application.getGUIManager(),
                    Gemma.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            Platform.hookDockIcon(LogoManager.getLogo());
        } catch (Throwable t) {
            Gemma.getErrorLogger().logError(t);
        }
    }
}