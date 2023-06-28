package de.abramov.network;

import de.abramov.train.data.RealEstate;

import java.util.List;

public interface INeuralNetwork {
    void train(List<RealEstate> trainingData);
    double predict(RealEstate realEstate);
    void backpropagate(double[] inputs, double target);
    double evaluate(List<RealEstate> testData);
}