/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import jtps.jTPS_Transaction;
import tam.CSGApp;
import tam.data.CSGData;
import tam.data.Team;
import tam.workspace.CSGWorkspace;

/**
 *
 * @author jaski
 */
public class AddTeam implements jTPS_Transaction{
    private String name;
    private String color;
    private String textColor;
    private String link;
    private CSGData data;
    private CSGWorkspace workspace;

    public AddTeam(String name, String color, String textColor, String link, CSGApp app) {
        this.name = name;
        this.color = color;
        this.textColor = textColor;
        this.link = link;
        data = (CSGData)app.getDataComponent();
        workspace = (CSGWorkspace)app.getWorkspaceComponent();
    }    

    @Override
    public void doTransaction() {
        data.addTeam(name, color, textColor, link);
        
        workspace.getProjectTab().getTeamComboBox().setItems(data.getTeamNames());
    }

    @Override
    public void undoTransaction() {
        Team team = new Team(name, color, textColor, link);
        data.removeTeam(team);
        
        workspace.getProjectTab().getTeamComboBox().setItems(data.getTeamNames());
    }
    
}
