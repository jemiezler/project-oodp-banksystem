package mfu.oodp.model.Agent;

import java.sql.Timestamp;
import java.util.UUID;

public class AgentActionLog {
    private UUID id;
    private Agent agentId;
    private String action;
    private String description;
    private Timestamp createAt;

    public AgentActionLog(UUID id, Agent agentId ,String action, Timestamp createAt, String description) {
        this.id = id;
        this.agentId = agentId;
        this.action = action;
        this.createAt = createAt;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Agent getAgentId() {
        return agentId;
    }

    public void setAgentId(Agent agentId) {
        this.agentId = agentId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }    

}
