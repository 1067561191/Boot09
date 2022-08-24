package edu.cming.service.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import edu.cming.bean.Course;
import edu.cming.bean.Location;
import edu.cming.bean.Student;
import edu.cming.service.SignService;
import org.springframework.stereotype.Service;

@Service("signService")
public class SignServiceImpl implements SignService {
    @Override
    public boolean sign(Course course, Student student, Location location) {
        boolean result = false;
        String responseStr = HttpRequest.get(
                        "https://mobilelearn.chaoxing.com/v2/apis/active/student/activelist?fid=0"
                                + "&courseId=" + course.getCourseId()
                                + "&classId=" + course.getClazzId()
                                + "&_=" + System.currentTimeMillis()
                )
                .header(Header.HOST, "mobilelearn.chaoxing.com")
                .header(Header.ORIGIN, "https://mobilelearn.chaoxing.com")
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
                    System.out.println("发现一个到没签");
                    HttpRequest.get(
                            "https://mobilelearn.chaoxing.com/newsign/preSign"
                                    + "?courseId=" + course.getCourseId()
                                    + "&classId=" + course.getClazzId()
                                    + "&activePrimaryId=" + aid
                                    + "&general=1&sys=1&ls=1&appType=15&&tid=&uid=" + student.getUid()
                                    + "&ut=s"
                    ).execute();
                    String signbody = null;
                    switch (nameOne) {
                        case "签到":
                        case "签到码签到":
                        case "手势签到":
                            signbody = HttpRequest.get(
                                    "https://mobilelearn.chaoxing.com/pptSign/stuSignajax"
                                            + "?activeId=" + aid
                                            + "&uid=" + student.getUid()
                                            + "&clientip=&latitude=-1&longitude=-1&appType=15"
                                            + "&fid=" + student.getFid()
                                            + "&name=" + student.getName()
                            ).execute().body();
                            System.out.println(nameOne + signbody);
                            result = true;
                            break;
                        case "位置签到":
                            if (null != location.getAddress()) {
                                signbody = HttpRequest.get(
                                        "https://mobilelearn.chaoxing.com/pptSign/stuSignajax"
                                                + "?name=" + student.getName()
                                                + "&address=" + location.getAddress()
                                                + "&activeId=" + aid
                                                + "&uid=" + student.getUid()
                                                + "&clientip=&latitude=" + location.getLat()
                                                + "&longitude=" + location.getLng()
                                                + "&fid=" + student.getFid()
                                                + "&appType=15&ifTiJiao=1"
                                ).execute().body();
                                System.out.println(nameOne + signbody);
                                result = true;
                            }
                            break;
                        case "二维码签到":
                            if (null != student.getEnc()) {
                                signbody = HttpRequest.get(
                                        "https://mobilelearn.chaoxing.com/pptSign/stuSignajax"
                                                + "?enc=" + student.getEnc()
                                                + "&name=" + student.getName()
                                                + "&activeId=" + aid
                                                + "&uid=" + student.getUid()
                                                + "&clientip=&useragent=&latitude=-1&longitude=-1&fid=" + student.getFid()
                                                + "&appType=15"
                                ).execute().body();
                                System.out.println(nameOne + signbody);
                                result = true;
                            }
                            break;
                        default:
                            System.out.println("发现未支持的签到活动");
                            break; // 退出switch
                    }
                    break; // 退出activeList遍历
                }
            }
        }
        return result;
    }
}
