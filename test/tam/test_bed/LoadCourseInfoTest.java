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
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tam.data.CSGData;
import tam.data.CourseInfo;
import static tam.test_bed.LoadCourseInfo.loadCourse;

/**
 *
 * @author jaski
 */
public class LoadCourseInfoTest {
    static final String CI_SUB = "subject";
    static final String CI_NUM = "number";
    static final String CI_SEM = "semester";
    static final String CI_YEAR = "year";
    static final String CI_TITLE = "title";
    static final String CI_INS = "instructor";
    static final String CI_LINK = "link";


    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of loadCourse method, of class LoadCourseInfo.
     */
    @Test
    public void testLoadCourse() {
        System.out.println("loadCourse");
        JsonObject json;
        CSGData data;
        try {
            json = loadJSONFile("C:\\Users\\jaski\\Google Drive\\CSE 219\\Homework2\\TAManager_Solution\\SiteSaveTest.json");
            data = loadCourse("C:\\Users\\jaski\\Google Drive\\CSE 219\\Homework2\\TAManager_Solution\\SiteSaveTest.json");

            String subject = json.getString(CI_SUB);
            String number = json.getString(CI_NUM);
            String semester = json.getString(CI_NUM);
            String year = json.getString(CI_YEAR);
            String ci_title = json.getString(CI_TITLE);
            String ins = json.getString(CI_INS);
            String ci_link = json.getString(CI_LINK);
            
            assertEquals(new CourseInfo(subject, number, semester, year, ci_title, ins, ci_link), data.getCourseInfo());
        } catch (IOException ex) {
            Logger.getLogger(LoadCourseInfoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO review the generated test code and remove the default call to fail.
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
