package mfu.oodp.controller;

/**
 * คลาสแม่ที่ให้ Controller อื่น ๆ ใช้ร่วมกัน เช่น log, validate
 */
public abstract class BaseController {
    public void logAction(String message) {
        System.out.println("[LOG] " + message);
    }

    public boolean isValidUsername(String input) {
        return input != null && input.matches("[a-zA-Z0-9_]{3,20}");
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        // ต้องมีทั้งตัวพิมพ์เล็ก พิมพ์ใหญ่ และตัวเลข
        boolean hasUpper = false, hasLower = false, hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
        }
        return hasUpper && hasLower && hasDigit;
    }
}
