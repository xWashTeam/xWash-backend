package com.xWash.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.model.entity.APIResult;
import com.xWash.model.entity.LocationComparator;
import com.xWash.model.entity.QueryResult;
import com.xWash.service.Impl.Distributor;
import com.xWash.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
                .sorted(building.equals("d19") ? LocationComparator.d19Comparator : LocationComparator.xi1Comparator)
                .forEach(entry -> {
                    resJson.set(entry.getKey(), JSONUtil.parseObj(entry.getValue().toJson()));
                });
        mv.setViewName("api");
        return APIResult.createWithData(200, resJson);
    }
}
