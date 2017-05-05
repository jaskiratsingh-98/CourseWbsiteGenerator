/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;
import tam.data.CSGData;
import tam.data.Recitation;

/**
 *
 * @author jaski
 */
public class UpdateRecitation implements jTPS_Transaction{
    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private String ta1;
    private String ta2;
    private String oldSection;
    private String oldInstructor;
    private String oldDayTime;
    private String oldLocation;
    private String oldTa1;
    private String oldTa2;
    private Recitation rec;
    private CSGData data;
    private TableView<Recitation> recitations;

    public UpdateRecitation(String section, String instructor, String dayTime, 
            String location, String ta1, String ta2, Recitation rec, CSGData data, TableView recitations) {
        this.section = section;
        this.instructor = instructor;
        this.dayTime = dayTime;
        this.location = location;
        this.ta1 = ta1;
        this.ta2 = ta2;
        this.rec = rec;
        oldSection = rec.getSection();
        oldInstructor = rec.getInstructor();
        oldDayTime = rec.getDayTime();
        oldLocation = rec.getLocation();
        oldTa1 = rec.getTa1();
        oldTa2 = rec.getTa2();
        this.data = data;
        this.recitations = recitations;
    }

    @Override
    public void doTransaction() {
        data.editRecitation(rec, section, instructor, dayTime, location, ta1, ta2);
        recitations.refresh();
    }

    @Override
    public void undoTransaction() {
        Recitation edit = new Recitation(section, instructor, dayTime, location, ta1, ta2);
        data.editRecitation(edit, oldSection, oldInstructor, oldDayTime, oldLocation, oldTa1, oldTa2);
        recitations.refresh();
    }
    
}
