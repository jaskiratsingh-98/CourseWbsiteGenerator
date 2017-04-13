/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import static tam.CSGProp.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author jaski
 */
public class RecitationTab {
    Tab tab;
    
    Label recitationLabel;
    Button hideButton;
    
    TableView<String> recitations;
    TableColumn<String, String> sectionColumn;
    TableColumn<String, String> instructorColumn;
    TableColumn<String, String> dayTimeColumn;
    TableColumn<String, String> locationColumn;
    TableColumn<String, String> ta1Column;
    TableColumn<String, String> ta2Column;
    
    Label addEdit;
    Label sectionLabel;
    TextField sectionTextField;
    Label instructorLabel;
    TextField instructorTextField;
    Label dayTimeLabel;
    TextField dayTimeTextField;
    Label locationLabel;
    TextField locationTextField;
    Label supervisingLabel;
    Label supervisingLabel2;
    
    ComboBox ta1ComboBox;
    ComboBox ta2ComboBox;
    
    Button addUpdate;
    Button clear;
    
    VBox mainPane;
    VBox addEditPane;
    
    public RecitationTab(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        tab = new Tab();
        
        recitationLabel = new Label(props.getProperty(RECITATION_LABEL));
        hideButton = new Button("-");
        
        HBox box1 = new HBox();
        box1.getChildren().addAll(recitationLabel, hideButton);
        
        recitations = new TableView();
        
        sectionColumn = new TableColumn(props.getProperty(SECTION_TEXT));
        instructorColumn = new TableColumn(props.getProperty(INSTRUCTOR_TEXT));
        dayTimeColumn = new TableColumn(props.getProperty(DAY_TIME_TEXT));
        locationColumn = new TableColumn(props.getProperty(LOCATION_TEXT));
        ta1Column = new TableColumn(props.getProperty(TA_TEXT));
        ta2Column = new TableColumn(props.getProperty(TA_TEXT));
        
        recitations.getColumns().addAll(sectionColumn, instructorColumn,
                dayTimeColumn, locationColumn,
                ta1Column, ta2Column);
        
        addEdit = new Label(props.getProperty(ADDEDIT_LABEL));
        
        sectionLabel = new Label(props.getProperty(SECTION_TEXT));
        sectionTextField = new TextField();
        
        instructorLabel = new Label(props.getProperty(INSTRUCTOR_TEXT));
        instructorTextField = new TextField();
        
        dayTimeLabel = new Label(props.getProperty(DAY_TIME_TEXT));
        dayTimeTextField = new TextField();
        
        locationLabel = new Label(props.getProperty(LOCATION_TEXT));
        locationTextField = new TextField();
        
        supervisingLabel = new Label(props.getProperty(SUPERVISING_LABEL));
        supervisingLabel2 = new Label(props.getProperty(SUPERVISING_LABEL));
        ta1ComboBox = new ComboBox();
        ta2ComboBox = new ComboBox();
        
        addUpdate = new Button(props.getProperty(ADDUPDATE_BUTTON));
        clear = new Button(props.getProperty(CLEAR_BUTTON));
        
        GridPane box2 = new GridPane();
        box2.setHgap(5.0);
        box2.setVgap(2.0);
        box2.add(sectionLabel, 1, 0);
        box2.add(sectionTextField, 3, 0);
        box2.add(instructorLabel, 1, 1);
        box2.add(instructorTextField, 3, 1);
        box2.add(dayTimeLabel, 1, 2);
        box2.add(dayTimeTextField, 3, 2);
        box2.add(locationLabel, 1, 3);
        box2.add(locationTextField, 3, 3);
        box2.add(supervisingLabel, 1, 4);
        box2.add(ta1ComboBox, 3, 4);
        box2.add(supervisingLabel2, 1, 5);
        box2.add(ta2ComboBox, 3, 5);
        box2.add(addUpdate, 1, 6);
        box2.add(clear, 3, 6);
        
        addEditPane = new VBox();
        
        addEditPane.getChildren().addAll(addEdit, box2);
        
        mainPane = new VBox();
        mainPane.getChildren().addAll(box1, recitations, addEditPane);
        tab.setText("Recitation Data");
        tab.setContent(mainPane);
    }
    
    public Tab getTab(){
        return tab;
    }

    public VBox getMainPane() {
        return mainPane;
    }

    public VBox getAddEditPane() {
        return addEditPane;
    }

    public TableView<String> getRecitations() {
        return recitations;
    }

    public Label getRecitationLabel() {
        return recitationLabel;
    }
    
}
