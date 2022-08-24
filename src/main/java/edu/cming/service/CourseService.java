package edu.cming.service;

import edu.cming.bean.Course;

import java.util.ArrayList;

public interface CourseService {

    /**
     * hutool的http工具自带cookie保留功能 可以直接查询课程html
     * @return 根目录未结课的课程集合
     */
    ArrayList<Course> getCourses();
}
