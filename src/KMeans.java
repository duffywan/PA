import java.util.ArrayList;
import java.util.List;

public class KMeans {
	int k;
	List<SampleData> dataset;
	List<Integer>[] clusters; // TODO set or list
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
	public void init() {
		for (int i = 0; i < k; i++) {
			clusters[i] = new ArrayList<Integer>();
			clusters[i].add(i);
			centroids[i] = dataset.get(i);
		}
	}

	/**
	 * this method iterats the k-means process, stop until no changes are made.
	 */
	public void run() {
		boolean changes = true;
		while (changes) {
			List<Integer>[] newClusters = initNewClusters();
			changes = false;
			for (int dataIndex = 0; dataIndex < dataset.size(); dataIndex++) {
				// for each data entry, compute distance to each centroid
				SampleData currData = dataset.get(dataIndex);
				Double minDis = Double.MAX_VALUE;
				int newCluster = currData.cluster;
				for (SampleData centroid : centroids) {
					Double disToCentroid = calculateDistance(currData, centroid);
					if (disToCentroid < minDis) {
						minDis = disToCentroid;
						newCluster = centroid.cluster;
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
					int index = newClusters[i].get(i);
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

	private Double calculateDistance(SampleData sampleData, SampleData centroid) {
		// TODO use edulic distance for now
		double rst = 0;
		for (int i = 0; i < numOfAttr; i++) {
			rst += Math.pow(
					sampleData.attributes.get(i) - centroid.attributes.get(i),
					2);
		}
		return Math.sqrt(rst);
	}
}
