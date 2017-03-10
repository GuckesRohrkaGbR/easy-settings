package de.torqdev.easysettings.core;

import java.util.Map;

/**
 * This interface is meant to be the main connection to your application.
 *
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public interface Settings {
    /**
     * Returns the value associated with the specified key.
     *
     * @param key the name of the setting you want to query
     * @param <T> the type of value you want to
     * @return the value stored in the setting with the specified key
     */
    <T> Setting<T> getSetting(String key);

    /**
     * Returns the {@link UnboundedSetting} with the specified key.
     *
     * @param key the name of the setting you want to query
     * @param <T> The value type you want to fetch
     * @return the setting with the specified key
     */
    <T> UnboundedSetting<T> getUnboundedSetting(String key);

    /**
     * Adds an {@link UnboundedSetting} to the settings container.
     *
     * @param key     the key to store the value to
     * @param setting the {@link UnboundedSetting} you want to store
     * @param <T>     the type of value the setting should contain
     */
    <T> void addUnboundedSetting(String key, UnboundedSetting<T> setting);

    /**
     * Returns the {@link ChoiceSetting} with the specified key.
     *
     * @param key the name of the setting you want to query
     * @param <T> The value type you want to fetch
     * @return the setting with the specified key
     */
    <T> ChoiceSetting<T> getChoiceSetting(String key);

    /**
     * Adds an {@link ChoiceSetting} to the settings container.
     *
     * @param key     the key to store the value to
     * @param setting the {@link ChoiceSetting} you want to store
     * @param <T>     the type of value the setting should contain
     */
    <T> void addChoiceSetting(String key, ChoiceSetting<T> setting);

    /**
     * Returns the {@link RangeSetting} with the specified key.
     *
     * @param key the name of the setting you want to query
     * @param <T> The value type you want to fetch
     * @return the setting with the specified key
     */
    <T extends Number> RangeSetting<T> getRangeSetting(String key);

    /**
     * Adds an {@link RangeSetting} to the settings container.
     *
     * @param key     the key to store the value to
     * @param setting the {@link RangeSetting} you want to store
     * @param <T>     the type of value the setting should contain
     */
    <T extends Number> void addRangeSetting(String key, RangeSetting<T> setting);

    /**
     * Returns the {@link FileSetting} with the specified key.
     *
     * @param key the name of the setting you want to query
     * @return the {@link FileSetting} with the specified key
     */
    FileSetting getFileSetting(String key);

    /**
     * Adds an {@link FileSetting} to the settings container.
     *
     * @param key     the key to store the value to
     * @param setting the {@link FileSetting} you want to store
     */
    void addFileSetting(String key, FileSetting setting);

    /**
     * Returns the {@link MultiselectSetting} with the specified key.
     *
     * @param key the name of the setting you want to query
     * @param <T> The value type you want to fetch
     * @return the setting with the specified key
     */
    <T> MultiselectSetting<T> getMultiselectSetting(String key);

    /**
     * Adds an {@link MultiselectSetting} to the settings container.
     *
     * @param key     the key to store the value to
     * @param setting the {@link MultiselectSetting} you want to store
     * @param <T>     the type of value the setting should contain
     */
    <T> void addMultiselectSetting(String key, MultiselectSetting<T> setting);

    /**
     * This function allows you to get all setting types by key. That way you can decide yourself,
     * which function you want to call. This is mainly to be used for user interfaces, not so much your business logic.
     *
     * @return all setting types by key
     */
    Map<String, SettingType> getSettingTypes();

    /**
     * Saves all settings stored in this container to the persistence layer.
     */
    void save();

    /**
     * Loads all settings from the persistence layer and updates the provided default values. This should be run after
     * all settings have been added.
     */
    void load();
}
