package de.abramov;

import de.abramov.network.INeuralNetwork;
import de.abramov.network.NeuralNetwork;
import de.abramov.network.configuration.Configuration;
import de.abramov.network.functions.Sigmoid;
import de.abramov.train.TrainDataGenerator;
import de.abramov.train.data.RealEstate;

import java.util.List;

public class Main {
    private static int trainDataSize = 10000;
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

        double[][] inputs = new double[trainData.size()][2];
        double[][] targets = new double[trainData.size()][1];
        double[][] testInputs = new double[testData.size()][2];
        double[][] testTargets = new double[testData.size()][1];
        prepareInputsAndTargets(trainData, inputs, targets);
        prepareInputsAndTargets(testData, testInputs, testTargets);

        INeuralNetwork neuralNetwork = new NeuralNetwork(neuralNetworkConfiguration)
                .train(inputs, targets)
                .evaluate(testInputs, testTargets);

        //Single Prediction
        var realEstate = new RealEstate(150000, 700);
        double[] toPredict = {realEstate.getPrice(), realEstate.getRent()};
        var prediction = neuralNetwork.predict(toPredict);
        System.out.println("Single Prediction of: " + realEstate.toString() + ": " + prediction);
    }

    private static void prepareInputsAndTargets(List<RealEstate> trainData, double[][] inputs, double[][] targets) {
        for (int i = 0; i < trainData.size(); i++) {
            RealEstate realEstate = trainData.get(i);
            double[] input = new double[2];
            input[0] = realEstate.getPrice();
            input[1] = realEstate.getRent();
            inputs[i] = input;

            targets[i][0] = (realEstate.isWorthwhile() ? 1.0d : 0.0d);
        }
    }

    private static void applyArguments(String[] args) {
        trainDataSize = Integer.parseInt(args[0]);
        testDataSize = Integer.parseInt(args[1]);
        equalDistribution = Boolean.parseBoolean(args[2]);
    }

}
