package de.torqdev.easysettings.ui.javafx;

import de.torqdev.easysettings.core.*;
import de.torqdev.easysettings.core.converters.StringConverterUtil;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
//FIXME: Do the clean code dance
@SuppressWarnings("unchecked")
public class ConfigurationDialog extends Dialog<Settings> {
    private static final ResourceBundle STR = ResourceBundle.getBundle("i18n.EasySettings", Locale.getDefault());

    private final Settings settings;
    private final Map<String, Node> inputFields = new ConcurrentHashMap<>();

    public ConfigurationDialog(final Settings settings) {
        this(settings, STR.getString("configure"), null, null);
    }

    public ConfigurationDialog(final Settings settings, final String title, final String headerText, final ImageView icon) {
        super();
        this.settings = settings;

        setTitle(title);
        setHeaderText(headerText);
        setGraphic(icon);

        createButtons();
        populatePane();
    }

    private void createButtons() {
        final ButtonType saveButton = new ButtonType(STR.getString("save"), ButtonBar.ButtonData.APPLY);
        this.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        this.setResultConverter(buttonType -> {
            Settings myReturn = null;
            if (buttonType == saveButton) {
                myReturn = updateSettings();
            }
            return myReturn;
        });
    }

    @Contract("-> !null")
    private Settings updateSettings() {
        Settings myReturn;

        final Map<String, SettingType> types = settings.getSettingTypes();
        types.keySet().forEach(this::updateSetting);

        myReturn = settings;
        settings.save();

        return myReturn;
    }

    private void updateSetting(final String key) {
        final Node valueNode = inputFields.get(key);

        final SettingType type = settings.getSettingTypes().get(key);

        switch (type) {
            case RANGE:
                final Slider slider = (Slider) valueNode;
                settings.getRangeSetting(key).setValue(slider.getValue());
                break;
            case CHOICE:
                final ChoiceBox<?> choiceBox = (ChoiceBox<?>) valueNode;
                settings.getChoiceSetting(key).setValue(choiceBox.getValue());
                break;
            case FILE:
                final Label label = (Label) valueNode;
                settings.getFileSetting(key).setValue(new File(label.getText()));
                break;
            case MULTISELECT:
                break;
            default:
                final TextField textField = (TextField) valueNode;
                settings
                        .getUnboundedSetting(key)
                        .setValue(
                                textField.getText()
                        );
        }
    }

    private void populatePane() {
        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));

        createSettingsFields(grid);

        this.getDialogPane().setContent(grid);
    }

    private void createSettingsFields(final GridPane grid) {
        final Map<String, SettingType> settingTypeMap = settings.getSettingTypes();

        settingTypeMap.forEach((key, type) -> {
            switch (type) {
                case CHOICE:
                    addChoiceSetting(key, grid);
                    break;
                case RANGE:
                    addRangeSetting(key, grid);
                    break;
                case FILE:
                    addFileSetting(key, grid);
                    break;
                case MULTISELECT:
                    break;
                default:
                    addUnboundedSetting(key, grid);
            }
        });
    }

    private void addUnboundedSetting(final String key, final GridPane grid) {
        final UnboundedSetting setting = settings.getUnboundedSetting(key);
        final StringConverter converter = StringConverterUtil.getConverter(setting.getSetting().getValueType());
        final Label label = new Label(key);
        final TextField field = new TextField(converter.toString(setting.getValue()));
        inputFields.put(key, field);

        addRow(grid, label, field);
    }

    private void addFileSetting(final String key, final GridPane grid) {
        final FileSetting setting = settings.getFileSetting(key);
        final StringConverter<File> converter = StringConverterUtil.getConverter(File.class);
        final Label label = new Label(key);
        final HBox hbox = new HBox(5);
        final Label fileLabel = new Label(converter.toString(setting.getValue()));
        final Button openFileButton = new Button(STR.getString("open.file"));

        inputFields.put(key, fileLabel);

        openFileButton.setOnAction(event -> {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(STR.getString("open.file"));

            fileLabel.textProperty().setValue(fileChooser.showOpenDialog(this.getOwner()).getAbsolutePath());
        });

        hbox.getChildren().addAll(fileLabel, openFileButton);
        addRow(grid, label, hbox);
    }

    private void addChoiceSetting(final String key, final GridPane grid) {
        final ChoiceSetting setting = settings.getChoiceSetting(key);
        final Label label = new Label(key);
        final ChoiceBox choiceBox = new ChoiceBox();
        inputFields.put(key, choiceBox);

        choiceBox.getItems().addAll(setting.getChoices());
        choiceBox.setValue(setting.getValue());

        addRow(grid, label, choiceBox);
    }

    private void addRangeSetting(final String key, final GridPane grid) {
        final RangeSetting setting = settings.getRangeSetting(key);
        final Label label = new Label(key);

        final Number min = setting.getMin();
        final Number max = setting.getMax();
        final Number current = (Number) setting.getValue();

        final Slider slider = new Slider(min.doubleValue(), max.doubleValue(), current.doubleValue());
        inputFields.put(key, slider);

        slider.setShowTickLabels(true);
        final Label valueLabel = new Label();
        valueLabel.textProperty().bindBidirectional(slider.valueProperty(), new NumberStringConverter());
        final HBox hbox = new HBox(5);

        hbox.getChildren().addAll(slider, valueLabel);

        addRow(grid, label, hbox);
    }

    private void addRow(final GridPane grid, final Node... nodes) {
        grid.addRow(getNextAvailableRow(grid), nodes);
    }

    private int getNextAvailableRow(final GridPane grid) {
        // Thats what happens when you make functions private that should be public.
        int myReturn;
        Method method;
        try {
            method = grid.getClass().getDeclaredMethod("getNumberOfRows");
            method.setAccessible(true);
            myReturn = (Integer) method.invoke(grid);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new EasySettingsException(e);
        }
        return myReturn;
    }

    private void addToolTipIfPresent(final Node myReturn, final Setting<?> setting) {
//        if (!setting.getHelpMessage().isEmpty()) {
//            final Tooltip tooltip = new Tooltip(setting.getHelpMessage());
//            Tooltip.install(myReturn, tooltip);
//        }
    }
}
