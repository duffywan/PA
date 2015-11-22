import java.util.ArrayList;
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
	protected double calculateDistance(SampleData sampleData,
			SampleData centroid) {
		List<List<Double>> minHashDataset = new ArrayList<List<Double>>();
		for (int i = 0; i < numOfAttr; i++) {
			minHashDataset.add(new ArrayList<Double>());
			minHashDataset.get(i).add(sampleData.attributes.get(i));
			minHashDataset.get(i).add(centroid.attributes.get(i));
		}
		MinHash minHash = new MinHash(minHashDataset);
		double[][] sigs = minHash.run();
		int agreeCount = 0;
		for (int i = 0; i < minHash.numOfHash; i++) {
			if (sigs[i][0] == sigs[i][1]) {
				// TODO original algorithm: sigs[i][0] == sigs[i][1]
				agreeCount++;
			}
		}
		double rst = 1 - (agreeCount / minHash.numOfHash);
		return rst;

	}

	public static void main(String[] args) {
		List<SampleData> dataset = SampleDataset.getSampleDataset();
		KmeansWithMinhash kmm = new KmeansWithMinhash(2, dataset);
		kmm.run();
	}
}
