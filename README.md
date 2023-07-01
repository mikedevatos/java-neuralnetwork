# Plain Java Implementation of a Neural Network.

## Detailed Description

It is a simple feed-forward network with backpropagation. As a activation function the sigmoid function is used. However I implemented LeakyRelu and Tanh as well. Binary Cross Entropy is used as a loss function.

 * Single Hidden Neuron layer (number of neurons are configurable)
 * Backpropagation, Feed Forward
 * Sigmoid, LeakyRelu, Tanh as activation functions
 * Binary Cross Entropy Loss function

## Usecase

A TrainDataGenerator generates a random dataset of train and test data with a given size. I use a simple RealEstate class here as a example. The network should predict if a house is expensive or not. The network is trained with the price and possible rent of a house. The network should predict if a house is expensive or not.

## Usage


This command will generate 10000 train and 1000 test data and train the network with it. Paramter true means that the generated data is equaly distributed.
```terminal 
    ./gradlew run --args='10000 1000 true'
``` 

## Run jUnit Tests

```terminal 
    ./gradlew test
```