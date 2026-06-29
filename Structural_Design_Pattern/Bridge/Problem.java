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

}

class NewDevice implements PlayQuality {

}
//Player and PlayQulaity very tightly coupled

public class Problem {

}
