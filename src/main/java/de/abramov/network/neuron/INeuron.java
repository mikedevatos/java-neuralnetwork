package de.abramov.network.neuron;

public interface INeuron {
    double feedForward(double[] inputs);

    void backpropagate(double[] inputs, double target);

}
