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
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    static final String JSON_RECS = "recitations";
    static final String JSON_SCHD = "schedule";
    static final String JSON_STU = "students";
    static final String JSON_TEAMS = "teams";
    
    

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

        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getTATab().getStartComboBox().setValue(startHour + ":00");
        workspace.getTATab().getEndComboBox().setValue(endHour + ":00");

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            dataManager.addTA(name, email);
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
                    .add(JSON_TA1, ra.getTa1().getName())
                    .add(JSON_TA2, ra.getTa2().getName()).build();
            taArrayBuilder.add(raJson);
        }
        JsonArray recitationsArray = recitationsArrayBuilder.build();
        
        //Build Schedule Array
        JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> sch = dataManager.getSchedule();
        for (Schedule sa : sch) {
            JsonObject schJson = Json.createObjectBuilder()
                    .add(JSON_TYPE, sa.getType())
                    .add(JSON_DATE, sa.getDate())
                    .add(JSON_TITLE, sa.getTitle())
                    .add(JSON_TOPIC, sa.getTopic()).build();
            taArrayBuilder.add(schJson);
        }
        JsonArray scheduleArray = scheduleArrayBuilder.build();
        
        //Build Students Array
        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> stu = dataManager.getStudents();
        for (Student st : stu) {
            JsonObject schJson = Json.createObjectBuilder()
                    .add(JSON_FIRSTNAME, st.getFirstName())
                    .add(JSON_LASTNAME, st.getLastName())
                    .add(JSON_TEAM, st.getTeam().getName())
                    .add(JSON_ROLE, st.getRole()).build();
            taArrayBuilder.add(schJson);
        }
        JsonArray studentArray = studentsArrayBuilder.build();

        //Build Students Array
        JsonArrayBuilder teamsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> team = dataManager.getTeams();
        for (Team tm : team) {
            JsonObject schJson = Json.createObjectBuilder()
                    .add(JSON_NAME, tm.getName())
                    .add(JSON_COLOR, tm.getColor())
                    .add(JSON_TEXTCOLOR, tm.getTextColor())
                    .add(JSON_LINK, tm.getLink()).build();
            taArrayBuilder.add(schJson);
        }
        JsonArray teamArray = teamsArrayBuilder.build();
        
        // THEN PUT IT ALL TOGETHER IN A JsonObject
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
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

        try {
            Files.copy(Paths.get(props.getProperty(SYLLABUS)), Paths.get(filePath + props.getProperty(SYLLABUS_PATH)), REPLACE_EXISTING);
            FileUtils.copyDirectoryToDirectory(new File(props.getProperty(JS_FOLDER)), new File(filePath));
            FileUtils.copyDirectoryToDirectory(new File(props.getProperty(CSS_FOLDER)), new File(filePath));
            FileUtils.copyDirectoryToDirectory(new File(props.getProperty((IMAGES_FOLDER))), new File(filePath));
            saveData(data, filePath + props.getProperty(JS_OFFICE_HOURS_PATH));
        } catch (IOException ioe) {
            System.out.println(ioe);
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }
}
