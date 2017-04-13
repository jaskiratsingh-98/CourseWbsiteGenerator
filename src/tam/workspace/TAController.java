package tam.workspace;

import djf.components.AppDataComponent;
import djf.controller.AppFileController;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_TITLE;
import static djf.settings.AppPropertyType.SAVE_WORK_TITLE;
import static djf.settings.AppPropertyType.WORK_FILE_EXT;
import static djf.settings.AppPropertyType.WORK_FILE_EXT_DESC;
import static djf.settings.AppStartupConstants.PATH_WORK;
import djf.ui.AppGUI;
import static tam.CSGProp.*;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import tam.CSGApp;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.style.TAStyle;
import static tam.style.TAStyle.CLASS_HIGHLIGHTED_GRID_CELL;
import static tam.style.TAStyle.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static tam.style.TAStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;
import tam.workspace.CSGWorkspace;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import tam.file.TAFiles;
import tam.file.TimeSlot;
import tam.transaction.*;
import tam.CSGProp.*;

/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file toolbar.
 *
 * @author Richard McKenna
 * @version 1.0
 */
public class TAController {

    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    CSGApp app;
    static jTPS jTPS;

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Constructor, note that the app must already be constructed.
     */
    public TAController(CSGApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        jTPS = new jTPS();
    }

