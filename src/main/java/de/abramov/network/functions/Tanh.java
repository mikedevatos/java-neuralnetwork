package de.abramov.network.functions;

public class Tanh implements ActivationFunction {
    @Override
    public double calculateActivation(double x) {
        return Math.tanh(x);
    }

    @Override
    public double calculateDerivative(double x) {
        return 1 - Math.pow(calculateActivation(x), 2);
    }
}
