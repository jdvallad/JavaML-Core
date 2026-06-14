import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class StreamingIterator {
    public int batchSize;
    private BufferedReader reader;
    private String filePath;
    private DataParser<String> parser;
    private boolean hasHeader;

    public StreamingIterator(int batchSize, String filePath, boolean hasHeader, DataParser<String> parser)
            throws Exception {
        this.batchSize = batchSize;
        this.filePath = filePath;
        this.hasHeader = hasHeader;
        this.parser = parser; // Inject the pre-scanned parser

        reset();
    }

    public DataPair[] nextBatch() throws Exception {
        List<DataPair> batch = new ArrayList<>();
        String line;

        while (batch.size() < batchSize && (line = reader.readLine()) != null) {
            batch.add(parser.parse(line));
        }

        return batch.toArray(new DataPair[0]);
    }

    public void reset() throws Exception {
        if (this.reader != null) {
            this.reader.close();
        }
        this.reader = new BufferedReader(new FileReader(filePath));

        // Skip the first line if the dataset has column headers
        if (this.hasHeader) {
            this.reader.readLine();
        }
    }

    public boolean hasNextBatch() throws Exception {
        return reader != null && reader.ready();
    }

    // Restored for your out-of-sample prediction showcase
    public DataPair[] get(int startIndex, int endIndex) throws Exception {
        reset();
        DataPair[] output = new DataPair[endIndex - startIndex];

        for (int i = 0; i < startIndex; i++) {
            reader.readLine();
        }

        for (int i = startIndex; i < endIndex; i++) {
            output[i - startIndex] = parser.parse(reader.readLine());
        }

        reset();
        return output;
    }
}