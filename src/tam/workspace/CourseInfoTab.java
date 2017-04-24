/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import properties_manager.PropertiesManager;
import tam.CSGApp;
import static tam.CSGProp.*;
import static tam.CSGProp.COURSE_INFO_LABEL;
import static tam.CSGProp.HOME_LABEL;
import static tam.CSGProp.INSNAME_LABEL;
import static tam.CSGProp.NUMBER_LABEL;
import static tam.CSGProp.SEMESTER_LABEL;
import static tam.CSGProp.SITE_LABEL;
import static tam.CSGProp.SUBJECT_LABEL;
import static tam.CSGProp.TITLE_LABEL;
import static tam.CSGProp.YEAR_LABEL;
import tam.data.CSGData;
import tam.data.CourseInfo;

/**
 *
 * @author jaski
 */
public class CourseInfoTab {
    CSGApp app;
    Tab courseTab;

    VBox borderPane;
    VBox mainPane;
    VBox infoPane;
    VBox tempPane;
    VBox stylePane;

    Label title1;
    Label subject;
    private ComboBox subjectComboBox;
    Label number;
    ComboBox numberComboBox;

    Label semester;
    private ComboBox semesterComboBox;
    Label year;
    ComboBox yearComboBox;
    GridPane box2;

    Label title;
    private TextField titleTextField;
    HBox box3;

    Label name;
    private TextField nameTextField;
    HBox box4;

    Label home;
    private TextField homeTextField;
    HBox box5;

    Button changeButton;
    GridPane box1;

    Label title2;
    Label description;
    Button selectTemp;
    Label sitePages;
    Label exportDir;

    TableView<String> siteTable;
    TableColumn<String, String> useColumn;
    TableColumn<String, String> navBarColumn;
    TableColumn<String, String> fileColumn;
    TableColumn<String, String> scriptColumn;

    Label title3;
    Label bannerImage;
    Button changeBanner;
    Label footerLeft;
    Button changeLeftFooter;
    Label footerRight;
    Button changeRightFooter;
    Label stylesheet;
    ComboBox chooseSheet;
    Label note;

