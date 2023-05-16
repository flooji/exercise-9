// rogue agent is a type of sensing agent

/* Initial beliefs and rules */
// initially, the agent believes that it hasn't received any temperature readings
received_reading(_).

rogue_clan([sensing_agent_5, sensing_agent_6, sensing_agent_7, sensing_agent_8, sensing_agent9]).

/* Initial goals */
!set_up_plans. // the agent has the goal to add pro-rogue plans

/* 
 * Plan for reacting to the addition of the goal !set_up_plans
 * Triggering event: addition of goal !set_up_plans
 * Context: true (the plan is always applicable)
 * Body: adds pro-rogue plans for reading the temperature without using a weather station
*/
+!set_up_plans : true
<-
  // removes plans for reading the temperature with the weather station
  .relevant_plans({ +!read_temperature }, _, LL);
  .remove_plan(LL);
  .relevant_plans({ -!read_temperature }, _, LL2);
  .remove_plan(LL2);

  	.my_name(Ag);
	.send(acting_agent, tell, witness_reputation(Ag, sensing_agent_1, temperature(10.8), -1));
	.send(acting_agent, tell, witness_reputation(Ag, sensing_agent_2, temperature(10.8), -1));
	.send(acting_agent, tell, witness_reputation(Ag, sensing_agent_3, temperature(10.8), -1));
	.send(acting_agent, tell, witness_reputation(Ag, sensing_agent_4, temperature(10.8), -1));
	.send(acting_agent, tell, witness_reputation(Ag, sensing_agent_5, temperature(11.3), 6));
	.send(acting_agent, tell, witness_reputation(Ag, sensing_agent_6, temperature(11.1), 6));
	.send(acting_agent, tell, witness_reputation(Ag, sensing_agent_7, temperature(10.9), 6));
	.send(acting_agent, tell, witness_reputation(Ag, sensing_agent_8, temperature(11.3), 6));
	.send(acting_agent, tell, witness_reputation(Ag, sensing_agent_9, temperature(-2), 12));

   .print("Added new plans");

  // adds a new plan for reading the temperature that doesn't require contacting the weather station
  // the agent will pick one of the first three temperature readings that have been broadcasted,
  // it will slightly change the reading, and broadcast it
  .add_plan({ +!read_temperature : received_reading(TempReading) & .number(TempReading)
     <-
      .print("Reading the temperature (Celcius): ", TempReading);

      .broadcast(tell, temperature(TempReading)) });

  // adds plan for reading temperature in case fewer than 3 readings have been received
  .add_plan({ +!read_temperature : received_reading(TempReading) & not .number(TempReading)
    <-

    // waits for 2000 milliseconds and finds all beliefs about received temperature readings
    .wait(2000);

    // tries again to "read" the temperature
    !read_temperature }).

// stores the temperature update from rogue_leader
+temperature(TempReading)[source(Ag)]: Ag == sensing_agent_9 <-
   // updates the belief about the received temperature
   //.print("Received temperature reading (Celcius): ", TempReading);
    -+received_reading(TempReading).

/* Import behavior of sensing agent */
{ include("sensing_agent.asl")}