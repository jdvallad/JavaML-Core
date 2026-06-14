public interface DataParser<T> {

    // Forces the parser to compute any necessary stats or one-hot dictionaries
    void scanMetadata() throws Exception;

    // Forces the parser to convert raw data into your neural network's required
    // format
    DataPair parse(T rawData) throws Exception;
}