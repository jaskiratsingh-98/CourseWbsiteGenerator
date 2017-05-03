/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import tam.CSGApp;
import tam.data.CSGData;
import tam.data.Student;
import tam.data.Team;

/**
 *
 * @author jaski
 */
public class ProjectController {
    CSGApp app;
    
    public ProjectController(CSGApp app){
        this.app = app;
    }
    
    public void addTeam(){
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTab tab = workspace.getProjectTab();
        CSGData data = (CSGData) app.getDataComponent();
        
        TextField nameTF = tab.getNameTextField();
        String name = nameTF.getText();
        ColorPicker colorPicker = tab.getColorPicker();
        String color = colorPicker.getValue().toString().substring(2, 8);
        ColorPicker textColorPicker = tab.getTextColorPicker();
        String textColor = textColorPicker.getValue().toString().substring(2, 8);
        TextField linkTF = tab.getLinkTextField();
        String link = linkTF.getText();
        
        data.addTeam(name, color, textColor, link);
        tab.getTeamComboBox().setItems(data.getTeamNames());
        tab.clearTeamItems();
    }
    
    public void editTeam(){
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTab tab = workspace.getProjectTab();
        
        TableView table = tab.getTeams();
        Team team = (Team)table.getSelectionModel().getSelectedItem();
        
        tab.getNameTextField().setText(team.getName());
        tab.getColorPicker().setValue(Color.valueOf(team.getColor()));
        tab.getTextColorPicker().setValue(Color.valueOf(team.getTextColor()));
        tab.getLinkTextField().setText(team.getLink());
        
        tab.getAddUpdate().setOnAction(e -> {
            updateTeam(team);
        });
        tab.getClear().setOnAction(e -> {
            tab.clearTeamItems();
        });
    }
    
    public void updateTeam(Team team){
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTab tab = workspace.getProjectTab();
        CSGData data = (CSGData) app.getDataComponent();
        
        TextField nameTF = tab.getNameTextField();
        String name = nameTF.getText();
        ColorPicker colorPicker = tab.getColorPicker();
        String color = colorPicker.getValue().toString().substring(2, 8);
        ColorPicker textColorPicker = tab.getTextColorPicker();
        String textColor = textColorPicker.getValue().toString().substring(2, 8);
        TextField linkTF = tab.getLinkTextField();
        String link = linkTF.getText();
        
        data.editTeam(team, name, color, textColor, link);
        tab.getTeams().refresh();
        tab.clearTeamItems();
        
    }
    
    public void removeTeam(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        CSGData data = (CSGData)app.getDataComponent();
        ProjectTab tab = workspace.getProjectTab();
        
        TableView table = tab.getTeams();
        Team team = (Team)table.getSelectionModel().getSelectedItem();
        
        data.removeTeam(team);
        tab.clearTeamItems();
        
    }
    public void addStudent(){
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTab tab = workspace.getProjectTab();
        CSGData data = (CSGData) app.getDataComponent();
        
        String firstName = tab.getFirstNameTF().getText();
        String lastName = tab.getLastNameTF().getText();
        String team = tab.getTeamComboBox().getValue().toString();
        String role = tab.getRoleTextField().getText();
        
        data.addStudent(firstName, lastName, team, role);
        tab.clearStuItems();
    }
    
    public void editStudent(){
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTab tab = workspace.getProjectTab();
        
        TableView table = tab.getStudents();
        Student stu = (Student)table.getSelectionModel().getSelectedItem();
        
        tab.getFirstNameTF().setText(stu.getFirstName());
        tab.getLastNameTF().setText(stu.getLastName());
        tab.getTeamComboBox().setValue(stu.getTeam());
        tab.getRoleTextField().setText(stu.getRole());
        
        tab.getAddUpdate2().setOnAction(e -> {
            updateStudent(stu);
        });
        tab.getClear2().setOnAction(e -> {
            tab.clearStuItems();
        });
    }
    
    public void updateStudent(Student stu){
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTab tab = workspace.getProjectTab();
        CSGData data = (CSGData) app.getDataComponent();
        
        String firstName = tab.getFirstNameTF().getText();
        String lastName = tab.getLastNameTF().getText();
        String team = tab.getTeamComboBox().getValue().toString();
        String role = tab.getRoleTextField().getText();
        
        data.editStudent(stu, firstName, lastName, team, role);
        tab.getStudents().refresh();
        tab.clearStuItems();
        
    }
    
    public void removeStudent(){
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTab tab = workspace.getProjectTab();
        CSGData data = (CSGData) app.getDataComponent();
        
        TableView table = tab.getStudents();
        Student stu = (Student)table.getSelectionModel().getSelectedItem();
        data.removeStudent(stu);
        tab.clearStuItems();
    }
}