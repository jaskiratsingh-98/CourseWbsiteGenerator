/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import djf.ui.AppMessageDialogSingleton;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import java.time.LocalDate;
import java.util.Calendar;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;
import tam.CSGApp;
import static tam.CSGProp.INVALID_DATE_TITLE;
import static tam.CSGProp.NOT_FRIDAY_MESSAGE;
import static tam.CSGProp.NOT_MONDAY_MESSAGE;
import tam.data.CSGData;
import tam.data.Schedule;
import tam.transaction.AddSchedule;
import tam.transaction.DeleteSchedule;
import tam.transaction.UpdateSchedule;
import static tam.workspace.RecitationController.jTPS;

/**
 *
 * @author jaski
 */
public class ScheduleController {

    CSGApp app;
    static jTPS jTPS;

    public ScheduleController(CSGApp initApp) {
        app = initApp;
        jTPS = new jTPS();
    }

    public void addSchedule() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ScheduleTab tab = workspace.getScheduleTab();
        CSGData data = (CSGData) app.getDataComponent();

        String type = tab.getTypeComboBox().getValue().toString();
        LocalDate date = tab.getDate().getValue();
        String time = tab.getTimeTextField().getText();
        String title = tab.getTitleTextField().getText();
        String topic = tab.getTopicTextField().getText();
        String link = tab.getLinkTextField().getText();
        String criteria = tab.getCriteriaTextField().getText();

        jTPS_Transaction addTrans = new AddSchedule(type, date.toString(), title, topic, time, link, criteria, data);
        jTPS.addTransaction(addTrans);
        tab.clearItems();
    }

    public void editSchedule() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ScheduleTab tab = workspace.getScheduleTab();

        TableView table = tab.getScheduleItems();
        Schedule sch = (Schedule) table.getSelectionModel().getSelectedItem();

        tab.getTypeComboBox().setValue(sch.getType());
        tab.getDate().setValue(LocalDate.of(sch.getYear(), sch.getMonth(), sch.getDay()));
        tab.getTimeTextField().setText(sch.getTime());
        tab.getTitleTextField().setText(sch.getTitle());
        tab.getTopicTextField().setText(sch.getTopic());
        tab.getLinkTextField().setText(sch.getLink());
        tab.getCriteriaTextField().setText(sch.getCriteria());

        tab.getAddUpdate().setOnAction(e -> {
            updateSchedule(sch);
        });
        tab.getLinkTextField().setOnAction(e -> {
            updateSchedule(sch);
        });

        tab.getClear().setOnAction(e -> {
            tab.clearItems();
        });
    }

    public void updateSchedule(Schedule schedule) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();
        ScheduleTab tab = workspace.getScheduleTab();

        String type = tab.getTypeComboBox().getValue().toString();
        String date = tab.getDate().getValue().toString();
        String time = tab.getTimeTextField().getText();
        String title = tab.getTitleTextField().getText();
        String topic = tab.getTopicTextField().getText();
        String link = tab.getLinkTextField().getText();
        String criteria = tab.getCriteriaTextField().getText();
        
        jTPS_Transaction updSch = new UpdateSchedule(type, date, title, topic, time, link, criteria, schedule, app);
        jTPS.addTransaction(updSch);
        tab.clearItems();
    }
    
    public void removeSchedule(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        CSGData data = (CSGData)app.getDataComponent();
        ScheduleTab tab = workspace.getScheduleTab();
        
        TableView table = tab.getScheduleItems();
        Schedule sch = (Schedule)table.getSelectionModel().getSelectedItem();
        
        jTPS_Transaction delSch = new DeleteSchedule(sch, data);
        jTPS.addTransaction(delSch);
        tab.clearItems();
    }

    public void setStartingMonday() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();
        LocalDate date = workspace.getScheduleTab().getStartDate().getValue();
        if (date.getDayOfWeek() == MONDAY) {
            data.setStartingMonday(date.getMonthValue(), date.getDayOfMonth());
            workspace.getScheduleTab().getMainPane().requestFocus();
        } else {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(INVALID_DATE_TITLE), props.getProperty(NOT_MONDAY_MESSAGE));
        }
    }

    public void setEndingFriday() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();
        LocalDate date = workspace.getScheduleTab().getEndDate().getValue();
        if (date.getDayOfWeek() == FRIDAY) {
            data.setEndingFriday(date.getMonthValue(), date.getDayOfMonth());
            workspace.getScheduleTab().getMainPane().requestFocus();
        } else {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(INVALID_DATE_TITLE), props.getProperty(NOT_FRIDAY_MESSAGE));
        }
    }
    
    public void handleKeyPress(KeyEvent e){
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
    
    public void undo(){
        jTPS.undoTransaction();
    }
    
    public void redo(){
        jTPS.doTransaction();
    }
}
