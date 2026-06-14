import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;

public class DataSplitter {

    public static void generateSplitsIfMissing(CoreConfig config) throws Exception {
        // Check if the splits already exist so we don't waste time doing it twice
        File trainFile = new File(config.trainPath);
        if (trainFile.exists()) {
            System.out.println("Data splits already exist. Bypassing generation.");
            return;
        }

        System.out.println("Generating reproducible data splits from raw Kaggle data...");

        BufferedReader reader = new BufferedReader(new FileReader(config.rawSourcePath));
        PrintWriter trainWriter = new PrintWriter(config.trainPath);
        PrintWriter valWriter = new PrintWriter(config.validationPath);
        PrintWriter testWriter = new PrintWriter(config.testPath);

        // Read and copy the header to all three files
        String header = reader.readLine();
        trainWriter.println(header);
        valWriter.println(header);
        testWriter.println(header);

        // Initialize the seeded randomizer
        Random random = new Random(config.randomSeed);
        String line;
        int trainCount = 0, valCount = 0, testCount = 0;

        while ((line = reader.readLine()) != null) {
            double randVal = random.nextDouble(); // Generates a number between 0.0 and 1.0

            if (randVal < config.trainSplit) {
                trainWriter.println(line);
                trainCount++;
            } else if (randVal < (config.trainSplit + config.validationSplit)) {
                valWriter.println(line);
                valCount++;
            } else {
                testWriter.println(line);
                testCount++;
            }
        }

        reader.close();
        trainWriter.close();
        valWriter.close();
        testWriter.close();

        System.out.printf("Splits Generated: Train (%,d) | Validate (%,d) | Test (%,d)\n\n",
                trainCount, valCount, testCount);
    }
}