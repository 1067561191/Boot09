package edu.cming.service.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import edu.cming.bean.Student;
import edu.cming.service.LoginService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Service("loginService")
public class LoginServiceImpl implements LoginService {


    /**
     * 登陆学习通获取cookie
     * @param student 用手机号码和des密码获取cookie
     * @return student 包含最新cookie的student
     */
    @Override
    public Student login(Student student) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("fid", "-1");
        paramMap.put("uname", student.getAccount());
        paramMap.put("password", student.getDespassword());
        paramMap.put("refer", "http%3A%2F%2F*.chaoxing.com");
        paramMap.put("t", "true");
        paramMap.put("forbidotherlogin", "0");
        paramMap.put("validate", "");
        paramMap.put("doubleFactorLogin", 0);
        HttpResponse response = HttpRequest.post("https://passport2.chaoxing.com/fanyalogin")
                .header(Header.ACCEPT, "application/json, text/javascript, */*; q=0.01")
                .header(Header.ACCEPT_ENCODING, "gzip, deflate, br")
                .header(Header.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header(Header.CONNECTION, "keep-alive")
                .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8")
                .header(Header.HOST, "passport2.chaoxing.com")
                .header(Header.ORIGIN, "https://passport2.chaoxing.com")
                .header(Header.REFERER, "http://passport2.chaoxing.com/login?fid=&newversion=true&refer=http://i.chaoxing.com")
                .header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_5; de-de) AppleWebKit/534.15+ (KHTML, like Gecko) Version/5.0.3 Safari/533.19.4")
                .charset(StandardCharsets.UTF_8)
                .form(paramMap)
                .execute();
        JSONObject body = JSONUtil.parseObj(response.body());
        Boolean status = body.getBool("status", false);
        if (!status) {
            return null;
        }
        List<String> setcookie = response.headers().get("Set-Cookie");
        for (String s : setcookie) {
            String key = s.substring(0, s.indexOf("="));
            String value = s.substring(s.indexOf("=") + 1, s.indexOf(";"));
            switch (key) {
                case "fid":
                    student.setFid(value);
                    break;
                case "_d":
                    student.setD(value);
                    break;
                case "vc3":
                    student.setVc3(value);
                    break;
                case "uf":
                    student.setUf(value);
                    break;
                case "UID":
                    student.setUid(value);
                    break;
            }
        }
//        response.close();
        return student;
    }
}
