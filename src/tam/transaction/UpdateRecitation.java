/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

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
    private Recitation rec;
    private CSGData data;

    public UpdateRecitation(String section, String instructor, String dayTime, 
            String location, String ta1, String ta2, Recitation rec, CSGData data) {
        this.section = section;
        this.instructor = instructor;
        this.dayTime = dayTime;
        this.location = location;
        this.ta1 = ta1;
        this.ta2 = ta2;
        this.rec = rec;
        this.data = data;
    }

    @Override
    public void doTransaction() {
        data.editRecitation(rec, section, instructor, dayTime, location, ta1, ta2);
    }

    @Override
    public void undoTransaction() {
        Recitation edit = new Recitation(section, instructor, dayTime, location, ta1, ta2);
        data.editRecitation(edit, rec.getSection(), rec.getInstructor(), rec.getDayTime(), rec.getLocation(), rec.getTa1(), rec.getTa2());
    }
    
}
