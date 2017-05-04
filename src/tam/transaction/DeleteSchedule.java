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
public class DeleteSchedule implements jTPS_Transaction{
    private String type;
    private String date;
    private String title;
    private String topic;
    private String time;
    private String link;
    private String criteria;
    private Schedule sch;
    private CSGData data;
    
    public DeleteSchedule(Schedule sch, CSGData data){
        this.type = sch.getType();
        this.date = sch.getDate();
        this.title = sch.getTitle();
        this.topic = sch.getTopic();
        this.time = sch.getTime();
        this.link = sch.getLink();
        this.criteria = sch.getCriteria();
        this.sch = sch;
        this.data = data;
    }

    @Override
    public void doTransaction() {
        data.removeSchedule(sch);
    }

    @Override
    public void undoTransaction() {
        data.addSchedule(type, date, title, topic, time, link, criteria);
    }
    
}
