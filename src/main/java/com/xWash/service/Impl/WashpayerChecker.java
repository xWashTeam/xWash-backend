package com.xWash.service.Impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.model.dao.Machine;
import com.xWash.model.entity.MStatus;
import com.xWash.model.entity.QueryResult;
import com.xWash.util.MysqlUtil;
import com.xWash.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service("washpayerChecker")
public class WashpayerChecker extends AbstractChecker {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MysqlUtil mysqlUtil;

    private final static String URL = "https://www.washpayer.com/user/startAction";
    private final static int TIMEOUT = 10000;
    private static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 10; MI 8 Build/QKQ1.190828.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045713 Mobile Safari/537.36 MMWEBID/8395 MicroMessenger/8.0.10.1960(0x28000A31) Process/tools WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64";
    private final static String STARTKEY = "ojqSxwAC-NiAEMqO3asVzc0CO6OI-1630153349410-11770";  // 不知道这参数是干啥的，但是不可或缺

    {
        authKey = "washpayer";
    }

    private String requestDevNo(String link) {
        HttpResponse response = HttpRequest
                .get(link)
                .cookie(getToken())
                .header("user-agent", USER_AGENT)
                .execute();
        return response.getCookieValue("user_dev_no");
    }

    private String getDevNo(Machine machine) {
        String devNo = redisUtil.hashGet("devno", machine.getName());
        if ( devNo != null) {
            return devNo;
        }
        devNo = requestDevNo(machine.getLink());
        redisUtil.hashSet("devno", machine.getName(), devNo);
        return devNo;
    }

    @Override
    protected HttpResponse request(Machine machine) throws IOException {
        String devNo = getDevNo(machine);
        JSONObject body = (JSONObject) JSONUtil.parse("{\n" +
                "  \"devNo\": \"" + devNo + "\",\n" +
                "  \"packageId\": \"2\",\n" +
                "  \"attachParas\": {\n" +
                "    \"startKey\": \"" + STARTKEY + "\",\n" +
                "  }\n" +
                "}\n");
        // TODO focus on timeout
        return HttpRequest
                .get(URL)
                .cookie(getToken())
                .timeout(TIMEOUT)
                .body(body.toString())
                .execute();
    }

    @Override
    protected QueryResult extract(HttpResponse response) throws IOException {
        JSONObject body = JSONUtil.parseObj(response.body());
        QueryResult qr = new QueryResult();
        qr.setStatus(body.getInt("result").equals(999) ? MStatus.AVAILABLE : MStatus.USING);
        return qr;
    }

    @Override
    public QueryResult check(Machine machine) {
        return new QueryResult();
//        try {
//            HttpResponse response = request(machine);
//            return extract(response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
    }
}
