/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jaski
 */
public class Pages {
    private StringProperty navbar;
    private StringProperty fileName;
    private StringProperty script;
    private boolean generate;
    
    public Pages(String a, String b, String c){
        navbar = new SimpleStringProperty(a);
        fileName = new SimpleStringProperty(b);
        script = new SimpleStringProperty(c);
        generate = false;
    }

    public String getNavbar() {
        return navbar.get();
    }

    public String getFileName() {
        return fileName.get();
    }

    public String getScript() {
        return script.get();
    }

    public void setNavbar(String navbar) {
        this.navbar.setValue(navbar);
    }

    public void setFileName(String fileName) {
        this.fileName.setValue(fileName);
    }

    public void setScript(String script) {
        this.script.setValue(script);
    }

    public boolean isGenerate() {
        return generate;
    }

    public void setGenerate(boolean generate) {
        this.generate = generate;
    }
    
}
