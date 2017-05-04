/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import jtps.jTPS_Transaction;
import tam.data.CSGData;
import tam.data.Team;

/**
 *
 * @author jaski
 */
public class DeleteTeam implements jTPS_Transaction{
    private String name;
    private String color;
    private String textColor;
    private String link;
    private Team team;
    private CSGData data;

    public DeleteTeam(Team team, CSGData data) {
        name = team.getName();
        color = team. getColor();
        textColor = team.getTextColor();
        link = team.getLink();
        this.data = data;
    }
    
    @Override
    public void doTransaction() {
        data.removeTeam(team);
    }

    @Override
    public void undoTransaction() {
        data.addTeam(name, color, textColor, link);
    }
    
}
