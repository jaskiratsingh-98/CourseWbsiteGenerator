package tam.style;

import djf.AppTemplate;
import djf.components.AppStyleComponent;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tam.data.TeachingAssistant;
import tam.workspace.CSGWorkspace;

/**
 * This class manages all CSS style for this application.
 *
 * @author Richard McKenna
 * @version 1.0
 */
public class CSGStyle extends AppStyleComponent {
    // FIRST WE SHOULD DECLARE ALL OF THE STYLE TYPES WE PLAN TO USE

    // WE'LL USE THIS FOR ORGANIZING LEFT AND RIGHT CONTROLS
    public static String CLASS_PLAIN_PANE = "plain_pane";

    // THESE ARE THE HEADERS FOR EACH SIDE
    public static String CLASS_HEADER_PANE = "header_pane";
    public static String CLASS_HEADER_LABEL = "header_label";

    // ON THE LEFT WE HAVE THE TA ENTRY
    public static String CLASS_TA_TABLE = "ta_table";
    public static String CLASS_TA_TABLE_COLUMN_HEADER = "ta_table_column_header";
    public static String CLASS_ADD_TA_PANE = "add_ta_pane";
    public static String CLASS_ADD_TA_TEXT_FIELD = "add_ta_text_field";
    public static String CLASS_ADD_TA_BUTTON = "add_ta_button";

    // ON THE RIGHT WE HAVE THE OFFICE HOURS GRID
    public static String CLASS_OFFICE_HOURS_GRID = "office_hours_grid";
    public static String CLASS_OFFICE_HOURS_GRID_COLUMN_HEADER_PANE = "office_hours_grid_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL = "office_hours_grid_time_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE = "office_hours_grid_day_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL = "office_hours_grid_day_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE = "office_hours_grid_time_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL = "office_hours_grid_time_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE = "office_hours_grid_ta_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL = "office_hours_grid_ta_cell_label";

    // FOR HIGHLIGHTING CELLS, COLUMNS, AND ROWS
    public static String CLASS_HIGHLIGHTED_GRID_CELL = "highlighted_grid_cell";
    public static String CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN = "highlighted_grid_row_or_column";
    public static String CLASS_TITLE_LABEL = "ci_title_label";
    public static String CLASS_CI_INFO_PANE = "ci_info_pane";
    public static String CLASS_MAIN_PANE = "csg_main_pane";
    public static String CLASS_SUB_PANE = "csg_sub_pane";
    public static String CLASS_SUB_SC_PANE = "sch_sub_pane";
    public static String CLASS_OVERALL_PANE = "csg_overall_pane";
    public static String CLASS_CI_BOX_PANE = "ci_box1";
    public static String CLASS_COMBO_BOX = "combo_box";
    public static String CLASS_CI_TEXTFIELD = "ci_textfield";
    public static String CLASS_MAIN_LABEL = "main_label";
    public static String CLASS_SUB_LABEL = "sub_label";
    public static String CLASS_COLOR_PICKER = "color_picker";
    
    public static String CLASS_TA_MAIN_PANE = "ta_main_pane";
    
    public static String CLASS_PT_MAIN_PANE = "pt_main_pane";
    public static String CLASS_PT_TEAM_PANE = "pt_team_pane";
    public static String CLASS_PT_STUDENT_PANE = "pt_student_pane";
    
    public static String CLASS_REC_TABLE = "rec_table";
    
    // THIS PROVIDES ACCESS TO OTHER COMPONENTS
    private AppTemplate app;

    /**
     * This constructor initializes all style for the application.
     *
     * @param initApp The application to be stylized.
     */
    public CSGStyle(AppTemplate initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // LET'S USE THE DEFAULT STYLESHEET SETUP
        super.initStylesheet(app);

        // INIT THE STYLE FOR THE FILE TOOLBAR
        app.getGUI().initFileToolbarStyle();

        // AND NOW OUR WORKSPACE STYLE
        initTAWorkspaceStyle();
    }

