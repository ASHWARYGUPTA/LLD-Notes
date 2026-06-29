
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
        RecentRecommendation r1 = new RecentRecommendation();

        r1.recommend();

        TrendingRecommendation t1 = new TrendingRecommendation();

        t1.recommend();

        GenreRecommendation g1 = new GenreRecommendation();

        // g1.recommend (); -> Naturally would do this -> But due to no restriction you implemented genreRecommend
        g1.genreRecommed();
    }
}
