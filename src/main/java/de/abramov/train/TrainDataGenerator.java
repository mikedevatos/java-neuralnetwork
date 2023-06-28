package de.abramov.train;

import de.abramov.train.data.RealEstate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TrainDataGenerator {

    public List<RealEstate> getTrainData(int trainDataSize, boolean equalDistribution) {
        List<RealEstate> realEstateList = new ArrayList<>();
        IntStream.range(0, trainDataSize).forEach(i -> realEstateList.add(getRandomRealEstate(equalDistribution)));
        return realEstateList;

    }

    private RealEstate getRandomRealEstate(boolean equalDistribution) {
        Random random = new Random();
        double rent = random.nextInt(10000);
        double price =0;
        if (equalDistribution) {
            if (random.nextBoolean()) {
                /*worthhile*/
                price = (rent * 12) * 16;
                price = price - (price * random.nextDouble());
            }else{
                /*not worthwhile*/
                price = (rent * 12) * 16;
                price = price + (price * random.nextDouble());
            }
        }
        return new RealEstate(price, rent);
    }


    public void printStatistics(List<RealEstate> realEstateList) {
        long count = realEstateList.stream().filter(RealEstate::isWorthwhile).count();
        System.out.println("========================= Data Statistic ==================");
        System.out.println("Anzahl der Immobilien: " + realEstateList.size());
        System.out.println("Anzahl der Immobilien die sich lohnen: " + count);
        System.out.println("Anzahl der Immobilien die sich nicht lohnen: " + (realEstateList.size() - count));
        System.out.println("===========================================================");
    }
}
