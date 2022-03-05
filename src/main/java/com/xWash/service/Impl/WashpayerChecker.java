package com.xWash.service.Impl;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.socket.SocketRuntimeException;
import com.xWash.model.dao.Machine;
import com.xWash.model.entity.MStatus;
import com.xWash.model.entity.MessageEnum;
import com.xWash.model.entity.QueryResult;
import com.xWash.util.MysqlUtil;
import com.xWash.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;

@Service("washpayerChecker")
public class WashpayerChecker extends AbstractChecker {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MysqlUtil mysqlUtil;

    private final static String URL = "https://www.washpayer.com/user/startAction";
    private final static int TIMEOUT = 10000;
    private final static String DEV_NO_COOKIE_NAME = "user_dev_no";
    private final static String STARTKEY = "ojqSxwAC-NiAEMqO3asVzc0CO6OI-1630153349410-11770";  // 不知道这参数是干啥的，但是不可或缺
    private final static String USER_AGENT = "Mozilla/5.0 (Linux; Android 12; MI 8 Build/SQ1D.211205.016.A1; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/86.0.4240.99 XWEB/3185 MMWEBSDK/20211202 Mobile Safari/537.36 MMWEBID/8395 MicroMessenger/8.0.18.2060(0x28001257) Process/toolsmp WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64";  // 不知道这参数是干啥的，但是不可或缺

    {
        authKey = "washpayer";
    }

    private String requestDevNo(String link) {
        HttpResponse response = HttpRequest
                .get(link)
                .header("user-agent", USER_AGENT)
                .cookie(getToken())
                .execute();
        if (response.getCookie(DEV_NO_COOKIE_NAME) == null) {
            throw new RuntimeException("Cookie not found");
        }
        return response.getCookieValue(DEV_NO_COOKIE_NAME);
    }

    private String getDevNo(Machine machine) {
        String devNo = redisUtil.hashGet("devno", machine.getName());
        if (devNo != null) {
            return devNo;
        }
        devNo = requestDevNo(machine.getLink());
        redisUtil.hashSet("devno", machine.getName(), devNo);
        return devNo;
    }

    @Override
    protected HttpResponse request(Machine machine) throws IORuntimeException {
        String devNo = getDevNo(machine);
        JSONObject body = (JSONObject) JSONUtil.parse("{\n" +
                "  \"devNo\": \"" + devNo + "\",\n" +
                "  \"packageId\": \"2\",\n" +
                "  \"attachParas\": {\n" +
                "    \"startKey\": \"" + STARTKEY + "\",\n" +
                "  }\n" +
                "}\n");
        return HttpRequest
                .post(URL)
                .cookie("jwt_auth_domain=MyUser " + getToken())
                .timeout(TIMEOUT)
                .body(body.toString())
                .execute();
    }

    @Override
    protected QueryResult extract(HttpResponse response) throws JSONException {
        JSONObject body = JSONUtil.parseObj(response.body());
        QueryResult qr = new QueryResult();
        if (body.get("result") == null) {
            throw new JSONException("Filed result not found");
        }
        qr.setStatus(body.getInt("result").equals(999) ? MStatus.AVAILABLE : MStatus.USING);
        return qr;
    }

    @Override
    public QueryResult check(Machine machine) {
        QueryResult qr = new QueryResult();
        try {
            HttpResponse response = request(machine);
            qr = extract(response);
        } catch (IORuntimeException | HttpException e) {
            if (e.getCause() instanceof SocketRuntimeException
            || e.getCause() instanceof SocketTimeoutException) {
                qr.setStatus(MStatus.USING);
            } else {
                qr.setStatus(MStatus.UNKNOWN);
                qr.setMessage(MessageEnum.MESSAGE_UNKNOWN);
            }
        } catch (JSONException e) {
            qr.setStatus(MStatus.UNKNOWN);
            qr.setMessage(MessageEnum.MESSAGE_JSON_PARSE_EXCEPTION);
        }

        return qr;
    }
}
