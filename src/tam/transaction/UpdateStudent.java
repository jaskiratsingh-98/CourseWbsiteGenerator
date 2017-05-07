/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import jtps.jTPS_Transaction;
import tam.data.CSGData;
import tam.data.Student;
import tam.workspace.CSGWorkspace;

/**
 *
 * @author jaski
 */
public class UpdateStudent implements jTPS_Transaction{
    private String firstName;
    private String lastName;
    private String team;
    private String role;
    private Student stu;
    private String oldFirstName;
    private String oldLastName;
    private String oldTeam;
    private String oldRole;
    private CSGData data;
    private CSGWorkspace workspace;

    public UpdateStudent(String firstName, String lastName, String team, String role, Student stu, CSGData data, CSGWorkspace workspace) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.role = role;
        this.stu = stu;
        oldFirstName = stu.getFirstName();
        oldLastName = stu.getLastName();
        oldTeam = stu.getTeam();
        oldRole = stu.getRole();
        this.data = data;
        this.workspace = workspace;
    }

    
    @Override
    public void doTransaction() {
        data.editStudent(stu, firstName, lastName, team, role);
        workspace.getProjectTab().getStudents().refresh();
    }

    @Override
    public void undoTransaction() {
        Student stu1 = new Student(firstName, lastName, team, role);
        data.editStudent(stu1, oldFirstName,oldLastName, oldTeam, oldRole);
        workspace.getProjectTab().getStudents().refresh();
    }
    
}
