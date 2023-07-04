package de.abramov.network;

import de.abramov.network.configuration.Configuration;
import de.abramov.network.neuron.Neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class NeuralNetwork implements INeuralNetwork {
    private final int inputSize;
    private final int hiddenSize;
    private final List<Neuron> hiddenNeurons;

    public NeuralNetwork(Configuration configuration) {
        this.inputSize = configuration.inputSize;
        this.hiddenSize = configuration.hiddenSize;
        double learningRate = configuration.learningRate;

        hiddenNeurons = new ArrayList<>();

        // create hidden neurons
        for (int i = 0; i < hiddenSize; i++) {
            hiddenNeurons.add(new Neuron(inputSize, learningRate, configuration.activationFunction));
        }

        // create output neuron
        hiddenNeurons.add(new Neuron(hiddenSize, learningRate, configuration.activationFunction));
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
            hiddenNeurons.get(hiddenSize).backpropagate(hiddenOutputs, target);
        }
        return this;
    }


    @Override
    public double predict(double[] inputs) {


        double[] hiddenOutputs = IntStream.range(0, hiddenSize)
                .parallel()
                .mapToDouble(i -> hiddenNeurons.get(i).calculateOutput(inputs))
                .toArray();

        return hiddenNeurons.get(hiddenSize).calculateOutput(hiddenOutputs);
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
            double prediction = predict(inputs[i]);
            boolean predictedIsWorthwhile = prediction >= 0.5;
            boolean actualIsWorthwhile = targets[i][0] == 1.0d;

            if (predictedIsWorthwhile == actualIsWorthwhile) {
                correctPredictions++;
            }

            // transform boolean to double
            double actualValue = actualIsWorthwhile ? 1.0 : 0.0;

            // Binary Cross-Entropy Loss calculation
            double loss = -actualValue * Math.log(prediction) - (1 - actualValue) * Math.log(1 - prediction);
            totalLoss += loss;
        }
        //<-
        double accuracy = (correctPredictions / (double) inputs.length) * 100;
        double averageLoss = totalLoss / inputs.length;

        System.out.println("Network Accuracy: " + accuracy + "%");
        System.out.println("Binary cross entropy (loss) : " + averageLoss);

        return this;
    }
}
