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
public class AddTeam implements jTPS_Transaction{
    private String name;
    private String color;
    private String textColor;
    private String link;
    private CSGData data;

    public AddTeam(String name, String color, String textColor, String link, CSGData data) {
        this.name = name;
        this.color = color;
        this.textColor = textColor;
        this.link = link;
        this.data = data;
    }    

    @Override
    public void doTransaction() {
        data.addTeam(name, color, textColor, link);
    }

    @Override
    public void undoTransaction() {
        Team team = new Team(name, color, textColor, link);
        data.removeTeam(team);
    }
    
}
