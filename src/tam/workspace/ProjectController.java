/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import djf.ui.AppMessageDialogSingleton;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.Y;
import static javafx.scene.input.KeyCode.Z;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import tam.CSGApp;
import tam.data.CSGData;
import tam.data.Student;
import tam.data.Team;
import tam.transaction.AddStudent;
import tam.transaction.AddTeam;
import tam.transaction.DeleteStudent;
import tam.transaction.DeleteTeam;
import tam.transaction.UpdateStudent;
import tam.transaction.UpdateTeam;
import static tam.workspace.TAController.jTPS;

/**
 *
 * @author jaski
 */
public class ProjectController {

    CSGApp app;
    static jTPS jTPS;

    public ProjectController(CSGApp app) {
        this.app = app;
        jTPS = new jTPS();
    }

    public void addTeam() {
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

        if (name.isEmpty() || link.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Empty Fields", "You must fill in all fields.");
        } else if (data.containsTeam(name)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Team Already Exists", "A team already exists with the same name.");
        } else {
            jTPS_Transaction addTeam = new AddTeam(name, color, textColor, link, app);
            jTPS.addTransaction(addTeam);
            tab.clearTeamItems();
        }
    }

    public void editTeam() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTab tab = workspace.getProjectTab();

        TableView table = tab.getTeams();
        Team team = (Team) table.getSelectionModel().getSelectedItem();

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

    public void updateTeam(Team team) {
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

        if (data.containsTeam(name)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Team Already Exists", "A team already exists with the same name.");
        } else if (name.isEmpty() || link.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Empty Fields", "You must fill in all fields.");
        } else {
            jTPS_Transaction editTeam = new UpdateTeam(name, color, textColor, link, data, team, workspace);
            jTPS.addTransaction(editTeam);
            tab.getTeams().refresh();
            tab.clearTeamItems();
        }
    }

    public void removeTeam() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData data = (CSGData) app.getDataComponent();
        ProjectTab tab = workspace.getProjectTab();

        TableView table = tab.getTeams();
        Team team = (Team) table.getSelectionModel().getSelectedItem();

        jTPS_Transaction delTeam = new DeleteTeam(team, data);

        jTPS.addTransaction(delTeam);
        tab.clearTeamItems();

    }

    public void addStudent() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTab tab = workspace.getProjectTab();
        CSGData data = (CSGData) app.getDataComponent();

        String firstName = tab.getFirstNameTF().getText();
        String lastName = tab.getLastNameTF().getText();
        String team = tab.getTeamComboBox().getValue().toString();
        String role = tab.getRoleTextField().getText();

        Student stu = new Student(firstName, lastName, team, role);

        if (firstName.isEmpty() || lastName.isEmpty() || team.isEmpty() || role.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Empty Fields", "You must fill in all fields.");
        } else if (data.containsStudent(stu)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Student Exists", "A student with the same name exists in the same team.");
        } else {
            jTPS_Transaction addStu = new AddStudent(firstName, lastName, team, role, data);
            jTPS.addTransaction(addStu);
            tab.clearStuItems();
        }
    }

    public void editStudent() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTab tab = workspace.getProjectTab();

        TableView table = tab.getStudents();
        Student stu = (Student) table.getSelectionModel().getSelectedItem();

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

    public void updateStudent(Student stu) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTab tab = workspace.getProjectTab();
        CSGData data = (CSGData) app.getDataComponent();

        String firstName = tab.getFirstNameTF().getText();
        String lastName = tab.getLastNameTF().getText();
        String team = tab.getTeamComboBox().getValue().toString();
        String role = tab.getRoleTextField().getText();

        if (firstName.isEmpty() || lastName.isEmpty() || team.isEmpty() || role.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Empty Fields", "You must fill in all fields.");
        } else if (data.containsStudent(firstName, lastName)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show("Student Exists", "A student with the same name exists in the same team.");
        } else {
            jTPS_Transaction editStu = new UpdateStudent(firstName, lastName, team, role, stu, data, workspace);
            jTPS.addTransaction(editStu);
            tab.getStudents().refresh();
            tab.clearStuItems();
        }
    }

    public void removeStudent() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTab tab = workspace.getProjectTab();
        CSGData data = (CSGData) app.getDataComponent();

        TableView table = tab.getStudents();
        Student stu = (Student) table.getSelectionModel().getSelectedItem();

        jTPS_Transaction delStu = new DeleteStudent(stu, data);
        jTPS.addTransaction(delStu);
        tab.clearStuItems();
    }

    public void handleUndoRedo(KeyCode key, KeyEvent e) {
        final KeyCombination ctrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_ANY);
        final KeyCombination ctrlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_ANY);

        if (e.getCode() == Z) {
            undo();
            e.consume();
        } else if (e.getCode() == Y) {
            redo();
            e.consume();
        }
    }

    public void undo() {
        jTPS.undoTransaction();
    }

    public void redo() {
        jTPS.doTransaction();
    }
}
