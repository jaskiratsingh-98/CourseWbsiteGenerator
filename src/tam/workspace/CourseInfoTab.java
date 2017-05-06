/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import tam.data.PageGenerateValueFactory;
import tam.data.Pages;

/**
 *
 * @author jaski
 */
public class CourseInfoTab {

    CSGApp app;
    CourseInfoController controller;
    Tab courseTab;

    ScrollPane borderPane;
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
    Label expDir;
    GridPane box1;

    Label title2;
    Label description;
    Button selectTemp;
    Label template;
    Label sitePages;
    Label exportDir;

    TableView<Pages> siteTable;
    TableColumn<Pages, CheckBox> useColumn;
    TableColumn<Pages, String> navBarColumn;
    TableColumn<Pages, String> fileColumn;
    TableColumn<Pages, String> scriptColumn;

    Label title3;
    Label bannerImage;
    ImageView bannerImageView;
    Button changeBanner;
    Label footerLeft;
    ImageView leftImageView;
    Button changeLeftFooter;
    Label footerRight;
    ImageView rightImageView;
    Button changeRightFooter;
    Label stylesheet;
    ComboBox chooseSheet;
    Label note;

    public CourseInfoTab(CSGApp app) {

        this.app = app;
        CSGData data = (CSGData) app.getDataComponent();
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
        ObservableList<String> semesters = FXCollections.observableArrayList();
        semesters.addAll(props.getPropertyOptionsList(SEMESTER_LIST));
        semesterComboBox.setItems(semesters);
        ObservableList<String> years = FXCollections.observableArrayList();
        int j = LocalDate.now().getYear() + 10;
        for(int i = LocalDate.now().getYear(); i < j; i++){
            years.add(Integer.toString(i));
        }
        year = new Label(props.getProperty(YEAR_LABEL));
        yearComboBox = new ComboBox();
        yearComboBox.setItems(years);

        title = new Label(props.getProperty(TITLE_LABEL));
        titleTextField = new TextField();

        name = new Label(props.getProperty(INSNAME_LABEL));
        nameTextField = new TextField();

        home = new Label(props.getProperty(HOME_LABEL));
        homeTextField = new TextField();

        exportDir = new Label(props.getProperty(DIR_LABEL));
        expDir = new Label(data.getExportDir());
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
        box1.add(expDir, 3, 5);
        box1.add(changeButton, 7, 5);

        infoPane.getChildren().addAll(title1, box1);

        title2 = new Label(props.getProperty(SITE_LABEL));
        description = new Label(props.getProperty(DESC_LABEL));

        selectTemp = new Button("Select Template Directory");
        template = new Label(data.getTemplate());
        sitePages = new Label(props.getProperty(SITE_PAGES_LABEL));

        siteTable = new TableView();
        siteTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Pages> pages = data.getPages();
        siteTable.setItems(pages);

        useColumn = new TableColumn(props.getProperty(USE_TEXT));
        navBarColumn = new TableColumn(props.getProperty(NAVBAR_TEXT));
        fileColumn = new TableColumn(props.getProperty(FILE_TEXT));
        scriptColumn = new TableColumn(props.getProperty(SCRIPT_TEXT));

        useColumn.setCellValueFactory(new PageGenerateValueFactory());
        navBarColumn.setCellValueFactory(
                new PropertyValueFactory<>("navbar")
        );

        fileColumn.setCellValueFactory(
                new PropertyValueFactory<>("fileName")
        );

        scriptColumn.setCellValueFactory(
                new PropertyValueFactory<>("script")
        );

        siteTable.getColumns().addAll(useColumn, navBarColumn, fileColumn, scriptColumn);
        siteTable.setMaxHeight(160);

        tempPane = new VBox();
        tempPane.getChildren().addAll(title2, description, selectTemp, template,
                sitePages, siteTable);

        title3 = new Label(props.getProperty(PAGE_LABEL));
        bannerImage = new Label(props.getProperty(BANNER_LABEL));
        bannerImageView = new ImageView();
        bannerImageView.setImage(new Image(data.getBannerImage()));
        changeBanner = new Button("Change");

        footerLeft = new Label(props.getProperty(FOOTER_LEFTLABEL));
        leftImageView = new ImageView(new Image(data.getLeftFooter()));
        changeLeftFooter = new Button("Change");

        footerRight = new Label(props.getProperty(FOOTER_RIGHTLABEL));
        rightImageView = new ImageView(new Image(data.getRightFooter()));
        changeRightFooter = new Button("Change");

        File[] f = new File("./work/css").listFiles();

        stylesheet = new Label(props.getProperty(STYLESHEET_LABEL));
        chooseSheet = new ComboBox();
        for (File a : f) {
            if (a.getPath().endsWith(".css")) {
                chooseSheet.getItems().add(a);
            }
        }

        note = new Label(props.getProperty(NOTE_LABEL));

        box2 = new GridPane();
        box2.setHgap(5.0);
        box2.setVgap(2.0);
        box2.add(bannerImage, 1, 0);
        box2.add(bannerImageView, 3, 0);
        box2.add(changeBanner, 7, 0);
        box2.add(footerLeft, 1, 1);
        box2.add(leftImageView, 3, 1);
        box2.add(changeLeftFooter, 7, 1);
        box2.add(footerRight, 1, 2);
        box2.add(rightImageView, 3, 2);
        box2.add(changeRightFooter, 7, 2);
        box2.add(stylesheet, 1, 3);
        box2.add(chooseSheet, 3, 3);

        stylePane = new VBox();
        stylePane.getChildren().addAll(title3, box2, note);

        courseTab = new Tab();

        mainPane.getChildren().addAll(infoPane, tempPane, stylePane);
        mainPane.setMinWidth(1300);

        borderPane = new ScrollPane();
        borderPane.setContent(mainPane);
        borderPane.fitToWidthProperty();

        courseTab.setText("Course Details");
        courseTab.setContent(borderPane);

        controller = new CourseInfoController(app);

        changeButton.setOnAction(e -> {
            controller.setExportDir();
        });

        changeBanner.setOnAction(e -> {
            controller.setBanner();
        });

        changeLeftFooter.setOnAction(e -> {
            controller.setLeftFooter();
        });

        changeRightFooter.setOnAction(e -> {
            controller.setRightFooter();
        });

        chooseSheet.setOnAction(e -> {
            controller.setStyleSheet();
        });

        selectTemp.setOnAction(e -> {
            controller.setTemplateDir();
        });
    }

    public Tab getTab() {
        return courseTab;
    }

    public Label getTitle1() {
        return title1;
    }

    public VBox getInfoPane() {
        return infoPane;
    }

    public VBox getMainPane() {
        return mainPane;
    }

    public GridPane getBox1() {
        return box1;
    }

    public VBox getTempPane() {
        return tempPane;
    }

    public VBox getStylePane() {
        return stylePane;
    }

    public ScrollPane getBorderPane() {
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

    public void setExpDir(String a) {
        expDir.setText(a);
    }

    public void setTemplate(String a) {
        template.setText(a);
    }

    public void setBannerImageView(String imageFile) {
        bannerImageView.setImage(new Image("file:" + imageFile));
    }

    public void setLeftImageView(String imageFile) {
        leftImageView.setImage(new Image("file:" + imageFile));
    }

    public void setRightImageView(String imageFile) {
        rightImageView.setImage(new Image("file:" + imageFile));
    }

    public ComboBox getChooseSheet() {
        return chooseSheet;
    }

}
