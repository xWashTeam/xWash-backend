package Checker;

import com.xWash.model.dao.Machine;
import com.xWash.service.Impl.ZhuamChecker;
import org.junit.Test;

public class ZhuamCheckerTest {

    @Test
    public void testCheck() {
        Machine machine = new Machine();
        machine.setLink("http://zhuam.myclassphp.com?machineCode=251181");
        machine.setMachineId("2511821");
        System.out.println(new ZhuamChecker().check(machine));
    }
}
