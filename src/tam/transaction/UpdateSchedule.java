/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import jtps.jTPS_Transaction;
import tam.CSGApp;
import tam.data.CSGData;
import tam.data.Schedule;
import tam.workspace.CSGWorkspace;

/**
 *
 * @author jaski
 */
public class UpdateSchedule implements jTPS_Transaction{
    private String type;
    private String date;
    private String title;
    private String topic;
    private String time;
    private String link;
    private String criteria;
    private String oldType;
    private String oldDate;
    private String oldTitle;
    private String oldTopic;
    private String oldTime;
    private String oldLink;
    private String oldCriteria;
    private Schedule sch;
    private CSGData data;
    private CSGWorkspace workspace;

    public UpdateSchedule(String type, String date, String title, String topic, 
            String time, String link, String criteria, Schedule sch, CSGApp app) {
        this.type = type;
        this.date = date;
        this.title = title;
        this.topic = topic;
        this.time = time;
        this.link = link;
        this.criteria = criteria;
        this.sch = sch;
        oldType = sch.getType();
        oldDate = sch.getDate();
        oldTitle = sch.getTitle();
        oldTopic = sch.getTopic();
        oldTime = sch.getTime();
        oldLink = sch.getLink();
        oldCriteria = sch.getCriteria();
        data = (CSGData)app.getDataComponent();
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
    }
    
    

    @Override
    public void doTransaction() {
        data.editSchedule(sch, type, date, title, topic, time, link, criteria);
        workspace.getScheduleTab().getScheduleItems().refresh();
    }

    @Override
    public void undoTransaction() {
        Schedule sch1 = new Schedule(type, date, title, topic, time, link, criteria);
        data.editSchedule(sch1, oldType, oldDate, oldTitle, oldTopic, oldTime, oldLink, oldCriteria);
        workspace.getScheduleTab().getScheduleItems().refresh();
    }
    
}
