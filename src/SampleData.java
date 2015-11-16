import java.util.List;

public class SampleData {
	List<Double> attributes;
	int cluster;
	int id;

	public SampleData(List<Double> attributes, int cluster) {
		this.attributes = attributes;
		this.cluster = cluster; // -1 for un-classified.

	}
}
