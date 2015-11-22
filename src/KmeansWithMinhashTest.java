import java.util.List;

public class KmeansWithMinhashTest extends KMeansTest {

	public static void main(String[] args) {
		PreprocessingData p = new PreprocessingData();
		List<SampleData> datasetWithTF = p.buildDatasetWithTE();
		System.out.println(datasetWithTF);
		KmeansWithMinhash kmm = new KmeansWithMinhash(4, datasetWithTF);
		kmm.run();
	}

}
