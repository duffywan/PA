import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinHashTest {
	public static void main(String[] args) {
		List<List<Double>> dataset = new ArrayList<>();
		dataset.add(Arrays.asList(1d, 0d, 0d, 1d));
		dataset.add(Arrays.asList(0d, 0d, 1d, 0d));
		dataset.add(Arrays.asList(0d, 1d, 0d, 1d));
		dataset.add(Arrays.asList(1d, 0d, 1d, 1d));
		dataset.add(Arrays.asList(0d, 0d, 1d, 0d));
		List<List<Double>> dataset1 = new ArrayList<>();
		dataset1.add(Arrays.asList(1d, 1d, 0d, 0d));
		dataset1.add(Arrays.asList(0d, 0d, 1d, 1d));
		MinHash minHash = new MinHash(dataset1);
		Double[][] sigs = minHash.run();
	}
}
