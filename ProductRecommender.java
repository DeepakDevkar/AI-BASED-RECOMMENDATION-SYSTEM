import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class ProductRecommender {
    public static void main(String[] args) {
        try {
            // Step 1: Load user-item preference data
            File dataFile = new File("data/preferences.csv"); // userId, itemId, preference
            DataModel model = new FileDataModel(dataFile);

            // Step 2: Define similarity metric
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Step 3: Define neighborhood
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Step 4: Build the recommender
            GenericUserBasedRecommender recommender =
                    new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Step 5: Recommend items for a user (e.g., user 1)
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);

            System.out.println("Recommended products for user 1:");
            for (RecommendedItem recommendation : recommendations) {
                System.out.printf("Item: %d, Score: %.2f%n", recommendation.getItemID(), recommendation.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
