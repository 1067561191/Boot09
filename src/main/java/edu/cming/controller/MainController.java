package edu.cming.controller;

import cn.hutool.crypto.symmetric.DES;
import edu.cming.bean.Course;
import edu.cming.bean.Location;
import edu.cming.bean.Student;
import edu.cming.service.CourseService;
import edu.cming.service.LoginService;
import edu.cming.service.SignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@RestController
public class MainController {

    @Resource(name = "loginService")
    private LoginService loginService;
    @Resource(name = "courseService")
    private CourseService courseService;
    @Resource(name = "signService")
    private SignService signService;

    @GetMapping("/sign")
    public String main(@NotNull String account
            , @NotNull String password
            , @NotNull String name
            , String enc, String address, String lng, String lat) {
        String despassword = new DES("ECB", "PKCS7Padding", "u2oh6Vu^HWe40fj".getBytes()).encryptHex(password, StandardCharsets.UTF_8);
        Location location = new Location(address, lng, lat);
        Student student = loginService.login(new Student(account, password, name, enc, despassword));
        ArrayList<Course> courses = courseService.getCourses();
        signService.active(courses, student, location);
        return "正在监听，用户可关闭此页面。";
    }
}
