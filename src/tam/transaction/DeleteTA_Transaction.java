/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;
import jtps.jTPS_Transaction;
import tam.CSGApp;
import tam.data.CSGData;
import tam.data.Recitation;
import tam.file.TimeSlot;
import tam.workspace.CSGWorkspace;

/**
 *
 * @author jaski
 */
public class DeleteTA_Transaction implements jTPS_Transaction {

    private String taName;
    private String taEmail;
    private CSGData data;
    private ArrayList<TimeSlot> hours;
    private ArrayList<Recitation> rec1;
    private ArrayList<Recitation> rec2;
    CSGWorkspace workspace;
    CSGApp app;

    public DeleteTA_Transaction(String taName, String taEmail, CSGData data, CSGWorkspace workspace) {
        this.taName = taName;
        this.taEmail = taEmail;
        this.data = data;
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        data.removeTA(taName);

        hours = TimeSlot.buildCustomList(data, taName);
        rec1 = new ArrayList<>();
        rec2 = new ArrayList<>();

        HashMap<String, Label> labels = workspace.getTATab().getOfficeHoursGridTACellLabels();

        for (Label label : labels.values()) {
            if (label.getText().equals(taName)
                    || (label.getText().contains(taName + "\n"))
                    || (label.getText().contains("\n" + taName))) {
                data.removeTAFromCell(label.textProperty(), taName);
            }
        }

//        for (Recitation r : data.getRecitations()) {
//            if (r.getTa1().equals(taName)) {
//                r.setTa1("");
//                rec1.add(r);
//            }
//            if (r.getTa2().equals(taName)) {
//                r.setTa2("");
//                rec2.add(r);
//            }
//        }

        workspace.getRecitationTab().getTa1ComboBox().setItems(data.getTaNames());
        workspace.getRecitationTab().getTa2ComboBox().setItems(data.getTaNames());
    }

    @Override
    public void undoTransaction() {
        data.addTA(taName, taEmail);

        for (int i = 0; i < hours.size(); i++) {
            TimeSlot officeHours = hours.get(i);
            String day = officeHours.getDay();
            String time = officeHours.getTime();
            String name = officeHours.getName();
            int t1 = Integer.parseInt(time.substring(0, time.indexOf("_")));
            data.addOfficeHoursReservation(day, time, name);
        }

//        for (Recitation r: rec1) {
//            
//        }
        workspace.getRecitationTab().getTa1ComboBox().setItems(data.getTaNames());
        workspace.getRecitationTab().getTa2ComboBox().setItems(data.getTaNames());
    }

}
