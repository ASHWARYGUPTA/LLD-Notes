/*
 * Teaching note:
 * Singleton works here because analytics state is meant to be shared across the
 * whole application. A single access point keeps counters consistent instead of
 * scattering duplicate state across many independently created objects.
 */

// This file contrasts singleton styles that keep one shared analytics object for
// the whole application. That scales better than the problem version because all
// callers read and update the same state instead of fragmenting it across copies.

//Thread Safe
//Eager Loading
class JudgeAnalytics {

    // Eager creation trades a little startup work for a guaranteed single instance.
    public static final JudgeAnalytics jd = new JudgeAnalytics();

    private JudgeAnalytics() {

    }

    public static JudgeAnalytics getInstance() {
        return jd;
    }
}

//Lazy Loading
//Not Thread Safe
//Due to Execution time creation
class JudgeAnalyticsLazy {

    private static JudgeAnalyticsLazy j1;

    private JudgeAnalyticsLazy() {

    }

    public static JudgeAnalyticsLazy getInstance() {
        // Lazy creation avoids upfront cost, but without locking two threads can race here.
        if (j1 == null) {
            j1 = new JudgeAnalyticsLazy();
            return j1;
        }
        return j1;
    }
}

public class Correct {

    public static void main(String[] args) {
        // Both references point to the same object, so metrics stay centralized.
        JudgeAnalytics j1 = JudgeAnalytics.jd;
        JudgeAnalytics j2 = JudgeAnalytics.jd;
        System.out.println(j1);//JudgeAnalytics@5e57643e
        System.err.println(j2);//JudgeAnalytics@5e57643e
    }
}
