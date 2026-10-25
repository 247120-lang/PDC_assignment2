package Question5;

public class ParallelMaxSearch extends SensorSearchEngine {

    public ParallelMaxSearch(double[] sensorData, int startIdx, int endIdx) {
        super(sensorData, startIdx, endIdx);
        this.result = -Double.MAX_VALUE;
    }

    @Override
    public void processGrid() {
        double max = -Double.MAX_VALUE;
        for (int i = startIdx; i < endIdx; i++) {
            if (sensorData[i] > max) {
                max = sensorData[i];
            }
        }
        this.result = max;
    }
}
