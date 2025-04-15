package mfu.oodp.view.agent;

import mfu.oodp.model.Agent.AgentActionLog;
import mfu.oodp.service.AgentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AgentLogViewer {

    public static void show(Component parent) {
        AgentService agentService = new AgentService();
        List<AgentActionLog> logs = agentService.getActionLogs();

        if (logs.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "No logs available.", "Agent Logs", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Column headers
        String[] columnNames = {"Agent Name", "Action", "Timestamp", "Description"};

        // Data rows
        Object[][] data = new Object[logs.size()][4];
        for (int i = 0; i < logs.size(); i++) {
            AgentActionLog log = logs.get(i);
            data[i][0] = log.getAgentId().getFirstName() + " " + log.getAgentId().getLastName();
            data[i][1] = log.getAction();
            data[i][2] = log.getCreateAt().toString();
            data[i][3] = log.getDescription();
        }

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        JOptionPane.showMessageDialog(parent, scrollPane, "📜 Agent Logs", JOptionPane.INFORMATION_MESSAGE);
    }
}
