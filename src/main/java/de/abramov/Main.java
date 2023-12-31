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
    private static final boolean SHUFFLE = true;
    private final static boolean NORMALIZE = true;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        var datasetProvider = getDataProvider(DataProvider.IRIS);
        datasetProvider.printStatistics();

        var inputSize = datasetProvider.getFeatures()[0].length;
        var outputSize = datasetProvider.getLabels()[0].length;
        var learningRate = 0.01;
        var epochs = 100;
        List<Integer> hiddenLayersSize = List.of(12,6);

        var neuralNetworkConfiguration = new Configuration(inputSize, hiddenLayersSize, outputSize, learningRate, epochs, ActivationFunction.LEAKY_RELU, ActivationFunction.SOFTMAX, LossFunction.CATEGORICAL_CROSS_ENTROPY);


        double[][] features = datasetProvider.getFeatures();
        double[][] labels = datasetProvider.getLabels();
        double[][] testFeatures = datasetProvider.getFeaturesTest();
        double[][] testLabels = datasetProvider.getLabelsTest();

        var neuralNetwork = new NeuralNetwork(features, labels, testFeatures, testLabels, neuralNetworkConfiguration);
        neuralNetwork.train();
    }

    private IDataProvider<?> getDataProvider(DataProvider dataProvider) {
        switch (dataProvider) {
            case IRIS -> {
                return new IrisProvider(NORMALIZE, SHUFFLE, 0.6);
            }
            case REAL_ESTATE -> {
                int trainDataSize = 200;
                return new RealEstateProvider(trainDataSize, equalDistribution, 0.6);
            }
            default -> throw new IllegalArgumentException("Unknown data provider: " + dataProvider);
        }
    }
}
