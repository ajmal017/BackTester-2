package data;

import java.io.*;
import java.util.Scanner;

public class FileDataCleaner {
    String inputFilePath;
    String outputFilePath;
    Scanner inputScanner;
    BufferedWriter outputWriter;

    public FileDataCleaner(String inputFilePath, String outputFilePath) throws IOException {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        inputScanner = new Scanner(new File(this.inputFilePath));
        outputWriter = new BufferedWriter(new FileWriter(this.outputFilePath));
        start();
        shutdown();
    }

    public void start() throws IOException {
        while(inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();
            if(!containsNull(line)) {
                outputWriter.write(line);
                outputWriter.newLine();
            }
        }
    }

    public void shutdown() throws IOException {
        if(inputScanner != null) inputScanner.close();
        if(outputWriter != null) outputWriter.close();
    }

    public boolean containsNull(String line) {
        return line.contains("null") || line.contains("Null") || line.contains("NULL");
    }
}
