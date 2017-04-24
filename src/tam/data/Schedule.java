/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jaski
 */
public class Schedule {
    private final StringProperty type;
    private final StringProperty date;
    private int month;
    private int day;
    private String time;
    private final StringProperty title;
    private final StringProperty topic;
    private String link;
    private String criteria;

    public Schedule(String type, LocalDate date, String title, String topic,
            String time, String link, String criteria){
        this.type = new SimpleStringProperty(type);
        this.date = new SimpleStringProperty(date.toString());
        this.time = time;
        this.title = new SimpleStringProperty(title);
        this.topic = new SimpleStringProperty(topic);
        this.link = link;
        this.criteria = criteria;
        int month = Integer.parseInt(date.toString().substring(5, 7));
        int day = Integer.parseInt(date.toString().substring(8));
    }
    
    public Schedule(String type, String date, String title, String topic,
            String time, String link, String criteria){
        this.type = new SimpleStringProperty(type);
        this.date = new SimpleStringProperty(date);
        this.time = time;
        this.title = new SimpleStringProperty(title);
        this.topic = new SimpleStringProperty(topic);
        this.link = link;
        this.criteria = criteria;
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8));
    }

    public String getType() {
        return type.get();
    }

    public void setType(String initType) {
        type.set(initType);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String initDate) {
        date.set(initDate);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String initTitle) {
        title.set(initTitle);
    }

    public String getTopic() {
        return topic.get();
    }

    public void setTopic(String initTopic) {
        topic.set(initTopic);
    }

    public String getTime() {
        return time;
    }

    public String getLink() {
        return link;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
    
    public int getMonth(){
        return month;
    }
    
    public int getDay(){
        return day;
    }
}
