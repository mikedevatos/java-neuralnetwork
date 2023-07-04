package de.abramov.network;

import de.abramov.network.configuration.Configuration;
import de.abramov.network.functions.MultiClassificationLossFunctions;
import de.abramov.network.math.ProbabilityUtils;
import de.abramov.network.neuron.Neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class NeuralNetwork implements INeuralNetwork {
    private final int inputSize;
    private final int hiddenSize;
    private final List<Neuron> hiddenNeurons;
    private final List<Neuron> outputNeurons;
    private final int outputSize;

    public NeuralNetwork(Configuration configuration) {
        this.inputSize = configuration.inputSize;
        this.hiddenSize = configuration.hiddenSize;
        this.outputSize = configuration.outputSize;
        double learningRate = configuration.learningRate;

        hiddenNeurons = new ArrayList<>();
        outputNeurons = new ArrayList<>();

        // create hidden neurons
        for (int i = 0; i < hiddenSize; i++) {
            hiddenNeurons.add(new Neuron(inputSize, learningRate, configuration.activationFunction));
        }

        // create output neurons
        for (int i = 0; i < outputSize; i++) {
            outputNeurons.add(new Neuron(hiddenSize, learningRate, configuration.activationFunction));
        }
    }

    @Override
    public NeuralNetwork train(double[][] inputs, double[][] targets) {
        for (int j = 0; j < inputs.length; j++) {
            double[] input = inputs[j];

            double target = targets[j][0];

            // Feedforward
            double[] hiddenOutputs = new double[hiddenSize];
            for (int i = 0; i < hiddenSize; i++) {
                hiddenOutputs[i] = hiddenNeurons.get(i).calculateOutput(input);
            }

            // Backpropagation for hidden neurons
            this.backpropagate(input, target);

            // Backpropagation for output neuron
            outputNeurons.forEach(neuron -> neuron.backpropagate(hiddenOutputs, target));
        }
        return this;
    }


    @Override
    public double[] predict(double[] inputs) {
        double[] hiddenOutputs = IntStream.range(0, hiddenSize)
                .parallel()
                .mapToDouble(i -> hiddenNeurons.get(i).calculateOutput(inputs))
                .toArray();


        double[] output = new double[outputSize];
        for(int i = 0; i < outputSize; i++) {
            output[i] = outputNeurons.get(i).calculateOutput(hiddenOutputs);
        }

        return output;

    }


    @Override
    public void backpropagate(double[] inputs, double target) {
        for (int i = hiddenSize - 1; i >= 0; i--) {
            Neuron neuron = hiddenNeurons.get(i);
            neuron.backpropagate(inputs, target);
        }
    }

    @Override
    public NeuralNetwork evaluate(double[][] inputs, double[][] targets) {
        double correctPredictions = 0;
        double totalLoss = 0;

        for (int i = 0; i < inputs.length; i++) {
            double[] prediction = predict(inputs[i]);

            boolean isEqual = ProbabilityUtils.probabilityEquals(prediction, targets[i], 0.1d);
            if (isEqual) {
                correctPredictions++;
            }

            double loss = MultiClassificationLossFunctions.CATEGORICAL_CROSS_ENTROPY.calculateError(prediction, targets[i]);
            totalLoss += loss;
        }
        double accuracy = (correctPredictions / (double) inputs.length) * 100;
        double averageLoss = totalLoss / inputs.length;

        System.out.println("Network Accuracy: " + accuracy + "%");
        System.out.println("Binary cross entropy (loss) : " + averageLoss);

        return this;
    }



    private void probabilityEquals(double[] prediction, double[] target) {

    }
}
