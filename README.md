# Plain Java Implementation of a Neural Network.

## Detailed Description

It is a simple feed-forward network with backpropagation. As a activation function the sigmoid function is used. However I implemented LeakyRelu and Tanh as well. Binary Cross Entropy is used as a loss function.

 * Single Hidden Neuron layer (number of neurons are configurable)
 * Backpropagation, Feed Forward
 * Sigmoid, LeakyRelu, Tanh as activation functions
 * Binary Cross Entropy Loss function

 ![Neural Network](https://upload.wikimedia.org/wikipedia/commons/9/99/Neural_network_example.svg)


## Use-Case

A TrainDataGenerator generates a random dataset of train and test data with a given size. I use a simple RealEstate class here as a example. The network should predict if a house is expensive or not. The network is trained with the price and possible rent of a house. 

## Usage
This command will generate 10000 train and 1000 test data and train the network with it. Paramter true means that the generated data is equaly distributed.
```bash 
    ./gradlew run --args='10000 1000 true'
``` 

### Output
    ========================= Data Statistic ==================
    Amount of data 10000
    True positiv: 5065
    True negativ: 4935
    ===========================================================
    Network Accuracy: 100.0%
    Binary cross entropy (loss) : 0.02577636346411718

## Configuration of the network

There is a Configuration class where you can configure the network. Parameter you can configure are:

   * inputSize: Size of the input layer (number of features, in my case with RealEstate it is 2 as I use price and rent)
   * hiddenSize: Size of the hidden layer (number of neurons)
   * learningRate: Learning rate of the network
   * activationFunction: Activation function of the network.

```java
    Configuration configuration = new Configuration(2, 3, 0.1, ActivationFunction.SIGMOID);
```


### Usage as docker container
    
```bash
    docker compose up
```

## Run jUnit Tests

```bash 
    ./gradlew test
```


# Technical Details

* Java 17
