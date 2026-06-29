
//Double Locking
class JudgeAnalyticsLazy {

    private static volatile JudgeAnalyticsLazy j1;

    private JudgeAnalyticsLazy() {

    }

    public static synchronized JudgeAnalyticsLazy getInstance() {
        if (j1 == null) {
            synchronized (JudgeAnalyticsLazy.class) { //-> Making sure that it is not initialized in any other thread
                if (j1 == null) {
                    j1 = new JudgeAnalyticsLazy();
                }
            }
        }
        return j1;
    }
}

public class Thread_Safety_Double_Locking {

}
