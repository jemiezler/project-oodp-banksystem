package mfu.oodp.operation;

import mfu.oodp.model.Agent.Agent;
import java.util.UUID;

public interface AgentOperation {
    Agent register(String username, String password, String email, String firstName, String lastName);
    Agent login(String username, String password);
    Agent getAgent(UUID id);
}
