package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task2 {
    public void cloneFile(Path copyFrom) {
        Path absolutePath = copyFrom.toAbsolutePath();
        Path pathName = copyFrom.getFileName();
        String[] baseAndExtension = pathName.toString().split("\\.(?=[^\\.]+$)");
        final Path dir = absolutePath.getParent();
        int whichCopy = 0;
        boolean isFileCreated = false;
        while (!isFileCreated) {
            whichCopy++;
            Path copyTo = getPathNameCopy(whichCopy, dir, baseAndExtension);
            isFileCreated = tryToCreateFile(copyFrom, copyTo);
        }
    }

    private Path getPathNameCopy(int whichCopy, Path dir, String[] baseAndExtension) {
        Path pathNameCopy;
        if (whichCopy == 1) {
            pathNameCopy = Path.of(dir + "\\" + baseAndExtension[0] + " - копия." + baseAndExtension[1]);
        } else {
            pathNameCopy =
                Path.of(dir + "\\" + baseAndExtension[0] + " - копия (" + whichCopy + ")." + baseAndExtension[1]);
        }
        return pathNameCopy;
    }

    private boolean tryToCreateFile(Path copyFrom, Path copyTo) {
        if (!Files.exists(copyTo)) {
            try {
                Files.copy(copyFrom, copyTo);
                return true;
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return false;
    }
}
