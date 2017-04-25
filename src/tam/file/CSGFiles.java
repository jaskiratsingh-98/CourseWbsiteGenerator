package tam.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import org.apache.commons.io.FileUtils;
import properties_manager.PropertiesManager;
import tam.CSGApp;
import static tam.CSGProp.*;
import tam.data.CSGData;
import tam.data.Recitation;
import tam.data.Schedule;
import tam.data.Student;
import tam.data.TeachingAssistant;
import tam.data.Team;
import tam.workspace.CSGWorkspace;

/**
 * This class serves as the file component for the TA manager app. It provides
 * all saving and loading services for the application.
 *
 * @author Richard McKenna
 */
public class CSGFiles implements AppFileComponent {

    // THIS IS THE APP ITSELF
    CSGApp app;

    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_UNDERGRAD = "undergrad";
    static final String JSON_TAS = "tas";
    static final String JSON_EMAIL = "email";
    static final String JSON_SECTION = "section";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_DAYTIME = "dayTime";
    static final String JSON_LOCATION = "location";
    static final String JSON_TA1 = "ta1";
    static final String JSON_TA2 = "ta2";
    static final String JSON_TYPE = "type";
    static final String JSON_DATE = "date";
    static final String JSON_TITLE = "title";
    static final String JSON_TOPIC = "topic";
    static final String JSON_FIRSTNAME = "firstName";
    static final String JSON_LASTNAME = "lastName";
    static final String JSON_TEAM = "team";
    static final String JSON_ROLE = "role";
    static final String JSON_COLOR = "color";
    static final String JSON_TEXTCOLOR = "textColor";
    static final String JSON_LINK = "link";
    static final String JSON_CRIT = "criteria";
    static final String JSON_RECS = "recitations";
    static final String JSON_SCHD = "schedule";
    static final String JSON_STU = "students";
    static final String JSON_TEAMS = "teams";
    static final String HOME_FILEPATH = "./scripts/HomeData.json";
    static final String CI_SUB = "subject";
    static final String CI_NUM = "number";
    static final String CI_SEM = "semester";
    static final String CI_YEAR = "year";
    static final String CI_TITLE = "title";
    static final String CI_INS = "instructor";
    static final String CI_LINK = "link";
    static final String SCH_HOL = "holidays";
    static final String SCH_LEC = "lectures";
    static final String SCH_REF = "references";
    static final String SCH_HWS = "hws";
    static final String JSON_WORK = "work";
    static final String JSON_MONTH = "month";
    static final String SCH_FILEPATH = "./scripts/ScheduleData.json";
    static final String JSON_UNDTA = "undergrad_tas";
    static final String JSON_GRADTA = "grad_tas";
    static final String MON_MON = "startingMondayMonth";
    static final String MON_DAY = "startingMondayDay";
    static final String FRI_MON = "endingFridayMonth";
    static final String FRI_DAY = "endingFridayDay";
    static final String TAS_FILEPATH = "./scripts/SyllabusData.json";
    static final String HWS_FILEPATH = "./scripts/HWsData.json";
    static final String JSON_PRO = "projects";
    static final String PRO_FILEPATH = "./scripts/ProjectsData.json";
    static final String JSON_SEM = "semester";
    static final String SCRIPT_FOLER = "./scripts";

    public CSGFiles(CSGApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        // CLEAR THE OLD DATA OUT
        CSGData dataManager = (CSGData) data;

        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(filePath);

        // LOAD THE START AND END HOURS
        String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);
        dataManager.setStartHour(Integer.parseInt(startHour));
        dataManager.setEndHour(Integer.parseInt(endHour));

        String subject = json.getString(CI_SUB);
        String number = json.getString(CI_NUM);
        String semester = json.getString(CI_NUM);
        String year = json.getString(CI_YEAR);
        String ci_title = json.getString(CI_TITLE);
        String ins = json.getString(CI_INS);
        String ci_link = json.getString(CI_LINK);
        dataManager.setCourseInfo(subject, number, semester, year, ci_title, ins, ci_link);

        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getTATab().getStartComboBox().setValue(startHour + ":00");
        workspace.getTATab().getEndComboBox().setValue(endHour + ":00");

