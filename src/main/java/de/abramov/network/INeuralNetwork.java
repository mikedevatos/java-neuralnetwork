package de.abramov.network;

public interface INeuralNetwork {
    NeuralNetwork train(double[][] inputs, double[][] targets);
    double[] predict(double[] inputs);
    void backpropagate(double[] input, double[] output, double[] target);
    NeuralNetwork evaluate(double[][] inputs, double[][] targets);
}