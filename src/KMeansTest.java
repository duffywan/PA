import java.util.ArrayList;
import java.util.List;

public class KMeansTest {
	List<SampleData> dataset = new ArrayList<>();
	SampleData sd0;
	SampleData sd1;
	SampleData sd2;
	SampleData sd3;

	public KMeansTest() {
		sd0 = new SampleData(new ArrayList<Double>(), 0);
		sd1 = new SampleData(new ArrayList<Double>(), 1);
		sd2 = new SampleData(new ArrayList<Double>(), 2);
		sd3 = new SampleData(new ArrayList<Double>(), 3);
		sd0.attributes.add(1d);
		sd0.attributes.add(1d);
		sd1.attributes.add(2d);
		sd1.attributes.add(1d);
		sd2.attributes.add(4d);
		sd2.attributes.add(3d);
		sd3.attributes.add(5d);
		sd3.attributes.add(4d);
		dataset.add(sd0);
		dataset.add(sd1);
		dataset.add(sd2);
		dataset.add(sd3);
	}

	public static void main(String[] args) {
		PreprocessingData p = new PreprocessingData();
		List<SampleData> dataset = p.buildDatasetWithTF();
		KMeans km = new KMeans(4, dataset);
		km.run();

	}
}
