package util;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileUtil {
    boolean checkIfExists(String filepath);

    File getFile(String filepath) throws FileNotFoundException;
}
