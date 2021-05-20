package com.xWash.checker;

import com.xWash.entity.QueryResult;
import com.xWash.service.Impl.UCleanAPPChecker;
import org.junit.Test;

public class UCleanAPPCheckerTest {
    UCleanAPPChecker uCleanAPPChecker = new UCleanAPPChecker();

    @Test
    public void testAPP(){
        QueryResult qr = uCleanAPPChecker.checkByQrLink("http://app.littleswan.com/u_download.html?type=Ujing& uuid=0000000000000A0007555202102270169223");
        System.out.println(qr.toJson());
    }
}
