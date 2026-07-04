/*
 * Problem version:
 * This design hardcodes one class for each platform-quality combination.
 * As soon as a new platform or a new quality mode appears, the number of classes
 * multiplies, which is why Bridge is used to split those two dimensions apart.
 *
 * Caveat:
 * This example is intentionally incomplete and non-compiling. The empty classes
 * below are useful as a teaching signal because they show how fast the class
 * hierarchy becomes unmanageable.
 */
//Video Playing Platform

interface PlayQuality {

    void play(String title);
}

class WebHDPlayer implements PlayQuality {

    public void play(String title) {
        System.out.println("Playing in Web in HD " + title);
    }
}

class MobileHDPlayer implements PlayQuality {

    public void play(String title) {
        System.out.println("Playing in Mobile in HD " + title);
    }
}

class SmartTVUltraHDPlayer implements PlayQuality {

    public void play(String title) {
        System.out.println("Smart TV Playing " + title + " in UHD");
    }
}

class Web4kPlayer implements PlayQuality {

    // Adding one more quality/platform pair would require yet another subclass.
    public void play(String title) {
        System.out.println("Playing in Web in 4K " + title);
    }
}

class NewDevice implements PlayQuality {

    // A brand new device would also need separate classes for SD, HD, UltraHD, etc.
    public void play(String title) {
        System.out.println("Playing on New Device " + title);
    }
}
//Player and PlayQulaity very tightly coupled

public class Problem {

}
