package Checker;

import com.xWash.model.dao.Machine;
import com.xWash.service.Impl.UCleanChecker;
import org.junit.Test;

public class UCleanCheckerTest {
    @Test
    public void testCheck() {
        Machine machine = new Machine();
        machine.setLink("https://q.ujing.com.cn/ucqrc/index.html?cd=0000000000000A0007555202104170176799");
        System.out.println(new UCleanChecker().check(machine));
    }
}
