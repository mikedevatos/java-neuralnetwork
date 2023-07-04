package de.abramov.network;

import de.abramov.network.configuration.Configuration;
import de.abramov.network.neuron.Neuron;
import de.abramov.train.data.RealEstate;

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
    public NeuralNetwork train(List<RealEstate> trainingData) {
        for (RealEstate realEstate : trainingData) {
            double[] inputs = new double[inputSize];
            inputs[0] = realEstate.getPrice();
            inputs[1] = realEstate.getRent();
            double target = realEstate.isWorthwhile() ? 1.0 : 0.0;

            // Feedforward
            double[] hiddenOutputs = new double[hiddenSize];
            for (int i = 0; i < hiddenSize; i++) {
                hiddenOutputs[i] = hiddenNeurons.get(i).calculateOutput(inputs);
            }

            // Backpropagation for hidden neurons
            this.backpropagate(inputs, target);

            // Backpropagation for output neuron
            hiddenNeurons.get(hiddenSize).backpropagate(hiddenOutputs, target);
        }
        return this;
    }


    @Override
    public double predict(RealEstate realEstate) {
        double[] inputs = { realEstate.getPrice(), realEstate.getRent() };

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
    public NeuralNetwork evaluate(List<RealEstate> testData) {
        double correctPredictions = 0;
        double totalLoss = 0;

        for (RealEstate realEstate : testData) {
            double prediction = predict(realEstate);
            boolean predictedIsWorthwhile = prediction >= 0.5;
            boolean actualIsWorthwhile = realEstate.isWorthwhile();

            if (predictedIsWorthwhile == actualIsWorthwhile) {
                correctPredictions++;
            }

            // transform boolean to double
            double actualValue = actualIsWorthwhile ? 1.0 : 0.0;

            // Binary Cross-Entropy Loss calculation
            double loss = -actualValue * Math.log(prediction) - (1 - actualValue) * Math.log(1 - prediction);
            totalLoss += loss;
        }

        double accuracy = (correctPredictions / (double) testData.size()) * 100;
        double averageLoss = totalLoss / testData.size();

        System.out.println("Network Accuracy: " + accuracy + "%");
        System.out.println("Binary cross entropy (loss) : " + averageLoss);

        return this;
    }
}
