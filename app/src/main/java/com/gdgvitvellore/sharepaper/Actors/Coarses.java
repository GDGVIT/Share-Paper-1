package com.gdgvitvellore.sharepaper.Actors;

import java.util.List;

/**
 * Created by ramkishorevs on 30/01/17.
 */

public class Coarses {

    int status;

    List<Lists> list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Lists> getList() {
        return list;
    }

    public void setList(List<Lists> list) {
        this.list = list;
    }

    public class Lists
    {
        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getSlots() {
            return slots;
        }

        public void setSlots(String slots) {
            this.slots = slots;
        }

        public String getFaculty() {
            return faculty;
        }

        public void setFaculty(String faculty) {
            this.faculty = faculty;
        }

        String courseCode;
        String courseName;
        String slots;
        String faculty;
    }

}
