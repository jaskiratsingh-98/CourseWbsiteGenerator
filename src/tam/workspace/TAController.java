package tam.workspace;

import djf.controller.AppFileController;
import djf.ui.AppGUI;
import static tam.TAManagerProp.*;
import djf.ui.AppMessageDialogSingleton;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.style.TAStyle;
import static tam.style.TAStyle.CLASS_HIGHLIGHTED_GRID_CELL;
import static tam.style.TAStyle.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static tam.style.TAStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;
import tam.workspace.TAWorkspace;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Button;

/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file toolbar.
 *
 * @author Richard McKenna
 * @version 1.0
 */
public class TAController {

    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    TAManagerApp app;

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Constructor, note that the app must already be constructed.
     */
    public TAController(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
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
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();

        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        TeachingAssistant ta = (TeachingAssistant) selectedItem;

        TAData data = (TAData) app.getDataComponent();

        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();

        Button addButton = workspace.getAddButton();

        if (ta.getName() != null) {
            nameTextField.setText(ta.getName());
            emailTextField.setText(ta.getEmail());
            addButton.setText("Update");

            addButton.setOnAction(e -> {
                updateTA(ta);
            });

        }
    }

    public void updateTA(TeachingAssistant ta) {
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String editName = nameTextField.getText();
        String editEmail = emailTextField.getText();
        TableView table = workspace.getTATable();

        TAData data = (TAData) app.getDataComponent();

        PropertiesManager props = PropertiesManager.getPropertiesManager();

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
                    data.editTA(editName, editEmail, ta);
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
                    data.editTA(editName, editEmail, ta);
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
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = (TAData) app.getDataComponent();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

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
                data.addTA(name, email);
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
            TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
            TableView taTable = workspace.getTATable();

            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                TeachingAssistant ta = (TeachingAssistant) selectedItem;
                String taName = ta.getName();
                TAData data = (TAData) app.getDataComponent();
                data.removeTA(taName);

                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
                for (Label label : labels.values()) {
                    if (label.getText().equals(taName)
                            || (label.getText().contains(taName + "\n"))
                            || (label.getText().contains("\n" + taName))) {
                        data.removeTAFromCell(label.textProperty(), taName);
                    }
                }
                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
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
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant) selectedItem;
            String taName = ta.getName();
            TAData data = (TAData) app.getDataComponent();
            String cellKey = pane.getId();

            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            data.toggleTAOfficeHours(cellKey, taName);

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    void handleGridCellMouseExited(Pane pane) {
        String cellKey = pane.getId();
        TAData data = (TAData) app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();

        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }
    }

    void handleGridCellMouseEntered(Pane pane) {
        String cellKey = pane.getId();
        TAData data = (TAData) app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();

        // THE MOUSED OVER PANE
        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_CELL);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }
    }

    public void clear() {
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
        workspace.getNameTextField().setText("");
        workspace.getEmailTextField().setText("");

        workspace.getAddButton().setText("Add TA");

        workspace.getNameTextField().requestFocus();
    }
}
