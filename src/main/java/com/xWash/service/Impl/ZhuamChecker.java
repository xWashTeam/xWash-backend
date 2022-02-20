package com.xWash.service.Impl;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.xWash.model.dao.Machine;
import com.xWash.model.entity.MStatus;
import com.xWash.model.entity.MessageEnum;
import com.xWash.model.entity.QueryResult;
import com.xWash.service.intf.IChecker;
import org.springframework.stereotype.Service;

@Service("zhuamChecker")
public class ZhuamChecker implements IChecker {
    private static final int timeout = 5000;
    private static final int ZHUAM_AVAILABLE = 1;
    private static final int ZHUAM_USING = 0;
    private static final String url = "http://zhua.myclassphp.com/index.php?m=Home&c=User&a=getIndexData";
    private final JSON postBody;

    {
        postBody = (JSON) JSON.parse("{\"uid\":\"831342\", \"merid\":\"251181\"}");
    }

    @Override
    public QueryResult check(Machine machine) {
        String link = machine.getLink();
        QueryResult qs = new QueryResult();
        HttpResponse res = null;
        JSON jo = null;
        try {
            res = getResponse(link);
            jo = (JSON) JSON.parse(res.body());
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
                if (status.equals(ZHUAM_AVAILABLE)) {
                    qs.setStatus(MStatus.AVAILABLE);
                } else if (status == ZHUAM_USING) {
                    qs.setStatus(MStatus.USING);
                    qs.setMessage(MessageEnum.MESSAGE_USING);
                }else{
                    qs.setStatus(MStatus.UNKNOWN);
                }
                break;
            case 401:
                qs.setStatus(MStatus.UNKNOWN);
                qs.setMessage(MessageEnum.MESSAGE_UNAUTH);
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
