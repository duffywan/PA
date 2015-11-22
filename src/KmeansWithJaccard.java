import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KmeansWithJaccard extends KMeans {

	public KmeansWithJaccard(int k, List<SampleData> dataset) {
		super(k, dataset);
	}

	@Override
	protected double calculateDistance(SampleData sampleData,
			SampleData centroid) {
		Double intersection = 0d, union = 0d;
		for (int i = 0; i < numOfAttr; i++) {
			intersection += 2 * Math.min(sampleData.attributes.get(i),
					centroid.attributes.get(i));
			union += sampleData.attributes.get(i) + centroid.attributes.get(i);
		}
		return 1 - intersection / union;
	}

	public static void main(String[] args) {
		SampleData sd = new SampleData(Arrays.asList(1d, 2d, 3d, 4d), 0);
		SampleData sd1 = new SampleData(Arrays.asList(5d, 6d, 7d, 8d), 1);
		List<SampleData> dataset = new ArrayList<>();
		dataset.add(sd);
		dataset.add(sd1);
		KmeansWithJaccard km = new KmeansWithJaccard(2, dataset);
		km.calculateDistance(sd, sd1);

	}

}
