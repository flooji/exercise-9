package tools;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

import java.util.HashMap;
import java.util.Map;

public class TempReadingManager extends Artifact {

    Map<String, Double> currentTempPerAgent = new HashMap<>();

    @OPERATION
    public void addCurrentTempForAgent(Object agent, Object temperature) {
        String agentName = agent.toString();
        Double temperatureValue = Double.parseDouble(temperature.toString());

        currentTempPerAgent.put(agentName, temperatureValue);
        // log("New temperature " + temperatureValue + " from " + agentName);
    }

    @OPERATION
    public void getCurrentTempForAgent(Object agent, OpFeedbackParam<Double> temperature) {
        String agentName = agent.toString();
        temperature.set(currentTempPerAgent.get(agentName));
    }
}
