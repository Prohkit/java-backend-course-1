package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task2 {
    public static void cloneFile(Path path) {
        Path absolutePath = path.toAbsolutePath();
        Path pathName = path.getFileName();
        String[] baseAndExtension = pathName.toString().split("\\.(?=[^\\.]+$)");
        final Path dir = absolutePath.getParent();
        int whichCopy = 0;
        boolean isFileCreated = false;
        while (!isFileCreated) {
            whichCopy++;
            Path pathNameCopy = getPathNameCopy(whichCopy, dir, baseAndExtension);
            isFileCreated = tryToCreateFile(pathNameCopy);
        }
    }

    private static Path getPathNameCopy(int whichCopy, Path dir, String[] baseAndExtension) {
        Path pathNameCopy;
        if (whichCopy == 1) {
            pathNameCopy = Path.of(dir + "\\" + baseAndExtension[0] + " - копия." + baseAndExtension[1]);
        } else {
            pathNameCopy =
                Path.of(dir + "\\" + baseAndExtension[0] + " - копия (" + whichCopy + ")." + baseAndExtension[1]);
        }
        return pathNameCopy;
    }

    private static boolean tryToCreateFile(Path pathNameCopy) {
        if (!Files.exists(pathNameCopy)) {
            try {
                Files.createFile(pathNameCopy);
                return true;
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return false;
    }
}
