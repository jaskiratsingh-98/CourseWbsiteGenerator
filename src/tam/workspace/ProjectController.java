/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import tam.CSGApp;
import tam.data.CSGData;

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
        
        nameTF.setText("");
        colorPicker.setValue(Color.WHITE);
        textColorPicker.setValue(Color.WHITE);
        linkTF.setText("");
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
    }
}
