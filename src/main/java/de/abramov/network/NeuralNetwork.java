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
    private final List<Neuron> neurons;

    public NeuralNetwork(Configuration configuration) {
        this.inputSize = configuration.inputSize;
        this.hiddenSize = configuration.hiddenSize;
        double learningRate = configuration.learningRate;

        neurons = new ArrayList<>();

        // Erstelle versteckte Neuronen
        for (int i = 0; i < hiddenSize; i++) {
            neurons.add(new Neuron(inputSize, learningRate, configuration.activationFunction));
        }

        // Erstelle Ausgabeneuron
        neurons.add(new Neuron(hiddenSize, learningRate, configuration.activationFunction));
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
                hiddenOutputs[i] = neurons.get(i).feedForward(inputs);
            }

            // Feedforward for output neuron
            neurons.get(hiddenSize).feedForward(hiddenOutputs);

            // Backpropagation for hidden neurons
            this.backpropagate(inputs, target);

            // Backpropagation for output neuron
            neurons.get(hiddenSize).backpropagate(hiddenOutputs, target);
        }
        return this;
    }


    @Override
    public double predict(RealEstate realEstate) {
        double[] inputs = { realEstate.getPrice(), realEstate.getRent() };

        double[] hiddenOutputs = IntStream.range(0, hiddenSize)
                .parallel()
                .mapToDouble(i -> neurons.get(i).feedForward(inputs))
                .toArray();

        return neurons.get(hiddenSize).feedForward(hiddenOutputs);
    }



    @Override
    public void backpropagate(double[] inputs, double target) {
        for (int i = hiddenSize - 1; i >= 0; i--) {
            Neuron neuron = neurons.get(i);
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
