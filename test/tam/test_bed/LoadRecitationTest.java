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
import tam.data.CourseInfo;
import tam.data.Recitation;
import static tam.test_bed.LoadCourseInfo.loadCourse;
import static tam.test_bed.LoadCourseInfoTest.CI_SUB;
import static tam.test_bed.LoadRecitation.JSON_SECTION;

/**
 *
 * @author jaski
 */
public class LoadRecitationTest {

    static final String JSON_RECS = "recitations";
    static final String JSON_SECTION = "section";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_DAYTIME = "dayTime";
    static final String JSON_LOCATION = "location";
    static final String JSON_TA1 = "ta1";
    static final String JSON_TA2 = "ta2";

    public LoadRecitationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of loadCourse method, of class LoadRecitation.
     */
    @Test
    public void testLoadCourse() throws Exception {
        System.out.println("loadCourse");
        JsonObject json;
        CSGData data;
        try {
            json = loadJSONFile("C:\\Users\\jaski\\Google Drive\\CSE 219\\Homework2\\TAManager_Solution\\SiteSaveTest.json");
            data = LoadRecitation.loadCourse("C:\\Users\\jaski\\Google Drive\\CSE 219\\Homework2\\TAManager_Solution\\SiteSaveTest.json");

            JsonArray jsonRecArray = json.getJsonArray(JSON_RECS);
            for (int i = 0; i < jsonRecArray.size(); i++) {
                JsonObject jsonRec = jsonRecArray.getJsonObject(i);
                String section = jsonRec.getString(JSON_SECTION);
                String instructor = jsonRec.getString(JSON_INSTRUCTOR);
                String dayTime = jsonRec.getString(JSON_DAYTIME);
                String location = jsonRec.getString(JSON_LOCATION);
                String ta1 = jsonRec.getString(JSON_TA1);
                String ta2 = jsonRec.getString(JSON_TA2);

                assertEquals(section, data.getRecitations().get(i).getSection());
                assertEquals(instructor, data.getRecitations().get(i).getInstructor());
                assertEquals(dayTime, data.getRecitations().get(i).getDayTime());
                assertEquals(location, data.getRecitations().get(i).getLocation());
                assertEquals(ta1, data.getRecitations().get(i).getTa1());
                assertEquals(ta2, data.getRecitations().get(i).getTa2());
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
