/*
 * Teaching note:
 * The Bill Pugh holder style keeps lazy initialization and thread safety at the
 * same time. It scales well because the JVM handles one-time creation only when
 * the instance is first needed, without locking every future access.
 */

// This example uses the Bill Pugh holder idiom to keep lazy initialization and
// thread safety without synchronizing every access. It scales well because the
// JVM creates the inner holder only when the instance is first requested.

class JudgeAnalyticsLazy {

    private JudgeAnalyticsLazy() {

    }

    // The nested holder delays allocation until getInstance() is actually used.
    private static class Holder {

        private static final JudgeAnalyticsLazy j1 = new JudgeAnalyticsLazy();
    }

    public static JudgeAnalyticsLazy getInstance() {
        // Clients only see a simple accessor while the holder manages one-time creation.
        return Holder.j1;
    }
}

// The wrapper class stays empty because the holder-based accessor is the feature being compared.
public class Thread_Safe_Bill_Purge {

}
