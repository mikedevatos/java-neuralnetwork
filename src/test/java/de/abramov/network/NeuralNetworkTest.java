package de.abramov.network;

import de.abramov.network.configuration.Configuration;
import de.abramov.network.functions.ActivationFunction;
import de.abramov.network.functions.LossFunction;
import de.abramov.train.data.RealEstate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NeuralNetworkTest {
    @Test
    public void testTrainAndPredict() {
        Configuration config = new Configuration(2, 2, 1, 0.1, 20,  ActivationFunction.SIGMOID, LossFunction.CATEGORICAL_CROSS_ENTROPY);
        NeuralNetwork network = new NeuralNetwork(config);

        List<RealEstate> trainingData = new ArrayList<>();
        trainingData.add(new RealEstate(100000, 500));
        trainingData.add(new RealEstate(200000, 1000));

        double[][] inputs = new double[trainingData.size()][2];
        double[][] targets = new double[trainingData.size()][1];

        for (int i = 0; i < trainingData.size(); i++) {
            RealEstate realEstate = trainingData.get(i);
            double[] input = new double[2];
            input[0] = realEstate.getPrice();
            input[1] = realEstate.getRent();
            inputs[i] = input;
            targets[i] = new double[]{realEstate.getRent()};
        }

        network.train(inputs, targets);

        RealEstate testData = new RealEstate(150000, 700);
        double[] toPredict = {testData.getPrice(), testData.getRent()};
        double[] prediction = network.predict(toPredict);

        // The prediction should be a value between 0 and 1.
        assertTrue(prediction[0] >= 0 && prediction[0] <= 1);
    }
}
