package com.xWash.service.Impl;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xWash.service.IChecker;
import com.xWash.entity.QueryResult;
import com.xWash.entity.MStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.*;
import org.springframework.stereotype.Service;

@Service("sodaChecker")
public class SodaChecker implements IChecker{

    public QueryResult checkByQrLink(String qrLink) {
        return checkByUrl(qrLink);
    }


    private QueryResult checkByUrl(String url) {
        String response = getResponse(url).body();
        return dealWithResponse(response);
    }

    private QueryResult dealWithResponse(String response){
        // 对返回的字符串进行处理
        QueryResult queryResult = new QueryResult();
        JSONObject jo = JSONUtil.parseObj(response);
        JSONObject joo = (JSONObject)jo.get("data");
        queryResult.setName((String) joo.get("serial"));
//        queryResult.setRemainTime((Integer) jo.get("remainTime"));
        String s = (String) joo.get("status");
        if(s.equals("AVAILABLE"))
            queryResult.setStatus(MStatus.AVAILABLE);
        else if(s.equals("USING"))
            queryResult.setStatus(MStatus.USING);
        else
            queryResult.setStatus(MStatus.UNKNOWN);
        queryResult.setMessage((String) jo.get("message"));
        return queryResult;
    }

    public HttpResponse getResponse(String url){
        return HttpRequest.get(url).execute();  // Hutool lib
    }

    public static void main(String[] args) {
        JSONObject jo = JSONUtil.parseObj("{\"status\":\"OK\",\"data\":{\"feature\":\"LAUNDRY\",\"hotline\":\"13570055540\",\"id\":37377,\"merchant\":{\"companyName\":\"\"},\"modes\":[{\"preset\":\"LAUNDRY_DRY\",\"name\":\"单脱\",\"value\":1,\"count\":1,\"unit\":\"次\",\"description\":\"\",\"originalValue\":1},{\"preset\":\"LAUNDRY_EXPRESS\",\"name\":\"快洗\",\"value\":2,\"count\":1,\"unit\":\"次\",\"description\":\"\",\"originalValue\":2},{\"preset\":\"LAUNDRY_STANDARD\",\"name\":\"标准洗\",\"value\":3,\"count\":1,\"unit\":\"次\",\"description\":\"\",\"originalValue\":3},{\"preset\":\"LAUNDRY_HEAVY\",\"name\":\"大物洗\",\"value\":4,\"count\":1,\"unit\":\"次\",\"description\":\"\",\"originalValue\":4}],\"online\":false,\"protocol\":\"TOKEN_V1\",\"serial\":\"MNBGDH0867\",\"status\":\"AVAILABLE\"},\"message\":\"\",\"code\":\"12080000\"}");
//        System.out.println(jo.toStringPretty());
        JSONObject joo = (JSONObject) (jo.get("data"));
//        System.out.println(joo.get("status"));
//        System.out.println(new SodaChecker().checkByQrLink("https://api.sodalife.xyz/v1/devices/MNBGDH0867"));
    }

}
