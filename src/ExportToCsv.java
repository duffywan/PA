import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportToCsv {
	public static void exportToCsv() {
		PreprocessingData p = new PreprocessingData();
		List<SampleData> dataset = p.buildDatasetWithTF();
		int numOfAttr = dataset.get(0).attributes.size();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
				"./docs/data.csv")))) {
			// write the header line
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < numOfAttr; i++) {
				if (i > 0) {
					sb.append(',');
				}
				sb.append(i);
			}
			bw.write(sb.toString());
			bw.newLine();
			for (SampleData sd : dataset) {
				sb = new StringBuilder();
				for (int i = 0; i < numOfAttr; i++) {
					if (i > 0) {
						sb.append(',');
					}
					sb.append(sd.attributes.get(i));
				}
				bw.write(sb.toString());
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ExportToCsv.exportToCsv();
	}
}
