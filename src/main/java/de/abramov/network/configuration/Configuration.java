package de.abramov.network.configuration;

import de.abramov.network.functions.ActivationFunction;

public class Configuration {
    public final ActivationFunction activationFunction;
    public final int inputSize;
    public final int hiddenSize;
    public final double learningRate;
    public final int outputSize;

    public Configuration(int inputSize, int hiddenSize, int outputSize, double learningRate, ActivationFunction activationFunction) {
        this.inputSize = inputSize;
        this.hiddenSize = hiddenSize;
        this.outputSize = outputSize;
        this.learningRate = learningRate;
        this.activationFunction = activationFunction;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "activationFunction=" + activationFunction +
                ", inputSize=" + inputSize +
                ", hiddenSize=" + hiddenSize +
                ", outputSize=" + outputSize +
                ", learningRate=" + learningRate +
                '}';
    }
}
