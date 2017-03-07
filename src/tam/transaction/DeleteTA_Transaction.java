/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import jtps.jTPS_Transaction;
import tam.data.TAData;

/**
 *
 * @author jaski
 */
public class DeleteTA_Transaction implements jTPS_Transaction{
    
    private String taName;
    private String taEmail;
    private TAData data;
    
    public DeleteTA_Transaction(String taName, String taEmail, TAData data){
        this.taName = taName;
        this.taEmail = taEmail;
        this.data = data;
    }
    
    @Override
    public void doTransaction(){
        data.removeTA(taName);
    }
    
    @Override
    public void undoTransaction(){
        data.addTA(taName, taEmail);
    }
    
}
