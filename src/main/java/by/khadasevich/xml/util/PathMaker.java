package by.khadasevich.xml.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathMaker {
    // to override default constructor
    private PathMaker() {
        // no realisation needed
    }
    /**
     * Return path to file with fileName in sub resources folder.
     * @param fileName - file name
     * @param subFolderName - sub folder in Resources
     * @return path to file with fileName in sub resources folder
     * @throws IllegalArgumentException if not exist file with fileName
     * or folder with subFolder name
     */
    public static String makeToResourcesSubFolderPath(final String fileName,
                                                  final String subFolderName) {
        Path path;
        try {
            // get file name
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URI uri = loader.getResource(subFolderName).toURI();
            String mainPath = Paths.get(uri).toString();
            path = Paths.get(mainPath, fileName);
        } catch (URISyntaxException e) {
            String message = String.format("Can't read file: %s", fileName);
            System.err.println(message);
            throw new IllegalArgumentException(message);
        }
        return path.toString();
    }
}
