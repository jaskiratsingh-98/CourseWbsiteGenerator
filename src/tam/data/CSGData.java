package tam.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import djf.components.AppDataComponent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import tam.CSGApp;
import tam.CSGProp;
import tam.workspace.CSGWorkspace;

/**
 * This is the data component for TAManagerApp. It has all the data needed to be
 * set by the user via the User Interface and file I/O can set and get all the
 * data from this object
 *
 * @author Richard McKenna
 */
public class CSGData implements AppDataComponent {

    // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES
    CSGApp app;
    
    CourseInfo courseInfo;
    ObservableList<Recitation> recitations;
    ObservableList<Schedule> schedule;
    ObservableList<Team> teams;
    ObservableList<Student> students;
    ObservableList<Pages> pages;
    StringProperty exportDir;
    StringProperty template;
    StringProperty bannerImage;
    StringProperty rightFooter;
    StringProperty leftFooter;
    StringProperty styleSheet;

    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<TeachingAssistant> teachingAssistants;

    // THIS WILL STORE ALL THE OFFICE HOURS GRID DATA, WHICH YOU
    // SHOULD NOTE ARE StringProperty OBJECTS THAT ARE CONNECTED
    // TO UI LABELS, WHICH MEANS IF WE CHANGE VALUES IN THESE
    // PROPERTIES IT CHANGES WHAT APPEARS IN THOSE LABELS
    HashMap<String, StringProperty> officeHours;

    // THESE ARE THE LANGUAGE-DEPENDENT VALUES FOR
    // THE OFFICE HOURS GRID HEADERS. NOTE THAT WE
    // LOAD THESE ONCE AND THEN HANG ON TO THEM TO
    // INITIALIZE OUR OFFICE HOURS GRID
    ArrayList<String> gridHeaders;
    ObservableList<String> office;

    // THESE ARE THE TIME BOUNDS FOR THE OFFICE HOURS GRID. NOTE
    // THAT THESE VALUES CAN BE DIFFERENT FOR DIFFERENT FILES, BUT
    // THAT OUR APPLICATION USES THE DEFAULT TIME VALUES AND PROVIDES
    // NO MEANS FOR CHANGING THESE VALUES
    int startHour;
    int endHour;
    
    int startingMondayMonth;
    int startingMondayDay;
    int endingFridayMonth;
    int endingFridayDay;

    // DEFAULT VALUES FOR START AND END HOURS IN MILITARY HOURS
    public static final int MIN_START_HOUR = 9;
    public static final int MAX_END_HOUR = 20;

    /**
     * This constructor will setup the required data structures for use, but
     * will have to wait on the office hours grid, since it receives the
     * StringProperty objects from the Workspace.
     *
     * @param initApp The application this data manager belongs to.
     */
    
    public CSGData(){
        courseInfo = new CourseInfo();
        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        teachingAssistants = FXCollections.observableArrayList();
        recitations = FXCollections.observableArrayList();
        schedule = FXCollections.observableArrayList();
        teams = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();
        template.set("./templates");
        bannerImage.set("file:./images/Open.png");

        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;

        //THIS WILL STORE OUR OFFICE HOURS
        officeHours = new HashMap();

        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
//        PropertiesManager props = PropertiesManager.getPropertiesManager();
//        ArrayList<String> timeHeaders = props.getPropertyOptionsList(CSGProp.OFFICE_HOURS_TABLE_HEADERS);
//        ArrayList<String> dowHeaders = props.getPropertyOptionsList(CSGProp.DAYS_OF_WEEK1);
        gridHeaders = new ArrayList();
        gridHeaders.add("Start");
        gridHeaders.add("End");
        gridHeaders.add("Mon");
        
    }
    public CSGData(CSGApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        courseInfo = new CourseInfo();
        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        teachingAssistants = FXCollections.observableArrayList();
        recitations = FXCollections.observableArrayList();
        schedule = FXCollections.observableArrayList();
        teams = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();
        pages = FXCollections.observableArrayList();
        template = new SimpleStringProperty("./templates");
        exportDir = new SimpleStringProperty("./export");
        
        bannerImage = new SimpleStringProperty("file:./SBUDarkRedShieldLogo.png");
        rightFooter = new SimpleStringProperty("file:./CSLogo.png");
        leftFooter = new SimpleStringProperty("file:./SBUWhiteShieldLogo.jpg");
        styleSheet = new SimpleStringProperty("");
        
        pages.add(new Pages("Home","index.html","HomeBuilder.js"));
        pages.add(new Pages("Syllabus","syllabus.html","SyllabusBuilder.js"));
        pages.add(new Pages("Schedule","schedule.html","ScheduleBuilder.js"));
        pages.add(new Pages("HWs","hws.html","HWsBuilder.js"));
        pages.add(new Pages("Projects","projects.html","ProjectsBuilder.js"));

        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;

        //THIS WILL STORE OUR OFFICE HOURS
        officeHours = new HashMap();
        office = FXCollections.observableArrayList();

        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> timeHeaders = props.getPropertyOptionsList(CSGProp.OFFICE_HOURS_TABLE_HEADERS);
        ArrayList<String> dowHeaders = props.getPropertyOptionsList(CSGProp.DAYS_OF_WEEK1);
        gridHeaders = new ArrayList();
        gridHeaders.addAll(timeHeaders);
        gridHeaders.addAll(dowHeaders);
    }

