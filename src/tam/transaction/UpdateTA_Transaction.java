/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;
import tam.data.CSGData;
import tam.data.TeachingAssistant;
import tam.workspace.CSGWorkspace;

/**
 *
 * @author jaski
 */
public class UpdateTA_Transaction implements jTPS_Transaction {

    private String oldName;
    private String oldEmail;
    private String newName;
    private String newEmail;
    CSGData data;
    CSGWorkspace workspace;
    TeachingAssistant ta;

    public UpdateTA_Transaction(String oldName, String oldEmail, String newName, String newEmail, CSGData data, CSGWorkspace workspace, TeachingAssistant ta) {
        this.oldName = oldName;
        this.oldEmail = oldEmail;
        this.newName = newName;
        this.newEmail = newEmail;
        this.data = data;
        this.workspace = workspace;
        this.ta = ta;
    }

    @Override
    public void doTransaction() {
        data.editTA(newName, newEmail, ta);

        for (Pane p : workspace.getTATab().getOfficeHoursGridTACellPanes().values()) {
            String cellKey = p.getId();
            data.toggleEditHours(cellKey, newName, oldName);
        }
        
        workspace.getTATab().getTATable().refresh();

    }

    @Override
    public void undoTransaction() {
        data.editTA(oldName, oldEmail, ta);

        for (Pane p : workspace.getTATab().getOfficeHoursGridTACellPanes().values()) {
            String cellKey = p.getId();
            data.toggleEditHours(cellKey, oldName, newName);
        }
        
        workspace.getTATab().getTATable().refresh();

    }

}
