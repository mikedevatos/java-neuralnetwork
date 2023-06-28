package de.abramov;

import de.abramov.network.INeuralNetwork;
import de.abramov.network.NeuralNetwork;
import de.abramov.train.TrainDataGenerator;
import de.abramov.train.data.RealEstate;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        int trainDataSize = 100000;
        int testDataSize = 100;
        boolean equalDistribution = true;
        TrainDataGenerator testDataGenerator = new TrainDataGenerator();
        List<RealEstate> trainRealEstateDataset = testDataGenerator.getTrainData(trainDataSize, equalDistribution);
        List<RealEstate> testRealEstateDataset = testDataGenerator.getTrainData(testDataSize, equalDistribution);
        testDataGenerator.printStatistics(trainRealEstateDataset);

        // Erstelle das neuronale Netzwerk
        int inputSize = 2; // Preis und Miete
        int hiddenSize = 2048; // Anzahl der versteckten Neuronen
        double learningRate = 0.1;
        INeuralNetwork neuralNetwork = new NeuralNetwork(inputSize, hiddenSize, learningRate);

        // Trainiere das Netzwerk
        neuralNetwork.train(trainRealEstateDataset);

        // Evaluierung des Netzwerks
        double accuracy = neuralNetwork.evaluate(testRealEstateDataset);
        System.out.println("Genauigkeit des neuronalen Netzwerks: " + accuracy);

        // Beispiel-Vorhersage für ein RealEstate-Objekt
        RealEstate exampleRealEstateGood = new RealEstate(100000, 1000); // Beispielobjekt mit Preis und Miete
        RealEstate exampleRealEstateBad = new RealEstate(10000000, 10000); // Beispielobjekt mit Preis und Miete
        double predictionOfGoodEstate = neuralNetwork.predict(exampleRealEstateGood);
        double predictionofBadEstate = neuralNetwork.predict(exampleRealEstateBad);
        System.out.println("Vorhersage für das Beispielobjekt: " + predictionOfGoodEstate + " real estate is worthwhile: "+exampleRealEstateGood.isWorthwhile());
        System.out.println("Vorhersage für das Beispielobjekt: " + predictionofBadEstate + " real estate is worthwhile: "+exampleRealEstateBad.isWorthwhile());
    }

}
