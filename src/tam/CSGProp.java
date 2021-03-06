package tam;

/**
 * This enum provides a list of all the user interface
 * text that needs to be loaded from the XML properties
 * file. By simply changing the XML file we could initialize
 * this application such that all UI controls are provided
 * in another language.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public enum CSGProp {
    // FOR SIMPLE OK/CANCEL DIALOG BOXES
    OK_PROMPT,
    CANCEL_PROMPT,
    
    // THESE ARE FOR TEXT PARTICULAR TO THE APP'S WORKSPACE CONTROLS
    TAS_HEADER_TEXT,
    NAME_COLUMN_TEXT,
    EMAIL_COLUMN_TEXT,
    NAME_PROMPT_TEXT,
    EMAIL_PROMPT_TEXT,
    ADD_BUTTON_TEXT,
    CLEAR_BUTTON_TEXT,
    OFFICE_HOURS_SUBHEADER,
    OFFICE_HOURS_TABLE_HEADERS,
    HOURS_LIST,
    DAYS_OF_WEEK,
    DAYS_OF_WEEK1,
    
    // THESE ARE FOR ERROR MESSAGES PARTICULAR TO THE APP
    MISSING_TA_NAME_TITLE,
    MISSING_TA_NAME_MESSAGE,
    MISSING_TA_EMAIL_TITLE,
    MISSING_TA_EMAIL_MESSAGE,
    TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE,
    TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE,
    EMAIL_ADDRESS_NOT_VALID,
    EMAIL_ADDRESS_NOT_VALID_MESSAGE,
    TA_NOT_CHANGED_TITLE,
    TA_NOT_CHANGED_MESSAGE,
    INVALID_START_HOUR_TITLE,
    INVALID_START_HOUR_MESSAGE,
    INVALID_END_HOUR_TITLE,
    INVALID_END_HOUR_MESSAGE,
    
    // FILE PATHS
    SYLLABUS,
    JS_GRID_BUILDER,
    JS_RECITATIONS_BUILDER,
    JS_RECITATIONS_DATA,
    SYLLABUS_PATH,
    JS_GRID_BUILDER_PATH,
    JS_OFFICE_HOURS_PATH,
    JS_RECITATIONS_BUILDER_PATH,
    JS_RECITATIONS_DATA_PATH,
    JS_FOLDER,
    CSS_FOLDER,
    IMAGES_FOLDER,
    TA_TOBE_DELETED_MESSAGE,
    CONTINUE_TITLE,
    SUBJECT_LABEL,
    NUMBER_LABEL,
    SEMESTER_LABEL,
    YEAR_LABEL,
    TITLE_LABEL,
    INSNAME_LABEL,
    HOME_LABEL,
    COURSE_INFO_LABEL,
    SITE_LABEL,
    DESC_LABEL,
    SITE_PAGES_LABEL,
    USE_TEXT,
    NAVBAR_TEXT,
    FILE_TEXT,
    SCRIPT_TEXT,
    PAGE_LABEL,
    BANNER_LABEL,
    FOOTER_LEFTLABEL,
    FOOTER_RIGHTLABEL,
    STYLESHEET_LABEL,
    NOTE_LABEL,
    
    //Recitation Label
    RECITATION_LABEL,
    SECTION_TEXT,
    INSTRUCTOR_TEXT,
    DAY_TIME_TEXT,
    LOCATION_TEXT,
    TA_TEXT,
    ADDEDIT_LABEL,
    SUPERVISING_LABEL,
    ADDUPDATE_BUTTON,
    CLEAR_BUTTON,
    SCHEDULE_LABEL,
    CAL_BOUNDS_LABEL,
    CAL_START,
    CAL_END,
    SCHEDULEITEMS_LABEL,
    TYPETEXT,
    DATETEXT,
    TITLETEXT,
    TOPICTEXT,
    TIMELABEL,
    LINKLABEL,
    CRITERIALABEL,
    PROJECTLABEL,
    TEAMS_LABEL,
    NAME_TEXT,
    COLOR_TEXT,
    TEXTCOLOR_TEXT,
    LINK_TEXT,
    NAME_LABEL,
    COLOR_LABEL,
    TEXTCOLOR_LABEL,
    LINK_LABEL,
    FIRSTNAME_TEXT,
    LASTNAME_TEXT,
    TEAM_TEXT,
    ROLE_TEXT,
    DIR_LABEL,
    UNDERGRAD_TEXT,
    STUDENTS_LABEL,
    EXPORT_TITLE,
    SCHEDULE_TYPES,
    INVALID_DATE_TITLE,
    NOT_MONDAY_MESSAGE,
    NOT_FRIDAY_MESSAGE,
    SEMESTER_LIST
}
