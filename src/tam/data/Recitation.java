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
public class Recitation<E extends Comparable<E>> implements Comparable<E> {
    private final StringProperty section;
    private final StringProperty instructor;
    private final StringProperty dayTime;
    private final StringProperty location;
    private final StringProperty ta1;
    private final StringProperty ta2;
    
    public Recitation(String section, String instructor, String dayTime, 
            String location, String ta1, String ta2){
        this.section = new SimpleStringProperty(section);
        this.instructor = new SimpleStringProperty(instructor);
        this.dayTime = new SimpleStringProperty(dayTime);
        this.location = new SimpleStringProperty(location);
        this.ta1 = new SimpleStringProperty(ta1);
        this.ta2 = new SimpleStringProperty(ta2);
    }
    
    public String getSection(){
        return section.get();
    }
    
    public void setSection(String section){
        this.section.set(section);
    }
    
    public String getInstructor(){
        return instructor.get();
    }
    
    public void setInstructor(String instructor){
        this.instructor.set(instructor);
    }
    
    public String getDayTime(){
        return dayTime.get();
    }
    
    public void setDayTime(String dayTime){
        this.dayTime.set(dayTime);
    }
    
    public String getLocation(){
        return location.get();
    }
    
    public void setLocation(String location){
        this.location.set(location);
    }

    public String getTa1() {
        return ta1.get();
    }

    public String getTa2() {
        return ta2.get();
    }

    public void setTa1(String ta1) {
        this.ta1.set(ta1);
    }

    public void setTa2(String ta2) {
        this.ta2.set(ta2);
    }
    
    @Override
    public int compareTo(E otherRecitation) {
        return getSection().compareTo(((Recitation)otherRecitation).getSection());
    }
    
}
