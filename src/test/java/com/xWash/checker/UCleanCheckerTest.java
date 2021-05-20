package com.xWash.checker;

import com.xWash.service.Impl.UCleanChecker;
import org.junit.Test;

public class UCleanCheckerTest {
    @Test
    public void test(){
        UCleanChecker checker = new UCleanChecker();
        System.out.println(checker.checkByQrLink("http://weixin.qq.com/r/Ej8rM5LE1M-rrdZQ92oAl?uuid=0000000000000A0007555201901200085449"));
    }
}
