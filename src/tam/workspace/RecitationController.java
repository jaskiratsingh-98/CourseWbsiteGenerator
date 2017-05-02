/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import javafx.scene.control.TableView;
import tam.CSGApp;
import tam.data.CSGData;
import tam.data.Recitation;

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
        RecitationTab tab = workspace.getRecitationTab();
        
        String section = tab.getSectionTextField().getText();
        String instructor = tab.getInstructorTextField().getText();
        String dayTime = tab.getDayTimeTextField().getText();
        String location = tab.getLocationTextField().getText();
        String ta1 = tab.getTa1ComboBox().getValue().toString();
        String ta2 = tab.getTa2ComboBox().getValue().toString();
        
        if(!(section.isEmpty()&&instructor.isEmpty()&&dayTime.isEmpty()&&
                location.isEmpty()&&ta1.isEmpty()&&ta2.isEmpty())){
            data.addRecitation(section, instructor, dayTime, location, ta1, ta2);
            tab.clearFields();
        }
    }
    
    public void editRecitation(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        RecitationTab tab = workspace.getRecitationTab();
        
        TableView table = tab.getRecitations();
        Recitation rec = (Recitation)table.getSelectionModel().getSelectedItem();
        
        tab.getSectionTextField().setText(rec.getSection());
        tab.getInstructorTextField().setText(rec.getInstructor());
        tab.getDayTimeTextField().setText(rec.getDayTime());
        tab.getLocationTextField().setText(rec.getLocation());
        tab.getTa1ComboBox().setValue(rec.getTa1());
        tab.getTa2ComboBox().setValue(rec.getTa2());
        
        tab.getAddUpdate().setOnAction(e -> {
            updateRecitation(rec);
        });
        
        tab.getClear().setOnAction(e -> {
            tab.clearFields();
        });
    }
    
    public void updateRecitation(Recitation rec){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        CSGData data = (CSGData)app.getDataComponent();
        RecitationTab tab = workspace.getRecitationTab();
        
        String section = tab.getSectionTextField().getText();
        String instructor = tab.getInstructorTextField().getText();
        String dayTime = tab.getDayTimeTextField().getText();
        String location = tab.getLocationTextField().getText();
        String ta1 = tab.getTa1ComboBox().getValue().toString();
        String ta2 = tab.getTa2ComboBox().getValue().toString();
        
        if(!(section.isEmpty()&&instructor.isEmpty()&&dayTime.isEmpty()&&
                location.isEmpty()&&ta1.isEmpty()&&ta2.isEmpty())){
            data.editRecitation(rec, section, instructor, dayTime,
                    location, ta1, ta2);
            tab.clearFields();
            tab.getRecitations().refresh();
        }
    }
    
    public void delRecitation(){
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        CSGData data = (CSGData)app.getDataComponent();
        RecitationTab tab = workspace.getRecitationTab();
        
        TableView table = tab.getRecitations();
        Recitation rec = (Recitation)table.getSelectionModel().getSelectedItem();
        
        data.removeRecitation(rec.getSection());
        tab.clearFields();
    }
}