        workspace.getCourseTab().getSubjectComboBox().setValue(subject);
        workspace.getCourseTab().getNumberComboBox().setValue(number);
        workspace.getCourseTab().getSemesterComboBox().setValue(semester);
        workspace.getCourseTab().getYearComboBox().setValue(year);
        workspace.getCourseTab().getTitleTextField().setText(ci_title);
        workspace.getCourseTab().getNameTextField().setText(ins);
        workspace.getCourseTab().getHomeTextField().setText(ci_link);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            boolean undergrad = jsonTA.getBoolean(JSON_UNDERGRAD);
            dataManager.addTA(name, email, undergrad);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            int t1 = Integer.parseInt(time.substring(0, time.indexOf("_")));
            dataManager.addOfficeHoursReservation(day, time, name);
        }

        JsonArray jsonRecArray = json.getJsonArray(JSON_RECS);
        for (int i = 0; i < jsonRecArray.size(); i++) {
            JsonObject jsonRec = jsonRecArray.getJsonObject(i);
            String section = jsonRec.getString(JSON_SECTION);
            String instructor = jsonRec.getString(JSON_INSTRUCTOR);
            String dayTime = jsonRec.getString(JSON_DAYTIME);
            String location = jsonRec.getString(JSON_LOCATION);
            String ta1 = jsonRec.getString(JSON_TA1);
            String ta2 = jsonRec.getString(JSON_TA2);
            dataManager.addRecitation(section, instructor, dayTime, location, ta1, ta2);
        }

        JsonArray jsonSchedArray = json.getJsonArray(JSON_SCHD);
        for (int i = 0; i < jsonSchedArray.size(); i++) {
            JsonObject jsonSch = jsonSchedArray.getJsonObject(i);
            String type = jsonSch.getString(JSON_TYPE);
            String date = jsonSch.getString(JSON_DATE);
            String time = jsonSch.getString(JSON_TIME);
            String title = jsonSch.getString(JSON_TITLE);
            String topic = jsonSch.getString(JSON_TOPIC);
            String link = jsonSch.getString(JSON_LINK);
            String criteria = jsonSch.getString(JSON_CRIT);
            dataManager.addSchedule(type, date, title, topic,
                    time, link, criteria);
        }

        JsonArray jsonTeamArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonTeamArray.size(); i++) {
            JsonObject jsonTeam = jsonTeamArray.getJsonObject(i);
            String name = jsonTeam.getString(JSON_NAME);
            String color = jsonTeam.getString(JSON_COLOR);
            String textColor = jsonTeam.getString(JSON_TEXTCOLOR);
            String link = jsonTeam.getString(JSON_LINK);
            dataManager.addTeam(name, color, textColor, link);
        }

        JsonArray jsonStuArray = json.getJsonArray(JSON_STU);
        for (int i = 0; i < jsonStuArray.size(); i++) {
            JsonObject jsonStu = jsonStuArray.getJsonObject(i);
            String firstName = jsonStu.getString(JSON_FIRSTNAME);
            String lastName = jsonStu.getString(JSON_LASTNAME);
            String team = jsonStu.getString(JSON_TEAM);
            String role = jsonStu.getString(JSON_ROLE);
            dataManager.addStudent(firstName, lastName, team, role);
        }
    }

// HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        // GET THE DATA
        CSGData dataManager = (CSGData) data;

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail())
                    .add(JSON_UNDERGRAD, ta.isUndergrad()).build();
            taArrayBuilder.add(taJson);
        }
        JsonArray undergradTAsArray = taArrayBuilder.build();

        // NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
        for (TimeSlot ts : officeHours) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_DAY, ts.getDay())
                    .add(JSON_TIME, ts.getTime())
                    .add(JSON_NAME, ts.getName()).build();
            timeSlotArrayBuilder.add(tsJson);
        }
        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();

        //Build Recitations Array
        JsonArrayBuilder recitationsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> rec = dataManager.getRecitations();
        for (Recitation ra : rec) {
            JsonObject raJson = Json.createObjectBuilder()
                    .add(JSON_SECTION, ra.getSection())
                    .add(JSON_INSTRUCTOR, ra.getInstructor())
                    .add(JSON_DAYTIME, ra.getDayTime())
                    .add(JSON_LOCATION, ra.getLocation())
                    .add(JSON_TA1, ra.getTa1())
                    .add(JSON_TA2, ra.getTa2()).build();
            recitationsArrayBuilder.add(raJson);
        }
        JsonArray recitationsArray = recitationsArrayBuilder.build();

        //Build Schedule Array
        JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> sch = dataManager.getSchedule();
        for (Schedule sa : sch) {
            JsonObject schJson = Json.createObjectBuilder()
                    .add(JSON_TYPE, sa.getType())
                    .add(JSON_DATE, sa.getDate())
                    .add(JSON_MONTH, sa.getMonth())
                    .add(JSON_DAY, sa.getDay())
                    .add(JSON_TIME, sa.getTime())
                    .add(JSON_TITLE, sa.getTitle())
                    .add(JSON_TOPIC, sa.getTopic())
                    .add(JSON_LINK, sa.getLink())
                    .add(JSON_CRIT, sa.getCriteria()).build();
            scheduleArrayBuilder.add(schJson);
        }
        JsonArray scheduleArray = scheduleArrayBuilder.build();

        //Build Students Array
        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> stu = dataManager.getStudents();
        for (Student st : stu) {
            JsonObject schJson = Json.createObjectBuilder()
                    .add(JSON_FIRSTNAME, st.getFirstName())
                    .add(JSON_LASTNAME, st.getLastName())
                    .add(JSON_TEAM, st.getTeam())
                    .add(JSON_ROLE, st.getRole()).build();
            studentsArrayBuilder.add(schJson);
        }
        JsonArray studentArray = studentsArrayBuilder.build();

        //Build Teams Array
        JsonArrayBuilder teamsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> team = dataManager.getTeams();
        for (Team tm : team) {
            JsonObject schJson = Json.createObjectBuilder()
                    .add(JSON_NAME, tm.getName())
                    .add(JSON_COLOR, tm.getColor())
                    .add(JSON_TEXTCOLOR, tm.getTextColor())
                    .add(JSON_LINK, tm.getLink()).build();
            teamsArrayBuilder.add(schJson);
        }
        JsonArray teamArray = teamsArrayBuilder.build();

        // THEN PUT IT ALL TOGETHER IN A JsonObject
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(CI_SUB, "" + dataManager.getCourseInfo().getSubject())
                .add(CI_NUM, "" + dataManager.getCourseInfo().getNumber())
                .add(CI_SEM, "" + dataManager.getCourseInfo().getSemester())
                .add(CI_YEAR, "" + dataManager.getCourseInfo().getYear())
                .add(CI_TITLE, "" + dataManager.getCourseInfo().getTitle())
                .add(CI_INS, "" + dataManager.getCourseInfo().getInsName())
                .add(CI_LINK, "" + dataManager.getCourseInfo().getInsHome())
                .add(MON_MON, dataManager.getStartingMondayMonth())
                .add(MON_DAY, dataManager.getStartingMondayDay())
                .add(FRI_MON, dataManager.getEndingFridayMonth())
                .add(FRI_DAY, dataManager.getEndingFridayDay())
                .add(JSON_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .add(JSON_RECS, recitationsArray)
                .add(JSON_SCHD, scheduleArray)
                .add(JSON_STU, studentArray)
                .add(JSON_TEAMS, teamArray)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(dataManagerJSO);
        jsonWriter.close();

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(dataManagerJSO);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filePath);
        pw.write(prettyPrinted);
        pw.close();
    }

    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        CSGData dataManager = (CSGData) data;

        //Build the CourseInfo JSON File
        JsonObject courseInfoWriter = Json.createObjectBuilder()
                .add(CI_SUB, "" + dataManager.getCourseInfo().getSubject())
                .add(CI_NUM, "" + dataManager.getCourseInfo().getNumber())
                .add(CI_SEM, "" + dataManager.getCourseInfo().getSemester())
                .add(CI_YEAR, "" + dataManager.getCourseInfo().getYear())
                .add(CI_TITLE, "" + dataManager.getCourseInfo().getTitle())
                .add(CI_INS, "" + dataManager.getCourseInfo().getInsName())
                .add(CI_LINK, "" + dataManager.getCourseInfo().getInsHome())
                .build();

        writeFile(courseInfoWriter, HOME_FILEPATH);

        //Build the OfficeHoursGrid JSON File
        //Build the Undergrad TAs Array
        JsonArrayBuilder undergradTasArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            if (ta.isUndergrad() == true) {
                JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail()).build();
                undergradTasArrayBuilder.add(taJson);
            }
        }
        JsonArray undergradTasArray = undergradTasArrayBuilder.build();

        //Build the Grad TAs Array
        JsonArrayBuilder gradTasArrayBuilder = Json.createArrayBuilder();
        for (TeachingAssistant ta : tas) {
            if (ta.isUndergrad() == false) {
                JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail()).build();
                gradTasArrayBuilder.add(taJson);
            }
        }
        JsonArray gradTasArray = gradTasArrayBuilder.build();

        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
        for (TimeSlot ts : officeHours) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_DAY, ts.getDay())
                    .add(JSON_TIME, ts.getTime())
                    .add(JSON_NAME, ts.getName()).build();
            timeSlotArrayBuilder.add(tsJson);
        }
        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();

        JsonObject officeHoursWriter = Json.createObjectBuilder()
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDTA, undergradTasArray)
                .add(JSON_GRADTA, gradTasArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .build();

        writeFile(officeHoursWriter, TAS_FILEPATH);

        //Build the Schedule JSON File
        //Build Holidays Array
        JsonArrayBuilder holidayArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> sch = dataManager.getSchedule();
        for (Schedule sa : sch) {
            if (sa.getType().equals("Holiday")) {
                JsonObject schJson = Json.createObjectBuilder()
                        .add(JSON_DATE, sa.getDate())
                        .add(JSON_MONTH, sa.getMonth())
                        .add(JSON_DAY, sa.getDay())
                        .add(JSON_TIME, sa.getTime())
                        .add(JSON_TITLE, sa.getTitle())
                        .add(JSON_TOPIC, sa.getTopic())
                        .add(JSON_LINK, sa.getLink())
                        .add(JSON_CRIT, sa.getCriteria()).build();
                holidayArrayBuilder.add(schJson);
            }
        }
        JsonArray holidayArray = holidayArrayBuilder.build();

        //Build Lectures Array
        JsonArrayBuilder lectureArrayBuilder = Json.createArrayBuilder();
        for (Schedule sa : sch) {
            if (sa.getType().equals("Lecture")) {
                JsonObject schJson = Json.createObjectBuilder()
                        .add(JSON_DATE, sa.getDate())
                        .add(JSON_MONTH, sa.getMonth())
                        .add(JSON_DAY, sa.getDay())
                        .add(JSON_TIME, sa.getTime())
                        .add(JSON_TITLE, sa.getTitle())
                        .add(JSON_TOPIC, sa.getTopic())
                        .add(JSON_LINK, sa.getLink())
                        .add(JSON_CRIT, sa.getCriteria()).build();
                lectureArrayBuilder.add(schJson);
            }
        }
        JsonArray lectureArray = lectureArrayBuilder.build();

        //Build References Array
        JsonArrayBuilder referenceArrayBuilder = Json.createArrayBuilder();
        for (Schedule sa : sch) {
            if (sa.getType().equals("Reference")) {
                JsonObject schJson = Json.createObjectBuilder()
                        .add(JSON_DATE, sa.getDate())
                        .add(JSON_MONTH, sa.getMonth())
                        .add(JSON_DAY, sa.getDay())
                        .add(JSON_TIME, sa.getTime())
                        .add(JSON_TITLE, sa.getTitle())
                        .add(JSON_TOPIC, sa.getTopic())
                        .add(JSON_LINK, sa.getLink())
                        .add(JSON_CRIT, sa.getCriteria()).build();
                referenceArrayBuilder.add(schJson);
            }
        }
        JsonArray referenceArray = referenceArrayBuilder.build();

        //Build HWs Array
        JsonArrayBuilder hwsArrayBuilder = Json.createArrayBuilder();
        for (Schedule sa : sch) {
            if (sa.getType().equals("HW")) {
                JsonObject schJson = Json.createObjectBuilder()
                        .add(JSON_DATE, sa.getDate())
                        .add(JSON_MONTH, sa.getMonth())
                        .add(JSON_DAY, sa.getDay())
                        .add(JSON_TIME, sa.getTime())
                        .add(JSON_TITLE, sa.getTitle())
                        .add(JSON_TOPIC, sa.getTopic())
                        .add(JSON_LINK, sa.getLink())
                        .add(JSON_CRIT, sa.getCriteria()).build();
                hwsArrayBuilder.add(schJson);
            }
        }
        JsonArray hwsArray = hwsArrayBuilder.build();

        JsonObject scheduleWriter = Json.createObjectBuilder()
                .add(MON_MON, dataManager.getStartingMondayMonth())
                .add(MON_DAY, dataManager.getStartingMondayDay())
                .add(FRI_MON, dataManager.getEndingFridayMonth())
                .add(FRI_DAY, dataManager.getEndingFridayDay())
                .add(SCH_HOL, holidayArray)
                .add(SCH_LEC, lectureArray)
                .add(SCH_REF, referenceArray)
                .add(SCH_HWS, hwsArray)
                .build();

        writeFile(scheduleWriter, SCH_FILEPATH);

        //Build HWs JSONFile
        JsonObject hwsWriter = Json.createObjectBuilder()
                .add(SCH_HWS, hwsArray)
                .build();

        writeFile(hwsWriter, HWS_FILEPATH);

        //Build Projects JSONFile
        JsonArrayBuilder overallArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder projectsArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder subArrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < 1; i++) {
            ObservableList<Team> teams = dataManager.getTeams();
            for (Team tm : teams) {
                ObservableList<Student> stu = tm.getStudents();
                for (Student st : stu) {
                    JsonObject stuJson = Json.createObjectBuilder()
                            .add(JSON_FIRSTNAME, st.getFirstName())
                            .add(JSON_LASTNAME, st.getLastName()).build();
                    subArrayBuilder.add(stuJson);
                }
                JsonArray subArray = subArrayBuilder.build();
                JsonObject teamJson = Json.createObjectBuilder()
                        .add(JSON_NAME, tm.getName())
                        .add(JSON_STU, subArray)
                        .add(JSON_LINK, tm.getLink()).build();
                projectsArrayBuilder.add(teamJson);
            }
            JsonArray projectsArray = projectsArrayBuilder.build();
            
            JsonObject overallJson = Json.createObjectBuilder()
                    .add(JSON_SEM, dataManager.getCourseInfo().getSemester()
                            + dataManager.getCourseInfo().getYear())
                    .add(JSON_PRO, projectsArray).build();
            overallArrayBuilder.add(overallJson);
        }

        JsonArray overallArray = overallArrayBuilder.build();
        
        JsonObject projectsWriter = Json.createObjectBuilder()
                .add(JSON_WORK, overallArray)
                .build();

        writeFile(projectsWriter, PRO_FILEPATH);
        
       String sr1 = SCRIPT_FOLER;
       File src = new File(sr1);
       FileUtils.copyDirectory(src, new File(filePath));
        
    }

    public void writeFile(JsonObject json, String filePath) {
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(json);
        jsonWriter.close();

        // INIT THE WRITER
        OutputStream os;
        try {
            os = new FileOutputStream(filePath);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(json);
            String prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyPrinted);
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSGFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
