/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import tam.CSGApp;
import tam.data.CSGData;

/**
 *
 * @author jaski
 */
public class RecitationController {
    CSGApp app;
    
    public RecitationController(CSGApp initApp){
        app = initApp;
    }
    
    public void addRecitation(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        CSGData data = (CSGData)app.getDataComponent();
        
        String section = workspace.getRecitationTab().
                getSectionTextField().getText();
        String instructor = workspace.getRecitationTab().
                getInstructorTextField().getText();
        String dayTime = workspace.getRecitationTab().
                getDayTimeTextField().getText();
        String location = workspace.getRecitationTab().
                getLocationTextField().getText();
        String ta1 = workspace.getRecitationTab().
                getTa1ComboBox().getValue().toString();
        String ta2 = workspace.getRecitationTab().
                getTa2ComboBox().getValue().toString();
        
        data.addRecitation(section, instructor, dayTime, location, ta1, ta2);
    }
}
