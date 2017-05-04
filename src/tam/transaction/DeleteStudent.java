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
public class DeleteStudent implements jTPS_Transaction{
    private String firstName;
    private String lastName;
    private String team;
    private String role;
    private Student student;
    private CSGData data;

    public DeleteStudent(Student student, CSGData data){
        firstName = student.getFirstName();
        lastName = student.getLastName();
        team = student.getTeam();
        role = student.getRole();
        this.student = student;
        this.data = data;
    }
    @Override
    public void doTransaction() {
        data.removeStudent(student);
    }

    @Override
    public void undoTransaction() {
        data.addStudent(firstName, lastName, team, role);
    }
    
}
