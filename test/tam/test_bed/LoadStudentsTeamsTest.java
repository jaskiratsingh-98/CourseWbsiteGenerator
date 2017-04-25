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
import static tam.test_bed.LoadRecitation.loadCourse;
import static tam.test_bed.LoadStudentsTeams.JSON_NAME;
import static tam.test_bed.LoadStudentsTeams.JSON_TEAMS;
import static tam.test_bed.LoadStudentsTeams.loadStudents;

/**
 *
 * @author jaski
 */
public class LoadStudentsTeamsTest {

    static final String JSON_STU = "students";
    static final String JSON_FIRSTNAME = "firstName";
    static final String JSON_LASTNAME = "lastName";
    static final String JSON_TEAM = "team";
    static final String JSON_NAME = "name";
    static final String JSON_ROLE = "role";
    static final String JSON_TEAMS = "teams";
    static final String JSON_COLOR = "color";
    static final String JSON_TEXTCOLOR = "textColor";
    static final String JSON_LINK = "link";

    public LoadStudentsTeamsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of loadCourse method, of class LoadStudents.
     */
    @Test
    public void testLoadCourse() throws Exception {
        System.out.println("loadCourse");
        JsonObject json;
        CSGData data;
        try {
            json = loadJSONFile("C:\\Users\\jaski\\Google Drive\\CSE 219\\Homework2\\TAManager_Solution\\SiteSaveTest.json");
            data = LoadStudentsTeams.loadStudents("C:\\Users\\jaski\\Google Drive\\CSE 219\\Homework2\\TAManager_Solution\\SiteSaveTest.json");

            JsonArray jsonTeamArray = json.getJsonArray(JSON_TEAMS);
            for (int i = 0; i < jsonTeamArray.size(); i++) {
                JsonObject jsonTeam = jsonTeamArray.getJsonObject(i);
                String name = jsonTeam.getString(JSON_NAME);
                String color = jsonTeam.getString(JSON_COLOR);
                String textColor = jsonTeam.getString(JSON_TEXTCOLOR);
                String link = jsonTeam.getString(JSON_LINK);
                
                assertEquals(name, data.getTeams().get(i).getName());
                assertEquals(color, data.getTeams().get(i).getColor());
                assertEquals(textColor, data.getTeams().get(i).getTextColor());
                assertEquals(link, data.getTeams().get(i).getLink());
            }

            JsonArray jsonStuArray = json.getJsonArray(JSON_STU);
            for (int i = 0; i < jsonStuArray.size(); i++) {
                JsonObject jsonStu = jsonStuArray.getJsonObject(i);
                String firstName = jsonStu.getString(JSON_FIRSTNAME);
                String lastName = jsonStu.getString(JSON_LASTNAME);
                String team = jsonStu.getString(JSON_TEAM);
                String role = jsonStu.getString(JSON_ROLE);

                assertEquals(firstName, data.getStudents().get(i).getFirstName());
                assertEquals(lastName, data.getStudents().get(i).getLastName());
                assertEquals(team, data.getStudents().get(i).getTeam());
                assertEquals(role, data.getStudents().get(i).getRole());
            }

        } catch (IOException ex) {
            Logger.getLogger(LoadCourseInfoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

}
