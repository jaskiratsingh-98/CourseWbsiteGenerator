/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import javafx.collections.ObservableList;
import static tam.CSGProp.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import tam.CSGApp;
import tam.data.CSGData;
import tam.data.Schedule;

/**
 *
 * @author jaski
 */
public class ScheduleTab {
    CSGApp app;
    Tab tab;
    
    Label schedule;
    
    private VBox boundariesPane;
    Label title1;
    Label calStart;
    DatePicker startDate;
    Label calEnd;
    DatePicker endDate;
    
    private VBox schedulePane;
    Label title2;
    Button hideButton;
    
    TableView<Schedule> scheduleItems;
    TableColumn<Schedule, String> typeColumn;
    TableColumn<Schedule, String> dateColumn;
    TableColumn<Schedule, String> titleColumn;
    TableColumn<Schedule, String> topicColumn;
    
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
    
    private VBox mainPane;
    
    
    public ScheduleTab(CSGApp app){
        this.app = app;
        
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
        scheduleItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<Schedule> schData = data.getSchedule();
        scheduleItems.setItems(schData);
        
        typeColumn = new TableColumn(props.getProperty(TYPETEXT));
        dateColumn = new TableColumn(props.getProperty(DATETEXT));
        titleColumn = new TableColumn(props.getProperty(TITLETEXT));
        topicColumn = new TableColumn(props.getProperty(TOPICTEXT));
        
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );
        
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );
        
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        
        topicColumn.setCellValueFactory(
                new PropertyValueFactory<>("topic")
        );
        
        scheduleItems.getColumns().addAll(typeColumn, dateColumn,
                titleColumn, topicColumn);
        
        addEditLabel = new Label(props.getProperty(ADDEDIT_LABEL));
        typeLabel = new Label(props.getProperty(TYPETEXT));
        typeComboBox = new ComboBox();
        
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
        
        addUpdate = new Button(props.getProperty(ADDUPDATE_BUTTON));
        clear = new Button(props.getProperty(CLEAR_BUTTON));
        
        GridPane box3 = new GridPane();
        box3.setHgap(5.0);
        box3.setVgap(2.0);
        box3.add(typeLabel, 1, 0);
        box3.add(typeComboBox, 3, 0);
        box3.add(dateLabel, 1, 1);
        box3.add(date, 3, 1);
        box3.add(timeLabel, 1, 2);
        box3.add(timeTextField, 3, 2);
        box3.add(titleLabel, 1, 3);
        box3.add(titleTextField, 3, 3);
        box3.add(topicLabel, 1, 4);
        box3.add(topicTextField, 3, 4);
        box3.add(linkLabel, 1, 5);
        box3.add(linkTextField, 3, 5);
        box3.add(criteriaLabel, 1, 6);
        box3.add(criteriaTextField, 3, 6);
        box3.add(addUpdate, 1, 7);
        box3.add(clear, 3, 7);
        
        schedulePane = new VBox();
        schedulePane.getChildren().addAll(box2, scheduleItems, addEditLabel, box3);
        
        mainPane = new VBox();
        mainPane.getChildren().addAll(schedule, boundariesPane, schedulePane);
        tab.setText("Schedule Data");
        tab.setContent(mainPane);
    }
    
    public Tab getTab(){
        return tab;
    }

    public VBox getBoundariesPane() {
        return boundariesPane;
    }

    public VBox getSchedulePane() {
        return schedulePane;
    }

    public VBox getMainPane() {
        return mainPane;
    }

    public Label getSchedule() {
        return schedule;
    }

    public Label getTitle1() {
        return title1;
    }

    public Label getTitle2() {
        return title2;
    }

    public Label getAddEditLabel() {
        return addEditLabel;
    }
    
    
    
}
