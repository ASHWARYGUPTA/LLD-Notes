/*
 * Teaching note:
 * This anti-pattern creates multiple analytics objects even though the data is
 * meant to be global. It fails to scale because each caller can update a
 * different copy, so the application's shared counts become unreliable.
 */

// This anti-pattern creates a fresh analytics object wherever it is needed.
// That works for a toy demo, but it fails to scale because shared counters stop
// being truly shared and different callers can drift out of sync.

class Judge {

    // Each instance owns its own counters, so the system has no single source of truth.
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
        // Separate allocations mean each reference tracks a different copy of the data.
        Judge j1 = new Judge();
        Judge j2 = new Judge();
        Judge j3 = new Judge();

        j1.getRunCnt();
        j2.getRunCnt();
        j3.getRunCnt();
        //j1 , j2 , j3 all different

    }
}
