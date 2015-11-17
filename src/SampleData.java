import java.util.List;

public class SampleData {
	List<Double> attributes;
	int cluster;
	int id;

	public SampleData(List<Double> attributes, int cluster) {
		this.attributes = attributes;
		this.cluster = cluster; // -1 for un-classified.
	}

	@Override
	public String toString() {
		String rst = "(Attributes = ";
		for (int i = 0; i < attributes.size(); i++) {
			if (i > 0) {
				rst += ",";
			}
			rst += attributes.get(i);
		}
		rst += " Cluster = " + cluster + " )";
		return rst;
	}
}
