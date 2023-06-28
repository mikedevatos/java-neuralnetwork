package de.abramov.network.neuron;

public interface INeuron {

    double feedForward(double[] inputs);

    void train(double[] inputs, double target);

    void backpropagate(double[] inputs, double target);


}
