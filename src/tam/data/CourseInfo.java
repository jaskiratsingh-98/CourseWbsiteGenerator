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

    private String subject;
    private String number;
    private String semester;
    private String year;
    private String title;
    private String insName;
    private String insHome;
    private String exportDir;
    private String tempDir;
    private Boolean generateHome;
    private Boolean generateSyllabus;
    private Boolean generateSchedule;
    private Boolean generateHWs;
    private Boolean generateProjects;
    private String bannerImage;
    private String leftFooter;
    private String rightFooter;
    private String stylesheet;

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

    public String getTempDir() {
        return tempDir;
    }

    public Boolean getGenerateHome() {
        return generateHome;
    }

    public Boolean getGenerateSyllabus() {
        return generateSyllabus;
    }

    public Boolean getGenerateSchedule() {
        return generateSchedule;
    }

    public Boolean getGenerateHWs() {
        return generateHWs;
    }

    public Boolean getGenerateProjects() {
        return generateProjects;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public String getLeftFooter() {
        return leftFooter;
    }

    public String getRightFooter() {
        return rightFooter;
    }

    public String getStylesheet() {
        return stylesheet;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    public void setInsHome(String insHome) {
        this.insHome = insHome;
    }

    public void setExportDir(String exportDir) {
        this.exportDir = exportDir;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    public void setGenerateHome(Boolean generateHome) {
        this.generateHome = generateHome;
    }

    public void setGenerateSyllabus(Boolean generateSyllabus) {
        this.generateSyllabus = generateSyllabus;
    }

    public void setGenerateSchedule(Boolean generateSchedule) {
        this.generateSchedule = generateSchedule;
    }

    public void setGenerateHWs(Boolean generateHWs) {
        this.generateHWs = generateHWs;
    }

    public void setGenerateProjects(Boolean generateProjects) {
        this.generateProjects = generateProjects;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public void setLeftFooter(String leftFooter) {
        this.leftFooter = leftFooter;
    }

    public void setRightFooter(String rightFooter) {
        this.rightFooter = rightFooter;
    }

    public void setStylesheet(String stylesheet) {
        this.stylesheet = stylesheet;
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
