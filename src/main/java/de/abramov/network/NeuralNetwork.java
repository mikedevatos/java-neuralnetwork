package de.abramov.network;

import de.abramov.network.configuration.Configuration;
import de.abramov.network.functions.ActivationFunction;
import de.abramov.network.neuron.Neuron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NeuralNetwork {
    private static final Logger LOG = LoggerFactory.getLogger(NeuralNetwork.class);
    private final double[][] inputs;
    private final double[][] targets;
    private final Configuration config;
    private final ActivationFunction hiddenLayerActivationFunction;
    private final ActivationFunction outputLayerActivationFunction;
    private final double[][] testInputs;
    private final double[][] testTargets;

    private final Neuron[] hiddenLayer;
    private final Neuron[] outputLayer;

    /**
     *
     * @param inputs - This are the features of the dataset
     * @param targets - This are the labels of the dataset
     * @param testInputs - This are the features of the test dataset
     * @param testTargets - This are the labels of the test dataset
     * @param config - This is the configuration of the neural network
     */
    public NeuralNetwork(double[][] inputs, double[][] targets, double[][] testInputs, double[][] testTargets, Configuration config) {
        this.inputs = inputs;
        this.targets = targets;
        this.testInputs = testInputs;
        this.testTargets = testTargets;
        this.config = config;

        hiddenLayerActivationFunction = config.hiddenLayerActivationFunction();
        outputLayerActivationFunction = config.outputLayerActivationFunction();

        hiddenLayer = new Neuron[config.hiddenSize()];
        outputLayer = new Neuron[config.outputSize()];

        for (int i = 0; i < config.hiddenSize(); i++) {
            hiddenLayer[i] = new Neuron(config.inputSize(), hiddenLayerActivationFunction);
        }

        for (int i = 0; i < config.outputSize(); i++) {
            outputLayer[i] = new Neuron(config.hiddenSize(), outputLayerActivationFunction);
        }
    }

    private double[] feedforward(double[] input) {
        double[] hiddenOutputs = new double[config.hiddenSize()];
        double[] output = new double[config.outputSize()];

        for (int i = 0; i < config.hiddenSize(); i++) {
            hiddenOutputs[i] = hiddenLayer[i].calculateOutput(input);
        }

        for (int i = 0; i < config.outputSize(); i++) {
            output[i] = outputLayer[i].calculateOutput(hiddenOutputs);
        }

        return outputLayerActivationFunction.calculateActivation(output);
    }

    public void train() {
        for (int epoch = 0; epoch < config.epochs(); epoch++) {
            for (int i = 0; i < inputs.length; i++) {
                double[] output = feedforward(inputs[i]);

                double[] output_error_signal = new double[config.outputSize()];
                double[] hidden_error_signal = new double[config.hiddenSize()];

                for (int j = 0; j < config.outputSize(); j++)
                    output_error_signal[j] = targets[i][j] - output[j];

                for (int j = 0; j < config.hiddenSize(); j++)
                    for (int k = 0; k < config.outputSize(); k++)
                        hidden_error_signal[j] += output_error_signal[k] * outputLayer[k].getWeight(j);

                updateWeights(i, output_error_signal, hidden_error_signal); //Aufruf der neuen Methode
            }

            if (epoch % 2 == 0) {
                double accuracy = evaluate(testInputs, testTargets);
                LOG.info("Epoch: {} Accuracy: {}%", epoch, String.format("%.2f", accuracy * 100));
            }
        }
    }

    private void updateWeights(int i, double[] output_error_signal, double[] hidden_error_signal) {
        for (int j = 0; j < config.hiddenSize(); j++) {
            hiddenLayer[j].adjustBias(hidden_error_signal[j], config.learningRate());
            hiddenLayer[j].adjustWeights(inputs[i], hidden_error_signal[j], config.learningRate());
        }

        double[] hiddenOutputs = new double[config.hiddenSize()];
        for (int k = 0; k < config.hiddenSize(); k++) {
            hiddenOutputs[k] = hiddenLayer[k].calculateOutput(inputs[i]);
        }
        for (int j = 0; j < config.outputSize(); j++) {
            outputLayer[j].adjustBias(output_error_signal[j], config.learningRate());
            outputLayer[j].adjustWeights(hiddenOutputs, output_error_signal[j], config.learningRate());
        }
    }

    public double evaluate(double[][] testInputs, double[][] testTargets) {
        int correctCount = 0;

        for (int i = 0; i < testInputs.length; i++) {
            double[] predicted = predict(testInputs[i]);
            int predictedIndex = 0;
            int targetIndex = 0;

            for (int j = 0; j < predicted.length; j++) {
                if (predicted[j] > predicted[predictedIndex])
                    predictedIndex = j;
                if (testTargets[i][j] > testTargets[i][targetIndex])
                    targetIndex = j;
            }

            if (predictedIndex == targetIndex)
                correctCount++;
        }

        return (double) correctCount / testInputs.length;
    }

    public double[] predict(double[] input) {
        return feedforward(input);
    }
}
