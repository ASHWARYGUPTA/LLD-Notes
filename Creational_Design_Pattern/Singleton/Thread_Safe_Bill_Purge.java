
class JudgeAnalyticsLazy {

    private JudgeAnalyticsLazy() {

    }

    private static class Holder {

        private static final JudgeAnalyticsLazy j1 = new JudgeAnalyticsLazy();
    }

    public static JudgeAnalyticsLazy getInstance() {
        return Holder.j1;
    }
}

public class Thread_Safe_Bill_Purge {

}
