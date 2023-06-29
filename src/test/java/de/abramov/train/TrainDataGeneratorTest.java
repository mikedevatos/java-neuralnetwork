package de.abramov.train;

import de.abramov.train.data.RealEstate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainDataGeneratorTest {
    private TrainDataGenerator trainDataGenerator;

    @BeforeEach
    public void setUp() {
        trainDataGenerator = new TrainDataGenerator();
    }

    @Test
    public void testGetTrainDataSize() {
        List<RealEstate> realEstates = trainDataGenerator.getTrainData(5, false);
        assertEquals(5, realEstates.size(), "The size of the list should be equal to the input trainDataSize.");
    }

    @Test
    public void testGetTrainDataWorthwhileDistribution() {
        List<RealEstate> realEstates = trainDataGenerator.getTrainData(100, true);
        long count = realEstates.stream().filter(RealEstate::isWorthwhile).count();
        assertTrue(count >= 40 && count <= 60, "The number of worthwhile real estates should be roughly half in case of equal distribution.");
    }

    @Test
    public void testGetTrainDataRentRange() {
        List<RealEstate> realEstates = trainDataGenerator.getTrainData(5, false);
        realEstates.forEach(realEstate -> assertTrue(realEstate.getRent() >= 450 && realEstate.getRent() <= 1850, "The rent should be within the specified range."));
    }

    @Test
    public void testGetTrainDataPrice() {
        List<RealEstate> realEstates = trainDataGenerator.getTrainData(5, true);
        realEstates.forEach(realEstate -> {
            double expectedMinPrice = (realEstate.getRent() * 12) * 16;
            assertTrue(realEstate.getPrice() >= expectedMinPrice * 0.8 && realEstate.getPrice() <= expectedMinPrice * 1.2, "The price should be within the expected range based on the rent.");
        });
    }
}
