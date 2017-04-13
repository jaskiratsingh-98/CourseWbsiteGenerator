/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import static tam.CSGProp.*;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
/**
 *
 * @author jaski
 */
public class ProjectTab {
    Tab tab;
    
    VBox mainPane;
    VBox teamPane;
    VBox studentsPane;
    
    Label title;
    
    Label teamTitle;
    Button hideTeamPane;
    Label studentTitle;
    Button hideStudentPane;
    
    TableView<String> teams;
    TableColumn<String, String> nameColumn;
    TableColumn<String, String> colorColumn;
    TableColumn<String, String> textColorColumn;
    TableColumn<String, String> linkColumn;
    
    Label addEdit;
    Label name;
    TextField nameTextField;
    Label color;
    ColorPicker colorPicker;
    Label textColor;
    ColorPicker textColorPicker;
    Label link;
    TextField linkTextField;
    Button addUpdate;
    Button clear;
    
    TableView<String> students;
    TableColumn<String, String> firstNameColumn;
    TableColumn<String, String> lastNameColumn;
    TableColumn<String, String> teamColumn;
    TableColumn<String, String> roleColumn;
    
    Label addEdit2;
    
    Label firstName;
    TextField firstNameTF;
    Label lastName;
    TextField lastNameTF;
    Label team;
    ComboBox teamComboBox;
    Label role;
    TextField roleTextField;
    
    Button addUpdate2;
    Button clear2;
    
    
    
    
    
    
    public ProjectTab(){
        tab = new Tab();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        title = new Label(props.getProperty(PROJECTLABEL));
        
        teamTitle = new Label(props.getProperty(TEAMS_LABEL));
        hideTeamPane = new Button("-");
        HBox box = new HBox();
        box.getChildren().addAll(teamTitle, hideTeamPane);
        
        
        teams = new TableView();
        nameColumn = new TableColumn(props.getProperty(NAME_TEXT));
        colorColumn = new TableColumn(props.getProperty(COLOR_TEXT));
        textColorColumn = new TableColumn(props.getProperty(TEXTCOLOR_TEXT));
        linkColumn = new TableColumn(props.getProperty(LINK_TEXT));
        teams.getColumns().addAll(nameColumn, colorColumn, textColorColumn, 
                linkColumn);
        
        addEdit = new Label(props.getProperty(ADDEDIT_LABEL));
        name = new Label(props.getProperty(NAME_LABEL));
        nameTextField = new TextField();
        HBox box1 = new HBox();
        box1.getChildren().addAll(name, nameTextField);
        
        color = new Label(props.getProperty(COLOR_LABEL));
        colorPicker = new ColorPicker();
        textColor = new Label(props.getProperty(TEXTCOLOR_LABEL));
        textColorPicker = new ColorPicker();   
        HBox box2 = new HBox();
        box2.getChildren().addAll(color, colorPicker, textColor, textColorPicker);
        
        link = new Label(props.getProperty(LINK_LABEL));
        linkTextField = new TextField();
        HBox box3 = new HBox();
        box3.getChildren().addAll(link, linkTextField);
        
        addUpdate = new Button(props.getProperty(ADDUPDATE_BUTTON));
        clear = new Button(props.getProperty(CLEAR_BUTTON));
        HBox box4 = new HBox();
        box4.getChildren().addAll(addUpdate, clear);
        
        teamPane = new VBox();
        teamPane.getChildren().addAll(box, teams, box1, box2,
                box3, box4);
        
        studentTitle = new Label(props.getProperty(TEAMS_LABEL));
        hideStudentPane = new Button("-");
        HBox box5 = new HBox();
        box5.getChildren().addAll(studentTitle, hideStudentPane);
        
        students = new TableView();
        firstNameColumn = new TableColumn(props.getProperty(FIRSTNAME_TEXT));
        lastNameColumn = new TableColumn(props.getProperty(LASTNAME_TEXT));
        teamColumn = new TableColumn(props.getProperty(TEAM_TEXT));
        roleColumn = new TableColumn(props.getProperty(ROLE_TEXT));
        students.getColumns().addAll(firstNameColumn, lastNameColumn, teamColumn, 
                roleColumn);
        
        addEdit = new Label(props.getProperty(ADDEDIT_LABEL));
        
        firstName = new Label(props.getProperty(FIRSTNAME_TEXT));
        firstNameTF = new TextField();
        HBox box6 = new HBox();
        box6.getChildren().addAll(firstName, firstNameTF);
        
        lastName = new Label(props.getProperty(LASTNAME_TEXT));
        lastNameTF = new TextField();
        HBox box7 = new HBox();
        box7.getChildren().addAll(lastName, lastNameTF);
        
        team = new Label(props.getProperty(TEAM_TEXT));
        teamComboBox = new ComboBox();
        HBox box8 = new HBox();
        box8.getChildren().addAll(team, teamComboBox);
        
        role = new Label(props.getProperty(ROLE_TEXT));
        roleTextField = new TextField();
        HBox box9 = new HBox();
        box9.getChildren().addAll(role, roleTextField);
        
        addUpdate2 = new Button(props.getProperty(ADDUPDATE_BUTTON));
        clear2 = new Button(props.getProperty(CLEAR_BUTTON));
        HBox box10 = new HBox();
        box10.getChildren().addAll(addUpdate2, clear2);
        
        studentsPane = new VBox();
        studentsPane.getChildren().addAll(box5, students, box6, box7,
                box8, box9, box10);
        
        mainPane = new VBox();
        mainPane.getChildren().addAll(title, teamPane, studentsPane);
        
        tab.setText("Project Data");
        tab.setContent(mainPane);
    }
    
    public Tab getTab(){
        return tab;
    }
}
