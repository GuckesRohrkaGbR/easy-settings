package de.torqdev.easysettings.core;

import java.io.File;

import static de.torqdev.easysettings.core.SettingType.FILE;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class FileSetting implements SettingContainer<File> {
    private final Setting<File> setting;

    protected FileSetting(final File defaultFile, final String helpMessage) {
        this.setting = new Setting<>(FILE, File.class, helpMessage);
        setValue(defaultFile);
    }

    @Override
    public Setting<File> getSetting() {
        return setting;
    }

    @Override
    public void setValue(final File value) {
        this.setting.setValue(value);
    }
}
