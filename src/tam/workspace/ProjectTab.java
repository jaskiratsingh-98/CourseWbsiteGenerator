/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import javafx.collections.ObservableList;
import static tam.CSGProp.*;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;
import tam.CSGApp;
import tam.data.CSGData;
import tam.data.Student;
import tam.data.Team;

/**
 *
 * @author jaski
 */
public class ProjectTab {
    CSGApp app;
    Tab tab;

    private VBox mainPane;
    private VBox teamPane;
    private VBox studentsPane;

    Label title;

    Label teamTitle;
    Button delButton;
    Label studentTitle;
    Button delStuButton;

    TableView<Team> teams;
    TableColumn<Team, String> nameColumn;
    TableColumn<Team, String> colorColumn;
    TableColumn<Team, String> textColorColumn;
    TableColumn<Team, String> linkColumn;

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

    TableView<Student> students;
    TableColumn<Student, String> firstNameColumn;
    TableColumn<Student, String> lastNameColumn;
    TableColumn<Student, String> teamColumn;
    TableColumn<Student, String> roleColumn;

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
    ScrollPane overallPane;
    
    ProjectController controller;

    public ProjectTab(CSGApp app) {
        this.app = app;
        
        tab = new Tab();
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        title = new Label(props.getProperty(PROJECTLABEL));

        teamTitle = new Label(props.getProperty(TEAMS_LABEL));
        delButton = new Button("-");
        HBox box = new HBox();
        box.getChildren().addAll(teamTitle, delButton);

        teams = new TableView();
        teams.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<Team> teamData = data.getTeams();
        teams.setItems(teamData);
        
        nameColumn = new TableColumn(props.getProperty(NAME_TEXT));
        colorColumn = new TableColumn(props.getProperty(COLOR_TEXT));
        textColorColumn = new TableColumn(props.getProperty(TEXTCOLOR_TEXT));
        linkColumn = new TableColumn(props.getProperty(LINK_TEXT));
        
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        
        colorColumn.setCellValueFactory(
                new PropertyValueFactory<>("color")
        );
        
        textColorColumn.setCellValueFactory(
                new PropertyValueFactory<>("textColor")
        );
        
        linkColumn.setCellValueFactory(
                new PropertyValueFactory<>("link")
        );
        
        teams.getColumns().addAll(nameColumn, colorColumn, textColorColumn,
                linkColumn);

        addEdit = new Label(props.getProperty(ADDEDIT_LABEL));
        name = new Label(props.getProperty(NAME_LABEL));
        nameTextField = new TextField();

        color = new Label(props.getProperty(COLOR_LABEL));
        colorPicker = new ColorPicker();
        textColor = new Label(props.getProperty(TEXTCOLOR_LABEL));
        textColorPicker = new ColorPicker();

        link = new Label(props.getProperty(LINK_LABEL));
        linkTextField = new TextField();

        addUpdate = new Button(props.getProperty(ADDUPDATE_BUTTON));
        clear = new Button(props.getProperty(CLEAR_BUTTON));

        GridPane box1 = new GridPane();
        box1 = new GridPane();
        box1.setHgap(5.0);
        box1.setVgap(2.0);
        box1.add(name, 1, 0);
        box1.add(nameTextField, 3, 0);
        box1.add(color, 1, 1);
        box1.add(colorPicker, 3, 1);
        box1.add(textColor, 7, 1);
        box1.add(textColorPicker, 9, 1);
        box1.add(link, 1, 2);
        box1.add(linkTextField, 3, 2);
        box1.add(addUpdate, 1, 3);
        box1.add(clear, 3, 3);

        teamPane = new VBox();
        teamPane.getChildren().addAll(box, teams, box1);

        studentTitle = new Label(props.getProperty(STUDENTS_LABEL));
        delStuButton = new Button("-");
        HBox box5 = new HBox();
        box5.getChildren().addAll(studentTitle, delStuButton);

        students = new TableView();
        students.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Student> stuData = data.getStudents();
        students.setItems(stuData);
        
        firstNameColumn = new TableColumn(props.getProperty(FIRSTNAME_TEXT));
        lastNameColumn = new TableColumn(props.getProperty(LASTNAME_TEXT));
        teamColumn = new TableColumn(props.getProperty(TEAM_TEXT));
        roleColumn = new TableColumn(props.getProperty(ROLE_TEXT));
        
        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );
        
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );
        
        teamColumn.setCellValueFactory(
                new PropertyValueFactory<>("team")
        );
        
        roleColumn.setCellValueFactory(
                new PropertyValueFactory<>("role")
        );
        
        students.getColumns().addAll(firstNameColumn, lastNameColumn, teamColumn,
                roleColumn);

        addEdit = new Label(props.getProperty(ADDEDIT_LABEL));

        firstName = new Label(props.getProperty(FIRSTNAME_TEXT));
        firstNameTF = new TextField();

        lastName = new Label(props.getProperty(LASTNAME_TEXT));
        lastNameTF = new TextField();

        team = new Label(props.getProperty(TEAM_TEXT));
        teamComboBox = new ComboBox();

        role = new Label(props.getProperty(ROLE_TEXT));
        roleTextField = new TextField();

        addUpdate2 = new Button(props.getProperty(ADDUPDATE_BUTTON));
        clear2 = new Button(props.getProperty(CLEAR_BUTTON));

        GridPane box2 = new GridPane();
        box2 = new GridPane();
        box2.setHgap(5.0);
        box2.setVgap(2.0);
        box2.add(addEdit, 1, 0);
        box2.add(firstName, 1, 1);
        box2.add(firstNameTF, 3, 1);
        box2.add(lastName, 1, 2);
        box2.add(lastNameTF, 3, 2);
        box2.add(team, 1, 3);
        box2.add(teamComboBox, 3, 3);
        box2.add(role, 1, 4);
        box2.add(roleTextField, 3, 4);
        box2.add(addUpdate2, 1, 5);
        box2.add(clear2, 3, 5);

        studentsPane = new VBox();
        studentsPane.getChildren().addAll(box5, students, box2);

        mainPane = new VBox();
        mainPane.getChildren().addAll(title, teamPane, studentsPane);
        
        overallPane = new ScrollPane();
        overallPane.setContent(mainPane);

        tab.setText("Project Data");
        tab.setContent(overallPane);
        
        controller = new ProjectController(app);
        
        mainPane.setOnKeyPressed(e -> {
            controller.handleUndoRedo(e.getCode(), e);
        });
        addUpdate.setOnAction(e ->{
            controller.addTeam();
        });
        linkTextField.setOnAction(e -> {
            controller.addTeam();
        });
        teams.setOnMouseClicked(e -> {
            controller.editTeam();
        });
        delButton.setOnAction(e -> {
            controller.removeTeam();
        });
        clear.setOnAction(e -> {
            clearTeamItems();
        });
        roleTextField.setOnAction(e ->{
            controller.addStudent();
        });
        addUpdate2.setOnAction(e ->{
            controller.addStudent();
        });
        students.setOnMouseClicked(e -> {
            controller.editStudent();
        });
        delStuButton.setOnAction(e -> {
            controller.removeStudent();
        });
        clear2.setOnAction(e -> {
            clearStuItems();
        });
    }

    public Tab getTab() {
        return tab;
    }

    public VBox getMainPane() {
        return mainPane;
    }

    public VBox getTeamPane() {
        return teamPane;
    }

    public VBox getStudentsPane() {
        return studentsPane;
    }

    public Label getTitle() {
        return title;
    }

    public Label getTeamTitle() {
        return teamTitle;
    }

    public Label getStudentTitle() {
        return studentTitle;
    }

    public Label getAddEdit() {
        return addEdit;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public ColorPicker getTextColorPicker() {
        return textColorPicker;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getLinkTextField() {
        return linkTextField;
    }

    public ComboBox getTeamComboBox() {
        return teamComboBox;
    }

    public TextField getFirstNameTF() {
        return firstNameTF;
    }

    public TextField getLastNameTF() {
        return lastNameTF;
    }

    public TextField getRoleTextField() {
        return roleTextField;
    }

    public TableView<Team> getTeams() {
        return teams;
    }

    public TableView<Student> getStudents() {
        return students;
    }

    public Button getAddUpdate() {
        return addUpdate;
    }

    public Button getClear() {
        return clear;
    }

    public Button getAddUpdate2() {
        return addUpdate2;
    }

    public Button getClear2() {
        return clear2;
    }

    public ProjectController getController() {
        return controller;
    }
    
    public void clearTeamItems(){
        nameTextField.setText("");
        colorPicker.setValue(Color.WHITE);
        textColorPicker.setValue(Color.WHITE);
        linkTextField.setText("");
    }
    
    public void clearStuItems(){
        firstNameTF.setText("");
        lastNameTF.setText("");
        teamComboBox.setValue(null);
        roleTextField.setText("");
    }
    
}
