package edu.cming.service;

import edu.cming.bean.Course;
import edu.cming.bean.Location;
import edu.cming.bean.Student;

public interface SignService {

    boolean sign(Course course, Student student, Location location);
}
