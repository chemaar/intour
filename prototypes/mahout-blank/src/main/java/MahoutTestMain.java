import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MahoutTestMain {
	
	public static void main(String []args) throws IOException, TasteException{
		//1-Load the file with recommendations
		//First version a file
		//Second version from memory
		DataModel model = new FileDataModel(new File("src/main/resources/recommendations.txt"));
		//2-Type of function to compare items
		UserSimilarity similarity = createSimilarityFunction(model);
		//Only recommend those beyond 0.1
		UserNeighborhood neighborhood = 
				createNeighborhoodFunction(model, similarity);
		//3-Create the recommender
		UserBasedRecommender recommender = 
				createRecommender(model, similarity, neighborhood);
		Recommender cachingRecommender = new CachingRecommender(recommender);
		//4-Make 3 recommendations for user 2
		List<RecommendedItem> recommendations = cachingRecommender.recommend(2, 3);
		for (RecommendedItem recommendation : recommendations) {
		  System.out.println(recommendation);
		}
		//Evaluate the quality of the recommender: ONLY FOR TESTING
		//It is possible to change the previous functions for creating the parameters		
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
		      @Override
		      public Recommender buildRecommender(DataModel model) throws TasteException {
		        UserSimilarity similarity = createSimilarityFunction(model);
		        UserNeighborhood neighborhood =
		          new NearestNUserNeighborhood(100, similarity, model);
		        return createRecommender(model, similarity, neighborhood);
		      }
		    };
		double result = evaluator.evaluate(recommenderBuilder, null, model, 0.9, 1.0);
		System.out.println(result);
	}


	public static UserNeighborhood createNeighborhoodFunction(DataModel model, UserSimilarity similarity) {
		//See the different implementations: http://archive-primary.cloudera.com/cdh4/cdh/4/mahout-0.7-cdh4.3.2/mahout-core/org/apache/mahout/cf/taste/neighborhood/UserNeighborhood.html
		//CachingUserNeighborhood, NearestNUserNeighborhood, ThresholdUserNeighborhood
		//return new NearestNUserNeighborhood(3, similarity, model)
		return new ThresholdUserNeighborhood(0.1, similarity, model);
	}

	public static GenericUserBasedRecommender createRecommender(DataModel model, UserSimilarity similarity,
			UserNeighborhood neighborhood) {
		return new GenericUserBasedRecommender(model, neighborhood, similarity);
	}

	public static UserSimilarity createSimilarityFunction(DataModel model) throws TasteException {
		//See the different implementations: https://archive.cloudera.com/cdh4/cdh/4/mahout-0.7-cdh4.5.0/mahout-core/org/apache/mahout/cf/taste/similarity/UserSimilarity.html
		//CachingUserSimilarity, CityBlockSimilarity, EuclideanDistanceSimilarity, GenericUserSimilarity, LogLikelihoodSimilarity, PearsonCorrelationSimilarity, SpearmanCorrelationSimilarity, TanimotoCoefficientSimilarity, UncenteredCosineSimilarity
		return new PearsonCorrelationSimilarity(model);
	}

}
