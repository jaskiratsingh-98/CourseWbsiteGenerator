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
import tam.data.CSGData;

/**
 *
 * @author jaski
 */
public class LoadCourseInfo {

    static final String CI_SUB = "subject";
    static final String CI_NUM = "number";
    static final String CI_SEM = "semester";
    static final String CI_YEAR = "year";
    static final String CI_TITLE = "title";
    static final String CI_INS = "instructor";
    static final String CI_LINK = "link";

    public static CSGData loadCourse(String filePath) throws IOException {
        JsonObject json = loadJSONFile(filePath);
        CSGData dataManager = new CSGData();
        String subject = json.getString(CI_SUB);
        String number = json.getString(CI_NUM);
        String semester = json.getString(CI_NUM);
        String year = json.getString(CI_YEAR);
        String ci_title = json.getString(CI_TITLE);
        String ins = json.getString(CI_INS);
        String ci_link = json.getString(CI_LINK);
        dataManager.setCourseInfo(subject, number, semester, year, ci_title, ins, ci_link);
        return dataManager;
    }
    
    private static JsonObject loadJSONFile(String jsonFilePath) throws IOException {
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
