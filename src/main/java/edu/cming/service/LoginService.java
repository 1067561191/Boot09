package edu.cming.service;

import edu.cming.bean.Student;

public interface LoginService {

    /**
     * 登陆学习通获取cookie
     * @param student 用手机号码和des密码获取cookie
     * @return student 包含最新cookie的student
     */
    Student login(Student student);
}
