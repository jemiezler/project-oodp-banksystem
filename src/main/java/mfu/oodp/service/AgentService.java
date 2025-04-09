package mfu.oodp.service;

import mfu.oodp.model.Agent.Agent;
import mfu.oodp.model.Agent.AgentActionLog;

import java.sql.Timestamp;
import java.util.*;

public class AgentService {

    private Map<UUID, Agent> agentDatabase = new HashMap<>();
    private List<AgentActionLog> actionLogs = new ArrayList<>();

    public Agent registerAgent(String username, String password, String email, String firstName, String lastName) {
        UUID id = UUID.randomUUID();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Agent agent = new Agent(id, username, password, email, firstName, lastName, now, null, true);
        agentDatabase.put(id, agent);

        logAction(agent, "REGISTER", "Agent registered");

        return agent;
    }

    public Agent login(String username, String password) {
        for (Agent agent : agentDatabase.values()) {
            if (agent.getUsername().equals(username) && agent.getPassword().equals(password)) {
                agent.setLastLogin(new Timestamp(System.currentTimeMillis()));
                logAction(agent, "LOGIN", "Agent logged in");
                return agent;
            }
        }
        return null;
    }

    public void logAction(Agent agent, String action, String description) {
        AgentActionLog log = new AgentActionLog(UUID.randomUUID(), agent, action, new Timestamp(System.currentTimeMillis()), description);
        actionLogs.add(log);
    }

    public List<AgentActionLog> getActionLogs() {
        return actionLogs;
    }

    public Agent getAgentById(UUID id) {
        return agentDatabase.get(id);
    }
}
