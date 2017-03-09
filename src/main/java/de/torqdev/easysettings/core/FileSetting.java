package de.torqdev.easysettings.core;

import java.io.File;

import static de.torqdev.easysettings.core.SettingType.FILE;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class FileSetting extends Setting<File> {
    protected FileSetting(File defaultFile, String helpMessage) {
        super(FILE, File.class, helpMessage);
        setValue(defaultFile);
    }
}
