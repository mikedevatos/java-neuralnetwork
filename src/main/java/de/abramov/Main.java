package de.abramov;

import de.abramov.network.INeuralNetwork;
import de.abramov.network.NeuralNetwork;
import de.abramov.network.configuration.Configuration;
import de.abramov.network.functions.Sigmoid;
import de.abramov.train.TrainDataGenerator;
import de.abramov.train.data.RealEstate;

public class Main {

    public static void main(String[] args) {
            int trainDataSize = 10000;
            int testDataSize = 1000;
            boolean equalDistribution = true;
            var testDataGenerator = new TrainDataGenerator();

            var trainRealEstateDataset = testDataGenerator.getTrainData(trainDataSize, equalDistribution);
            var testRealEstateDataset = testDataGenerator.getTrainData(testDataSize, equalDistribution);

            testDataGenerator.printStatistics(trainRealEstateDataset);

            // Change these paramter if you want to experiment with the network.
            var neuralNetworkConfiguration = new Configuration(2, 64, 0.1, new Sigmoid());

            INeuralNetwork neuralNetwork = new NeuralNetwork(neuralNetworkConfiguration)
                    .train(trainRealEstateDataset)
                    .evaluate(testRealEstateDataset);

            //Single Prediction
            var realEstate = new RealEstate(150000, 700);
            var prediction = neuralNetwork.predict(realEstate);
            System.out.println("Single Prediction of: " +realEstate.toString()+": "+ prediction);
        }

}
