/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import jtps.jTPS_Transaction;
import tam.data.CSGData;
import tam.workspace.CSGWorkspace;

/**
 *
 * @author jaski
 */
public class AddTA_Transaction implements jTPS_Transaction {

    private String taName;
    private String taEmail;
    private CSGData data;
    private CSGWorkspace workspace;

    public AddTA_Transaction(String name, String email, CSGData data, CSGWorkspace workspace) {
        taName = name;
        taEmail = email;
        this.data = data;
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        data.addTA(taName, taEmail);

        workspace.getRecitationTab().getTa1ComboBox().setItems(data.getTaNames());
        workspace.getRecitationTab().getTa2ComboBox().setItems(data.getTaNames());
    }

    @Override
    public void undoTransaction() {
        data.removeTA(taName);
        
        workspace.getRecitationTab().getTa1ComboBox().setItems(data.getTaNames());
        workspace.getRecitationTab().getTa2ComboBox().setItems(data.getTaNames());
    }
}
