package de.abramov.data;

import de.abramov.data.dto.RealEstate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TrainDataGenerator {
    private static final Logger logger = LoggerFactory.getLogger(TrainDataGenerator.class);
    private final Random rand = new Random();
    public List<RealEstate> getTrainData(int trainDataSize, boolean equalDistribution) {
        List<RealEstate> realEstateList = new ArrayList<>();
        IntStream.range(0, trainDataSize).forEach(i -> realEstateList.add(getRandomRealEstate(equalDistribution)));
        return realEstateList;
    }

    private RealEstate getRandomRealEstate(boolean equalDistribution) {
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
    public void printStatistics(List<RealEstate> realEstateList) {
        long count = realEstateList.stream().filter(RealEstate::isWorthwhile).count();
        logger.info("========================= Data Statistic ==================");
        logger.info("Amount of data " + realEstateList.size());
        logger.info("True positiv: " + count);
        logger.info("True negativ: " + (realEstateList.size() - count));
        logger.info("===========================================================");
    }
}
