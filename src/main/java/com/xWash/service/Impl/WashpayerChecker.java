package com.xWash.service.Impl;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.entity.MStatus;
import com.xWash.entity.QueryResult;
import com.xWash.service.IChecker;
import com.xWash.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("washpayerChecker")
public class WashpayerChecker implements IChecker {
    @Autowired
    RedisUtil redisUtil;
    private final static String checkUrl = "https://www.washpayer.com/user/startAction";
    private final static int timeout = 10000;
    private final static String startKey = "ojqSxwAC-NiAEMqO3asVzc0CO6OI-1630153349410-11770";  // 不知道这参数是干啥的，但是不可或缺
    private final static String authCookie = "jwt_auth_domain=MyUser;"+"MyUser_session_id=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2MzAxNTc4ODMsInVzZXJfaWQiOiI2MGFkYzI1MDZmMjkyNTRiNjRiZDQ2MDYiLCJleHAiOjE2MzI3Nzg2ODN9.5l8UTY4Wsi1JeFnSrNSfQ-HPN_vUUuxJJ9fbjy7yF3k";
    private final JSONObject postBody;

    {
        postBody = (JSONObject) JSONUtil.parse("{\n" +
                "  \"devNo\": \"\",\n" +
                "  \"packageId\": \"2\",\n" +
                "  \"attachParas\": {\n" +
                "    \"startKey\": \"" + startKey + "\",\n" +
                "  }\n" +
                "}\n");
    }

    public String getNoFromQrLink(String qrLink){
        String devNo = redisUtil.hashGet("washpayer_link_to_devno",qrLink);
        if (devNo != null && !devNo.equals("")) {
            return devNo;
        }
        HttpResponse response = HttpRequest.get(qrLink)
                .cookie(authCookie)
                .header("user-agent","Mozilla/5.0 (Linux; Android 10; MI 8 Build/QKQ1.190828.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045713 Mobile Safari/537.36 MMWEBID/8395 MicroMessenger/8.0.10.1960(0x28000A31) Process/tools WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64")
                .execute();
        devNo = response.getCookie("user_dev_no").getValue();
        System.out.println("1");

        redisUtil.hashSet("washpayer_link_to_devno",qrLink,devNo);

        return devNo;
    }

    @Override
    public QueryResult checkByQrLink(String qrLink) {
        String no = getNoFromQrLink(qrLink);
        QueryResult qs = new QueryResult();
        synchronized (postBody){
            postBody.set("devNo",no);
            try {
                qs.setName(no);
                HttpResponse response = null;
                try {
                    response = getResponse(qrLink);
                }catch (HttpException e){
                    // 这个服务商比较骚，设备有人用就会长时间阻塞请求（差不多50s），所以5s超时后直接判定为有人用就行了
                    qs.setStatus(MStatus.USING);
                    return qs;
                }catch (Exception e){
                    // TODO log一下
                    return qs;
                }
                switch (response.getStatus()){
                    case 401:
                        qs.setStatus(MStatus.UNKNOWN);
                        qs.setMessage("认证过期，请联系管理员！");
                        break;
                    case 200:
                        JSONObject result = JSONUtil.parseObj(response.body());
                        qs.setStatus((result.get("result")).equals(999)? MStatus.AVAILABLE:MStatus.USING);
                        break;
                }
            }catch (Exception e){
                // 写入日志
            }finally {
                return qs;
            }
        }
    }

    @Override
    public HttpResponse getResponse(String qrLink) throws HttpException {

        return HttpRequest.post(checkUrl)
                .cookie(authCookie)
                .timeout(timeout)
                .body(postBody.toString())
                .execute();
    }
}
