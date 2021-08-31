package com.xWash.admin;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.entity.APIResult;
import com.xWash.entity.QueryResult;
import com.xWash.service.Impl.Distributor;
import com.xWash.util.BuildingFileUtil;
import com.xWash.util.ComparatorsUtil;
import com.xWash.util.RedisUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(path = "/api", method = {RequestMethod.GET, RequestMethod.POST})
public class CheckController {


    @Autowired
    Distributor distributor;
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping(path = "/{building}")
    @ResponseBody
    public APIResult getMachineStatus(@PathVariable(name = "building") String building
            , String autoUpdate // 前端自动更新请求标识
            , @CookieValue(value = "user", defaultValue = "") String userCookie
            , HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
        ModelAndView mv = new ModelAndView();
        if (!redisUtil.hashExist(building)) {
            session.setAttribute("msg", "没有找到" + building);
            response.sendError(404);
            mv.setViewName("404");
            return APIResult.createOnlyCode(404);
        }
        Map<String, QueryResult> map = redisUtil.hashGetAll(building);
        JSONObject resJson = new JSONObject(true);
        map.entrySet().stream()
                .sorted(ComparatorsUtil.getComparator(building))
                .forEach(entry->{
                    resJson.set(entry.getKey(),JSONUtil.parseObj(entry.getValue().toJson()));
                });
        mv.setViewName("api");
        return APIResult.createWithData(200, resJson);
    }
}
