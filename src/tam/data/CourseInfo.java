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
public class CourseInfo {

    private String subject = "";
    private String number = "";
    private String semester = "";
    private String year = "";
    private String title = "";
    private String insName = "";
    private String insHome = "";
    private String exportDir;

    public CourseInfo() {

    }

    public CourseInfo(String subject, String number, String semester,
            String year, String title, String insName, String insHome) {
        this.subject = subject;
        this.number = number;
        this.semester = semester;
        this.year = year;
        this.title = title;
        this.insName = insName;
        this.insHome = insHome;
    }

    public void setCourseInfo(String subject, String number, String semester,
            String year, String title, String insName, String insHome) {
        this.subject = subject;
        this.number = number;
        this.semester = semester;
        this.year = year;
        this.title = title;
        this.insName = insName;
        this.insHome = insHome;
    }

    public String getSubject() {
        return subject;
    }

    public String getNumber() {
        return number;
    }

    public String getSemester() {
        return semester;
    }

    public String getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getInsName() {
        return insName;
    }

    public String getInsHome() {
        return insHome;
    }

    public String getExportDir() {
        return exportDir;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof CourseInfo)) {
            return false;
        }
        else{
            CourseInfo ci = (CourseInfo) other;
            return subject.equals(ci.subject)
                && number.equals(ci.number)
                && semester.equals(ci.semester)
                && year.equals(ci.year)
                && subject.equals(ci.subject)
                && insHome.equals(ci.insHome)
                && insName.equals(ci.insName)
                && title.equals(ci.title);
        }

    }

}
