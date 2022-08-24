package edu.cming.service.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import edu.cming.bean.Course;
import edu.cming.service.CourseService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service("courseService")
public class CourseServiceImpl implements CourseService {
    @Override
    public ArrayList<Course> getCourses() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("courseType", "1");
        paramMap.put("courseFolderId", "0");
        paramMap.put("baseEducation", "0");
        paramMap.put("superstarClass", "");
        paramMap.put("courseFolderSize", "0");
        String responseStr = HttpRequest.post("http://mooc1-1.chaoxing.com/visit/courselistdata")
                .header(Header.HOST, "mooc1-1.chaoxing.com")
                .header(Header.ORIGIN, "http://mooc1-1.chaoxing.com")
                .header(Header.REFERER, "http://mooc1-1.chaoxing.com/visit/interaction?s=a3e2d0270a383c7416109135e217a8b8")
                .form(paramMap)
                .execute()
                .body();
        Elements courseUl = Jsoup.parse(responseStr).body().getElementById("courseList").children();
        ArrayList<Course> courses = new ArrayList<>();
        for (Element courseLi : courseUl) {
            Element course_cover_div = courseLi.getElementsByClass("course-cover").get(0);
            if (course_cover_div.childrenSize() > 2) {
                continue;
            }
            courses.add(
                    new Course(
                            courseLi.attr("courseId"),
                            courseLi.attr("clazzId")
                    )
            );
        }

        return courses;
    }
}
