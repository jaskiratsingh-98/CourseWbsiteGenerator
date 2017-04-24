/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.test_bed;

import tam.data.CSGData;


/**
 *
 * @author jaski
 */
public class TestSave {
    
    CSGData data;

    public void demonstrateSave() throws FileNotFoundException {
        
        String filePath = "SiteSaveTest1.json";
        data = new CSGData();
        
        data.addTA("Jaskirat", "Singh", true);
        data.addTA("Jaskiran", "Kaur", false);
//        data.addOfficeHours("Mon", "12_00pm", "Jaskirat");
        data.addTeam("Team1", "ffffff", "222222", "team1.com");
        data.addStudent("Harpreet", "Kaur", "Team1", "Lead");
        data.addRecitation("S05", "McKenna", "T 3:00 - 5:00", "Javits", "Jaskirat", "Jaskiran");
        data.addSchedule("Holiday", "2017-05-06", "Topic", "Link", "4:00 PM", "google.com", "None");
        data.setCourseInfo("CSE", "219", "Fall", "2016", "Computer Science III", "Richard McKenna", "http://www.google.com");
        
        SaveClass.save(data, filePath);
    }

    public static void main(String[] args) {
        TestSave a = new TestSave();
        try {
            a.demonstrateSave();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestSave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
