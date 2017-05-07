/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import jtps.jTPS_Transaction;
import tam.data.CSGData;
import tam.data.Team;
import tam.workspace.CSGWorkspace;

/**
 *
 * @author jaski
 */
public class UpdateTeam implements jTPS_Transaction{
    private String name;
    private String color;
    private String textColor;
    private String link;
    private String oldName;
    private String oldColor;
    private String oldTextColor;
    private String oldLink;
    private CSGData data;
    private Team team;
    private CSGWorkspace workspace;

    public UpdateTeam(String name, String color, String textColor, String link, CSGData data, Team team, CSGWorkspace workspace) {
        this.name = name;
        this.color = color;
        this.textColor = textColor;
        this.link = link;
        this.data = data;
        this.team = team;
        oldName = team.getName();
        oldColor = team.getColor();
        oldTextColor = team.getTextColor();
        oldLink = team.getLink();
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        data.editTeam(team, name, color, textColor, link);
        workspace.getProjectTab().getTeams().refresh();
    }

    @Override
    public void undoTransaction() {
        Team team1 = new Team(name, color, textColor, link);
        data.editTeam(team1, oldName, oldColor, oldTextColor, oldLink);
        workspace.getProjectTab().getTeams().refresh();
        
    }
    
}
