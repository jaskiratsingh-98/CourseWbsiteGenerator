/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transaction;

import jtps.jTPS_Transaction;
import tam.data.CSGData;

/**
 *
 * @author jaski
 */
public class AddTA_Transaction implements jTPS_Transaction{
    
    private String taName;
    private String taEmail;
    private CSGData data;
    
    
    public AddTA_Transaction(String name, String email, CSGData data){
        taName = name;
        taEmail = email;
        this.data = data;
        
    }
    
    @Override
    public void doTransaction(){
        data.addTA(taName, taEmail);
    }
    
    @Override
    public void undoTransaction(){
        data.removeTA(taName);
    }
}
