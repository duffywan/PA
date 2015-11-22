import java.util.List;

public class FlockingTest {
	public static void main(String[] args) {
		PreprocessingData preprocessingData = new PreprocessingData();
		List<SampleData> dataset = preprocessingData.buildDataset();
		Flocking flocking = new Flocking(dataset);
		dataset = flocking.run();
		System.out.println(dataset);
		KmeansWithJaccard kmj = new KmeansWithJaccard(4, dataset);
		kmj.run();
	}
}
