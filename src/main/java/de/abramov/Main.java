package de.abramov;

import de.abramov.network.INeuralNetwork;
import de.abramov.network.NeuralNetwork;
import de.abramov.network.configuration.Configuration;
import de.abramov.network.functions.Sigmoid;
import de.abramov.train.TrainDataGenerator;
import de.abramov.train.data.RealEstate;

public class Main {
    private static int trainDataSize = 1000;
    private static int testDataSize = 1000;
    private static boolean equalDistribution = true;

    public static void main(String[] args) {
        if (args.length != 0 && args.length !=3) {
            System.out.println("Wrong number of arguments. Please provide the following arguments: trainDataSize, testDataSize, equalDistribution");
            return;
        }
        if (args.length == 3) {
            applyArguments(args);
        }

        var testDataGenerator = new TrainDataGenerator();
        var trainData = testDataGenerator.getTrainData(trainDataSize, equalDistribution);
        var testData = testDataGenerator.getTrainData(testDataSize, equalDistribution);

        testDataGenerator.printStatistics(trainData);

        // Change these paramter if you want to experiment with the network.
        var neuralNetworkConfiguration = new Configuration(2, 64, 0.1, new Sigmoid());

        INeuralNetwork neuralNetwork = new NeuralNetwork(neuralNetworkConfiguration)
                .train(trainData)
                .evaluate(testData);

        //Single Prediction
        var realEstate = new RealEstate(150000, 700);
        var prediction = neuralNetwork.predict(realEstate);
        System.out.println("Single Prediction of: " + realEstate.toString() + ": " + prediction);
    }

    private static void applyArguments(String[] args) {
        trainDataSize = Integer.parseInt(args[0]);
        testDataSize = Integer.parseInt(args[1]);
        equalDistribution = Boolean.parseBoolean(args[2]);
    }

}
