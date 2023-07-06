package de.abramov.data.provider;

import de.abramov.data.dto.RealEstate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class RealEstateProvider implements IDataProvider<RealEstate> {
    private static final Logger logger = LoggerFactory.getLogger(RealEstateProvider.class);
    private final Random rand = new Random();
    private final List<RealEstate> trainData;
    private final List<RealEstate> testData;
    private final ArrayList<RealEstate> dataset;

    public RealEstateProvider(int dataSetSize, boolean equalDistribution, double trainTestSplitRatio) {
        dataset = new ArrayList<>();

        IntStream.range(0, dataSetSize).forEach(i -> dataset.add(getRandom(true)));
        int trainDataset = (int) (dataSetSize * trainTestSplitRatio);
        int testDataset = dataSetSize - trainDataset;

        trainData = dataset.subList(0, trainDataset);
        testData = dataset.subList(trainDataset, trainDataset + testDataset);

    }

    @Override
    public List<RealEstate> getTrainData() {
        return trainData;
    }

    @Override
    public List<RealEstate> getTestData() {
        return testData;
    }

    @Override
    public RealEstate getRandom(boolean equalDistribution) {
        Random random = new Random();
        double rent = random.nextDouble(450, 1850);
        double price =0;
        if (equalDistribution) {
            if (random.nextBoolean()) {
                /*worthhile*/
                double minPriceToBeWorthwhile = (rent * 12) * 16;
                double percentage = (rand.nextInt(20) + 1) / 100.0;
                price = minPriceToBeWorthwhile * (1 - percentage);
            }else{
                /*not worthwhile*/
                double minPriceToBeWorthwhile = (rent * 12) * 16;
                double percentage = 1 + rand.nextInt(20) / 100.0;
                price = minPriceToBeWorthwhile * percentage;
            }
        }
        return new RealEstate(price, rent);
    }

    @Override
    public double[][] getFeatures() {
        double[][] features = new double[trainData.size()][2];
        for (int i = 0; i < trainData.size(); i++) {
            features[i][0] = trainData.get(i).getPrice();
            features[i][1] = trainData.get(i).getRent();
        }
        return features;
    }

    @Override
    public double[][] getLabels() {
        double[][] labels = new double[trainData.size()][1];
        for (int i = 0; i < trainData.size(); i++) {
            labels[i][0] = trainData.get(i).isWorthwhile() ? 1 : 0;
        }
        return labels;
    }

    @Override
    public double[][] getFeaturesTest() {
        double[][] features = new double[testData.size()][2];
        for (int i = 0; i < testData.size(); i++) {
            features[i][0] = testData.get(i).getPrice();
            features[i][1] = testData.get(i).getRent();
        }
        return features;
    }

    @Override
    public double[][] getLabelsTest() {
        double[][] labels = new double[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            labels[i][0] = testData.get(i).isWorthwhile() ? 1 : 0;
        }
        return labels;
    }

    @Override
    public String getDescription() {
        return "RealEstate Dataset";
    }

    public void printStatistics() {
        long count = dataset.stream().filter(RealEstate::isWorthwhile).count();
        logger.info("========================= Data Statistic ==================");
        logger.info("Amount of data " + dataset.size());
        logger.info("True positiv: " + count);
        logger.info("True negativ: " + (dataset.size() - count));
        logger.info("===========================================================");
    }
}
