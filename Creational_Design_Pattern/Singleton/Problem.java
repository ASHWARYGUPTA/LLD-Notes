
class Judge {

    public int RunCount = 0;
    public int SubmitCount = 0;

    public void Run() {
        this.RunCount++;
        return;
    }

    public void Submit() {
        this.SubmitCount++;
        return;
    }

    public int getRunCnt() {
        return this.RunCount;
    }

    public int getSubmitCnt() {
        return this.SubmitCount;
    }
}

public class Problem {

    public static void main(String[] args) {
        Judge j1 = new Judge();
        Judge j2 = new Judge();
        Judge j3 = new Judge();

        j1.getRunCnt();
        j2.getRunCnt();
        j3.getRunCnt();
        //j1 , j2 , j3 all different

    }
}
