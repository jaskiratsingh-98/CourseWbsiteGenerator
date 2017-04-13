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
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import properties_manager.PropertiesManager;
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

/**
 *
 * @author jaski
 */
public class CourseInfoTab {

    Tab courseTab;

    VBox mainPane;
    VBox infoPane;
    VBox tempPane;
    VBox stylePane;

    Label title1;
    Label subject;
    ComboBox subjectComboBox;
    Label number;
    ComboBox numberComboBox;
    HBox box1;

    Label semester;
    ComboBox semesterComboBox;
    Label year;
    ComboBox yearComboBox;
    HBox box2;

    Label title;
    TextField titleTextField;
    HBox box3;

    Label name;
    TextField nameTextField;
    HBox box4;

    Label home;
    TextField homeTextField;
    HBox box5;

    Button changeButton;

    Label title2;
    Label description;
    Button selectTemp;
    Label sitePages;

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

    public CourseInfoTab() {
        mainPane = new VBox();
        infoPane = new VBox();

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        title1 = new Label(props.getProperty(COURSE_INFO_LABEL));

        subject = new Label(props.getProperty(SUBJECT_LABEL));
        subjectComboBox = new ComboBox();
        number = new Label(props.getProperty(NUMBER_LABEL));
        numberComboBox = new ComboBox();
        box1 = new HBox();
        box1.getChildren().addAll(subject, subjectComboBox, number, numberComboBox);

        semester = new Label(props.getProperty(SEMESTER_LABEL));
        semesterComboBox = new ComboBox();
        year = new Label(props.getProperty(YEAR_LABEL));
        yearComboBox = new ComboBox();
        box2 = new HBox();
        box2.getChildren().addAll(semester, semesterComboBox, year, yearComboBox);

        title = new Label(props.getProperty(TITLE_LABEL));
        titleTextField = new TextField();
        box3 = new HBox();
        box3.getChildren().addAll(title, titleTextField);

        name = new Label(props.getProperty(INSNAME_LABEL));
        nameTextField = new TextField();
        box4 = new HBox();
        box4.getChildren().addAll(name, nameTextField);

        home = new Label(props.getProperty(HOME_LABEL));
        homeTextField = new TextField();
        box5 = new HBox();
        box5.getChildren().addAll(home, homeTextField);

        changeButton = new Button("Change");

        infoPane.getChildren().addAll(title1, box1, box2, box3, box4, box5, changeButton);

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

        stylePane = new VBox();
        stylePane.getChildren().addAll(title3, box6, box7, box8, box9, note);

        courseTab = new Tab();

        mainPane.getChildren().addAll(infoPane, tempPane, stylePane);

        courseTab.setText("Course Details");
        courseTab.setContent(mainPane);
    }

    public Tab getTab() {
        return courseTab;
    }

}
