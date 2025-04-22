package mfu.oodp.config;

import mfu.oodp.model.Transaction.Transaction;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TransactionBackupUtil {

    /**
     * บันทึกข้อมูลธุรกรรมทั้งหมดไปยังไฟล์ CSV
     * @param transactions รายการธุรกรรมที่ต้องการ backup
     * @param filePath path ที่ต้องการเก็บไฟล์ (เช่น "backup_transactions.csv")
     */
    public static void backupTransactionsToFile(List<Transaction> transactions, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Header
            writer.write("ID,Time,From,To,Amount,Type,Agent");
            writer.newLine();

            for (Transaction tx : transactions) {
                String line = String.join(",",
                    tx.getTransactionId(),
                    tx.getTransactionTime().toString(),
                    tx.getFromAccount() != null ? tx.getFromAccount().getAccountId() : "-",
                    tx.getToAccount() != null ? tx.getToAccount().getAccountId() : "-",
                    String.valueOf(tx.getAmount()),
                    tx.getTransactionType().name(),
                    tx.getAgentId() != null ? tx.getAgentId().getFirstName() + " " + tx.getAgentId().getLastName() : "-"
                );
                writer.write(line);
                writer.newLine();
            }

            System.out.println("✅ Transaction backup saved to " + filePath);
        } catch (IOException e) {
            System.err.println("❌ Failed to write transaction backup: " + e.getMessage());
        }
    }
}
