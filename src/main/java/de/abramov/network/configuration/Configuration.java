package de.abramov.network.configuration;

import de.abramov.network.functions.ActivationFunction;
import de.abramov.network.functions.LossFunction;

public class Configuration {
    public final ActivationFunction activationFunction;
    public final int inputSize;
    public final int hiddenSize;
    public final double learningRate;
    public final int outputSize;
    public final int epochs;
    public final LossFunction lossFunction;

    public Configuration(int inputSize, int hiddenSize, int outputSize, double learningRate, int epochs, ActivationFunction activationFunction, LossFunction lossFunction) {
        this.inputSize = inputSize;
        this.hiddenSize = hiddenSize;
        this.outputSize = outputSize;
        this.learningRate = learningRate;
        this.activationFunction = activationFunction;
        this.epochs = epochs;
        this.lossFunction = lossFunction;
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
