/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import java.util.ArrayList;
import jtps.jTPS_Transaction;
import tam.data.TAData;
import tam.file.TimeSlot;
import tam.workspace.TAWorkspace;

/**
 *
 * @author jaski
 */
public class ChangeEndTime_Transaction implements jTPS_Transaction{
    
    private int oldTime;
    private int newTime;
    private ArrayList<TimeSlot> officeHours;
    TAData data;
    TAWorkspace workspace;

    public ChangeEndTime_Transaction(int newTime, int oldTime, ArrayList<TimeSlot> officeHours, TAData data, TAWorkspace workspace) {
        this.newTime = newTime;
        this.oldTime = oldTime;
        this.officeHours = officeHours;
        this.data = data;
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        data.setEndHour(newTime);
        workspace.resetWorkspace();
        workspace.reloadWorkspace(data);
        for (int i = 0; i < officeHours.size(); i++) {
            String day = officeHours.get(i).getDay();
            String time = officeHours.get(i).getTime();
            String name = officeHours.get(i).getName();
            data.addOfficeHoursReservation(day, time, name);
        }
        workspace.getEndComboBox().setValue(newTime + ":00");
    }

    @Override
    public void undoTransaction() {
        data.setEndHour(oldTime);
        workspace.resetWorkspace();
        workspace.reloadWorkspace(data);
        for (int i = 0; i < officeHours.size(); i++) {
            String day = officeHours.get(i).getDay();
            String time = officeHours.get(i).getTime();
            String name = officeHours.get(i).getName();
            data.addOfficeHoursReservation(day, time, name);
        }
        workspace.getEndComboBox().setValue(oldTime + ":00");
    }
}
