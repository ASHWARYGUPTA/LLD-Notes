
import java.util.LinkedList;
import java.util.Queue;

// Shared Resource
class OnlineJudge {

    // Maximum submissions the queue can hold
    private static final int BUFFER_SIZE = 5;

    // Submission Queue (Buffer)
    private Queue<Integer> submissionQueue = new LinkedList<>();

    private int submissionId = 1;

    // ---------------- PRODUCER ----------------
    // Students submit solutions
    public synchronized void submitSolution() throws InterruptedException {

        /*
         * If queue is already full,
         * producer cannot add more submissions.
         */
        while (submissionQueue.size() == BUFFER_SIZE) {

            System.out.println(
                    "Queue Full (" + submissionQueue.size()
                    + "). Student waits..."
            );

            // Release lock and wait
            wait();
        }

        int id = submissionId++;

        submissionQueue.add(id);

        System.out.println(
                "Student submitted Solution #" + id
                + " | Queue Size = "
                + submissionQueue.size()
        );

        /*
         * Wake consumer if it was waiting
         * because queue was empty.
         */
        notifyAll();
    }

    // ---------------- CONSUMER ----------------
    // Judge Server evaluates solutions
    public synchronized void judgeSolution() throws InterruptedException {

        /*
         * Queue empty
         * Nothing to judge
         */
        while (submissionQueue.isEmpty()) {

            System.out.println(
                    "No submissions. Judge Server waiting..."
            );

            wait();
        }

        int id = submissionQueue.poll();

        System.out.println(
                "Judge evaluating Solution #" + id
                + " | Queue Size = "
                + submissionQueue.size()
        );

        /*
         * Queue now has space.
         * Wake producers.
         */
        notifyAll();

        /*
         * Compilation + Test Cases
         * Takes longer than submission.
         */
        Thread.sleep(2000);

        System.out.println(
                "Solution #" + id + " Accepted ✅"
        );
    }
}

// ---------------- STUDENT ----------------
class Student extends Thread {

    private OnlineJudge judge;

    Student(OnlineJudge judge, String name) {
        super(name);
        this.judge = judge;
    }

    @Override
    public void run() {

        try {

            for (int i = 1; i <= 10; i++) {

                judge.submitSolution();

                // Students submit very quickly
                Thread.sleep(500);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// ---------------- JUDGE SERVER ----------------
class JudgeServer extends Thread {

    private OnlineJudge judge;

    JudgeServer(OnlineJudge judge) {
        this.judge = judge;
    }

    @Override
    public void run() {

        try {

            while (true) {

                judge.judgeSolution();

                // Judge is slow
                Thread.sleep(2000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// ---------------- DRIVER ----------------
public class Main2 {

    public static void main(String[] args) {

        OnlineJudge judge = new OnlineJudge();

        Student s1 = new Student(judge, "Student-1");
        Student s2 = new Student(judge, "Student-2");
        Student s3 = new Student(judge, "Student-3");

        JudgeServer server = new JudgeServer(judge);

        server.start();

        s1.start();
        s2.start();
        s3.start();
    }
}
