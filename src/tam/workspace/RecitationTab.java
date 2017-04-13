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
        HBox box2 = new HBox();
        box2.getChildren().addAll(sectionLabel, sectionTextField);
        
        instructorLabel = new Label(props.getProperty(INSTRUCTOR_TEXT));
        instructorTextField = new TextField();
        HBox box3 = new HBox();
        box3.getChildren().addAll(instructorLabel, instructorTextField);
        
        dayTimeLabel = new Label(props.getProperty(DAY_TIME_TEXT));
        dayTimeTextField = new TextField();
        HBox box4 = new HBox();
        box4.getChildren().addAll(dayTimeLabel, dayTimeTextField);
        
        locationLabel = new Label(props.getProperty(LOCATION_TEXT));
        locationTextField = new TextField();
        HBox box5 = new HBox();
        box5.getChildren().addAll(locationLabel, locationTextField);
        
        supervisingLabel = new Label(props.getProperty(SUPERVISING_LABEL));
        supervisingLabel2 = new Label(props.getProperty(SUPERVISING_LABEL));
        ta1ComboBox = new ComboBox();
        ta2ComboBox = new ComboBox();
        HBox box6 = new HBox();
        box6.getChildren().addAll(supervisingLabel, ta1ComboBox);
        HBox box7 = new HBox();
        box7.getChildren().addAll(supervisingLabel2, ta2ComboBox);
        
        addUpdate = new Button(props.getProperty(ADDUPDATE_BUTTON));
        clear = new Button(props.getProperty(CLEAR_BUTTON));
        HBox box8 = new HBox();
        box8.getChildren().addAll(addUpdate, clear);
        
        addEditPane = new VBox();
        
        addEditPane.getChildren().addAll(addEdit, box2, box3, box4, box5, box6, box7, box8);
        
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
}
