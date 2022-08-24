package edu.cming.bean;

public class Course {

    private String courseId;

    private String clazzId;

    public Course() {
    }

    public Course(String courseId, String clazzId) {
        this.courseId = courseId;
        this.clazzId = clazzId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", clazzId='" + clazzId + '\'' +
                '}';
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getClazzId() {
        return clazzId;
    }

    public void setClazzId(String clazzId) {
        this.clazzId = clazzId;
    }
}
