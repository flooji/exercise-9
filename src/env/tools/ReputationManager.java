package tools;

import cartago.*;

import java.util.*;

public class ReputationManager extends Artifact {
    Map<String, List<Rating>> interactionTrustRatingsPerAgent = new HashMap<>();
    Map<String, List<Rating>> certificationTrustRatingsPerAgent = new HashMap<>();
    Map<String, List<Rating>> witnessRatingsPerAgent = new HashMap<>();

    @OPERATION
    public void addInteractionTrustRating(Object sourceAgent, Object targetAgent, Object temperature, Object receivedRating) {
        Rating rating = createRating(sourceAgent, targetAgent, temperature, receivedRating);
        addRating(rating, interactionTrustRatingsPerAgent);
    }

    @OPERATION
    public void addCertificationTrustRating(Object sourceAgent, Object targetAgent, Object temperature, Object receivedRating) {
        Rating rating = createRating(sourceAgent, targetAgent, temperature, receivedRating);
        addRating(rating, certificationTrustRatingsPerAgent);
    }

    @OPERATION
    public void addWitnessRating(Object sourceAgent, Object targetAgent, Object temperature, Object receivedRating) {
        Rating rating = createRating(sourceAgent, targetAgent, temperature, receivedRating);
        addRating(rating, witnessRatingsPerAgent);
    }

    private static Rating createRating(Object sourceAgent, Object targetAgent, Object temperature, Object receivedRating) {
        String sourceAgentName = sourceAgent.toString();
        String targetAgentName = targetAgent.toString();
        Double temperatureValue = Double.parseDouble(temperature.toString());
        Double ratingValue = Double.parseDouble(receivedRating.toString());

        return new Rating(sourceAgentName, targetAgentName, temperatureValue, ratingValue);
    }

    private void addRating(Rating rating, Map<String, List<Rating>> ratingsPerAgent) {
        String targetAgentName = rating.getTargetAgentName();
        if (ratingsPerAgent.containsKey(targetAgentName)) {
            // log("Adding rating for existing agent: " + rating);
            List<Rating> ratings = ratingsPerAgent.get(targetAgentName);
            ratings.add(rating);
        } else {
            // log("Adding rating for new agent: " + rating);
            List<Rating> ratings = new ArrayList<>();
            ratings.add(rating);
            ratingsPerAgent.put(targetAgentName, ratings);
        }
    }

    @OPERATION
    public void getAgentWithHighestRating(OpFeedbackParam<String> agentName) {
        double highestRating = 0.0;
        String agentWithHighestRating = "";
        for (String agentNameKey : certificationTrustRatingsPerAgent.keySet()) {

            // calculate Interaction Trust average
            List<Rating> itRatings = interactionTrustRatingsPerAgent.containsKey(agentNameKey) ? interactionTrustRatingsPerAgent.get(agentNameKey) : new ArrayList<>();
            Double avgITRating = getAverageRating(itRatings);

            // calculate Certification Trust average
            List<Rating> ctRatings = certificationTrustRatingsPerAgent.containsKey(agentNameKey) ? certificationTrustRatingsPerAgent.get(agentNameKey) : new ArrayList<>();
            Double avgCTRating = getAverageRating(ctRatings);

            // calculate Witness Trust average
            List<Rating> witnessRatings = witnessRatingsPerAgent.containsKey(agentNameKey) ? witnessRatingsPerAgent.get(agentNameKey) : new ArrayList<>();
            Double avgWRating = getAverageRating(witnessRatings);

            double avgRating = (avgITRating + avgCTRating + avgWRating) / 3;

            // log("Avg rating: " + avgRating + " for agent " + agentNameKey);

            if(avgRating > highestRating) {
                highestRating = avgRating;
                agentWithHighestRating = agentNameKey;
            }
        }
        agentName.set(agentWithHighestRating);
        // log(String.format("Agent with highest rating is %s", agentWithHighestRating));
    }

    private Double getAverageRating(List<Rating> ratings) {
        if(ratings.isEmpty()) {
            return 0.0;
        }
        Double ratingSum = ratings.stream()
                .map(Rating::getRatingValue)
                .reduce(0.0, Double::sum);
        return ratingSum / ratings.size();
    }
}
