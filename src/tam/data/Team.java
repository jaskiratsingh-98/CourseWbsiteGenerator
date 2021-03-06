/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jaski
 */
public class Team {
    private final StringProperty name;
    private final StringProperty color;
    private final StringProperty textColor;
    private final StringProperty link;
    ObservableList<Student> students;
    
    public Team(String name, String color, String textColor, String link){
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.textColor = new SimpleStringProperty(textColor);
        this.link = new SimpleStringProperty(link);
        students = FXCollections.observableArrayList();
    }

    public String getName() {
        return name.get();
    }
    
    public void setName(String name){
        this.name.set(name);
    }

    public String getColor() {
        return color.get();
    }
    
    public void setColor(String color){
        this.color.set(color);
    }

    public String getTextColor() {
        return textColor.get();
    }
    
    public void setTextColor(String textColor){
        this.textColor.set(textColor);
    }

    public String getLink() {
        return link.get();
    }
    
    public void setLink(String link){
        this.link.set(link);
    }
    
    public void addStudent(Student a){
        students.add(a);
    }
    
    public ObservableList getStudents(){
        return students;
    }
}
