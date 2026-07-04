/*
 * Teaching note:
 * Double-checked locking keeps the singleton lazy while reducing contention
 * after initialization. It scales better than synchronizing every call because
 * most reads avoid the heavy path once the shared instance already exists.
 */

// This version keeps the lazy singleton but reduces the locking cost after the
// object has already been created. It scales better than a fully synchronized
// method because most calls only read the cached instance and skip the lock.

//Double Locking
class JudgeAnalyticsLazy {

    // volatile is part of the teaching point here: readers must see a fully built object.
    private static volatile JudgeAnalyticsLazy j1;

    private JudgeAnalyticsLazy() {

    }

    public static synchronized JudgeAnalyticsLazy getInstance() {
        // The outer check avoids locking work once initialization has completed.
        if (j1 == null) {
            synchronized (JudgeAnalyticsLazy.class) { //-> Making sure that it is not initialized in any other thread
                // The inner check prevents duplicate creation when two threads arrive together.
                if (j1 == null) {
                    j1 = new JudgeAnalyticsLazy();
                }
            }
        }
        return j1;
    }
}

// The outer class remains minimal because the concurrency trade-off is shown in getInstance().
public class Thread_Safety_Double_Locking {

}
