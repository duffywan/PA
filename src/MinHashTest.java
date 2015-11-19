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
		MinHash minHash = new MinHash(dataset);
		Double[][] sigs = minHash.run();
	}
}
