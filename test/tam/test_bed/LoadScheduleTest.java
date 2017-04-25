/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.test_bed;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tam.data.CSGData;
import static tam.test_bed.LoadRecitationTest.JSON_RECS;
import static tam.test_bed.LoadSchedule.JSON_SCHD;

/**
 *
 * @author jaski
 */
public class LoadScheduleTest {

    static final String JSON_TYPE = "type";
    static final String JSON_DATE = "date";
    static final String JSON_TITLE = "title";
    static final String JSON_TOPIC = "topic";
    static final String JSON_TIME = "time";
    static final String JSON_LINK = "link";
    static final String JSON_CRIT = "criteria";
    static final String JSON_SCHD = "schedule";

    public LoadScheduleTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of loadCourse method, of class LoadSchedule.
     */
    @Test
    public void testLoadCourse() throws Exception {
        System.out.println("loadCourse");
        JsonObject json;
        CSGData data;
        try {
            json = loadJSONFile("C:\\Users\\jaski\\Google Drive\\CSE 219\\Homework2\\TAManager_Solution\\SiteSaveTest.json");
            data = LoadSchedule.loadCourse("C:\\Users\\jaski\\Google Drive\\CSE 219\\Homework2\\TAManager_Solution\\SiteSaveTest.json");

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

                assertEquals(type, data.getSchedule().get(i).getType());
                assertEquals(date, data.getSchedule().get(i).getDate());
                assertEquals(time, data.getSchedule().get(i).getTime());
                assertEquals(title, data.getSchedule().get(i).getTitle());
                assertEquals(topic, data.getSchedule().get(i).getTopic());
                assertEquals(link, data.getSchedule().get(i).getLink());
                assertEquals(criteria, data.getSchedule().get(i).getCriteria());
            }

        } catch (IOException ex) {
            Logger.getLogger(LoadCourseInfoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

}
