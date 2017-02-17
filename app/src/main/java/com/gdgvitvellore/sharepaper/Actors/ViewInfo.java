package com.gdgvitvellore.sharepaper.Actors;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ramkishorevs on 26/01/17.
 */

public class ViewInfo implements Serializable {

    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Paper> getPaper() {
        return paper;
    }

    public void setPaper(List<Paper> paper) {
        this.paper = paper;
    }

    String message;


    @SerializedName("papers")
    List<Paper> paper;

    public class Paper implements Serializable
    {


        @SerializedName("courseCode")
        String c_cd;

        String slot;

        @SerializedName("noOfImages")
        String no_of_images;

        String regno;

        public String getRegno() {
            return regno;
        }

        public void setRegno(String regno) {
            this.regno = regno;
        }

        String sem;
        String year;

        @SerializedName("imgArr")
        List<String> img_arr;

        String createdAt;
        String updatedAt;
        String id;

        public String getC_cd() {
            return c_cd;
        }

        public void setC_cd(String c_cd) {
            this.c_cd = c_cd;
        }

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }

        public String getNo_of_images() {
            return no_of_images;
        }

        public void setNo_of_images(String no_of_images) {
            this.no_of_images = no_of_images;
        }

        public String getSem() {
            return sem;
        }

        public void setSem(String sem) {
            this.sem = sem;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public List<String> getImg_arr() {
            return img_arr;
        }

        public void setImg_arr(List<String> img_arr) {
            this.img_arr = img_arr;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
