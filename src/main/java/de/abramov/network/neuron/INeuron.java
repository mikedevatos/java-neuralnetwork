package de.abramov.network.neuron;

public interface INeuron {
    double calculateOutput(double[] inputs);

    void backpropagate(double[] inputs, double target);

}
