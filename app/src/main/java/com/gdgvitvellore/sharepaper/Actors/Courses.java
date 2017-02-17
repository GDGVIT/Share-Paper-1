package com.gdgvitvellore.sharepaper.Actors;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by ramkishorevs on 04/02/17.
 */

public class Courses extends RealmObject
{
    private String class_number;
    private String course_code;
    private String course_option;
    private String course_mode;
    private String course_title;
    private String faculty;
    private String slot;
    private String venue;

    public String getClass_number() {
        return class_number;
    }

    public void setClass_number(String class_number) {
        this.class_number = class_number;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_option() {
        return course_option;
    }

    public void setCourse_option(String course_option) {
        this.course_option = course_option;
    }

    public String getCourse_mode() {
        return course_mode;
    }

    public void setCourse_mode(String course_mode) {
        this.course_mode = course_mode;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}

