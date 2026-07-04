/*
 * Teaching note:
 * This file follows Dependency Inversion by making the high-level recommendation
 * flow depend on the `RecommendationStrategy` abstraction instead of concrete
 * recommendation classes. That makes algorithms replaceable without rewriting
 * orchestration code. The tradeoff is that object wiring must happen explicitly.
 */

interface RecommendationStrategy {

    // High-level code depends on this small contract, which prevents the
    // recommendation engine from being locked to one concrete algorithm.
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

    // The high-level module stores an abstraction, not a concrete implementation.
    private RecommendationStrategy recommendationStrategy;

    public RecommendationAlgorithm(RecommendationStrategy recommendationStrategy) {
        this.recommendationStrategy = recommendationStrategy;
    }

    public void recommend() {
        // Delegation keeps policy selection separate from execution details.
        recommendationStrategy.recommend();
    }

}

public class Main { // High Level

    public static void main(String[] args) {
        RecommendationAlgorithm r1 = new RecommendationAlgorithm(new TrendingRecommendation());
        r1.recommend();

    }
}
