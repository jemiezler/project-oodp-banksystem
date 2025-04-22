package mfu.oodp.controller;

import mfu.oodp.model.Agent.Agent;
import mfu.oodp.service.AgentService;
import mfu.oodp.operation.AgentOperation;

import java.util.UUID;

public class AgentController extends BaseController implements AgentOperation {
    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @Override
    public Agent register(String username, String password, String email, String firstName, String lastName) {
        if (!isValidUsername(username) || !isValidPassword(password)) {
            logAction("❌ Invalid registration input");
            return null;
        }
        return agentService.registerAgent(username, password, email, firstName, lastName);
    }

    @Override
    public Agent login(String username, String password) {
        return agentService.login(username, password);
    }

    @Override
    public Agent getAgent(UUID id) {
        return agentService.getAgentById(id);
    }
}
