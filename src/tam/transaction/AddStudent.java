/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import jtps.jTPS_Transaction;
import tam.data.CSGData;
import tam.data.Student;

/**
 *
 * @author jaski
 */
public class AddStudent implements jTPS_Transaction{
    private String firstName;
    private String lastName;
    private String team;
    private String role;
    private CSGData data;

    public AddStudent(String firstName, String lastName, String team, String role, CSGData data) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.role = role;
        this.data = data;
    }

    @Override
    public void doTransaction() {
        data.addStudent(firstName, lastName, team, role);
    }

    @Override
    public void undoTransaction() {
        Student stu = new Student(firstName, lastName, team, role);
        data.removeStudent(stu);
    }
    
    
}
