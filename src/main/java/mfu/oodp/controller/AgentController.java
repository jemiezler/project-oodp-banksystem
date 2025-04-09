package mfu.oodp.controller;

import mfu.oodp.model.Agent.Agent;
import mfu.oodp.service.AgentService;

import java.util.UUID;

public class AgentController {
    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    public Agent register(String username, String password, String email, String firstName, String lastName) {
        return agentService.registerAgent(username, password, email, firstName, lastName);
    }

    public Agent login(String username, String password) {
        return agentService.login(username, password);
    }

    public Agent getAgent(UUID id) {
        return agentService.getAgentById(id);
    }
}
