/*
 * Teaching note:
 * This file duplicates the same notification workflow in multiple classes.
 * That works for two channels, but common steps like logging or analytics can
 * drift out of sync as more classes appear. Template Method solves that by
 * keeping the invariant algorithm in one base class, with the tradeoff that
 * subclasses accept a fixed overall sequence.
 */

class EmailNotification {

    public void send(String to, String message) {
        // Every notification type repeats the same pipeline manually, which is
        // why this design invites copy-paste bugs and inconsistent updates.
        //Step1 : Rate Limiting
        System.out.println("Rating limiting done for " + to);
        //Step2 : Validating Email Recipeint
        System.out.println("Validated Reciptent ");
        String formatted = message.trim();
        //Step3 : Logging
        System.out.println("Logging before send " + formatted + " to " + to);
        //Step4 : Compose email
        String comoposeEmail = "<html><p> " + formatted + "</p></html>";
        //Step5 : Send email
        System.out.println("Sending email to " + to);
        //Step6 : Analytics
        System.out.println("Analytics updated for: " + to);
    }
}

class SMSNotification {

    public void send(String to, String message) {
        // This method mirrors EmailNotification almost step for step.
        // The repetition is the signal that a template method can extract the common algorithm.
        //Step1 : Rate Limiting
        System.out.println("Rating limiting done for " + to);
        //Step2 : Validating phone Recipeint
        System.out.println("Validated phone ");
        String formatted = message.trim();
        //Step3 : Logging
        System.out.println("Logging before send " + formatted + " to " + to);
        //Step4 : Compose email
        String comoposeEmail = "[ " + formatted + "]";
        //Step5 : Send email
        System.out.println("Sending email to " + to);
        //Step6 : Analytics
        System.out.println("Analytics updated for: " + to);
    }
}

public class Problem {

}
