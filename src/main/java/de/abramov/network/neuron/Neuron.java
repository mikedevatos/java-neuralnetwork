package de.abramov.network.neuron;

import de.abramov.network.functions.ActivationFunction;

import java.util.concurrent.ThreadLocalRandom;

public class Neuron implements INeuron {
    private final static ThreadLocalRandom random = ThreadLocalRandom.current();
    private final ActivationFunction activationFunction;
    private final double[] weights;
    private double bias;
    private final double learningRate;

    public Neuron(int inputSize, double learningRate, ActivationFunction activationFunction) {
        this.weights = new double[inputSize];
        this.bias = 0.0;
        this.learningRate = learningRate;
        this.activationFunction = activationFunction;

        initRandomWeightMinusOneToOne(inputSize);
    }

    private void initRandomWeightMinusOneToOne(int inputSize) {
        for (int i = 0; i < inputSize; i++) {
            weights[i] = random.nextDouble() * 2 - 1;
        }
    }

    @Override
    public double feedForward(double[] inputs) {
        if (inputs.length != weights.length) {
            throw new IllegalArgumentException("The length of the inputs does not match the number of weights");
        }

        double sum = 0.0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i] * weights[i];
        }
        sum += bias;

        return activationFunction.calculateActivation(sum);
    }

    @Override
    public void backpropagate(double[] inputs, double target) {
        double output = feedForward(inputs);
        double error = target - output;
        double delta = error * activationFunction.calculateDerivative(output);

        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * delta * inputs[i];
        }
        bias += learningRate * delta;
    }
}
