package de.torqdev.easysettings.core;

import java.io.File;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class FileSettingBuilder {
    public Setting<File> build() {
        return null;
    }

    public FileSettingBuilder defaultValue(File defaultFile) {
        return this;
    }
}
