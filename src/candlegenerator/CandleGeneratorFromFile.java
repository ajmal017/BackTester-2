package candlegenerator;

import struct.Candle;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CandleGeneratorFromFile implements CandleGenerator {

    String filePath;
    Scanner scanner;

    public CandleGeneratorFromFile(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        scanner = new Scanner(new File(this.filePath));
        start();
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNextLine();
    }

    @Override
    public Candle next() {
        String line = scanner.nextLine();
        String[] nums = line.split(",");
        double open = Double.parseDouble(nums[1]);
        double high = Double.parseDouble(nums[2]);
        double low = Double.parseDouble(nums[3]);
        double close = Double.parseDouble(nums[4]);
        Date date = null;
        try {
            //date = new SimpleDateFormat("yyyy-MM-dd").parse(nums[0]);
            date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(nums[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Candle(high, low, open, close, date);
    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {
        if(scanner != null) scanner.close();
    }
}
