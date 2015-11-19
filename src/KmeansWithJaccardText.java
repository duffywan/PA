public class KmeansWithJaccardText extends KMeansTest {

	public static void main(String[] args) {
		KmeansWithJaccardText test = new KmeansWithJaccardText();
		KmeansWithJaccard kMeans = new KmeansWithJaccard(2, test.dataset);
		kMeans.run();

	}

}
