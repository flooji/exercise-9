package tools;

public class Rating {
    private final String sourceAgentName;
    private final String targetAgentName;
    private final Double temperature;
    private final Double ratingValue;

    public Rating(String sourceAgentName, String targetAgentName, Double temperature, Double ratingValue) {
        this.sourceAgentName = sourceAgentName;
        this.targetAgentName = targetAgentName;
        this.temperature = temperature;
        this.ratingValue = ratingValue;
    }

    public String getSourceAgentName() {
        return sourceAgentName;
    }

    public String getTargetAgentName() {
        return targetAgentName;
    }

    public Double getRatingValue() {
        return ratingValue;
    }

    public Double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "Rating [sourceAgentName=" + sourceAgentName + ", temperature=" + temperature
                + ", ratingValue=" + ratingValue + "]";
    }
}
