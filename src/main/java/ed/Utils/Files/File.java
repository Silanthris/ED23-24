package ed.Utils.Files;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;

/**
 * The `File` class represents the file operations used in the application.
 *
 * @author Daniel Faria & Adélio Carneiro
 * @version 1.0
 */
public class File {
    /**
     * This method returns the absolute path of a file from the resources folder.
     *
     * @param fileName The name of the file
     * @return The absolute path of the file
     */
    private static URL getFileURLFromResources(String fileName) {
        return File.class.getClassLoader().getResource(fileName);
    }

    /**
     * This method returns the absolute path of a file from the resources folder.
     *
     * @param fileURL The name of the file
     * @return The absolute path of the file
     * @throws FileNotFoundException If the file is not found
     */
    private static String getAbsolutePathFromURL(URL fileURL) throws FileNotFoundException {
        if (Objects.isNull(fileURL)) {
            throw new FileNotFoundException("File not found!");
        }

        return fileURL.getPath();
    }

    /**
     * This method returns the absolute path of a file from the resources folder.
     *
     * @param fileName The name of the file
     * @return The absolute path of the file
     * @throws FileNotFoundException If the file is not found
     */
    public static String getPathFromResources(String fileName) throws FileNotFoundException {
        URL fileURL = getFileURLFromResources(fileName);
        String absoluteFilePath = getAbsolutePathFromURL(fileURL);

        if (Objects.isNull(absoluteFilePath)) {
            throw new FileNotFoundException("The following file was not found -> " + fileName);
        }

        return absoluteFilePath;
    }

    /**
     * This method returns the absolute path of a file from the resources folder.
     *
     * @param fileURL The url of the file
     * @return The absolute path of the file
     * @throws FileNotFoundException If the file is not found
     */
    private static String getAbsoluteFilePathFromURL(URL fileURL) throws FileNotFoundException {
        if (Objects.isNull(fileURL)) {
            throw new FileNotFoundException("File not found!");
        }

        return fileURL.getFile();
    }

    /**
     * This method returns the absolute path of a file from the resources folder.
     *
     * @param fileName The name of the file
     * @return The absolute path of the file
     * @throws FileNotFoundException If the file is not found
     */
    public static java.io.File getFileFromResources(String fileName) throws FileNotFoundException {
        URL fileURL = getFileURLFromResources(fileName);
        String absoluteFilePath = getAbsoluteFilePathFromURL(fileURL);

        if (Objects.isNull(absoluteFilePath)) {
            throw new FileNotFoundException("The following file was not found -> " + fileName);
        }

        return new java.io.File(absoluteFilePath);
    }
}
