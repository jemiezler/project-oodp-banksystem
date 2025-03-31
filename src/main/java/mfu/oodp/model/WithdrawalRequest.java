package mfu.oodp.model;

public class WithdrawalRequest {
    private String id; // รหัสของลูกค้า
    private double amount; // จำนวนเงินที่ต้องการถอน
    private String status; // สถานะของคำขอถอนเงิน (Pending, Approved, Rejected)
    
    // Constructor

    public WithdrawalRequest(String id, double amount) {
        this.id = id;
        this.amount = amount;
        this.status = "Pending"; // ค่าสถานะเริ่มต้นคือ Pending
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public void approve() {
        status = "Approved";  //เปลี่ยนสถานะ เป็น Approved
    }

    public void reject() {
        status = "Rejected";  //เปลี่ยนสถานะ เป็น Rejected
    }

    @Override
    public String toString() {
        return "Client ID: " + id + ", Amount: " + amount + ", Status: " + status;
    }
}