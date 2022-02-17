package com.Utility;

import org.openqa.selenium.WebElement;

public class Courses {
    private WebElement date, time, classCode, deliveryMode, courseName, week, session, zoomLink;

    public Courses(WebElement date, WebElement time, WebElement classCode, WebElement deliveryMode, WebElement courseName, WebElement week, WebElement session, WebElement zoomLink) {
        this.date = date;
        this.time = time;
        this.classCode = classCode;
        this.deliveryMode = deliveryMode;
        this.courseName = courseName;
        this.week = week;
        this.session = session;
        this.zoomLink = zoomLink;
    }

    public WebElement getDate() {
        return date;
    }

    public void setDate(WebElement date) {
        this.date = date;
    }

    public WebElement getTime() {
        return time;
    }

    public void setTime(WebElement time) {
        this.time = time;
    }

    public WebElement getClassCode() {
        return classCode;
    }

    public void setClassCode(WebElement classCode) {
        this.classCode = classCode;
    }

    public WebElement getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(WebElement deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public WebElement getCourseName() {
        return courseName;
    }

    public void setCourseName(WebElement courseName) {
        this.courseName = courseName;
    }

    public WebElement getWeek() {
        return week;
    }

    public void setWeek(WebElement week) {
        this.week = week;
    }

    public WebElement getSession() {
        return session;
    }

    public void setSession(WebElement session) {
        this.session = session;
    }

    public WebElement getZoomLink() {
        return zoomLink;
    }

    public void setZoomLink(WebElement zoomLink) {
        this.zoomLink = zoomLink;
    }
}
