package de.abramov.network;

public interface INeuralNetwork {
    NeuralNetwork train(double[][] inputs, double[][] targets);
    double[] predict(double[] inputs);
    void backpropagate(double[] inputs, double target);
    NeuralNetwork evaluate(double[][] inputs, double[][] targets);
}