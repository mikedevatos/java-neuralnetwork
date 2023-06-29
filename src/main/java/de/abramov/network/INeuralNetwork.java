package de.abramov.network;

import de.abramov.train.data.RealEstate;

import java.util.List;

public interface INeuralNetwork {
    NeuralNetwork train(List<RealEstate> trainingData);
    double predict(RealEstate realEstate);
    void backpropagate(double[] inputs, double target);
    NeuralNetwork evaluate(List<RealEstate> testData);
}