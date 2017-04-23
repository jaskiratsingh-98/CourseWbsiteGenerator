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
public class Student {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty team;
    private final StringProperty role;

    public Student(String firstName, String lastName, String team, String role) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.team = new SimpleStringProperty(team);
        this.role = new SimpleStringProperty(role);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getTeam() {
        return team.get();
    }

    public String getRole() {
        return role.get();
    }
    
    public void setFirstName(String first){
        firstName.set(first);
    }
    
    public void setLastName(String last){
        lastName.set(last);
    }
    
    public void setTeam(String t1){
        team.set(t1);
    }
    
    public void setRole(String r1){
        role.set(r1);
    }
    
}
