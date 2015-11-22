import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Flocking {
	public static final int INI_POS_BOUND = 200;
	public static final double SENSE_RANGE = 100d;
	public static final int ITERATION_COUNT = 200;
	public static final double WSR = 1;
	public static final double WAR = 1;
	public static final double WCR = 1;
	public static final double WDS = 1;
	public static final double WDD = 1;
	List<Boid> boids;
	List<SampleData> dataset;

	public Flocking(List<SampleData> dataset) {
		boids = new ArrayList<>();
		this.dataset = dataset;
	}

	/**
	 * assign each dataset into a boid, initialize velocity and position
	 */
	private void init() {
		for (int i = 0; i < dataset.size(); i++) {
			boids.add(getRandomBoid());
		}
	}

	private Boid getRandomBoid() {
		Random r = new Random();
		int[] ps = new int[2];
		for (int i = 0; i < 2; i++) {
			int dir = r.nextInt(2);
			dir = dir == 0 ? 1 : -1;
			ps[i] = r.nextInt(INI_POS_BOUND) * dir;
		}
		return new Boid(new Velocity(0, 0), ps[0], ps[1]);
	}

	public List<SampleData> run() {
		init();
		for (int c = 0; c < ITERATION_COUNT; c++) {
			List<Boid> newBoids = new ArrayList<>();
			for (int bid = 0; bid < boids.size(); bid++) {
				Boid b = boids.get(bid);
				int localCount = 0;
				Velocity varTotal = new Velocity(0, 0); // alignment
				Velocity vsr = new Velocity(0, 0); // separation
				Velocity vcr = new Velocity(0, 0); // cohesion
				Velocity vds = new Velocity(0, 0); // similarity
				Velocity vdd = new Velocity(0, 0); // dissimilarity
				for (int xid = 0; xid < boids.size(); xid++) {
					Boid x = boids.get(xid);
					double d = getDistance(b, x);
					if (d > SENSE_RANGE) {
						continue;
					}
					varTotal.vx += x.v.vx;
					varTotal.vy += x.v.vy;

					vsr.vx += d == 0 ? 0 : (x.v.vx + b.v.vx) / d;
					vsr.vy += d == 0 ? 0 : (x.v.vy + b.v.vy) / d;

					vcr.vx += x.x - b.x;
					vcr.vy += x.y - b.y;

					double sim = computeSimilarity(xid, bid);
					vds.vx += sim * d * x.v.vx;
					vds.vy += sim * d * x.v.vy;

					vdd.vx += sim * d == 0 ? 0 : (1 / (sim * d)) * x.v.vx;
					vdd.vy += sim * d == 0 ? 0 : (1 / (sim * d)) * x.v.vy;

					localCount++;
				}
				Velocity var = localCount == 0 ? new Velocity(0, 0)
						: new Velocity(varTotal.vx / localCount, varTotal.vy
								/ localCount);
				// compute updated weighted velocity for b
				Velocity newV = updateVelocity(var, vsr, vcr, vds, vdd);
				newBoids.add(new Boid(newV, b.x - newV.vx, newV.vy));
			}
			boids = newBoids;
		}
		// update dataset
		List<SampleData> points = new ArrayList<>();
		for (int i = 0; i < boids.size(); i++) {
			List<Double> attributes = new ArrayList<>();
			attributes.add(boids.get(i).x);
			attributes.add(boids.get(i).y);
			SampleData sd = new SampleData(attributes, i);
			points.add(sd);
		}
		return points;
	}

	private double computeSimilarity(int xid, int bid) {
		SampleData sd1 = dataset.get(xid);
		SampleData sd2 = dataset.get(bid);
		double rst = 0;
		for (int i = 0; i < sd1.attributes.size(); i++) {
			rst += Math.pow(sd1.attributes.get(i) - sd2.attributes.get(i), 2);
		}
		return Math.sqrt(rst);
	}

	private Velocity updateVelocity(Velocity var, Velocity vsr, Velocity vcr,
			Velocity vds, Velocity vdd) {
		return new Velocity(WAR * var.vx + WSR * vsr.vx + WCR * vcr.vx + WDS
				* vds.vx + WDD * vdd.vx, WAR * var.vy + WSR * vsr.vy + WCR
				* vcr.vy + WDS * vds.vy + WDD * vdd.vy);
	}

	private double getDistance(Boid b, Boid x) {
		return Math.sqrt((b.x - x.x) * (b.x - x.x) + (b.y - x.y) * (b.y - x.y));
	}

}
