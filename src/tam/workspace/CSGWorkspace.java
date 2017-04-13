package tam.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import tam.CSGApp;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import tam.CSGProp;
import tam.style.TAStyle;
import tam.data.TAData;
import tam.data.TeachingAssistant;

/**
 * This class serves as the workspace component for the TA Manager
 * application. It provides all the user interface controls in 
 * the workspace area.
 * 
 * @author Richard McKenna
 */
public class CSGWorkspace extends AppWorkspaceComponent {
    CSGApp app;

    TAController controller;
    TabPane tabPane;
    TATab taTab;
    CourseInfoTab courseTab;
    private VBox borderPane; 
    private RecitationTab recitationTab;
    private ScheduleTab scheduleTab;
    private ProjectTab projectTab;
    
    

    /**
     * The contstructor initializes the user interface, except for
     * the full office hours grid, since it doesn't yet know what
     * the hours will be until a file is loaded or a new one is created.
     */
    public CSGWorkspace(CSGApp initApp) {
        app = initApp;

        controller = new TAController(app);
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace = new VBox();
        tabPane = new TabPane();
        taTab = new TATab(app);
        courseTab = new CourseInfoTab(app);
        recitationTab = new RecitationTab();
        scheduleTab = new ScheduleTab();
        projectTab = new ProjectTab();
        
        tabPane.getTabs().add(courseTab.getTab());
        tabPane.getTabs().add(taTab.getTab());
        tabPane.getTabs().add(recitationTab.getTab());
        tabPane.getTabs().add(scheduleTab.getTab());
        tabPane.getTabs().add(projectTab.getTab());
        
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        borderPane = new VBox();
        borderPane.getChildren().add(tabPane);
        workspace.getChildren().add(borderPane);
    }
    
    
    // WE'LL PROVIDE AN ACCESSOR METHOD FOR EACH VISIBLE COMPONENT
    // IN CASE A CONTROLLER OR STYLE CLASS NEEDS TO CHANGE IT

    @Override
    public void resetWorkspace() {
        taTab.resetTAWorkspace();
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        taTab.reloadTAWorkspace(dataComponent);
    }

    public TATab getTATab() {
        return taTab;
    }
    
    public CourseInfoTab getCourseTab(){
        return courseTab;
    }

    public RecitationTab getRecitationTab() {
        return recitationTab;
    }

    public ScheduleTab getScheduleTab() {
        return scheduleTab;
    }

    public ProjectTab getProjectTab() {
        return projectTab;
    }

    public VBox getBorderPane() {
        return borderPane;
    }
    
    
    
}
