import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import junit.framework.TestCase;
import vmthread.VMThread;

/**
 * Tests for CoopScheduler.
 *
 * @author Tim
 * @version Feb 23, 2008
 */
public class CoopSchedulerTest extends TestCase
{
    /** Test execution. */
    public void testSimpleExec()
    {
        int [] globals = new int[5];
        VMThread t1 = new VMThread(
            new Scanner(
                "i32.const 5\n" +
                "set_global 0\n" +
                "i32.const 3\n" +
                "get_global 0\n" +
                "i32.add\n" +
                "set_global 1\n"), globals);

        List<VMThread> threadList = new ArrayList<VMThread>();
        threadList.add(t1);

        PriorityScheduler scheduler = new PriorityScheduler(threadList);

        assertSame("Hint: getCurrentThread returned wrong object",
            t1, scheduler.getCurrentThread());

        scheduler.run(1);
        assertEquals("Hint: symbol didn't have expected value",
                0, globals[0]);

        scheduler.run(1);
        assertEquals("Hint: symbol didn't have expected value",
            5, globals[0]);

        scheduler.run(3);

        scheduler.run(1);
        assertEquals("Hint: symbol didn't have expected value",
            5, globals[0]);
        assertEquals("Hint: symbol didn't have expected value",
            8, globals[1]);
    }
}