    /**
     * Called each time new work is created or loaded, it resets all data and
     * data structures such that they can be used for new values.
     */
    @Override
    public void resetData() {
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        teachingAssistants.clear();
        recitations.clear();
        schedule.clear();
        teams.clear();
        students.clear();
        officeHours.clear();
    }

    // ACCESSOR METHODS
    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public ArrayList<String> getGridHeaders() {
        return gridHeaders;
    }

    public ObservableList getTeachingAssistants() {
        return teachingAssistants;
    }

    public ObservableList<Recitation> getRecitations() {
        return recitations;
    }

    public ObservableList<Schedule> getSchedule() {
        return schedule;
    }

    public ObservableList<Team> getTeams() {
        return teams;
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    public String getCellKey(int col, int row) {
        return col + "_" + row;
    }

    public StringProperty getCellTextProperty(int col, int row) {
        String cellKey = getCellKey(col, row);
        return officeHours.get(cellKey);
    }

    public HashMap<String, StringProperty> getOfficeHours() {
        return officeHours;
    }

    public void setStartHour(int startTime) {
        startHour = startTime;
    }

    public void setEndHour(int startTime) {
        endHour = startTime;
    }

    public int getNumRows() {
        return ((endHour - startHour) * 2) + 1;
    }

    public String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    public String getCellKey(String day, String time) {
        int col = gridHeaders.indexOf(day);
        int row = 1;
        int hour = Integer.parseInt(time.substring(0, time.indexOf("_")));
        int milHour = hour;
        if (hour != 12 && time.contains("pm")) {
            milHour += 12;
        }
        if (milHour > 23) {
            milHour = milHour % 24;
        }
        row += (milHour - startHour) * 2;
        if (time.contains("_30")) {
            row += 1;
        }
        return getCellKey(col, row);
    }

    public TeachingAssistant getTA(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return ta;
            }
        }
        return null;
    }
    
    public Team getTeam(String testName) {
        for (Team ta : teams) {
            if (ta.getName().equals(testName)) {
                return ta;
            }
        }
        return null;
    }

    /**
     * This method is for giving this data manager the string property for a
     * given cell.
     */
    public void setCellProperty(int col, int row, StringProperty prop) {
        String cellKey = getCellKey(col, row);
        officeHours.put(cellKey, prop);
    }

    /**
     * This method is for setting the string property for a given cell.
     */
    public void setGridProperty(ArrayList<ArrayList<StringProperty>> grid,
            int column, int row, StringProperty prop) {
        grid.get(row).set(column, prop);
    }

    private void initOfficeHours(int initStartHour, int initEndHour) {
        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
        startHour = initStartHour;
        endHour = initEndHour;

        // EMPTY THE CURRENT OFFICE HOURS VALUES
        officeHours.clear();

        // WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
        // OFFICE HOURS GRID AND FEED THEM TO OUR DATA
        // STRUCTURE AS WE GO
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        workspaceComponent.getTATab().reloadOfficeHoursGrid(this);
    }

    public void initHours(String startHourText, String endHourText) {
        int initStartHour = Integer.parseInt(startHourText);
        int initEndHour = Integer.parseInt(endHourText);
        if ((initStartHour >= MIN_START_HOUR)
                && (initEndHour <= MAX_END_HOUR)
                && (initStartHour <= initEndHour)) {
            // THESE ARE VALID HOURS SO KEEP THEM
            initOfficeHours(initStartHour, initEndHour);
        }
    }

    public boolean containsTA(String testName, String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return true;
            }
            if (ta.getEmail().equals(testEmail)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsTAName(String testName, String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsTAEmail(String testName, String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getEmail().equals(testEmail)) {
                return true;
            }
        }
        return false;
    }

    public void editTA(String editName, String editEmail, TeachingAssistant ta) {
        if (containsTA(ta.getName(), ta.getEmail())) {
            TeachingAssistant taToEdit
                    = teachingAssistants.get(teachingAssistants.indexOf(ta));
            taToEdit.setName(editName);
            taToEdit.setEmail(editEmail);
        }

        Collections.sort(teachingAssistants);
    }
    
    public void addTA(String initName, String initEmail, boolean undergrad){
        TeachingAssistant ta = new TeachingAssistant(initName, initEmail, undergrad);

        // ADD THE TA
        if (!containsTA(initName, initEmail)) {
            teachingAssistants.add(ta);
        }

        // SORT THE TAS
        Collections.sort(teachingAssistants);
    }

    public void addTA(String initName, String initEmail) {
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(initName, initEmail);

        // ADD THE TA
        if (!containsTA(initName, initEmail)) {
            teachingAssistants.add(ta);
        }

        // SORT THE TAS
        Collections.sort(teachingAssistants);
    }

    public void removeTA(String name) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (name.equals(ta.getName())) {
                teachingAssistants.remove(ta);
                return;
            }
        }
    }

    public void addOfficeHoursReservation(String day, String time, String taName) {
        String cellKey = getCellKey(day, time);
        toggleTAOfficeHours(cellKey, taName);
    }

    /**
     * This function toggles the taName in the cell represented by cellKey.
     * Toggle means if it's there it removes it, if it's not there it adds it.
     */
    public void toggleTAOfficeHours(String cellKey, String taName) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();

        // IF IT ALREADY HAS THE TA, REMOVE IT
        if (cellText.contains(taName)) {
            removeTAFromCell(cellProp, taName);
        } // OTHERWISE ADD IT
        else if (cellText.length() == 0) {
            cellProp.setValue(taName);
        } else {
            cellProp.setValue(cellText + "\n" + taName);
        }
    }

    public void toggleEditHours(String cellKey, String newName, String oldName) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();

        // IF IT ALREADY HAS THE TA, REMOVE IT
        if (cellText.contains(oldName)) {
            cellProp.setValue(cellText + "\n" + newName);
            removeTAFromCell(cellProp, oldName);

        } // OTHERWISE ADD IT
    }

    /**
     * This method removes taName from the office grid cell represented by
     * cellProp.
     */
    public void removeTAFromCell(StringProperty cellProp, String taName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) {
            cellProp.setValue("");
        } // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellText.indexOf(taName) == 0) {
            int startIndex = cellText.indexOf("\n") + 1;
            cellText = cellText.substring(startIndex);
            cellProp.setValue(cellText);
        } // IS IT IN THE MIDDLE OF A LIST OF TAs
        else if (cellText.indexOf(taName) < cellText.indexOf("\n", cellText.indexOf(taName))) {
            int startIndex = cellText.indexOf("\n" + taName);
            int endIndex = startIndex + taName.length() + 1;
            cellText = cellText.substring(0, startIndex) + cellText.substring(endIndex);
            cellProp.setValue(cellText);
        } // IT MUST BE THE LAST TA
        else {
            int startIndex = cellText.indexOf("\n" + taName);
            cellText = cellText.substring(0, startIndex);
            cellProp.setValue(cellText);
        }
    }

    public boolean hasTAInRange(int startTime, int editTime) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        int range = (editTime - startTime) * 2;

        for (int row = 1; row <= range; row++) {
            for (int col = 2; col < 7; col++) {
                String cellKey = getCellKey(col, row);
                StringProperty cellProp = officeHours.get(cellKey);
                String cellText = cellProp.getValue();
                if(cellText.length()>0){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean hasTAInRangeEnd(int endTime, int editTime) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        int range = (endTime - editTime) * 2;
        
        int row = (endHour - startHour)*2;

        for (int i=0; i < range; i++) {
            for (int col = 2; col < 7; col++) {
                String cellKey = getCellKey(col, row);
                StringProperty cellProp = officeHours.get(cellKey);
                String cellText = cellProp.getValue();
                if(cellText.length()>0){
                    return true;
                }
                row--;
            }
        }
        return false;
    }
    
    public void addRecitation(String section, String instructor, String dayTime,
            String location, String ta1, String ta2) {
        // MAKE THE TA
        Recitation ra = new Recitation(section, instructor, dayTime, 
                location, ta1, ta2);

        // ADD THE TA
        if (!containsRecitation(ra)) {
            recitations.add(ra);
        }

        // SORT THE TAS
        Collections.sort(recitations);
    }
    
    public void editRecitation(Recitation recitation){
//        if (containsRecitation(recitation)) {
//            TeachingAssistant taToEdit
//                    = teachingAssistants.get(teachingAssistants.indexOf(ta));
//            taToEdit.setName(editName);
//            taToEdit.setEmail(editEmail);
//        }

        Collections.sort(recitations);
    }
    
    public boolean containsRecitation(Recitation ra){
        for (Recitation ra1 : recitations) {
            if ((ra.getSection().equals(ra1.getSection())) &&
                (ra.getInstructor().equals(ra1.getInstructor())) &&
                (ra.getDayTime().equals(ra1.getDayTime())) &&
                (ra.getLocation().equals(ra1.getLocation())) &&
                (ra.getTa1().equals(ra1.getTa1())) &&
                (ra.getTa2().equals(ra1.getTa2()))){
                return true;
            }
        }
        return false;
    }
    
    public void addSchedule(String type, LocalDate date, String title, 
            String topic, String time, String link, String criteria){
        Schedule sch = new Schedule(type, date, title, 
                topic, time, link, criteria);

        if (!containsSchedule(sch)) {
            schedule.add(sch);
        }
        
//        Collections.sort(recitations);
    }
    
    public void addSchedule(String type, String date, String title, 
            String topic, String time, String link, String criteria){
        Schedule sch = new Schedule(type, date, 
                title, topic, time, link, criteria);

        if (!containsSchedule(sch)) {
            schedule.add(sch);
        }
        
//        Collections.sort(recitations);
    }
    
    public boolean containsSchedule(Schedule sch){
        for (Schedule sch1 : schedule) {
            if ((sch.getType().equals(sch1.getType())) &&
                (sch.getDate().equals(sch1.getDate())) &&
                (sch.getTitle().equals(sch1.getTitle())) &&
                (sch.getTopic().equals(sch1.getTopic()))){
                return true;
            }
        }
        return false;
    }
    
    public void addTeam(String name, String color, String textColor, String link){
        Team team = new Team(name, color, textColor, link);

        if (!containsTeam(team)) {
            teams.add(team);
        }
        
//        Collections.sort(recitations);
    }
    
    public boolean containsTeam(Team team){
        for (Team team1 : teams) {
            if ((team.getName().equals(team1.getName())) &&
                (team.getColor().equals(team1.getColor())) &&
                (team.getTextColor().equals(team1.getTextColor())) &&
                (team.getLink().equals(team1.getLink()))){
                return true;
            }
        }
        return false;
    }
    
    public void addOfficeHours(String a, String b, String c){
        office.add(a);
        office.add(b);
        office.add(c);
    }
    
    public ObservableList getOffice(){
        return office;
    }
    
    public void addStudent(String firstName, String lastName, 
            String team, String role){
        Student student = new Student(firstName, lastName, team, role);

        if (!containsStudent(student)) {
            students.add(student);
        }
        
        Team addToTeam = getTeam(team);
        addToTeam.addStudent(student);
        
//        Collections.sort(recitations);
    }
    
    public boolean containsStudent(Student stu){
        for (Student stu1 : students) {
            if ((stu.getFirstName().equals(stu1.getFirstName())) &&
                (stu.getLastName().equals(stu1.getLastName())) &&
                (stu.getTeam().equals(stu1.getTeam())) &&
                (stu.getRole().equals(stu1.getRole()))){
                return true;
            }
        }
        return false;
    }
    
    public void setCourseInfo(String a,String b, String c, String d, String e, String f, String g){
        courseInfo.setCourseInfo(a, b, c, d, e, f, g);
    }
    
    public CourseInfo getCourseInfo(){
        return courseInfo;
    }
    
    public void setStartingMonday(int month, int day){
        startingMondayMonth = month;
        startingMondayDay = day;
    }
    
    public void setEndingFriday(int month, int day){
        endingFridayMonth = month;
        endingFridayDay = day;
    }

    public int getStartingMondayMonth() {
        return startingMondayMonth;
    }

    public int getStartingMondayDay() {
        return startingMondayDay;
    }

    public int getEndingFridayMonth() {
        return endingFridayMonth;
    }

    public int getEndingFridayDay() {
        return endingFridayDay;
    }
    
    public String getTemplate(){
        return template.get();
    }
    
    public void setTemplate(String s){
        template.setValue(s);
    }
    
    public String getBannerImage(){
        return bannerImage.get();
    }
    
    public void setBannerImage(String v){
        bannerImage.setValue(v);
    }

    public String getRightFooter() {
        return rightFooter.get();
    }
    
    public void setRightFooter(String s){
        rightFooter.setValue(s);
    }

    public String getLeftFooter() {
        return leftFooter.get();
    }
    
    public void setLeftFooter(String s){
        leftFooter.setValue(s);
    }

    public ObservableList<Pages> getPages() {
        return pages;
    }
    
    public String getExportDir(){
        return exportDir.get();
    }
    
    public void setExportDir(String a){
        exportDir.setValue(a);
    }
    
    public void setStyleSheet(String s){
        styleSheet.setValue(s);
    }
    
    public String getStyleSheet(){
        return styleSheet.getValue();
    }
}
