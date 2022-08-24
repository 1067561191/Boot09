package edu.cming.service;

import edu.cming.bean.Course;
import edu.cming.bean.Location;
import edu.cming.bean.Student;

public interface SignService {

    /**
     * 扫描形参课程的活动列表
     * 有签到活动就签
     * --尚未实现拍照签到
     * @param course 扫描的课程
     * @param student 包含最新cookie
     * @param location 位置签到要用
     * @return ture：签到成功/false：签到失败或者无签到活动
     */
    boolean sign(Course course, Student student, Location location);
}
