package de.abramov.network;

import de.abramov.network.configuration.Configuration;
import de.abramov.network.functions.Sigmoid;
import de.abramov.train.data.RealEstate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NeuralNetworkTest {
    @Test
    public void testTrainAndPredict() {
        Configuration config = new Configuration(2, 2, 0.1, new Sigmoid());
        NeuralNetwork network = new NeuralNetwork(config);

        List<RealEstate> trainingData = new ArrayList<>();
        trainingData.add(new RealEstate(100000, 500));
        trainingData.add(new RealEstate(200000, 1000));

        network.train(trainingData);

        RealEstate testData = new RealEstate(150000, 700);
        double prediction = network.predict(testData);

        // The prediction should be a value between 0 and 1.
        assertTrue(prediction >= 0 && prediction <= 1);
    }
}
