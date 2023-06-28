package de.abramov.network;

import de.abramov.network.neuron.Neuron;
import de.abramov.train.data.RealEstate;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork implements INeuralNetwork {
    private final int inputSize;
    private final int hiddenSize;
    private List<Neuron> neurons;

    public NeuralNetwork(int inputSize, int hiddenSize, double learningRate) {
        this.inputSize = inputSize;
        this.hiddenSize = hiddenSize;

        neurons = new ArrayList<>();

        // Erstelle versteckte Neuronen
        for (int i = 0; i < hiddenSize; i++) {
            neurons.add(new Neuron(inputSize, learningRate));
        }

        // Erstelle Ausgabeneuron
        neurons.add(new Neuron(hiddenSize, learningRate));
    }

    @Override
    public void train(List<RealEstate> trainingData) {
        for (RealEstate realEstate : trainingData) {
            double[] inputs = new double[inputSize];
            inputs[0] = realEstate.getPrice();
            inputs[1] = realEstate.getRent();
            double target = realEstate.isWorthwhile() ? 1.0 : 0.0;

            // Feedforward
            double[] hiddenOutputs = new double[hiddenSize];
            for (int i = 0; i < hiddenSize; i++) {
                hiddenOutputs[i] = neurons.get(i).feedForward(inputs);
            }

            // Feedforward für Ausgabeneuron
            double output = neurons.get(hiddenSize).feedForward(hiddenOutputs);

            // Backpropagation für Ausgabeneuron
            neurons.get(hiddenSize).train(hiddenOutputs, target);

            // Backpropagation für versteckte Neuronen
            for (int i = hiddenSize - 1; i >= 0; i--) {
                Neuron neuron = neurons.get(i);
                neuron.train(inputs, target);
            }
        }
    }


    @Override
    public double predict(RealEstate realEstate) {
        double[] inputs = { realEstate.getPrice(), realEstate.getRent() };

        // Feedforward
        double[] hiddenOutputs = new double[hiddenSize];
        for (int i = 0; i < hiddenSize; i++) {
            hiddenOutputs[i] = neurons.get(i).feedForward(inputs);
        }

        // Rückgabe der Vorhersage des Ausgabeneurons
        return neurons.get(hiddenSize).feedForward(hiddenOutputs);
    }


    @Override
    public void backpropagate(double[] inputs, double target) {
        for (int i = neurons.size() - 1; i >= 0; i--) {
            Neuron neuron = neurons.get(i);
            neuron.backpropagate(inputs, target);
        }
    }

    @Override
    public double evaluate(List<RealEstate> testData) {
        int correctPredictions = 0;
        for (RealEstate realEstate : testData) {
            double prediction = predict(realEstate);
            boolean predictedIsWorthwhile = prediction >= 0.5;
            boolean actualIsWorthwhile = realEstate.isWorthwhile();

            if (predictedIsWorthwhile == actualIsWorthwhile) {
                correctPredictions++;
            }
        }

        return (double) correctPredictions / testData.size();
    }

    private double calculateError(double[] nextLayerWeights, double[] layerOutputs, int currentIndex, double target) {
        double sum = 0.0;
        for (int i = 0; i < nextLayerWeights.length; i++) {
            sum += nextLayerWeights[i] * layerOutputs[currentIndex + i + 1];
        }
        return sum * (layerOutputs[currentIndex] * (1 - layerOutputs[currentIndex])) * (target - layerOutputs[currentIndex]);
    }
}
