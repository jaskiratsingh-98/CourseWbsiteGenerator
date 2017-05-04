/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import properties_manager.PropertiesManager;
import tam.CSGApp;
import static tam.CSGProp.EXPORT_TITLE;
import tam.data.CSGData;

/**
 *
 * @author jaski
 */
public class CourseInfoController {
    CSGApp app;
    
    public CourseInfoController(CSGApp initApp){
        app = initApp;
    }
    
    public void setExportDir(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData data = (CSGData)app.getDataComponent();
        try{
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle(props.getProperty(EXPORT_TITLE));
        File newDir = fileChooser.showDialog(app.getGUI().getWindow());
        data.setExportDir(newDir.getAbsolutePath());
        CSGWorkspace a = (CSGWorkspace)app.getWorkspaceComponent();
        a.getCourseTab().setExpDir(data.getExportDir());
        
        }
        catch(Exception e){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }
    
    public void setBanner(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData data = (CSGData)app.getDataComponent();
        try{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(props.getProperty(EXPORT_TITLE));
        File newBanner = fileChooser.showOpenDialog(app.getGUI().getWindow());
        data.setBannerImage(newBanner.getAbsolutePath());
        String fileName = newBanner.getName();
        File dst = new File(data.getExportDir()+"/images");
        FileUtils.copyFileToDirectory(newBanner, dst);
        File oldName = new File(dst+"/"+fileName);
        File newName = new File(dst+"/BannerImage.jpg");
        oldName.renameTo(newName);
        CSGWorkspace a = (CSGWorkspace)app.getWorkspaceComponent();
        a.getCourseTab().setBannerImageView(data.getBannerImage());
        
        }
        catch(Exception e){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }
    
    public void setRightFooter(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData data = (CSGData)app.getDataComponent();
        try{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(props.getProperty(EXPORT_TITLE));
        File newBanner = fileChooser.showOpenDialog(app.getGUI().getWindow());
        data.setRightFooter(newBanner.getAbsolutePath());
        String fileName = newBanner.getName();
        File dst = new File(data.getExportDir()+"/images");
        FileUtils.copyFileToDirectory(newBanner, dst);
        File oldName = new File(dst+"/"+fileName);
        File newName = new File(dst+"/RightFooter.jpg");
        oldName.renameTo(newName);
        CSGWorkspace a = (CSGWorkspace)app.getWorkspaceComponent();
        a.getCourseTab().setRightImageView(data.getRightFooter());
        
        }
        catch(Exception e){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }
    
    public void setLeftFooter(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData data = (CSGData)app.getDataComponent();
        try{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(props.getProperty(EXPORT_TITLE));
        File newBanner = fileChooser.showOpenDialog(app.getGUI().getWindow());
        data.setLeftFooter(newBanner.getAbsolutePath());
        String fileName = newBanner.getName();
        File dst = new File(data.getExportDir()+"/images");
        FileUtils.copyFileToDirectory(newBanner, dst);
        File oldName = new File(dst+"/"+fileName);
        File newName = new File(dst+"/LeftFooter.jpg");
        oldName.renameTo(newName);
        CSGWorkspace a = (CSGWorkspace)app.getWorkspaceComponent();
        a.getCourseTab().setLeftImageView(data.getLeftFooter());
        
        }
        catch(Exception e){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }
    
    public void setStyleSheet(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData data = (CSGData)app.getDataComponent();
        CSGWorkspace a = (CSGWorkspace)app.getWorkspaceComponent();
        
        ComboBox comboBox = a.getCourseTab().getChooseSheet();
        String file = comboBox.getValue().toString();
        data.setStyleSheet(file);
    }
    
    public void setTemplateDir(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData data = (CSGData)app.getDataComponent();
        try{
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle(props.getProperty(EXPORT_TITLE));
        File newBanner = fileChooser.showDialog(app.getGUI().getWindow());
        data.setTemplate(newBanner.getAbsolutePath());
        CSGWorkspace a = (CSGWorkspace)app.getWorkspaceComponent();
        a.getCourseTab().setTemplate(data.getTemplate());
        
        }
        catch(Exception e){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }
}
