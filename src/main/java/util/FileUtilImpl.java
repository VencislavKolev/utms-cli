package util;

import java.io.File;
import java.io.FileNotFoundException;

import static common.GlobalConstants.NOT_FOUND_FILE;

public class FileUtilImpl implements FileUtil {

    public FileUtilImpl() {
    }

    @Override
    public boolean checkIfExists(String filepath) {
        File file = new File(filepath);
        return file.isFile();
    }


    @Override
    public File getFile(String filepath) throws FileNotFoundException {
        boolean exists = this.checkIfExists(filepath);

        if (!exists) {
            throw new FileNotFoundException(NOT_FOUND_FILE);
        }
        return new File(filepath);
    }

}
