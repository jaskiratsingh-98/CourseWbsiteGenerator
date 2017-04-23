/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.test_bed;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import tam.CSGApp;
import tam.data.CSGData;
import tam.data.Recitation;
import tam.data.Schedule;
import tam.data.Student;
import tam.data.TeachingAssistant;
import tam.data.Team;
import tam.file.CSGFiles;
import tam.file.TimeSlot;

/**
 *
 * @author jaski
 */
public class TestSave {

    ObservableList<Recitation> recitations;
    ObservableList<Schedule> schedule;
    ObservableList<Team> teams;
    ObservableList<Student> students;
    ObservableList<TeachingAssistant> teachingAssistants;
    
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
    static final String JSON_RECS = "recitations";
    static final String JSON_SCHD = "schedule";
    static final String JSON_STU = "students";
    static final String JSON_TEAMS = "teams";

    public void demonstrateSave() {
        String filePath = "SiteSaveTest.json";
        
        teachingAssistants = FXCollections.observableArrayList();
        recitations = FXCollections.observableArrayList();
        schedule = FXCollections.observableArrayList();
        teams = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();

        TeachingAssistant ta1 = new TeachingAssistant("Jaskirat", "Singh");
        ta1.setUndergrad(true);
        TeachingAssistant ta2 = new TeachingAssistant("Jaskiran", "Kaur");
        ta2.setUndergrad(false);
        Team team1 = new Team("Team1", "ffffff", "222222", "Designer");
        Student a = new Student("Jaskirat", "Singh", team1, "Designer");
        Recitation rec = new Recitation("S05", "McKenna", "T 3:00 - 5:00", "Javits", ta1, ta2);
        Schedule e = new Schedule("Holiday", "Date", "Topic", "Link");

        teachingAssistants.add(ta1);
        teachingAssistants.add(ta2);
        students.add(a);
        recitations.add(rec);
        teams.add(team1);
        schedule.add(e);

        try {
            JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
            for (TeachingAssistant ta : teachingAssistants) {
                JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail())
                        .add(JSON_UNDERGRAD, ta.isUndergrad()).build();
                taArrayBuilder.add(taJson);
            }
            JsonArray undergradTAsArray = taArrayBuilder.build();

            // NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
//            JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
//            ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
//            for (TimeSlot ts : officeHours) {
//                JsonObject tsJson = Json.createObjectBuilder()
//                        .add(JSON_DAY, ts.getDay())
//                        .add(JSON_TIME, ts.getTime())
//                        .add(JSON_NAME, ts.getName()).build();
//                timeSlotArrayBuilder.add(tsJson);
//            }
//            JsonArray timeSlotsArray = timeSlotArrayBuilder.build();

            //Build Recitations Array
            JsonArrayBuilder recitationsArrayBuilder = Json.createArrayBuilder();
            for (Recitation ra : recitations) {
                JsonObject raJson = Json.createObjectBuilder()
                        .add(JSON_SECTION, ra.getSection())
                        .add(JSON_INSTRUCTOR, ra.getInstructor())
                        .add(JSON_DAYTIME, ra.getDayTime())
                        .add(JSON_LOCATION, ra.getLocation())
                        .add(JSON_TA1, ra.getTa1().getName())
                        .add(JSON_TA2, ra.getTa2().getName()).build();
                recitationsArrayBuilder.add(raJson);
            }
            JsonArray recitationsArray = recitationsArrayBuilder.build();

            //Build Schedule Array
            JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
            for (Schedule sa : schedule) {
                JsonObject schJson = Json.createObjectBuilder()
                        .add(JSON_TYPE, sa.getType())
                        .add(JSON_DATE, sa.getDate())
                        .add(JSON_TITLE, sa.getTitle())
                        .add(JSON_TOPIC, sa.getTopic()).build();
                scheduleArrayBuilder.add(schJson);
            }
            JsonArray scheduleArray = scheduleArrayBuilder.build();

            //Build Students Array
            JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
            for (Student st : students) {
                JsonObject schJson = Json.createObjectBuilder()
                        .add(JSON_FIRSTNAME, st.getFirstName())
                        .add(JSON_LASTNAME, st.getLastName())
                        .add(JSON_TEAM, st.getTeam().getName())
                        .add(JSON_ROLE, st.getRole()).build();
                studentsArrayBuilder.add(schJson);
            }
            JsonArray studentArray = studentsArrayBuilder.build();

            //Build Students Array
            JsonArrayBuilder teamsArrayBuilder = Json.createArrayBuilder();
            for (Team tm : teams) {
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
//                    .add(JSON_START_HOUR, "" + dataManager.getStartHour())
//                    .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                    .add(JSON_TAS, undergradTAsArray)
//                    .add(JSON_OFFICE_HOURS, timeSlotsArray)
                    .add(JSON_RECS, recitationsArray)
                    .add(JSON_SCHD, scheduleArray)
                    .add(JSON_STU, studentArray)
                    .add(JSON_TEAM, teamArray)
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
        } catch (Exception ex) {
            Logger.getLogger(TestSave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args){
        TestSave a = new TestSave();
        a.demonstrateSave();
    }

}
