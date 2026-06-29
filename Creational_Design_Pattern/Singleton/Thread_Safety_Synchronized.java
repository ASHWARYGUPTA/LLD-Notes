
class JudgeAnalyticsLazy {

    private static JudgeAnalyticsLazy j1;

    private JudgeAnalyticsLazy() {

    }

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

    public static void main(String[] args) {

    }
}
