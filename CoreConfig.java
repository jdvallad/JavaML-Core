public abstract class CoreConfig {

    // --- HYPERPARAMETERS ---
    public int batchSize = 128;
    public double learningRate = 0.001;
    public double decayRate = 0.995;
    public int epochs = 10;

    // --- ARCHITECTURE ---
    public int[] nodes;
    public String[] activations;
    public String costFunction;

    // --- Artifact Management ---
    public String loadModelPath;
    public String saveModelPath;

    // --- SPLIT SETTINGS ---
    public long randomSeed = 42L;
    public double trainSplit = 0.80;
    public double validationSplit = 0.10;

    // --- GENERIC DATA PIPELINE PATHS ---
    // The core library expects these to exist, but doesn't care what they are
    public String rawSourcePath;
    public String trainPath;
    public String validationPath;
    public String testPath;
    public boolean hasHeader = true;

    public CoreConfig() {

    }
}