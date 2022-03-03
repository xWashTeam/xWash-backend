package com.xWash.service.Impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xWash.model.dao.Machine;
import com.xWash.model.entity.MStatus;
import com.xWash.model.entity.QueryResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("zhuamChecker")
public class ZhuamChecker extends AbstractChecker {
    private static final int timeout = 5000;
    private static final int ZHUAM_AVAILABLE = 1;
    private static final int ZHUAM_USING = 0;
    private static final String url = "http://zhua.myclassphp.com/index.php?m=Home&c=User&a=getIndexData";
    private static final Map<String, Object> params = new HashMap<>();

    {
        params.put("uid", "831342");
    }

    @Override
    protected HttpResponse request(Machine machine) throws IOException {
        params.put("merid", machine.getMachineId());
        return HttpRequest.post(url)
                .form(params)
                .timeout(timeout)
                .execute();
    }

    @Override
    protected QueryResult extract(HttpResponse response) throws IOException {
        String res = response.body();
        QueryResult qs = new QueryResult();
        JSONObject json = JSONUtil.parseObj(res);
        int status = json.getInt("status");
        switch (status) {
            case ZHUAM_AVAILABLE:
                qs.setStatus(MStatus.AVAILABLE);
                break;
            case ZHUAM_USING:
                qs.setStatus(MStatus.USING);
                break;
            default:
                qs.setStatus(MStatus.UNKNOWN);
        }
        return qs;
    }

    @Override
    public QueryResult check(Machine machine) {
        HttpResponse response = null;
        try {
            response = request(machine);
            // TODO aspect log if code != 200
            return extract(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
