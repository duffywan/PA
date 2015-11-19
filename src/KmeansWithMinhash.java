import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Q3 incorporate the Minhash as a similarity in K-means and compare your
 * results with Kmeans of different meaures(at least two)
 * 
 * @author Yi Wan
 *
 */
public class KmeansWithMinhash extends KMeans {

	public KmeansWithMinhash(int k, List<SampleData> dataset) {
		super(k, dataset);
	}

	/**
	 * use Minhash as similarity measure
	 */
	@Override
	protected Double calculateDistance(SampleData sampleData,
			SampleData centroid) {
		List<List<Double>> minHashDataset = new ArrayList<List<Double>>();
		for (int i = 0; i < numOfAttr; i++) {
			minHashDataset.add(new ArrayList<Double>());
			minHashDataset.get(i).add(sampleData.attributes.get(i));
			minHashDataset.get(i).add(centroid.attributes.get(i));
		}
		// TODO del trace
		MinHash minHash = new MinHash(minHashDataset);
		// TODO del trace
		Double[][] sigs = minHash.run();
		int agreeCount = 0;
		for (int i = 0; i < minHash.numOfHash; i++) {
			if (sigs[i][0] != 0d && sigs[i][1] != 0d) {
				agreeCount++;
			}
		}
		return (double) (agreeCount / numOfAttr);
	}

	public static void main(String[] args) {
		List<Double> s1 = Arrays.asList(1d, 2d, 3d, 4d);
		List<Double> s2 = Arrays.asList(5d, 6d, 7d, 8d);
		List<SampleData> dataset = new ArrayList<>();
		dataset.add(new SampleData(s1, 0));
		dataset.add(new SampleData(s2, 1));
		KmeansWithMinhash km = new KmeansWithMinhash(2, dataset);
		km.run();
	}
}
