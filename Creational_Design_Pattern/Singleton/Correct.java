
//Thread Safe
//Eager Loading
class JudgeAnalytics {

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
        if (j1 == null) {
            j1 = new JudgeAnalyticsLazy();
            return j1;
        }
        return j1;
    }
}

public class Correct {

    public static void main(String[] args) {
        JudgeAnalytics j1 = JudgeAnalytics.jd;
        JudgeAnalytics j2 = JudgeAnalytics.jd;
        System.out.println(j1);//JudgeAnalytics@5e57643e
        System.err.println(j2);//JudgeAnalytics@5e57643e
    }
}
