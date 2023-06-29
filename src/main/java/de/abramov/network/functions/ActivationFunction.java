package de.abramov.network.functions;

public interface ActivationFunction {
    double calculateActivation(double x);
    double calculateDerivative(double x);
}