    /**
     * This function specifies all the style classes for all user interface
     * controls in the workspace.
     */
    private void initTAWorkspaceStyle() {
        // LEFT SIDE - THE HEADER
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        workspaceComponent.getBorderPane().getStyleClass().add(CLASS_OVERALL_PANE);
        
        //Course Info Tab
        workspaceComponent.getCourseTab().getTitle1().getStyleClass().add(CLASS_TITLE_LABEL);
        workspaceComponent.getCourseTab().getTitle2().getStyleClass().add(CLASS_TITLE_LABEL);
        workspaceComponent.getCourseTab().getTitle3().getStyleClass().add(CLASS_TITLE_LABEL);
        workspaceComponent.getCourseTab().getInfoPane().getStyleClass().add(CLASS_SUB_PANE);
        workspaceComponent.getCourseTab().getMainPane().getStyleClass().add(CLASS_MAIN_PANE);
        workspaceComponent.getCourseTab().getTempPane().getStyleClass().add(CLASS_SUB_PANE);
        workspaceComponent.getCourseTab().getStylePane().getStyleClass().add(CLASS_SUB_PANE);
        workspaceComponent.getCourseTab().getBox1().getStyleClass().add(CLASS_CI_BOX_PANE);
        workspaceComponent.getCourseTab().getSubjectComboBox().getStyleClass().add(CLASS_COMBO_BOX);
        workspaceComponent.getCourseTab().getSemesterComboBox().getStyleClass().add(CLASS_COMBO_BOX);
        workspaceComponent.getCourseTab().getNumberComboBox().getStyleClass().add(CLASS_COMBO_BOX);
        workspaceComponent.getCourseTab().getYearComboBox().getStyleClass().add(CLASS_COMBO_BOX);
        workspaceComponent.getCourseTab().getNameTextField().getStyleClass().add(CLASS_CI_TEXTFIELD);
        workspaceComponent.getCourseTab().getHomeTextField().getStyleClass().add(CLASS_CI_TEXTFIELD);
        workspaceComponent.getCourseTab().getTitleTextField().getStyleClass().add(CLASS_CI_TEXTFIELD);
        
        //TA Tab
        workspaceComponent.getTATab().getMainPane().getStyleClass().add(CLASS_TA_MAIN_PANE);
        workspaceComponent.getTATab().getOverallPane().getStyleClass().add(CLASS_MAIN_PANE);
        workspaceComponent.getTATab().getTAsHeaderLabel().getStyleClass().add(CLASS_MAIN_LABEL);
        workspaceComponent.getTATab().getOfficeHoursSubheaderLabel().getStyleClass().add(CLASS_MAIN_LABEL);
        
        TableView<TeachingAssistant> taTable = workspaceComponent.getTATab().getTATable();
        taTable.getStyleClass().add(CLASS_TA_TABLE);
//        for (TableColumn tableColumn : taTable.getColumns()) {
//            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
//        }
        
        //Recitations Tab
        workspaceComponent.getRecitationTab().getMainPane().getStyleClass().add(CLASS_MAIN_PANE);
        workspaceComponent.getRecitationTab().getAddEditPane().getStyleClass().add(CLASS_SUB_PANE);
        workspaceComponent.getRecitationTab().getRecitations().getStyleClass().add(CLASS_REC_TABLE);
        workspaceComponent.getRecitationTab().getRecitationLabel().getStyleClass().add(CLASS_MAIN_LABEL);
        workspaceComponent.getRecitationTab().getAddEdit().getStyleClass().add(CLASS_SUB_LABEL);
        
        //Schedule Tab
        workspaceComponent.getScheduleTab().getMainPane().getStyleClass().add(CLASS_MAIN_PANE);
        workspaceComponent.getScheduleTab().getSchedulePane().getStyleClass().add(CLASS_SUB_PANE);
        workspaceComponent.getScheduleTab().getBoundariesPane().getStyleClass().add(CLASS_SUB_PANE);
        workspaceComponent.getScheduleTab().getBoundariesPane().getStyleClass().add(CLASS_SUB_SC_PANE);
        workspaceComponent.getScheduleTab().getSchedule().getStyleClass().add(CLASS_MAIN_LABEL);
        workspaceComponent.getScheduleTab().getTitle1().getStyleClass().add(CLASS_SUB_LABEL);
        workspaceComponent.getScheduleTab().getTitle2().getStyleClass().add(CLASS_SUB_LABEL);
        workspaceComponent.getScheduleTab().getAddEditLabel().getStyleClass().add(CLASS_SUB_LABEL);
        
        //Projects Tab
        workspaceComponent.getProjectTab().getMainPane().getStyleClass().add(CLASS_MAIN_PANE);
        workspaceComponent.getProjectTab().getTeamPane().getStyleClass().add(CLASS_SUB_PANE);
        workspaceComponent.getProjectTab().getStudentsPane().getStyleClass().add(CLASS_SUB_PANE);
        workspaceComponent.getProjectTab().getTitle().getStyleClass().add(CLASS_MAIN_LABEL);
        workspaceComponent.getProjectTab().getStudentTitle().getStyleClass().add(CLASS_SUB_LABEL);
        workspaceComponent.getProjectTab().getTeamTitle().getStyleClass().add(CLASS_SUB_LABEL);
        workspaceComponent.getProjectTab().getAddEdit().getStyleClass().add(CLASS_SUB_LABEL);
        workspaceComponent.getProjectTab().getColorPicker().getStyleClass().add(CLASS_COLOR_PICKER);
        workspaceComponent.getProjectTab().getTextColorPicker().getStyleClass().add(CLASS_COLOR_PICKER);
    }

    /**
     * This method initializes the style for all UI components in the office
     * hours grid. Note that this should be called every time a new TA Office
     * Hours Grid is created or loaded.
     */
    public void initOfficeHoursGridStyle() {
        // RIGHT SIDE - THE OFFICE HOURS GRID TIME HEADERS
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        workspaceComponent.getTATab().getOfficeHoursGridPane().getStyleClass().add(CLASS_OFFICE_HOURS_GRID);
        setStyleClassOnAll(workspaceComponent.getTATab().getOfficeHoursGridTimeHeaderPanes(), CLASS_OFFICE_HOURS_GRID_COLUMN_HEADER_PANE);
//        setStyleClassOnAll(workspaceComponent.getTATab().getOfficeHoursGridTimeHeaderLabels(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getTATab().getOfficeHoursGridDayHeaderPanes(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE);
//        setStyleClassOnAll(workspaceComponent.getTATab().getOfficeHoursGridDayHeaderLabels(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getTATab().getOfficeHoursGridTimeCellPanes(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE);
//        setStyleClassOnAll(workspaceComponent.getTATab().getOfficeHoursGridTimeCellLabels(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL);
        setStyleClassOnAll(workspaceComponent.getTATab().getOfficeHoursGridTACellPanes(), CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
//        setStyleClassOnAll(workspaceComponent.getTATab().getOfficeHoursGridTACellLabels(), CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL);
    }

    /**
     * This helper method initializes the style of all the nodes in the nodes
     * map to a common style, styleClass.
     */
    private void setStyleClassOnAll(HashMap nodes, String styleClass) {
        for (Object nodeObject : nodes.values()) {
            Node n = (Node) nodeObject;
            n.getStyleClass().add(styleClass);
        }
    }
}
