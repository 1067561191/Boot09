package edu.cming.service;

import edu.cming.bean.Course;
import edu.cming.bean.Location;
import edu.cming.bean.Student;

import java.util.ArrayList;

public interface SignService {


    void active(ArrayList<Course> courses, Student student, Location location);

}
