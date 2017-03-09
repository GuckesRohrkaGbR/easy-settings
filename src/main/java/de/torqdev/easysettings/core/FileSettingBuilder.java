package de.torqdev.easysettings.core;

import java.io.File;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class FileSettingBuilder {
    private File defaultFile;
    private String helpMessage;

    public Setting<File> build() {
        return new FileSetting(defaultFile, helpMessage);
    }

    public FileSettingBuilder defaultValue(File defaultFile) {
        this.defaultFile = defaultFile;
        return this;
    }

    public FileSettingBuilder withHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
        return this;
    }
}
