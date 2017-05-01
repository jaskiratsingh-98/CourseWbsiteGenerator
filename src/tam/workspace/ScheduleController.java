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
import properties_manager.PropertiesManager;
import tam.CSGApp;
import static tam.CSGProp.INVALID_DATE_TITLE;
import static tam.CSGProp.NOT_FRIDAY_MESSAGE;
import static tam.CSGProp.NOT_MONDAY_MESSAGE;
import tam.data.CSGData;

/**
 *
 * @author jaski
 */
public class ScheduleController {

    CSGApp app;

    public ScheduleController(CSGApp initApp) {
        app = initApp;
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

        data.addSchedule(type, date, title, topic, time, link, criteria);
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
}
