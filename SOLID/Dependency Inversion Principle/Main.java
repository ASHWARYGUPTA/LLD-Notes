
interface RecommendationStrategy {

    void recommend();
}

class RecentRecommendation implements RecommendationStrategy {

    public void recommend() {
        System.err.println("Recent");
        return;
    }
}

class TrendingRecommendation implements RecommendationStrategy {

    public void recommend() {
        System.err.println("Trending");

        return;
    }
}

class GenreRecommendation implements RecommendationStrategy {// Low Level

    public void recommend() {
        System.err.println("Genre");

        return;
    }
}

class RecommendationAlgorithm {

    private RecommendationStrategy recommendationStrategy;

    public RecommendationAlgorithm(RecommendationStrategy recommendationStrategy) {
        this.recommendationStrategy = recommendationStrategy;
    }

    public void recommend() {
        recommendationStrategy.recommend();
    }

}

public class Main { // High Level

    public static void main(String[] args) {
        RecommendationAlgorithm r1 = new RecommendationAlgorithm(new TrendingRecommendation());
        r1.recommend();

    }
}
