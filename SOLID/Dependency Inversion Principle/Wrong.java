/*
 * Teaching note:
 * This file violates Dependency Inversion because the high-level flow talks
 * directly to concrete recommendation classes. That couples orchestration to
 * implementation details and makes extension awkward. A strategy interface
 * removes that rigidity, with the tradeoff of adding an abstraction and explicit injection.
 */

class RecentRecommendation {

    public void recommend() {
        System.err.println("Recent");
        return;
    }
}

class TrendingRecommendation {

    public void recommend() {
        System.err.println("Trending");

        return;
    }
}

class GenreRecommendation {// Low Level

    public void genreRecommed() {
        System.err.println("Genre");

        return;
    }
}

public class Wrong { // High Level

    public static void main(String[] args) {
        // The high-level code is forced to know every concrete recommender.
        // Adding or replacing algorithms means editing this method directly.
        RecentRecommendation r1 = new RecentRecommendation();

        r1.recommend();

        TrendingRecommendation t1 = new TrendingRecommendation();

        t1.recommend();

        GenreRecommendation g1 = new GenreRecommendation();

        // This file also shows how direct concrete coupling makes API shape
        // inconsistencies easier to leak into callers.
        // g1.recommend (); -> Naturally would do this -> But due to no restriction you implemented genreRecommend
        g1.genreRecommed();
    }
}
