package de.abramov.network.neuron;

import java.util.Random;

public class Neuron implements INeuron {
    private double[] weights;
    private double bias;
    private double learningRate;

    public Neuron(int inputSize, double learningRate) {
        this.weights = new double[inputSize];
        this.bias = 0.0;
        this.learningRate = learningRate;

        // Initialisiere die Gewichte mit Zufallswerten zwischen -1 und 1
        Random random = new Random();
        for (int i = 0; i < inputSize; i++) {
            weights[i] = random.nextDouble() * 2 - 1;
        }
    }

    @Override
    public double feedForward(double[] inputs) {
        if (inputs.length != weights.length) {
            throw new IllegalArgumentException("Die Länge der Eingaben stimmt nicht mit der Anzahl der Gewichte überein");
        }

        double sum = 0.0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i] * weights[i];
        }
        sum += bias;

        return sigmoid(sum);
    }

    @Override
    public void train(double[] inputs, double target) {
        double output = feedForward(inputs);
        double error = target - output;

        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * error * inputs[i];
        }
        bias += learningRate * error;
    }

    @Override
    public void backpropagate(double[] inputs, double target) {
        double output = feedForward(inputs);
        double error = target - output;
        double delta = error * sigmoidDerivative(output);

        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * delta * inputs[i];
        }
        bias += learningRate * delta;
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double sigmoidDerivative(double x) {
        return sigmoid(x) * (1 - sigmoid(x));
    }

    public double[] getWeights() {
        return weights;
    }

    public double getBias() {
        return bias;
    }

    public double getLearningRate() {
        return learningRate;
    }
}
