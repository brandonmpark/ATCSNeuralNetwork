# XOR Neural Network in Java
#### Brandon Park
#### Dr. Nelson
#### ATCS: Neural Networks (Period 3)
#### October 15, 2021

# Usage Instructions
## Configuration
All configuration options can be set in a JSON file passed as a command line option when running the program. These can be placed in the `src/config/` directory. The program comes with a default configuration file and presets for and, or, and xor. The default configuration file, `src/config/defaultConfig.json` should **not** be edited without caution. A list of configuration options can be found below.

## Testing/Training Sets
The first line of any training or testing file should contain the number of sets in the file. Each successive line in a testing file should contain a single set, formatted as `input1 input2`. For training files, each line should be formatted as `input1 input2 output`.

## Configuration Options
### General Network Configuration
- *inputNodes*: number of input nodes in the network (positive integer)
- *hiddenNodes*: number of hidden nodes in the network (positive integer)
- *outputNodes*: number of output nodes in the network (positive integer)
### Running
- *useWeightsFile*: whether to use a weights file or not (boolean)
- *weightsFilePath*: if useWeightsFile is true; file path of the weights file (file path)
- *useTestingFile*: whether to use a testing file or not (boolean)
- *testingFilePath*: if useTestingFile is true; file path of the testing file (file path)
### Training
- *lambda*: the learning rate (positive double)
- *maxIterations*: the maximum number of training cycles (positive integer)
- *errorThreshold*: the goal threshold to be met (positive double)
- *minRandom*: the lower bound of random generation for weights (double)
- *maxRandom*: the upper bound of random generation for weights (double)
- *useTrainingFile*: whether to use a training file or not (boolean)
- *trainingFilePath*: if useTrainingFile is true; file path of the training file (file path)
- *saveWeights*: whether to save weights to a file or not (boolean)
- *savedWeightsFilePath*: if saveWeights is true; file path of the file where weights should be saved (file path)


