import java.util.ArrayList;
import java.util.List;

public class KMeans {
	int k;
	List<SampleData> dataset;
	List<Integer>[] clusters; // TODO set or list?
	SampleData[] centroids;
	int numOfAttr;

	@SuppressWarnings("unchecked")
	public KMeans(int k, List<SampleData> dataset) {
		this.k = k;
		this.dataset = dataset;
		this.clusters = new List[k];
		this.centroids = new SampleData[k];
		this.numOfAttr = dataset.get(0).attributes.size();
	}

	/**
	 * This method randomly pick k centroids as the initial centroids
	 */
	private void init() {
		int share = dataset.size() / k;
		for (int i = 0; i < k; i++) {
			clusters[i] = new ArrayList<Integer>();// TODO
			clusters[i].add(share * i);
			centroids[i] = dataset.get(share * i);
		}
	}

	/**
	 * this method iterats the k-means process, stop until no changes are made.
	 */
	public void run() {
		init();
		// TODO delete trace
		System.out.println("initial");
		for (int i = 0; i < centroids.length; i++) {
			System.out.print(centroids[i]);
			System.out.print(" members: ");
			for (Integer memberIdx : clusters[i]) {
				System.out.print(dataset.get(memberIdx));
			}
			System.out.println("");
		}
		boolean changes = true;
		int iterationCount = 0;
		while (changes && iterationCount < 100) {
			List<Integer>[] newClusters = initNewClusters();
			changes = false;
			for (int dataIndex = 0; dataIndex < dataset.size(); dataIndex++) {
				// for each data entry, compute distance to each centroid
				SampleData currData = dataset.get(dataIndex);
				Double minDis = Double.MAX_VALUE;
				int newCluster = currData.cluster;
				for (int i = 0; i < centroids.length; i++) {
					SampleData centroid = centroids[i];
					double disToCentroid = calculateDistance(currData, centroid);

					if (disToCentroid < minDis) {
						minDis = disToCentroid;
						newCluster = i;
					}
				}
				if (newCluster != currData.cluster) {
					currData.cluster = newCluster;
					changes = true;
				}
				newClusters[newCluster].add(dataIndex);
			}
			// recompute the centroid for each cluster
			SampleData[] newCentroids = recomputeCentroids(newClusters);
			clusters = newClusters;
			centroids = newCentroids;
			// TODO delete trace
			System.out.println("new round");
			for (int i = 0; i < centroids.length; i++) {
				System.out.print(i);
				System.out.print(" members: ");
				for (Integer memberIdx : clusters[i]) {
					System.out.print("," + memberIdx);
				}
				System.out.println("");
			}
			iterationCount++;
		}

	}

	private SampleData[] recomputeCentroids(List<Integer>[] newClusters) {
		SampleData[] newCentroids = new SampleData[k];
		for (int i = 0; i < k; i++) {
			SampleData newCentroid = new SampleData(new ArrayList<Double>(), i);
			int numOfMember = newClusters[i].size();
			for (int j = 0; j < numOfAttr; j++) {
				double attrVal = 0;
				for (int id = 0; id < newClusters[i].size(); id++) {
					int index = newClusters[i].get(id);
					attrVal += dataset.get(index).attributes.get(j)
							/ numOfMember;
				}
				newCentroid.attributes.add(attrVal);
			}
			newCentroids[i] = newCentroid;
		}

		return newCentroids;
	}

	private List<Integer>[] initNewClusters() {
		List<Integer>[] newClusters = new List[k];
		for (int i = 0; i < newClusters.length; i++) {
			newClusters[i] = new ArrayList<Integer>();
		}
		return newClusters;
	}

	protected double calculateDistance(SampleData sampleData,
			SampleData centroid) {
		// use Euclic distance for now
		double rst = 0;
		for (int i = 0; i < numOfAttr; i++) {
			rst += Math.pow(
					sampleData.attributes.get(i) - centroid.attributes.get(i),
					2);
		}
		return Math.sqrt(rst);
	}
}