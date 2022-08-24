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
        Student student = applicationContext.getBean("student", Student.class); // 实例化个人信息
        student.setDespassword(new DES("ECB", "PKCS7Padding", "u2oh6Vu^HWe40fj".getBytes()).encryptHex(student.getPassword(), StandardCharsets.UTF_8)); // des密码
        Location location = applicationContext.getBean("location", Location.class); // 实例化位置信息
        student = applicationContext.getBean("loginService", LoginService.class).login(student); // 登陆学习通 拿到Cookie
        ArrayList<Course> courses = applicationContext.getBean("courseService", CourseService.class).getCourses(); // 根目录课程信息
        SignService signService = applicationContext.getBean("signService", SignService.class);
        boolean status = true;
        while (status) {
            for (Course course : courses) {
                status = !signService.sign(course, student, location); // 扫描课程的近2小时的活动 并签到
            }
            ThreadUtil.sleep(60, TimeUnit.SECONDS); // 未发现就等60秒再次扫描
        }
    }

}
