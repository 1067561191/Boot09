package edu.cming;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.crypto.symmetric.DES;
import edu.cming.bean.Course;
import edu.cming.bean.Location;
import edu.cming.bean.Student;
import edu.cming.service.CourseService;
import edu.cming.service.LoginService;
import edu.cming.service.SignService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Boot09Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Boot09Application.class, args);
        Student student = applicationContext.getBean("student", Student.class);
        student.setDespassword(new DES("ECB", "PKCS7Padding", "u2oh6Vu^HWe40fj".getBytes()).encryptHex(student.getPassword(), StandardCharsets.UTF_8));
        Location location = applicationContext.getBean("location", Location.class);
        LoginService loginService = applicationContext.getBean("loginService", LoginService.class);
        student = loginService.login(student);
        CourseService courseService = applicationContext.getBean("courseService", CourseService.class);
        ArrayList<Course> courses = courseService.getCourses();
        SignService signService = applicationContext.getBean("signService", SignService.class);
        boolean status = true;
        while (status) {
            for (Course course : courses) {
                status = !signService.sign(course, student, location);
            }
            System.out.println("status = " + status);
            ThreadUtil.sleep(60, TimeUnit.SECONDS);
        }
    }

}
