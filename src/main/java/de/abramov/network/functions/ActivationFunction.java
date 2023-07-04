package de.abramov.network.functions;

public enum ActivationFunction {

    SIGMOID {
        @Override
        public double calculateActivation(double x) {
            return 1 / (1 + Math.exp(-x));
        }

        @Override
        public double calculateDerivative(double x) {
            return calculateActivation(x) * (1 - calculateActivation(x));
        }
    },
    RELU {
        @Override
        public double calculateActivation(double x) {
            return Math.max(0, x);
        }

        @Override
        public double calculateDerivative(double x) {
            return x > 0 ? 1 : 0;
        }
    },
    LEAKY_RELU {
        @Override
        public double calculateActivation(double x) {
            return x > 0 ? x : 0.01 * x;
        }

        @Override
        public double calculateDerivative(double x) {
            return x > 0 ? 1 : 0.01;
        }
    },
    TANH {
        @Override
        public double calculateActivation(double x) {
            return Math.tanh(x);
        }

        @Override
        public double calculateDerivative(double x) {
            return 1 - Math.pow(calculateActivation(x), 2);
        }
    };


    public abstract double calculateActivation(double x);

    public abstract double calculateDerivative(double x);
}
