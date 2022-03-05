package utils;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import org.junit.Test;

import java.net.SocketTimeoutException;

public class HutoolTest {
    @Test
    public void testParseJsonException() {
    }

    @Test
    public void testHttpTimeout() {
        try {
            HttpResponse response = HttpRequest.get("google.com").timeout(2).execute();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
