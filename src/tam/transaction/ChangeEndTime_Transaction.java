/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import java.util.ArrayList;
import jtps.jTPS_Transaction;
import tam.data.CSGData;
import tam.file.TimeSlot;
import tam.workspace.CSGWorkspace;

/**
 *
 * @author jaski
 */
public class ChangeEndTime_Transaction implements jTPS_Transaction{
    
    private int oldTime;
    private int newTime;
    private ArrayList<TimeSlot> officeHours;
    CSGData data;
    CSGWorkspace workspace;

    public ChangeEndTime_Transaction(int newTime, int oldTime, ArrayList<TimeSlot> officeHours, CSGData data, CSGWorkspace workspace) {
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
            int setTime = Integer.parseInt(time.substring(0, time.indexOf("_")));
            if(time.contains("pm") && (!time.contains("12"))){
                setTime =+ 12;
            }
            if(setTime <= newTime){
                data.addOfficeHoursReservation(day, time, name);
            }
        }
        workspace.getTATab().getEndComboBox().setValue(newTime + ":00");
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
            int setTime = Integer.parseInt(time.substring(0, time.indexOf("_")));
            if(time.contains("pm") && (!time.contains("12"))){
                setTime =+ 12;
            }
            if(setTime <= oldTime){
                data.addOfficeHoursReservation(day, time, name);
            }
        }
        workspace.getTATab().getEndComboBox().setValue(oldTime + ":00");
    }
}
