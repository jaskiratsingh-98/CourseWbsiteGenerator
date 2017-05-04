/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import jtps.jTPS_Transaction;
import tam.data.CSGData;

/**
 *
 * @author jaski
 */
public class AddRecitation implements jTPS_Transaction{
    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private String ta1;
    private String ta2;
    private CSGData data;

    public AddRecitation(String section, String instructor, String dayTime, String location, String ta1, String ta2, CSGData data) {
        this.section = section;
        this.instructor = instructor;
        this.dayTime = dayTime;
        this.location = location;
        this.ta1 = ta1;
        this.ta2 = ta2;
        this.data = data;
    }

    @Override
    public void doTransaction() {
        data.addRecitation(section, instructor, dayTime, location, ta1, ta2);
    }

    @Override
    public void undoTransaction() {
        data.removeRecitation(section);
    }
    
}
