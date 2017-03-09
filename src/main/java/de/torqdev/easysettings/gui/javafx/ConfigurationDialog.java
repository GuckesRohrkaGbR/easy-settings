package de.torqdev.easysettings.gui.javafx;

import de.torqdev.easysettings.core.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.converter.NumberStringConverter;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class ConfigurationDialog extends Dialog<Settings> {
    private static final ResourceBundle STR = ResourceBundle.getBundle("i18n.EasySettings", Locale.getDefault());

    private final Settings settings;
    private final Map<String, Node> inputFields = new ConcurrentHashMap<>();

    public ConfigurationDialog(final Settings settings) {
        this(settings, STR.getString("configure"), null, null);
    }

    public ConfigurationDialog(final Settings settings, String title, String headerText, ImageView icon) {
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

        final Map<String, Setting<?>> settingsMap = settings.getSettings();
        settingsMap.keySet().forEach(key -> updateSetting(key, settingsMap));

        myReturn = settings;
        settings.save();

        return myReturn;
    }

    private void updateSetting(String key, Map<String, Setting<?>> settingsMap) {
        final Node valueNode = inputFields.get(key);

        final Setting keySetting = settingsMap.get(key);
        switch (keySetting.getSettingType()) {
            case RANGE:
                final Slider slider = (Slider) ((HBox) valueNode).getChildrenUnmodifiable().get(0);
                keySetting.setValue(slider.getValue());
                break;
            case CHOICE:
                final ChoiceBox<?> choiceBox = (ChoiceBox<?>) valueNode;
                keySetting.setValue(choiceBox.getValue());
                break;
            case FILE:
                final Label label = (Label) ((HBox) valueNode).getChildrenUnmodifiable().get(0);
                keySetting.setValue(new File(label.getText()));
                break;
            case UNBOUNDED:
                final TextField textField = (TextField) valueNode;
                keySetting.setFromStringValue(textField.getText());
                break;
            default:
                break;
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
        final int[] counter = {0};
        final Map<String, Setting<?>> settingsMap = settings.getSettings();

        settingsMap.keySet().forEach(key -> {
            grid.add(new Label(key + ":"), 0, counter[0]);
            grid.add(addControlForSetting(key, settingsMap.get(key)), 1, counter[0]);
            counter[0]++;
        });
    }

    @Contract("_, _ -> !null")
    private <T> Node addControlForSetting(final String key, final Setting<T> setting) {
        final Node myReturn;

        switch (setting.getSettingType()) {
            case RANGE:
                myReturn = createRangeInputNode((RangeSetting) setting);
                break;
            case CHOICE:
                myReturn = createChoiceInputNode(setting);
                break;
            case FILE:
                myReturn = createFileInputNode((FileSetting) setting);
                break;
            default:
                myReturn = createTextInputNode(setting);
                break;
        }

        addToolTipIfPresent(myReturn, setting);
        inputFields.put(key, myReturn);
        return myReturn;
    }

    private void addToolTipIfPresent(final Node myReturn, final Setting<?> setting) {
        if (!setting.getHelpMessage().isEmpty()) {
            final Tooltip tooltip = new Tooltip(setting.getHelpMessage());
            Tooltip.install(myReturn, tooltip);
        }
    }

    @Contract("_ -> !null")
    private Node createTextInputNode(final Setting<?> setting) {
        return new TextField(setting.getValue().toString());
    }

    @Contract("_ -> !null")
    private <T> Node createChoiceInputNode(final Setting<T> setting) {
        final ChoiceBox<T> choiceBox = new ChoiceBox<>();

        choiceBox.getItems().addAll(((ChoiceSetting<T>) setting).getChoices());
        choiceBox.setValue(setting.getValue());

        return choiceBox;
    }

    @Contract("_ -> !null")
    private <T extends Number> Node createRangeInputNode(final Setting<T> setting) {
        Node myReturn = createTextInputNode(setting);
        if (Double.class.isAssignableFrom(setting.getValueType())) {
            RangeSetting<T> rangeSetting = (RangeSetting<T>) setting;
            final Number min = rangeSetting.getMin();
            final Number max = rangeSetting.getMax();
            final Number current = setting.getValue();

            final Slider slider = new Slider(min.doubleValue(), max.doubleValue(), current.doubleValue());
            slider.setShowTickLabels(true);
            final Label valueLabel = new Label();
            valueLabel.textProperty().bindBidirectional(slider.valueProperty(), new NumberStringConverter());
            final HBox hbox = new HBox(5);

            hbox.getChildren().addAll(slider, valueLabel);

            myReturn = hbox;
        }

        return myReturn;
    }

    private Node createFileInputNode(final Setting<File> setting) {
        final HBox myReturn = new HBox(5);

        final Label fileLabel = new Label(setting.getValue().toString());
        final Button openFileButton = new Button(STR.getString("open.file"));

        openFileButton.setOnAction(event -> {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(STR.getString("open.file"));

            fileLabel.textProperty().setValue(fileChooser.showOpenDialog(this.getOwner()).getAbsolutePath());
        });

        myReturn.getChildren().addAll(fileLabel, openFileButton);
        return myReturn;
    }
}
