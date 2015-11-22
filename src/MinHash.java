import java.util.List;

public class MinHash {
	List<List<Double>> dataset;
	double[][] sigs;
	int numOfAttr;// rows
	int numOfSets; // columns
	int numOfHash;

	public MinHash(List<List<Double>> dataset) {
		this.dataset = dataset;
		this.numOfHash = 2;
		this.numOfAttr = dataset.size();
		this.numOfSets = dataset.get(0).size();
		this.sigs = new double[numOfHash][numOfSets];
		for (int i = 0; i < sigs.length; i++) {
			for (int j = 0; j < sigs[0].length; j++) {
				sigs[i][j] = Double.MAX_VALUE;
			}
		}
	}

	public double[][] run() {
		// compute h1(r), h2(r),..., hn(r)
		double[][] hashes = new double[numOfAttr][numOfHash];
		// handle each row
		for (int i = 0; i < numOfAttr; i++) {
			hashes[i] = computeHashes(i);
			// handle each column
			for (int j = 0; j < numOfSets; j++) {
				if (dataset.get(i).get(j) == 0) {
					continue;
				}
				for (int h = 0; h < numOfHash; h++) {
					sigs[h][j] = Math.min(sigs[h][j], hashes[i][h]);
				}
			}
			// TODO delete trace
			/*
			 * System.out.println("row = " + i + ":"); for (int j = 0; j <
			 * sigs.length; j++) { System.out.println(Arrays.toString(sigs[j]));
			 * }
			 */
		}
		return sigs;
	}

	private double[] computeHashes(int r) {
		// for now we use two hash functions 1. (x + 1) mod 5 2. (3x +
		// 1) mod 5
		double[] rst = new double[numOfHash];
		rst[0] = (r + 1) % 5;
		rst[1] = (3 * r + 1) % 5;
		return rst;
	}

}
