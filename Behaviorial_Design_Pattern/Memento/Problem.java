
/*
 * Problem version:
 * This approach tries to support "save" and "restore" for a resume editor, but
 * it does so by letting another class know the editor's internal fields directly.
 *
 * Why this is a problem:
 * 1. Encapsulation is broken because snapshot code depends on the editor's raw state.
 * 2. If the editor structure changes, snapshot/restore logic must also change.
 * 3. Undo history becomes hard to manage cleanly when many states must be stored.
 * 4. The editor exposes too much mutable data just so other classes can restore it.
 *
 * Why Memento is required:
 * Memento lets the editor create a safe snapshot of its own state and restore it
 * later without exposing internal details to the outside world. That keeps undo/
 * rollback support separate from the editor's business logic while preserving
 * encapsulation.
 */
class ResumeEditor {

    // These fields are public here, which makes saving/restoring easy for the
    // demo but also shows the design smell: outside code can freely depend on
    // and mutate editor internals.
    public String name;
    public String experience;
    public String education;
}

class ResumeSnapshot {

    // The snapshot now knows the exact shape of ResumeEditor state.
    // That tight coupling is the reason this "problem" version does not scale well.
    private String name;
    public String experience;
    public String education;

    public ResumeSnapshot(String name, String experience, String education) {
        this.name = name;
        this.experience = experience;
        this.education = education;
    }

    public void restore(ResumeEditor editor) {
        // Restore works by directly rewriting the editor's internal fields.
        // In a proper Memento design, the editor itself should control how its
        // state is captured and restored instead of exposing internals like this.
        editor.name = name;
        editor.experience = experience;
        editor.education = education;
    }
}

// The file is intentionally minimal because the teaching focus is the design
// problem: state history is being implemented in a way that breaks encapsulation.
public class Problem {

}
