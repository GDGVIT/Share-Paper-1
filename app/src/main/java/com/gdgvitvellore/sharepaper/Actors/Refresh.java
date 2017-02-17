package com.gdgvitvellore.sharepaper.Actors;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by ramkishorevs on 04/02/17.
 */

public class Refresh extends RealmObject {

    private String reg_no;
    private String name;
    private String semester;

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public RealmList<Courses> getCourses() {
        return courses;
    }

    public void setCourses(RealmList<Courses> courses) {
        this.courses = courses;
    }

    private String school;
    private RealmList<Courses> courses;
}

