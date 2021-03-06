/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import djf.ui.AppMessageDialogSingleton;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import tam.CSGApp;
import static tam.CSGProp.EMAIL_ADDRESS_NOT_VALID;
import static tam.CSGProp.EMAIL_ADDRESS_NOT_VALID_MESSAGE;
import tam.data.CSGData;
import tam.data.Recitation;
import tam.transaction.AddRecitation;
import tam.transaction.DeleteRecitation;
import tam.transaction.UpdateRecitation;
import static tam.workspace.TAController.jTPS;

/**
 *
 * @author jaski
 */
public class RecitationController {

    CSGApp app;
    static jTPS jTPS;

    public RecitationController(CSGApp initApp) {
        app = initApp;
        jTPS = new jTPS();
    }

    public void addRecitation() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();
        RecitationTab tab = workspace.getRecitationTab();
        String ta1, ta2;

        String section = tab.getSectionTextField().getText();
        String instructor = tab.getInstructorTextField().getText();
        String dayTime = tab.getDayTimeTextField().getText();
        String location = tab.getLocationTextField().getText();
        ComboBox ta1CB = tab.getTa1ComboBox();
        if (ta1CB.getValue() == null) {
            ta1 = "";
        } else {
            ta1 = tab.getTa1ComboBox().getValue().toString();
        }
        ComboBox ta2CB = tab.getTa2ComboBox();
        if (ta2CB.getValue() == null) {
            ta2 = "";
        } else {
            ta2 = ta2CB.getValue().toString();
        }

        jTPS_Transaction addRec = new AddRecitation(section, instructor, dayTime, location, ta1, ta2, data);

        if (section.isEmpty() || instructor.isEmpty() || dayTime.isEmpty() || location.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Empty Fields", "You must fill in all necessary fields.");
        } else if (data.containsRecitation(section)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Section Already Exists", "A recitation with the same section number already exists.");
        } else {
            jTPS.addTransaction(addRec);
            tab.clearFields();
        }
    }

    public void editRecitation() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        RecitationTab tab = workspace.getRecitationTab();

        TableView table = tab.getRecitations();
        Recitation rec = (Recitation) table.getSelectionModel().getSelectedItem();

        tab.getSectionTextField().setText(rec.getSection());
        tab.getInstructorTextField().setText(rec.getInstructor());
        tab.getDayTimeTextField().setText(rec.getDayTime());
        tab.getLocationTextField().setText(rec.getLocation());
        tab.getTa1ComboBox().setValue(rec.getTa1());
        tab.getTa2ComboBox().setValue(rec.getTa2());

        tab.getAddUpdate().setOnAction(e -> {
            updateRecitation(rec);
        });

        tab.getClear().setOnAction(e -> {
            tab.clearFields();
        });
    }

    public void updateRecitation(Recitation rec) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();
        RecitationTab tab = workspace.getRecitationTab();
        String ta1, ta2;

        String section = tab.getSectionTextField().getText();
        String instructor = tab.getInstructorTextField().getText();
        String dayTime = tab.getDayTimeTextField().getText();
        String location = tab.getLocationTextField().getText();
        ComboBox ta1CB = tab.getTa1ComboBox();
        if (ta1CB.getValue() == null) {
            ta1 = "";
        } else {
            ta1 = tab.getTa1ComboBox().getValue().toString();
        }
        ComboBox ta2CB = tab.getTa2ComboBox();
        if (ta2CB.getValue() == null) {
            ta2 = "";
        } else {
            ta2 = ta2CB.getValue().toString();
        }

        if (data.containsRecitation(section)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Already Exists", "Recitation exists with section name.");
        } else if ((section.isEmpty() || instructor.isEmpty() || dayTime.isEmpty()
                || location.isEmpty())) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Section Already Exists", "A recitation with the same section number already exists.");
        } else {
            jTPS_Transaction editRec = new UpdateRecitation(section, instructor, dayTime, location, ta1, ta2, rec, data, tab.getRecitations());
            jTPS.addTransaction(editRec);
            tab.clearFields();
        }
    }

    public void delRecitation() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();
        RecitationTab tab = workspace.getRecitationTab();

        TableView table = tab.getRecitations();
        Recitation rec = (Recitation) table.getSelectionModel().getSelectedItem();

        jTPS_Transaction delRec = new DeleteRecitation(rec, data);
        jTPS.addTransaction(delRec);
        tab.clearFields();
    }

    public void handleKeyPress(KeyEvent e) {
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

    public void undo() {
        jTPS.undoTransaction();
    }

    public void redo() {
        jTPS.doTransaction();
    }
}
