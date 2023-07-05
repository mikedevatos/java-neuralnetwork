package de.abramov;

import de.abramov.data.dto.RealEstate;
import de.abramov.data.provider.DataProvider;
import de.abramov.data.provider.IDataProvider;
import de.abramov.data.provider.IrisProvider;
import de.abramov.data.provider.RealEstateProvider;
import de.abramov.network.NeuralNetwork;
import de.abramov.network.configuration.Configuration;
import de.abramov.network.functions.ActivationFunction;
import de.abramov.network.functions.LossFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {

    private static boolean equalDistribution = true;
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final boolean SHUFFLE = true;
    private final static boolean NORMALIZE = true;

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        var datasetProvider = getDataProvider(DataProvider.IRIS);
        datasetProvider.printStatistics();

        var inputSize = datasetProvider.getFeatures()[0].length;
        var outputSize = datasetProvider.getLabels()[0].length;

        var neuralNetworkConfiguration = new Configuration(inputSize, 128, outputSize, 0.01, 6, ActivationFunction.LEAKY_RELU, ActivationFunction.SOFTMAX, LossFunction.CATEGORICAL_CROSS_ENTROPY);

        double[][] inputs = datasetProvider.getFeatures();
        double[][] targets = datasetProvider.getLabels();
        double[][] testInputs = datasetProvider.getFeaturesTest();
        double[][] testTargets = datasetProvider.getLabelsTest();

        NeuralNetwork neuralNetwork = new NeuralNetwork(inputs, targets, testInputs, testTargets, neuralNetworkConfiguration);
        neuralNetwork.train();
    }

    private IDataProvider<?> getDataProvider(DataProvider dataProvider) {
        switch (dataProvider) {
            case IRIS:
                return new IrisProvider(NORMALIZE, SHUFFLE, 0.8);
            case REAL_ESTATE:
                int trainDataSize = 200;
                return new RealEstateProvider(trainDataSize, equalDistribution, 0.6);
            default:
                throw new IllegalArgumentException("Unknown data provider: " + dataProvider);
        }
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

}
