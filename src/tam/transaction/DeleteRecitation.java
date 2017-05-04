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
public class DeleteRecitation implements jTPS_Transaction{
    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private String ta1;
    private String ta2;
    private CSGData data;

    public DeleteRecitation(Recitation rec, CSGData data) {
        this.section = rec.getSection();
        this.instructor = rec.getInstructor();
        this.dayTime = rec.getDayTime();
        this.location = rec.getLocation();
        this.ta1 = rec.getTa1();
        this.ta2 = rec.getTa2();
        this.data = data;
    }

    @Override
    public void doTransaction() {
        data.removeRecitation(section);
    }

    @Override
    public void undoTransaction() {
        data.addRecitation(section, instructor, dayTime, location, ta1, ta2);
    }
    
   
}
