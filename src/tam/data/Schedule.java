/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jaski
 */
public class Schedule {
    private final StringProperty type;
    private final StringProperty date;
    private final StringProperty title;
    private final StringProperty topic;
    
    public Schedule(String type, String date, String title, String topic){
        this.type = new SimpleStringProperty(type);
        this.date = new SimpleStringProperty(date);
        this.title = new SimpleStringProperty(title);
        this.topic = new SimpleStringProperty(topic);
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
}
