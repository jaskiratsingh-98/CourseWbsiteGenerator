/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.test_bed;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import tam.data.CSGData;
import static tam.test_bed.LoadRecitation.JSON_RECS;

/**
 *
 * @author jaski
 */
public class LoadSchedule {

    static final String JSON_TYPE = "type";
    static final String JSON_DATE = "date";
    static final String JSON_TITLE = "title";
    static final String JSON_TOPIC = "topic";
    static final String JSON_TIME = "time";
    static final String JSON_LINK = "link";
    static final String JSON_CRIT = "criteria";
    static final String JSON_SCHD = "schedule";

    public static CSGData loadCourse(String filePath) throws IOException {
        JsonObject json = loadJSONFile(filePath);
        CSGData dataManager = new CSGData();

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
}
