package de.abramov.network.functions;

public class Sigmoid implements ActivationFunction {
    @Override
    public double calculateActivation(double x) {
        return 1d / (1d + Math.exp(-x));
    }

    @Override
    public double calculateDerivative(double x) {
        return calculateActivation(x) * (1 - calculateActivation(x));
    }
}
