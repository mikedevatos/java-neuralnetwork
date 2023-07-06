package de.abramov.network.neuron;

import de.abramov.network.functions.ActivationFunction;

public class Neuron {
    private double[] weights;
    private double bias;
    private final ActivationFunction activationFunction;

    public Neuron(int inputSize, ActivationFunction activationFunction) {
        this.weights = new double[inputSize];
        this.activationFunction = activationFunction;
        this.bias = Math.random() * 2 - 1; // initialize bias to a random value between -1 and 1

        // Initialize weights to random values between -1 and 1
        for (int i = 0; i < inputSize; i++) {
            this.weights[i] = Math.random() * 2 - 1;
        }
    }

    public double calculateOutput(double[] input) {
        double output = bias;
        for (int i = 0; i < input.length; i++) {
            output += input[i] * weights[i];
        }
        return activationFunction.calculateActivation(output);
    }

    public void adjustWeights(double[] input, double error, double learningRate) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * input[i] * error;
        }
    }

    public void adjustBias(double error, double learningRate) {
        bias += learningRate * error;
    }

    public double getWeight(int index) {
        return weights[index];
    }
}
