/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.application;

import static com.ptc.services.common.tools.DateUtil.addDays;
import integrityfit.Config;
import static integrityfit.Config.dfDay;
import static integrityfit.Config.dfDayTime;
import static integrityfit.Config.dfTime;
import integrityfit.IntegrityTransactions;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.util.Duration;
import jfx.messagebox.MessageBox;
// import jfx.messagebox.MessageBox;

/**
 * The controller for the small STeF Retriever application
 *
 * @author veckardt
 */
public class TestAppController implements Initializable {

    static FadeTransition messageTransition = null;
    @FXML
    static Label messageBar;
    @FXML
    private ChoiceBox fldMin, targetDir, fldFromDay, fldToDay;
    @FXML
    private TextField fldFromTime, fldToTime;
    @FXML
    private RadioButton actOption1, actOption2, actOption3;
    @FXML
    private ToggleGroup tGroup;
    @FXML
    private TextField fldQueryName, fldHostName, fldItemIDs, fldPort;
    @FXML
    private CheckBox overwriteFlag, closeFlag;
    @FXML
    private TextArea logData;
    static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
    private static Integer loglevel = 5;
    private ObservableList<String> targetDirOptions =
            FXCollections.observableArrayList();
    // Application Property File
    Config cfg = new Config();

    @FXML
    private void exitForm(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void genFixture(ActionEvent event) {

        int option = 0;

        putLog(5, "GenFixture started ...");

        // SimpleDateFormat dtDayTime = new SimpleDateFormat(cfg.dtDayTimeFormat, cfg.locale);

        Date endDate = null;
        Date startDate = null;
        // putLog(5, tGroup.getSelectedToggle().toString());

        // if option 1 is selected
        if (tGroup.getSelectedToggle().toString().contains("actOption1")) { // log ("Error: Invalid selection!");
            option = 1;
            startDate = null;
            endDate = null;
            // if option 2 is selected
        } else if (tGroup.getSelectedToggle().toString().contains("actOption2")) {
            option = 2;
            endDate = new Date();
            long t = endDate.getTime();
            long min = Long.parseLong(fldMin.getValue().toString());
            startDate = new Date(t - (min * ONE_MINUTE_IN_MILLIS));
            putLog(5, "2: Start Date: " + startDate.toString());
            putLog(5, "2: End Date  : " + endDate.toString());

            // if option 3 is selected
        } else if (tGroup.getSelectedToggle().toString().contains("actOption3")) {
            option = 3;
            try {
                if (!checkRequiredFieldTF("Generation: From Time", fldFromTime)) {
                    return;
                }
                if (!checkRequiredFieldTF("Generation: To Time", fldToTime)) {
                    return;
                }
                putLog(5, "3: Start Date: " + fldFromDay.getValue().toString());
                putLog(5, "3: End Date: " + fldToDay.getValue().toString());
                
                // log(fldFromDay.getValue().toString()+" "+fldFromTime.getText());
                startDate = dfDayTime.parse(fldFromDay.getValue().toString() + " " + fldFromTime.getText());
                endDate = dfDayTime.parse(fldToDay.getValue().toString() + " " + fldToTime.getText());

                if (startDate.after(endDate)) {
                    MessageBox.show(TestApp.stage,
                            "The 'from' date can not be after the 'to' date, please correct!",
                            "Error",
                            MessageBox.ICON_ERROR);
                    return;
                }
                putLog(5, "3: Start Date: " + startDate.toString());
                putLog(5, "3: End Date: " + endDate.toString());

            } catch (ParseException ex) {
                Logger.getLogger(TestAppController.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }
        putLog(5, "Selected Option is " + option);

        if (!checkRequiredFieldTF("Config: Query Name", fldQueryName)) {
            return;
        }
        if (!checkRequiredFieldTF("Config: Hostname", fldHostName)) {
            return;
        }
        if (!checkRequiredFieldTF("Config: Port", fldPort)) {
            return;
        }

        if (!checkRequiredFieldCB("Generation: Target Fit Directory", targetDir)) {
            return;
        }

        displayMessage("Initialize Test Case generation ...");
        IntegrityTransactions ia = new IntegrityTransactions(
                startDate,
                endDate,
                fldQueryName.getText(),
                fldHostName.getText(),
                fldPort.getText(),
                fldItemIDs.getText());
        displayMessage("Test Case generation in progress ...");
        if (ia.readHistoryAndGenFitNesseTc(cfg.fitBasePath + targetDir.getValue() + "\\content.txt", overwriteFlag.isSelected(), option)) {
            if (ia.getNumberOfChangesIdentified() == 0) {
                MessageBox.show(TestApp.stage,
                        "Test Case generation aborted, no data found!\n\nPlease check your settings and/or the query definition for '" + fldQueryName.getText() + "'.",
                        "Error dialog",
                        MessageBox.ICON_ERROR);
            } else {
                displayMessage("Test Case generation completed for " + ia.getNumberOfChangesIdentified() + " change sets successfully.");
                saveConfig();
                if (closeFlag.isSelected()) {
                    System.exit(0);
                }
            }
        } else if (!overwriteFlag.isSelected()) // displayMessage("Test Case generation aborted, test file might exist?");
        {
            MessageBox.show(TestApp.stage,
                    "Test Case generation aborted, test file might exist?",
                    "Error dialog",
                    MessageBox.ICON_ERROR);
        } else {
            MessageBox.show(TestApp.stage,
                    "Test Case generation aborted!",
                    "Error dialog",
                    MessageBox.ICON_ERROR);
        }
        putLog(5, "GenFixture completed.");
    }

    private Boolean checkRequiredFieldTF(String fieldName, TextField textField) {
        if (textField.getText() == null || textField.getText().isEmpty()) {
            MessageBox.show(TestApp.stage,
                    "Please enter a value into the field '" + fieldName + "'!",
                    "Error",
                    MessageBox.ICON_ERROR);
            return false;
        }
        return true;
    }

    private Boolean checkRequiredFieldCB(String fieldName, ChoiceBox choiceBox) {
        if (choiceBox.getValue() == null || choiceBox.getValue().toString().isEmpty()) {
            MessageBox.show(TestApp.stage,
                    "Please enter a value into the field '" + fieldName + "'!",
                    "Error",
                    MessageBox.ICON_ERROR);
            return false;
        }
        return true;
    }

    private void setTextField(TextField textfield, String property, String defaultValue) {
        textfield.setText(defaultValue);
        if (cfg.Get(property) != null) {
            textfield.setText(cfg.Get(property));
        }
    }

    private void scanPath(File dir) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    String path = file.getCanonicalPath().replace(cfg.fitBasePath, "");
                    if (!path.contains("SetUp")) {
                        targetDirOptions.add(path);
                    }
                    scanPath(file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Is called before the form opens
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // SimpleDateFormat dtDayFormat = new SimpleDateFormat(Config.dtDayFormat, cfg.locale);
        // SimpleDateFormat dtTime = new SimpleDateFormat(Config.dtTimeFormat, cfg.locale);
        Date now = new Date();

        // check if objects defined in TestApp.fxml
        assert messageBar != null : "fx:id=\"messageBar\" was not injected: check your FXML file 'TestApp.fxml'.";
        assert overwriteFlag != null : "fx:id=\"overwriteFlag\" was not injected: check your FXML file 'TestApp.fxml'.";
        assert logData != null : "fx:id=\"messageBar\" was not injected: check your FXML file 'TestApp.fxml'.";
        assert closeFlag != null : "fx:id=\"messageBar\" was not injected: check your FXML file 'TestApp.fxml'.";
        assert actOption1 != null : "fx:id=\"actOption1\" was not injected: check your FXML file 'TestApp.fxml'.";
        assert actOption2 != null : "fx:id=\"actOption2\" was not injected: check your FXML file 'TestApp.fxml'.";
        assert actOption3 != null : "fx:id=\"actOption3\" was not injected: check your FXML file 'TestApp.fxml'.";

        displayMessage("Initializing field values ...");
        // Field 1:  Minutes Field
        ObservableList<String> optionsMin =
                FXCollections.observableArrayList();
        for (int i = 1; i <= 25; i++) {
            optionsMin.add(Integer.toString(i));
        }
        fldMin.setItems(optionsMin);
        fldMin.getSelectionModel().select("5");
        if (cfg.Get("Minutes") != null) {
            fldMin.getSelectionModel().select(cfg.Get("Minutes"));
        }

        // load another base path if set
        if (cfg.Get("FitnesseBasePath") != null) {
            cfg.fitBasePath = cfg.Get("FitnesseBasePath");
        }

        // Field 2: targetDir
        File file = new File(cfg.fitBasePath);
        scanPath(file);

        // Field: targetDir
        targetDir.setItems(targetDirOptions);
        targetDir.getSelectionModel().select(2);
        if (cfg.Get("TargetDir") != null) {
            targetDir.getSelectionModel().select(cfg.Get("TargetDir"));
        }

        // Field 3: fldFromDay
        String fromDay = cfg.Get("FromDay");
        if (fromDay == null) {
            fromDay = dfDay.format(new Date());
        }
        
        int idFromDay = -1;
        ObservableList<String> optFromDay =
                FXCollections.observableArrayList();
        for (int i = 6; i >= 0; i--) {
            optFromDay.add(dfDay.format(addDays(now, -i)));
            if (fromDay.contentEquals(dfDay.format(addDays(now, -i)))) {
                idFromDay = 6-i;
            }
        }
        fldFromDay.setItems(optFromDay);
        fldFromDay.getSelectionModel().select(dfDay.format(now));
        if (idFromDay>-1) {
            fldFromDay.getSelectionModel().select(idFromDay);
        }

        // Field 4: To Day 
        String toDay = cfg.Get("ToDay");
        if (toDay == null) {
            toDay = dfDay.format(new Date());
        }
        int idToDay = -1;        
        ObservableList<String> optToDay =
                FXCollections.observableArrayList();
        for (int i = 6; i >= 0; i--) {
            optToDay.add(dfDay.format(addDays(now, -i)));
            if (toDay.contentEquals(dfDay.format(addDays(now, -i)))) {
                idToDay = 6-i;
            }            
        }
        fldToDay.setItems(optToDay);
        fldToDay.getSelectionModel().select(dfDay.format(now));
        if (idToDay>-1) {
            fldToDay.getSelectionModel().select(idToDay);
        }

        // Field 5: FromTime/ToTime
        setTextField(fldFromTime, "FromTime", dfTime.format(now));
        setTextField(fldToTime, "ToTime", dfTime.format(now));

        // Field 6: actOption
        if (cfg.Get("Option") != null) {
            String option = cfg.Get("Option");
            if (option.contains("Option1")) {
                actOption1.setSelected(true);
                actOption2.setSelected(false);
                actOption3.setSelected(false);
            } else if (option.contains("Option2")) {
                actOption1.setSelected(false);
                actOption2.setSelected(true);
                actOption3.setSelected(false);
            } else {
                actOption1.setSelected(false);
                actOption2.setSelected(false);
                actOption3.setSelected(true);
            }
        }
        // More fields
        setTextField(fldQueryName, "QueryName", "HistoryFilter");
        setTextField(fldHostName, "HostName", "localhost");
        setTextField(fldPort, "Port", "7001");
        setTextField(fldItemIDs, "ItemIDs", "");

        // Close Flag
        if (cfg.Get("CloseFlag") != null) {
            String option = cfg.Get("CloseFlag");
            closeFlag.setSelected(option.contentEquals("true"));
        }
        displayMessage("Please select the appropriate options ...");
    }

    /**
     * Save Variables into properties files
     */
    public void saveConfig() {
        cfg.Set("FitnesseBasePath", cfg.fitBasePath);
        cfg.Set("TargetDir", targetDir.getValue().toString());
        cfg.Set("Minutes", fldMin.getValue().toString());
        cfg.Set("FromDay", fldFromDay.getValue().toString());
        cfg.Set("ToDay", fldToDay.getValue().toString());
        cfg.Set("FromTime", fldFromTime.getText());
        cfg.Set("ToTime", fldToTime.getText());
        if (tGroup.getSelectedToggle().toString().contains("actOption1")) {
            cfg.Set("Option", "Option1");
        } else if (tGroup.getSelectedToggle().toString().contains("actOption2")) {
            cfg.Set("Option", "Option2");
        } else {
            cfg.Set("Option", "Option3");
        }
        cfg.Set("QueryName", fldQueryName.getText());
        cfg.Set("HostName", fldHostName.getText());
        cfg.Set("Port", fldPort.getText());
        cfg.Set("ItemIDs", fldItemIDs.getText());
        cfg.Set("CloseFlag", Boolean.toString(closeFlag.isSelected()));

        cfg.Save();

        // 
    }

    /**
     * Displays a short message in the message bar
     *
     * @param message
     */
    public static void displayMessage(String message) {
        if (messageBar != null) {
            if (messageTransition != null) {
                messageTransition.stop();
            } else {
                messageTransition = new FadeTransition(Duration.millis(3000), messageBar);
                messageTransition.setFromValue(1.0);
                messageTransition.setToValue(0.0);
                messageTransition.setDelay(Duration.millis(4000));
                messageTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        messageBar.setVisible(false);
                    }
                });
            }
            messageBar.setText(message);
            messageBar.setVisible(true);
            messageBar.setOpacity(1.0);
            messageTransition.playFromStart();
        }
    }

    
    public void putLog(Integer level, String string) {
        
        if (loglevel >= level) {
            if (logData!= null)
                logData.setText(logData.getText() + "\n" + string);
            else
                logData.setText(string);
            System.out.println(">> " + string);
        }
    }
}
