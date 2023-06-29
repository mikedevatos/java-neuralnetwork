package de.abramov.network.functions;

public class LeakyRelu implements ActivationFunction {
    @Override
    public double calculateActivation(double x) {
        return x > 0 ? x : 0.01 * x;
    }

    @Override
    public double calculateDerivative(double x) {
        return x > 0 ? 1 : 0.01;
    }
}
