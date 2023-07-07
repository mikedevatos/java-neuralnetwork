# Plain Java Implementation of a Neural Network.

## Detailed Description

It is a simple Multi Layer Perceptron. As a activation functions I implemented Sigmoid, LeakyRelu, Softmax.

 * Single Hidden Neuron layer (number of neurons are configurable)
 * Backpropagation, Feed Forward
 * Sigmoid, LeakyRelu, Tanh as activation functions. Sigmoid is used in the example.
 * Categorical Cross Entropy Loss function for multi class classification.

 ![Neural Network](https://hc-linux.eu/github/iris-nn.png)


## How it works.

So far I implemented two DataProvider. A DataProvider is responsible for providing the data for the network. 
 * RealEstateDataProvider: This DataProvider generates random data for the network. This is a binary classification problem. The network should predict if a real estate is a good investment or not. Even with Categorical Cross Entropy as loss function the network is able to solve this problem.
 * IrisDataProvider: This DataProvider loads the Iris dataset from a csv file. A small classic dataset from Fisher, 1936. One of the earliest datasets used for evaluation of classification methodologies. This is a three class problem. [Iris Dataset](https://archive.ics.uci.edu/ml/datasets/iris)


## Usage

### Training with IRIS Dataset
    private void run() {
        var datasetProvider = getDataProvider(DataProvider.IRIS);

```
INFO  IrisDataUtil - Dataset normalized
INFO  IrisDataUtil - Dataset shuffled
INFO  IrisProvider - ========================= Data Statistic ==================
INFO  IrisProvider - Total dataset size: 150
INFO  IrisProvider - Training dataset size: 120
INFO  IrisProvider - Test data set size: 30
INFO  IrisProvider - Classes: 3
INFO  IrisProvider - ===========================================================
INFO  NeuralNetwork - Epoch: 0 Accuracy: 83,33%
INFO  NeuralNetwork - Epoch: 2 Accuracy: 96,67%
INFO  NeuralNetwork - Epoch: 4 Accuracy: 93,33%
```
### Training with RealEstate Dataset
    private void run() {
        var datasetProvider = getDataProvider(DataProvider.REAL_ESTATE);
```
INFO  NeuralNetwork - Epoch: 0 Accuracy: 100,00%
INFO  NeuralNetwork - Epoch: 2 Accuracy: 100,00%
INFO  NeuralNetwork - Epoch: 4 Accuracy: 100,00%
```
### Your Own DataProvider
You can implement your own DataProvider. Just implement the IDataProvider interface and provide your data to the network.


## Configuration of the network

There is a Configuration class where you can configure the network.

        List<Integer> hiddenLayersSize = List.of(12,6); // 2 hidden layers with 12 and 6 neurons

        var neuralNetworkConfiguration = new Configuration(inputSize, hiddenLayersSize, outputSize, 0.01, 1000, ActivationFunction.LEAKY_RELU, ActivationFunction.SOFTMAX, LossFunction.CATEGORICAL_CROSS_ENTROPY);

   * inputSize: Size of the input layer (number of features, in my case with RealEstate it is 2 as I use price and rent)
   * hiddenLayersSize: Number of Hidden Layers and the size of each layer
   * outputSize: Size of the output layer (number of classes)
   * learningRate: Learning rate of the network
   * epochs: Number of epochs
   * activationFunction: Activation function of the hidden layer
   * outputActivationFunction: Activation function of the output layer
   * lossFunction: Loss function of the network

```example
     new Configuration(4, 128, outputSize, 0.01, 6,ActivationFunction.LEAKY_RELU,  ActivationFunction.SOFTMAX, LossFunction.CATEGORICAL_CROSS_ENTROPY);
```
This will create a network with 4 input neurons, 128 hidden neurons, 3 output neurons, learning rate 0.01, 6 epochs, LeakyRelu as activation function for the hidden layer, Softmax as activation function for the output layer and Categorical Cross Entropy as loss function.

### Usage as docker container
    
```bash
    docker compose up
```

## Run jUnit Tests

```bash 
    ./gradlew test
```
