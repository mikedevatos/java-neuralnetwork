package de.abramov.network.neuron;

import de.abramov.network.functions.Sigmoid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NeuronTest {
    @Test
    public void testFeedForward() {
        Neuron neuron = new Neuron(2, 0.1, new Sigmoid());
        double output = neuron.feedForward(new double[]{1, 1});
        assertTrue(output >= 0 && output <= 1);
    }

}
