package de.abramov.train;

import de.abramov.train.data.RealEstate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TrainDataGenerator {
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
        System.out.println("========================= Data Statistic ==================");
        System.out.println("Amount of data " + realEstateList.size());
        System.out.println("True positiv: " + count);
        System.out.println("True negativ: " + (realEstateList.size() - count));
        System.out.println("===========================================================");
    }
}
