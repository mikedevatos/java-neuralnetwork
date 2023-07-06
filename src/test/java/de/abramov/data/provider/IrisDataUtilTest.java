package de.abramov.data.provider;

import static org.junit.jupiter.api.Assertions.*;

import de.abramov.data.CSVDataReader;
import de.abramov.data.DataReader;
import de.abramov.data.dto.Iris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IrisDataUtilTest {

    private DataReader dataReader;

    @TempDir
    File tempDir;

    @BeforeEach
    void setUp() {
        this.dataReader = new CSVDataReader();
    }

    @Test
    void loadIrisDataSetTest() throws IOException {
        File testFile = new File(tempDir, "test-iris.csv");
        FileWriter fileWriter = new FileWriter(testFile);
        fileWriter.write("5.1,3.5,1.4,0.2,Setosa\n");
        fileWriter.write("7.0,3.2,4.7,1.4,Versicolor\n");
        fileWriter.write("6.3,3.3,6.0,2.5,Virginica\n");
        fileWriter.close();

        List<Iris> dataSet = IrisDataUtil.loadIrisDataSet(false, false);

        assertNotNull(dataSet);
        assertEquals(3, dataSet.size());
        assertEquals("Setosa", dataSet.get(0).variety);
        assertEquals("Versicolor", dataSet.get(1).variety);
        assertEquals("Virginica", dataSet.get(2).variety);
    }

    @Test
    void normalizeTest() throws IOException {
        File testFile = new File(tempDir, "test-iris.csv");
        FileWriter fileWriter = new FileWriter(testFile);
        fileWriter.write("5.1,3.5,1.4,0.2,Setosa\n");
        fileWriter.write("7.0,3.2,4.7,1.4,Versicolor\n");
        fileWriter.write("6.3,3.3,6.0,2.5,Virginica\n");
        fileWriter.close();

        List<Iris> dataSet = IrisDataUtil.loadIrisDataSet(true, false);

        assertNotNull(dataSet);
        assertEquals(3, dataSet.size());

        for(Iris iris : dataSet) {
            assertTrue(iris.sepalLength >= 0 && iris.sepalLength <= 1);
            assertTrue(iris.sepalWidth >= 0 && iris.sepalWidth <= 1);
            assertTrue(iris.petalLength >= 0 && iris.petalLength <= 1);
            assertTrue(iris.petalWidth >= 0 && iris.petalWidth <= 1);
        }
    }

    // Similar tests can be written for getInputs, getTargets and split methods.
}
