package edu.cming.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import edu.cming.bean.Course;
import edu.cming.bean.Location;
import edu.cming.bean.Student;
import edu.cming.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("signService")
public class SignServiceImpl implements SignService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Async("applicationTaskExecutor")
    @Override
    public void active(ArrayList<Course> courses, Student student, Location location) {
        int sleepSeconds = 55;
        log.info("用户：{}\n密码：{}\n姓名：{}\nenc：{}\n位置信息：{}",student.getAccount(),student.getPassword(),student.getName(),student.getEnc(),location.toString());
        log.info("用户的根目录下存在{}个课程，开始监听...\n用户请勿关闭此控制台，否则程序停止工作。",courses.size());
        while (true) {
            for (Course course : courses) {
                if (sign(course, student, location)) {
                    log.info("签到成功，已退出监听，用户可关闭此控制台。");
                    return;
                } else {
                    log.info("课程ID：{}\t暂无签到活动。", course.getCourseId());
                }
            }
            try {
                log.info("所有课程扫描结束\n暂未发现签到活动\n等待{}秒后再次扫描...", sleepSeconds);
                Thread.sleep(sleepSeconds * 1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 扫描形参课程的活动列表
     * 有签到活动就签
     * --尚未实现拍照签到
     *
     * @param course   扫描的课程
     * @param student  包含最新cookie
     * @param location 位置签到要用
     * @return ture：签到成功/false：签到失败或者无签到活动
     */
    private boolean sign(Course course, Student student, Location location) {
        boolean result = false;
        String Cookies = "_d=" + student.getD() + "; "
                + "vc3=" + student.getVc3() + "; "
                + "UID=" + student.getUid() + "; "
                + "uf=" + student.getUf();
        String responseStr = HttpRequest.get(
                        "https://mobilelearn.chaoxing.com/v2/apis/active/student/activelist?fid=0"
                                + "&courseId=" + course.getCourseId()
                                + "&classId=" + course.getClazzId()
                                + "&_=" + System.currentTimeMillis()
                )
                .header("Cookie", Cookies)
                .execute()
                .body();
        JSONArray activeList = JSONUtil.parseObj(responseStr).getJSONObject("data").getJSONArray("activeList");
        for (int i = 0; i < activeList.size(); i++) {
            JSONObject active = activeList.getJSONObject(i);
            Integer otherId = active.getInt("otherId");
            Integer status = active.getInt("status");
            long startTime = active.getLong("startTime");
            if (otherId != null) {
                if (otherId >= 0 && otherId <= 5 && status == 1 && (System.currentTimeMillis() - startTime) < 60 * 60 * 1000) {
                    String nameOne = activeList.getJSONObject(i).getStr("nameOne");
                    String aid = activeList.getJSONObject(i).getStr("id");
                    log.info("发现一个到没签");
                    HttpRequest.get(
                                    "https://mobilelearn.chaoxing.com/newsign/preSign"
                                            + "?courseId=" + course.getCourseId()
                                            + "&classId=" + course.getClazzId()
                                            + "&activePrimaryId=" + aid
                                            + "&general=1&sys=1&ls=1&appType=15&&tid=&uid=" + student.getUid()
                                            + "&ut=s"
                            )
                            .header("Cookie", Cookies)
                            .execute();
                    switch (nameOne) {
                        case "签到":
                        case "签到码签到":
                        case "手势签到": {
                            String signbody = HttpRequest.get(
                                            "https://mobilelearn.chaoxing.com/pptSign/stuSignajax"
                                                    + "?activeId=" + aid
                                                    + "&uid=" + student.getUid()
                                                    + "&clientip=&latitude=-1&longitude=-1&appType=15"
                                                    + "&fid=" + student.getFid()
                                                    + "&name=" + student.getName()
                                    )
                                    .header("Cookie", Cookies)
                                    .execute()
                                    .body();
                            log.info(nameOne + signbody);
                            result = true;
                            break;
                        }
                        case "位置签到": {
                            if (null != location.getAddress()) {
                                String signbody = HttpRequest.get(
                                                "https://mobilelearn.chaoxing.com/pptSign/stuSignajax"
                                                        + "?name=" + student.getName()
                                                        + "&address=" + location.getAddress()
                                                        + "&activeId=" + aid
                                                        + "&uid=" + student.getUid()
                                                        + "&clientip=&latitude=" + location.getLat()
                                                        + "&longitude=" + location.getLng()
                                                        + "&fid=" + student.getFid()
                                                        + "&appType=15&ifTiJiao=1"
                                        )
                                        .header("Cookie", Cookies)
                                        .execute()
                                        .body();
                                log.info(nameOne + signbody);
                                result = true;
                            }
                            break;
                        }
                        case "二维码签到": {
                            if (null != student.getEnc()) {
                                String signbody = HttpRequest.get(
                                                "https://mobilelearn.chaoxing.com/pptSign/stuSignajax"
                                                        + "?enc=" + student.getEnc()
                                                        + "&name=" + student.getName()
                                                        + "&activeId=" + aid
                                                        + "&uid=" + student.getUid()
                                                        + "&clientip=&useragent=&latitude=-1&longitude=-1&fid=" + student.getFid()
                                                        + "&appType=15"
                                        )
                                        .header("Cookie", Cookies)
                                        .execute()
                                        .body();
                                log.info(nameOne + signbody);
                                result = true;
                            }
                            break;
                        }
                        default: {
                            log.error("发现未支持的签到活动");
                            break; // 退出switch
                        }
                    }
                    break; // 退出activeList遍历
                }
            }
        }
        return result;
    }
}
