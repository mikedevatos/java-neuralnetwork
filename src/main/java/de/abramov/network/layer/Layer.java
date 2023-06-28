package de.abramov.network.layer;

import de.abramov.network.neuron.Neuron;

import java.util.ArrayList;

public class Layer extends ArrayList<Neuron> {
    public Layer(int size, int numOutputs) {
        for (int neuronNum = 0; neuronNum < size; neuronNum++) {
            add(new Neuron(numOutputs, neuronNum));
        }
    }
}
