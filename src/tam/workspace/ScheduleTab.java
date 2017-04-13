/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import static tam.CSGProp.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class ScheduleTab {
    Tab tab;
    
    Label schedule;
    
    VBox boundariesPane;
    Label title1;
    Label calStart;
    DatePicker startDate;
    Label calEnd;
    DatePicker endDate;
    
    VBox schedulePane;
    Label title2;
    Button hideButton;
    
    TableView<String> scheduleItems;
    TableColumn<String, String> typeColumn;
    TableColumn<String, String> dateColumn;
    TableColumn<String, String> titleColumn;
    TableColumn<String, String> topicColumn;
    
    Label addEditLabel;
    Label typeLabel;
    ComboBox typeComboBox; 
    Label dateLabel;
    DatePicker date;
    Label timeLabel;
    TextField timeTextField;
    Label titleLabel;
    TextField titleTextField;
    Label topicLabel;
    TextField topicTextField;
    Label linkLabel;
    TextField linkTextField;
    Label criteriaLabel;
    TextField criteriaTextField;
    
    Button addUpdate;
    Button clear;
    
    VBox mainPane;
    
    
    public ScheduleTab(){
        tab = new Tab();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        schedule = new Label(props.getProperty(SCHEDULE_LABEL));
        
        title1 = new Label(props.getProperty(CAL_BOUNDS_LABEL));
        calStart = new Label(props.getProperty(CAL_START));
        startDate = new DatePicker();
        calEnd = new Label(props.getProperty(CAL_END));
        endDate = new DatePicker();
        HBox box1 = new HBox();
        box1.getChildren().addAll(calStart, startDate, calEnd, endDate);
        
        boundariesPane = new VBox();
        boundariesPane.getChildren().addAll(title1, box1);
        
        title2 = new Label(props.getProperty(SCHEDULEITEMS_LABEL));
        hideButton = new Button("-");
        HBox box2 = new HBox();
        box2.getChildren().addAll(title2, hideButton);
        
        scheduleItems = new TableView();
        typeColumn = new TableColumn(props.getProperty(TYPETEXT));
        dateColumn = new TableColumn(props.getProperty(DATETEXT));
        titleColumn = new TableColumn(props.getProperty(TITLETEXT));
        topicColumn = new TableColumn(props.getProperty(TOPICTEXT));
        scheduleItems.getColumns().addAll(typeColumn, dateColumn,
                titleColumn, topicColumn);
        
        addEditLabel = new Label(props.getProperty(ADDEDIT_LABEL));
        typeLabel = new Label(props.getProperty(TYPETEXT));
        typeComboBox = new ComboBox();
        HBox box3 = new HBox();
        box3.getChildren().addAll(typeLabel, typeComboBox);
        
        dateLabel = new Label(props.getProperty(DATETEXT));
        date = new DatePicker();
        HBox box4 = new HBox();
        box4.getChildren().addAll(dateLabel, date);
        
        timeLabel = new Label(props.getProperty(TIMELABEL));
        timeTextField = new TextField();
        HBox box5 = new HBox();
        box5.getChildren().addAll(timeLabel, timeTextField);
        
        titleLabel = new Label(props.getProperty(TITLETEXT));
        titleTextField = new TextField();
        HBox box6 = new HBox();
        box6.getChildren().addAll(titleLabel, titleTextField);
        
        topicLabel = new Label(props.getProperty(TOPICTEXT));
        topicTextField = new TextField();
        HBox box7 = new HBox();
        box7.getChildren().addAll(topicLabel, topicTextField);
        
        linkLabel = new Label(props.getProperty(LINKLABEL));
        linkTextField = new TextField();
        HBox box8 = new HBox();
        box8.getChildren().addAll(linkLabel, linkTextField);
        
        criteriaLabel = new Label(props.getProperty(CRITERIALABEL));
        criteriaTextField = new TextField();
        HBox box9 = new HBox();
        box9.getChildren().addAll(criteriaLabel, criteriaTextField);
        
        schedulePane = new VBox();
        schedulePane.getChildren().addAll(box2, scheduleItems, box3,
                box4, box5, box6, box7,
                box8, box9);
        
        mainPane = new VBox();
        mainPane.getChildren().addAll(schedule, boundariesPane, schedulePane);
        tab.setText("Schedule Data");
        tab.setContent(mainPane);
    }
    
    public Tab getTab(){
        return tab;
    }
}
