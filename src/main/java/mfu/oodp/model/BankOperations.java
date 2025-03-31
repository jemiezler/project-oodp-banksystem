package mfu.oodp.model;

interface BankOperations {
    void deposit(double amount); // ฝากเงิน
    void withdraw(double amount) throws InsufficientFundsException; // ถอนเงิน
   
    void displayBalance(); // แสดงยอดเงินคงเหลือ
}
