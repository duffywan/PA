import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Q3 incorporate the Minhash as a similarity in K-means
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
		MinHash minHash = new MinHash(minHashDataset);
		Double[][] sigs = minHash.run();
		int agreeCount = 0;
		for (int i = 0; i < minHash.numOfHash; i++) {
			if (sigs[i][0] != 0 && sigs[i][1] != 0) {
				agreeCount++;
			}
		}
		double rst = 1 - (agreeCount / numOfAttr);
		return rst;

	}

	public static void main(String[] args) {
		List<Double> s1 = Arrays.asList(1d, 2d, 3d, 4d);
		List<Double> s2 = Arrays.asList(5d, 6d, 7d, 8d);
		List<SampleData> dataset = new ArrayList<>();
		dataset.add(new SampleData(s1, 0));
		dataset.add(new SampleData(s2, 1));

		List<SampleData> dataset1 = new ArrayList<>();
		List<Double> l1 = new ArrayList<>();
		l1.add(1d);
		l1.add(0d);
		List<Double> l2 = new ArrayList<>();
		l2.add(0d);
		l2.add(1d);
		SampleData sd1 = new SampleData(l1, 0);
		SampleData sd2 = new SampleData(l2, 2);
		dataset1.add(sd1);
		dataset1.add(sd1);
		dataset1.add(sd2);
		dataset1.add(sd2);
		KmeansWithMinhash km = new KmeansWithMinhash(2, dataset1);
		System.out.println(km.calculateDistance(sd1, sd2));
		// km.run();
	}
}
