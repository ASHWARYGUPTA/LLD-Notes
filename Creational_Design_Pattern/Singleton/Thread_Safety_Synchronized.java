/*
 * Teaching note:
 * Synchronizing the accessor fixes the race in lazy initialization, so the
 * example works correctly with multiple threads. The trade-off is that every
 * access pays locking cost, which is why later versions optimize this pattern.
 */

// This version fixes the lazy singleton race by serializing access to instance
// creation. It scales functionally because only one object is produced, but the
// coarse lock adds overhead on every call even after initialization is done.

class JudgeAnalyticsLazy {

    private static JudgeAnalyticsLazy j1;

    private JudgeAnalyticsLazy() {

    }

    // Every caller must enter the same critical section, which keeps creation safe.
    //Not that efficent -> After every call it makes sure that all instances are at level sync -> git pull logic
    public static synchronized JudgeAnalyticsLazy getInstance() {
        if (j1 == null) {
            j1 = new JudgeAnalyticsLazy();
            return j1;
        }
        return j1;
    }
}

public class Thread_Safety_Synchronized {

    // No driver code is needed here; the synchronized accessor itself is the comparison point.
    public static void main(String[] args) {

    }
}
