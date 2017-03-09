package de.torqdev.easysettings.core;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class SettingBuilder {
    public <T> UnboundedSettingBuilder<T> unboundedSetting() {
        return new UnboundedSettingBuilder<>();
    }

    public <T> ChoiceSettingBuilder<T> choiceSetting() {
        return new ChoiceSettingBuilder<>();
    }

    public <T extends Comparable<T>> RangeSettingBuilder<T> rangeSetting() {
        return new RangeSettingBuilder<>();
    }

    public FileSettingBuilder fileSetting() {
        return new FileSettingBuilder();
    }
}
