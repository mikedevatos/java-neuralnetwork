package de.abramov.network.configuration;

import de.abramov.network.functions.ActivationFunction;
import de.abramov.network.functions.LossFunction;

import java.util.List;

public record Configuration(int inputSize, List<Integer> hiddenLayersSize, int outputSize, double learningRate, int epochs,
                            ActivationFunction hiddenLayerActivationFunction, ActivationFunction outputLayerActivationFunction, LossFunction lossFunction) {
}