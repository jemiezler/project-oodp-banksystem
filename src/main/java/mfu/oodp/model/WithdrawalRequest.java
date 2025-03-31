package mfu.oodp.model;

public class WithdrawalRequest {
    private enum Status { // สถานะของคำขอถอนเงิน
        PENDING, APPROVED, REJECTED
    }

    private String id; // รหัสของลูกค้า
    private double amount; // จำนวนเงินที่ต้องการถอน
    private Status status; // สถานะของคำขอถอนเงิน (Pending, Approved, Rejected)
    
    // Constructor
    public WithdrawalRequest(String id, double amount) {
        this.id = id;
        this.amount = amount;
        this.status = Status.PENDING; // ค่าสถานะเริ่มต้นคือ Pending
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Status getStatus() {
        return status;
    }

    public void approve() {
        status = Status.APPROVED;  //เปลี่ยนสถานะ เป็น Approved
    }

    public void reject() {
        status = Status.REJECTED;  //เปลี่ยนสถานะ เป็น Rejected
    }

    @Override
    public String toString() {
        return "Client ID: " + id + ", Amount: " + amount + ", Status: " + status;
    }
}