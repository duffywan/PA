public class KmeansWithMinhashTest extends KMeansTest {

	public static void main(String[] args) {
		KmeansWithMinhashTest test = new KmeansWithMinhashTest();
		KmeansWithMinhash kMeans = new KmeansWithMinhash(2, test.dataset);
		kMeans.run();
	}

}
