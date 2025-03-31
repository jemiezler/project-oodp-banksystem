package mfu.oodp.test;
import static org.junit.jupiter.api.Assertions.*;
import mfu.oodp.model.WithdrawalRequest;
import org.junit.jupiter.api.Test;


class WithdrawalRequestTest {
    
    @Test
    void testWithdrawalRequestCreation() {
        WithdrawalRequest request = new WithdrawalRequest("John Doe", 500.0);
        assertEquals("John Doe", request.getId());
        assertEquals(500.0, request.getAmount());
        assertEquals("Pending", request.getStatus());
    }

    @Test
    void testApproveWithdrawalRequest() {
        WithdrawalRequest request = new WithdrawalRequest("Jane Doe", 300.0);
        request.approve();
        assertEquals("Approved", request.getStatus());
    }
    
    @Test
    void testRejectWithdrawalRequest() {
        WithdrawalRequest request = new WithdrawalRequest("Alice", 200.0);
        request.reject();
        assertEquals("Rejected", request.getStatus());
    }
}

