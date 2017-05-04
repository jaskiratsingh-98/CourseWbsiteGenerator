/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import jtps.jTPS_Transaction;
import tam.data.CSGData;
import tam.data.Schedule;

/**
 *
 * @author jaski
 */
public class AddSchedule implements jTPS_Transaction{
    private String type;
    private String date;
    private String title;
    private String topic;
    private String time;
    private String link;
    private String criteria;
    private CSGData data;

    public AddSchedule(String type, String date, String title, String topic, String time, String link, String criteria, CSGData data) {
        this.type = type;
        this.date = date;
        this.title = title;
        this.topic = topic;
        this.time = time;
        this.link = link;
        this.criteria = criteria;
        this.data = data;
    }

    @Override
    public void doTransaction() {
        data.addSchedule(type, date, title, topic, time, link, criteria);
    }

    @Override
    public void undoTransaction() {
        Schedule sch = new Schedule(type, date, title, topic, time, link, criteria);
        data.removeSchedule(sch);
    }
    
}
