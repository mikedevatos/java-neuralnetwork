package de.abramov.network.neuron;

import de.abramov.network.functions.ActivationFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NeuronTest {
    @Test
    public void testFeedForward() {
        Neuron neuron = new Neuron(2,  ActivationFunction.SIGMOID);
        double output = neuron.calculateOutput(new double[]{1, 1});
        assertTrue(output >= 0 && output <= 1);
    }

}
