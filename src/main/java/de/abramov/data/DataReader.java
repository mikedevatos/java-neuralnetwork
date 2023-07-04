package de.abramov.data;

import java.io.File;
import java.util.List;

public interface DataReader {
    List<String[]> readFile(File file, char separator);
}
