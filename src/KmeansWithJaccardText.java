import java.util.List;

public class KmeansWithJaccardText extends KMeansTest {

	public static void main(String[] args) {
		PreprocessingData p = new PreprocessingData();
		List<SampleData> dataset = p.buildDatasetWithTF();
		KmeansWithJaccard km = new KmeansWithJaccard(4, dataset);
		km.run();
	}
}
