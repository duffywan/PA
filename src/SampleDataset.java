import java.util.ArrayList;
import java.util.List;

class SampleDataset {
	private static List<SampleData> dataset;

	public static List<SampleData> getSampleDataset() {
		if (dataset == null) {
			dataset = new ArrayList<>();
			List<Double> attr0 = new ArrayList<>();
			List<Double> attr1 = new ArrayList<>();
			List<Double> attr2 = new ArrayList<>();
			List<Double> attr3 = new ArrayList<>();
			attr0.add(0d);
			attr0.add(1d);
			attr1.add(0d);
			attr1.add(1d);
			attr2.add(1d);
			attr2.add(0d);
			attr3.add(1d);
			attr3.add(0d);
			dataset.add(new SampleData(attr0, 0));
			dataset.add(new SampleData(attr1, 1));
			dataset.add(new SampleData(attr2, 2));
			dataset.add(new SampleData(attr3, 3));
		}
		return dataset;
	}
}
