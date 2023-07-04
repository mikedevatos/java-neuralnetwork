package de.abramov.network.functions;

public enum MultiClassificationLossFunctions {

    CATEGORICAL_CROSS_ENTROPY {
        @Override
        public double calculateError(double[] output, double[] target) {
            double error = 0;
            for (int i = 0; i < target.length; i++) {
                error += target[i] * Math.log(output[i]);
            }
            return -error;
        }
    };

    public abstract double calculateError(double[] target, double[] output);

}
