package de.torqdev.easysettings.core;

import de.torqdev.easysettings.core.io.PropertiesHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public final class Settings {
    private final Map<String, Setting<?>> settingsMap = new LinkedHashMap<>();
    private final PropertiesHandler handler;

    public Settings() {
        this(null);
    }

    public Settings(final PropertiesHandler handler) {
        this.handler = handler;
    }

    public void addSetting(final String name, final Setting<?> setting) {
        settingsMap.put(name, setting);
    }

    @NotNull
    public Map<String, Setting<?>> getSettings() {
        return Collections.unmodifiableMap(settingsMap);
    }

    public <T> Setting<T> get(final String setting) {
        // TODO: make safe cast
        return (Setting<T>) getSettings().get(setting);
    }

    public void save() throws EasySettingsException {
        if (handler != null) {
            handler.saveSettingsToFile(this);
        }
    }
}
