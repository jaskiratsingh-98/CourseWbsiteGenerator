/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import tam.data.CSGData;
import tam.data.Recitation;
import tam.data.Schedule;
import tam.data.Student;
import tam.data.TeachingAssistant;
import tam.data.Team;
import static tam.file.CSGFiles.JSON_NAME;

/**
 *
 * @author jaski
 */
public class SaveClass {
    
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
    static final String TAS_FILEPATH = "./scripts/OfficeHoursGridData.json";
    static final String HWS_FILEPATH = "./scripts/HWsData.json";
    static final String JSON_PRO = "projects";
    static final String PRO_FILEPATH = "./scripts/ProjectsData.json";
    static final String JSON_SEM = "semester";
    static final String SCRIPT_FOLER = "./scripts";
    
    public static void save(CSGData dataManager, String filePath) throws FileNotFoundException{

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
//        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
//        ObservableList<String> officeHours = dataManager.getOffice();
//        for (int i = 0; i < officeHours.size(); i+=3) {
//            JsonObject tsJson = Json.createObjectBuilder()
//                    .add(JSON_DAY, officeHours.get(i))
//                    .add(JSON_TIME, officeHours.get(i+1))
//                    .add(JSON_NAME, officeHours.get(i+2)).build();
//            timeSlotArrayBuilder.add(tsJson);
//        }
//        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();

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
//                .add(JSON_OFFICE_HOURS, timeSlotsArray)
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
}
