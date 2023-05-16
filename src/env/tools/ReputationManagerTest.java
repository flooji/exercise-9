package tools;

import cartago.OpFeedbackParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReputationManagerTest {
    private ReputationManager reputationManager;

    @BeforeEach
    void setUp() {
       reputationManager = new ReputationManager();
    }

    @Test
    void addInteractionTrustRating() {
        reputationManager.addInteractionTrustRating("sourceAgent1", "targetAgent1", 5.0, 1.0);
        assert(reputationManager.interactionTrustRatingsPerAgent.containsKey("targetAgent1"));
        assert(reputationManager.interactionTrustRatingsPerAgent.get("targetAgent1").size() == 1);
    }

    @Test
    void addCertificationTrustRating() {
        reputationManager.addCertificationTrustRating("sourceAgent2", "targetAgent2", 3.0, 2.0);
        assert(reputationManager.certificationTrustRatingsPerAgent.containsKey("targetAgent2"));
        assert(reputationManager.certificationTrustRatingsPerAgent.get("targetAgent2").size() == 1);

    }

    @Test
    void addWitnessRating() {
        reputationManager.addCertificationTrustRating("sourceAgent3", "targetAgent4", 6.0, -1.0);
        assert(reputationManager.certificationTrustRatingsPerAgent.containsKey("targetAgent4"));
        assert(reputationManager.certificationTrustRatingsPerAgent.get("targetAgent4").size() == 1);
    }

    @Test
    void getAgentWithHighestRating() {
        reputationManager.addInteractionTrustRating("sourceAgent1", "targetAgent1", 5.0, 1.0);
        reputationManager.addInteractionTrustRating("sourceAgent1", "targetAgent1", 5.0, 2.0);
        reputationManager.addInteractionTrustRating("sourceAgent2", "targetAgent2", 7.0, 3.0);

        reputationManager.addCertificationTrustRating("sourceAgent1", "targetAgent1", 5.0, 0.5);
        reputationManager.addCertificationTrustRating("sourceAgent2", "targetAgent2", 7.0, 2.0);
        reputationManager.addCertificationTrustRating("sourceAgent2", "targetAgent2", 7.0, 3.0);

        OpFeedbackParam<String> agentName = new OpFeedbackParam<>();
        reputationManager.getAgentWithHighestRating(agentName);
        assert(agentName.get().equals("targetAgent2"));
    }
}