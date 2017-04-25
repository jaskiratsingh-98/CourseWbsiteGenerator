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
import tam.data.CSGData;
import static tam.test_bed.LoadCourseInfo.loadCourse;

/**
 *
 * @author jaski
 */
public class LoadRecitation {

    static final String JSON_RECS = "recitations";
    static final String JSON_SECTION = "section";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_DAYTIME = "dayTime";
    static final String JSON_LOCATION = "location";
    static final String JSON_TA1 = "ta1";
    static final String JSON_TA2 = "ta2";

    public static CSGData loadCourse(String filePath) throws IOException {
        JsonObject json = loadJSONFile(filePath);
        CSGData dataManager = new CSGData();

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

        return dataManager;
    }

    public static JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }
    
    public static void main(String[] args){
        try {
            loadCourse("C:\\Users\\jaski\\Google Drive\\CSE 219\\Homework2\\TAManager_Solution\\SiteSaveTest.json");
        } catch (IOException ex) {
            Logger.getLogger(LoadCourseInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
