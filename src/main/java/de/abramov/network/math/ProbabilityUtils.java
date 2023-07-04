package de.abramov.network.math;

public class ProbabilityUtils {
    private static double jensenShannonDivergence(double[] p, double[] q) {
        double[] m = new double[p.length];
        for (int i = 0; i < p.length; i++) {
            m[i] = 0.5 * (p[i] + q[i]);
        }
        return 0.5 * klDivergence(p, m) + 0.5 * klDivergence(q, m);
    }

    private static double klDivergence(double[] p, double[] q) {
        double divergence = 0.0;
        for (int i = 0; i < p.length; ++i) {
            if (p[i] != 0.0 && q[i] != 0.0) {
                divergence += p[i] * Math.log(p[i] / q[i]);
            }
        }
        return divergence / Math.log(2); // Um die Divergenz in Bits statt in Nat zu bekommen.
    }

    public static boolean probabilityEquals(double[] prediction, double[] target, double threshold) {
        double divergence = jensenShannonDivergence(prediction, target);
        if (divergence < threshold) {
            return true;
        } else {
            return false;
        }
    }

}
