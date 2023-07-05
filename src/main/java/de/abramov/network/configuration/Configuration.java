package de.abramov.network.configuration;

import de.abramov.network.functions.ActivationFunction;
import de.abramov.network.functions.LossFunction;

public record Configuration(int inputSize, int hiddenSize, int outputSize, double learningRate, int epochs,
                            ActivationFunction hiddenLayerActivationFunction, ActivationFunction outputLayerActivationFunction,  LossFunction lossFunction) {
}