    public CourseInfoTab(CSGApp app) {
        this.app = app;
        CSGData data = (CSGData)app.getDataComponent();
        CourseInfo courseInfo = data.getCourseInfo();
        mainPane = new VBox();
        infoPane = new VBox();

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        title1 = new Label(props.getProperty(COURSE_INFO_LABEL));

        subject = new Label(props.getProperty(SUBJECT_LABEL));
        subjectComboBox = new ComboBox();
        number = new Label(props.getProperty(NUMBER_LABEL));
        numberComboBox = new ComboBox();

        semester = new Label(props.getProperty(SEMESTER_LABEL));
        semesterComboBox = new ComboBox();
        year = new Label(props.getProperty(YEAR_LABEL));
        yearComboBox = new ComboBox();

        title = new Label(props.getProperty(TITLE_LABEL));
        titleTextField = new TextField();

        name = new Label(props.getProperty(INSNAME_LABEL));
        nameTextField = new TextField();

        home = new Label(props.getProperty(HOME_LABEL));
        homeTextField = new TextField();

        exportDir = new Label(props.getProperty(DIR_LABEL));
        changeButton = new Button("Change");
        
        box1 = new GridPane();
        box1.setHgap(5.0);
        box1.setVgap(2.0);
        box1.add(subject, 1, 0);
        box1.add(subjectComboBox, 3, 0);
        box1.add(number, 7, 0);
        box1.add(numberComboBox, 9, 0);
        box1.add(semester, 1, 1);
        box1.add(semesterComboBox, 3, 1);
        box1.add(year, 7, 1);
        box1.add(yearComboBox, 9, 1);
        box1.add(title, 1, 2);
        box1.add(titleTextField, 3, 2);
        box1.add(name, 1, 3);
        box1.add(nameTextField, 3, 3);
        box1.add(home, 1, 4);
        box1.add(homeTextField, 3, 4);
        box1.add(exportDir, 1, 5);
        box1.add(changeButton, 7, 5);

        infoPane.getChildren().addAll(title1, box1);

        title2 = new Label(props.getProperty(SITE_LABEL));
        description = new Label(props.getProperty(DESC_LABEL));

        selectTemp = new Button("Select Template Directory");
        sitePages = new Label(props.getProperty(SITE_PAGES_LABEL));

        siteTable = new TableView();
        useColumn = new TableColumn(props.getProperty(USE_TEXT));
        navBarColumn = new TableColumn(props.getProperty(NAVBAR_TEXT));
        fileColumn = new TableColumn(props.getProperty(FILE_TEXT));
        scriptColumn = new TableColumn(props.getProperty(SCRIPT_TEXT));

        siteTable.getColumns().addAll(useColumn, navBarColumn, fileColumn, scriptColumn);

        tempPane = new VBox();
        tempPane.getChildren().addAll(title2, description, selectTemp, sitePages, siteTable);

        title3 = new Label(props.getProperty(PAGE_LABEL));

        bannerImage = new Label(props.getProperty(BANNER_LABEL));
        changeBanner = new Button("Change");
        HBox box6 = new HBox();
        box6.getChildren().addAll(bannerImage, changeBanner);

        footerLeft = new Label(props.getProperty(FOOTER_LEFTLABEL));
        changeLeftFooter = new Button("Change");
        HBox box7 = new HBox();
        box7.getChildren().addAll(footerLeft, changeLeftFooter);

        footerRight = new Label(props.getProperty(FOOTER_RIGHTLABEL));
        changeRightFooter = new Button("Change");
        HBox box8 = new HBox();
        box8.getChildren().addAll(footerRight, changeRightFooter);

        stylesheet = new Label(props.getProperty(STYLESHEET_LABEL));
        chooseSheet = new ComboBox();
        HBox box9 = new HBox();
        box9.getChildren().addAll(stylesheet, chooseSheet);

        note = new Label(props.getProperty(NOTE_LABEL));
        
        box2 = new GridPane();
        box2.setHgap(5.0);
        box2.setVgap(2.0);
        box2.add(bannerImage, 1, 0);
        box2.add(changeBanner, 3, 0);
        box2.add(footerLeft, 1, 1);
        box2.add(changeLeftFooter, 3, 1);
        box2.add(footerRight, 1, 2);
        box2.add(changeRightFooter, 3, 2);
        box2.add(stylesheet, 1, 3);
        box2.add(chooseSheet, 3, 3);

        stylePane = new VBox();
        stylePane.getChildren().addAll(title3, box2, note);

        courseTab = new Tab();

        mainPane.getChildren().addAll(infoPane, tempPane, stylePane);
        
        borderPane = new VBox();
        borderPane.getChildren().add(mainPane);

        courseTab.setText("Course Details");
        courseTab.setContent(borderPane);

    }

    public Tab getTab() {
        return courseTab;
    }

    public Label getTitle1() {
        return title1;
    }
    
    public VBox getInfoPane(){
        return infoPane;
    }
    
    public VBox getMainPane(){
        return mainPane;
    }
    
    public GridPane getBox1(){
        return box1;
    }
    
    public VBox getTempPane(){
        return tempPane;
    }
    
    public VBox getStylePane(){
        return stylePane;
    }
    
    public VBox getBorderPane(){
        return borderPane;
    }

    public ComboBox getSubjectComboBox() {
        return subjectComboBox;
    }

    public ComboBox getSemesterComboBox() {
        return semesterComboBox;
    }

    public ComboBox getNumberComboBox() {
        return numberComboBox;
    }

    public TextField getTitleTextField() {
        return titleTextField;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getHomeTextField() {
        return homeTextField;
    }

    public ComboBox getYearComboBox() {
        return yearComboBox;
    }

    public Label getTitle2() {
        return title2;
    }

    public Label getTitle3() {
        return title3;
    }

    
    
}
