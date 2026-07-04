/*
 * Memento solution:
 * This version works because the editor itself owns the logic for capturing and
 * restoring its state. The history object only stores snapshots and asks the
 * editor to save/restore; it does not reach inside the editor's fields.
 *
 * Why this works:
 * 1. Encapsulation is preserved because only ResumeEditor knows its internals.
 * 2. Undo history is centralized inside ResumeHistory instead of being mixed
 *    into editor business logic.
 * 3. Snapshots are immutable, so past states stay reliable after later edits.
 * 4. Adding more history operations is easier because the caretaker stores
 *    mementos without understanding resume details.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Memento {

    // Memento is intentionally immutable so old states cannot be tampered with
    // after they are saved in history.
    private final String name;
    private final String education;
    private final String experience;
    private final List<String> skills;

    public Memento(String name, String education, String experience, List<String> skills) {
        this.name = name;
        this.education = education;
        this.experience = experience;
        this.skills = new ArrayList<>(skills);
    }

    public String getName() {
        return name;
    }

    public String getEducation() {
        return education;
    }

    public String getExperience() {
        return experience;
    }

    public List<String> getSkills() {
        // Returning a copy protects the stored snapshot from accidental mutation.
        return new ArrayList<>(skills);
    }
}

class ResumeEditor {

    private String name;
    private String education;
    private String experience;
    private List<String> skills = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setSkills(List<String> skills) {
        // Editor copies caller input so outside list changes do not silently
        // modify the editor's current state.
        this.skills = new ArrayList<>(skills);
    }

    public void printResume() {
        System.out.println("Name       : " + name);
        System.out.println("Education  : " + education);
        System.out.println("Experience : " + experience);
        System.out.println("Skills     : " + skills);
        System.out.println();
    }

    public Memento save() {
        // The originator creates its own snapshot. This is the key reason the
        // pattern preserves encapsulation.
        return new Memento(name, education, experience, skills);
    }

    public void restore(Memento m) {
        // Restore also happens inside the originator, so external classes do not
        // need direct field access to roll back state.
        this.name = m.getName();
        this.education = m.getEducation();
        this.experience = m.getExperience();
        this.skills = m.getSkills();
    }
}

class ResumeHistory {

    private final Stack<Memento> history = new Stack<>();

    public void save(ResumeEditor editor) {
        // Caretaker stores snapshots, but it does not know what fields exist in
        // ResumeEditor. That loose knowledge is why the design scales better.
        history.push(editor.save());
    }

    public void undo(ResumeEditor editor) {
        if (history.isEmpty()) {
            System.out.println("No saved state available.");
            return;
        }

        // Undo restores the most recently saved stable snapshot.
        // This is why unsaved edits can be discarded safely without exposing
        // ResumeEditor internals to the history object.
        editor.restore(history.pop());
    }
}

public class Main {

    public static void main(String[] args) {
        ResumeEditor editor = new ResumeEditor();
        ResumeHistory history = new ResumeHistory();

        editor.setName("Ash");
        editor.setEducation("B.Tech CSE");
        editor.setExperience("Fresher");
        editor.setSkills(List.of("Java", "OOP"));
        history.save(editor);

        System.out.println("Initial Resume");
        editor.printResume();

        editor.setExperience("1 year at Startup");
        editor.setSkills(List.of("Java", "OOP", "System Design"));
        history.save(editor);

        System.out.println("After First Update");
        editor.printResume();

        editor.setEducation("B.Tech CSE, Advanced Java Certification");
        editor.setSkills(List.of("Java", "OOP", "System Design", "Spring Boot"));

        System.out.println("Current Unsaved Changes");
        editor.printResume();

        // Undo restores the latest saved version instead of trusting the editor's
        // current mutable fields, which is exactly why Memento is useful.
        history.undo(editor);
        System.out.println("After Undo");
        editor.printResume();
    }
}