    /**
     * This helper method should be called every time an edit happens.
     */
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }

    public void handleEditTA() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTATab().getTATable();

        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        TeachingAssistant ta = (TeachingAssistant) selectedItem;

        TAData data = (TAData) app.getDataComponent();

        TextField nameTextField = workspace.getTATab().getNameTextField();
        TextField emailTextField = workspace.getTATab().getEmailTextField();

        Button addButton = workspace.getTATab().getAddButton();

        if (ta.getName() != null) {
            nameTextField.setText(ta.getName());
            emailTextField.setText(ta.getEmail());
            addButton.setText("Update");

            addButton.setOnAction(e -> {
                updateTA(ta);
            });

            nameTextField.setOnAction(e -> {
                updateTA(ta);
            });

            emailTextField.setOnAction(e -> {
                updateTA(ta);
            });

//            workspace.getClearButton().setOnAction(e -> {
//                clear();
//                return;
//            });
        }
    }

    public void updateTA(TeachingAssistant ta) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField nameTextField = workspace.getTATab().getNameTextField();
        TextField emailTextField = workspace.getTATab().getEmailTextField();
        String origName = ta.getName();
        String editName = nameTextField.getText();
        String editEmail = emailTextField.getText();
        TableView table = workspace.getTATab().getTATable();

        TAData data = (TAData) app.getDataComponent();

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        jTPS_Transaction updateTA = new UpdateTA_Transaction(origName, ta.getEmail(), editName, editEmail, data, workspace, ta);

        if (editName.equals(ta.getName()) && editEmail.equals(ta.getEmail())) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(TA_NOT_CHANGED_TITLE), props.getProperty(TA_NOT_CHANGED_MESSAGE));
        } else if (editName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
        } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (editEmail.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
        } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTAName(editName, editEmail)) {
            if (data.containsTAEmail(editName, editEmail)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
            } else if (validateEmailAddress(editEmail) == false) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(EMAIL_ADDRESS_NOT_VALID), props.getProperty(EMAIL_ADDRESS_NOT_VALID_MESSAGE));
            } else {
                if (validateEmailAddress(editEmail) == true) {
                    // ADD THE NEW TA TO THE DATA
                    jTPS.addTransaction(updateTA);
                }

                // CLEAR THE TEXT FIELDS
                nameTextField.setText("");
                emailTextField.setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                nameTextField.requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
                table.refresh();
            }
        } else if (data.containsTAEmail(editName, editEmail)) {
            if (data.containsTAName(editName, editEmail)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
            } else if (validateEmailAddress(editEmail) == false) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(EMAIL_ADDRESS_NOT_VALID), props.getProperty(EMAIL_ADDRESS_NOT_VALID_MESSAGE));
            } else {
                if (validateEmailAddress(editEmail) == true) {
                    // ADD THE NEW TA TO THE DATA
                    jTPS.addTransaction(updateTA);
                }

                // CLEAR THE TEXT FIELDS
                nameTextField.setText("");
                emailTextField.setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                nameTextField.requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
                table.refresh();
            }
        }
    }

    /**
     * This method responds to when the user requests to add a new TA via the
     * UI. Note that it must first do some validation to make sure a unique name
     * and email address has been provided.
     */
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField nameTextField = workspace.getTATab().getNameTextField();
        TextField emailTextField = workspace.getTATab().getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = (TAData) app.getDataComponent();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        jTPS_Transaction taTrans = new AddTA_Transaction(name, email, data);

        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
        } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
        } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
        } // EVERYTHING IS FINE, ADD A NEW TA
        else if (validateEmailAddress(email) == false) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(EMAIL_ADDRESS_NOT_VALID), props.getProperty(EMAIL_ADDRESS_NOT_VALID_MESSAGE));
        } else {
            if (validateEmailAddress(email) == true) {
                // ADD THE NEW TA TO THE DATA
                jTPS.addTransaction(taTrans);

            }

            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    private boolean validateEmailAddress(String email) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        pattern = Pattern.compile(EMAIL_PATTERN);

        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * This function provides a response for when the user presses a keyboard
     * key. Note that we're only responding to Delete, to remove a TA.
     *
     * @param code The keyboard code pressed.
     */
    public void handleKeyPress(KeyCode code) {

        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            // GET THE TABLE
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            TableView taTable = workspace.getTATab().getTATable();

            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                TeachingAssistant ta = (TeachingAssistant) selectedItem;
                String taName = ta.getName();
                TAData data = (TAData) app.getDataComponent();

                jTPS_Transaction delTATrans = new DeleteTA_Transaction(ta.getName(), ta.getEmail(), data, workspace);
                jTPS.addTransaction(delTATrans);
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }

    public void handleUndoRedo(KeyCode key, KeyEvent e) {

        final KeyCombination ctrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_ANY);
        final KeyCombination ctrlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_ANY);

        if (ctrlZ.match(e)) {
            jTPS.undoTransaction();
            e.consume();
        } else if (ctrlY.match(e)) {
            jTPS.doTransaction();
            e.consume();
        }
    }

    /**
     * This function provides a response for when the user clicks on the office
     * hours grid to add or remove a TA to a time slot.
     *
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTATab().getTATable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant) selectedItem;
            String taName = ta.getName();
            TAData data = (TAData) app.getDataComponent();
            String cellKey = pane.getId();

            jTPS_Transaction togTrans = new ToggleCell(cellKey, taName, data);

            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            jTPS.addTransaction(togTrans);

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    void handleGridCellMouseExited(Pane pane) {
        String cellKey = pane.getId();
        TAData data = (TAData) app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        Pane mousedOverPane = workspace.getTATab().getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getTATab().getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getTATab().getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getTATab().getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTATab().getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTATab().getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }
    }

    void handleGridCellMouseEntered(Pane pane) {
        String cellKey = pane.getId();
        TAData data = (TAData) app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        // THE MOUSED OVER PANE
        Pane mousedOverPane = workspace.getTATab().getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_CELL);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getTATab().getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getTATab().getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getTATab().getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTATab().getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTATab().getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }
    }

    public void clear() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTATab().getTATable();
        taTable.getSelectionModel().clearSelection();
        workspace.getTATab().getNameTextField().setText("");
        workspace.getTATab().getEmailTextField().setText("");

        workspace.getTATab().getAddButton().setText("Add TA");

        workspace.getTATab().getNameTextField().requestFocus();

        workspace.getTATab().getNameTextField().setOnAction(e -> {
            handleAddTA();
        });
        workspace.getTATab().getEmailTextField().setOnAction(e -> {
            handleAddTA();
        });
        workspace.getTATab().getAddButton().setOnAction(e -> {
            handleAddTA();
        });
    }

    public void handleStartTime() throws IOException {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ComboBox startComboBox = workspace.getTATab().getStartComboBox();
        TAData data = (TAData) app.getDataComponent();
        TAFiles file = (TAFiles) app.getFileComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(data);

        String start = (String) startComboBox.getValue();
        int startTime = (Integer.parseInt(start.substring(0, start.indexOf(":"))));

        jTPS_Transaction changeStart = new ChangeStartTime_Transaction(startTime, data.getStartHour(), officeHours, data, workspace);

        if (startTime > data.getStartHour()) {
            if (startTime >= data.getEndHour()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(INVALID_START_HOUR_TITLE), props.getProperty(INVALID_START_HOUR_MESSAGE));
                startComboBox.setValue(data.getStartHour() + ":00");
            } else if (data.hasTAInRange(data.getStartHour(), startTime)) {
                if (promptToContinue()) {
                    jTPS.addTransaction(changeStart);
                }
            } else {
                jTPS.addTransaction(changeStart);
            }
        } else {
            if (startTime > data.getEndHour()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(INVALID_START_HOUR_TITLE), props.getProperty(INVALID_START_HOUR_MESSAGE));
            } else {
                jTPS.addTransaction(changeStart);
            }
        }

    }

    public void handleEndTime() throws IOException {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ComboBox endComboBox = workspace.getTATab().getEndComboBox();
        TAData data = (TAData) app.getDataComponent();
        TAFiles file = (TAFiles) app.getFileComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(data);

        String start = (String) endComboBox.getValue();
        int endTime = (Integer.parseInt(start.substring(0, start.indexOf(":"))));

        jTPS_Transaction changeEnd = new ChangeEndTime_Transaction(endTime, data.getEndHour(), officeHours, data, workspace);

        if (endTime < data.getEndHour()) {
            if (endTime <= data.getStartHour()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(INVALID_END_HOUR_TITLE), props.getProperty(INVALID_END_HOUR_MESSAGE));
                endComboBox.setValue(data.getEndHour() + ":00");
            } else if (data.hasTAInRangeEnd(data.getEndHour(), endTime)) {
                if (promptToContinue()) {
                    jTPS.addTransaction(changeEnd);
                }
            } else {
                jTPS.addTransaction(changeEnd);
            }
        } else {
            if (endTime < data.getStartHour()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(INVALID_END_HOUR_TITLE), props.getProperty(INVALID_END_HOUR_MESSAGE));
            } else {
                jTPS.addTransaction(changeEnd);
            }
        }
    }

    public boolean promptToContinue() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.show(props.getProperty(CONTINUE_TITLE), props.getProperty(TA_TOBE_DELETED_MESSAGE));

        String selection = yesNoDialog.getSelection();

        if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
            return true;
        } else if (selection.equals(AppYesNoCancelDialogSingleton.CANCEL)) {
            return false;
        } else if (selection.equals(AppYesNoCancelDialogSingleton.NO)) {
            return false;
        }
        return false;
    }
}
