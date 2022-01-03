package com.xWash.service.Impl;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.model.entity.MStatus;
import com.xWash.model.entity.QueryResult;
import com.xWash.service.intf.IChecker;
import org.springframework.stereotype.Service;

@Service("zhuamChecker")
public class ZhuamChecker implements IChecker {
    private final static int timeout = 5000;
    private final static String url = "http://zhua.myclassphp.com/index.php?m=Home&c=User&a=getIndexData";
    private final JSONObject postBody;

    {
        postBody = JSONUtil.parseObj("{\"uid\":\"831342\", \"merid\":\"251181\"}");
    }

    @Override
    public QueryResult checkByQrLink(String qrLink) {

        QueryResult qs = new QueryResult();
        HttpResponse res = null;
        JSONObject jo = null;
        try {
            res = getResponse(qrLink);
            jo = JSONUtil.parseObj(res.body());
        } catch (HttpException e) {
            // TODO log
            return qs;
        } catch (Exception e) {
            // TODO log
            return qs;
        }

        switch (res.getStatus()) {
            case 200:
                // 通过status设置
                Integer status = (Integer) jo.get("status");
                if (status.equals(1)) {
                    qs.setStatus(MStatus.AVAILABLE);
                } else if (status == 0) {
                    qs.setStatus(MStatus.USING);
                    qs.setMessage("已被使用");
                }else{
                    qs.setStatus(MStatus.UNKNOWN);
                }
                break;
            case 401:
                qs.setStatus(MStatus.UNKNOWN);
                qs.setMessage("权限过期，请联系管理员");
                break;
        }
        return qs;
    }

    @Override
    public HttpResponse getResponse(String qrLink) {
        String body, merid = qrLink.substring(40);  // api不变，这样最快
        synchronized (postBody) {
            postBody.set("merid", merid);
            body = postBody.toString();
        }
        return HttpRequest.post(url)
                .body(body)
                .timeout(timeout)
                .execute();
    }
}
