package validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;

public class Validation {

    // Checks if any string is null or empty
    public static boolean isEmpty(String... text) {
        for (String str : text) {
            if (str == null || str.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidPassword(String password){
        String passRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!*?&%$])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(passRegex);
        Matcher matcher = pattern.matcher(password);
        
        return matcher.matches();
    }
    
    public static boolean isValidEmail(String email) {
        // Define the regex for a valid email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        // Compile the regex into a pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // If the email is null, it is not valid
        if (email == null) {
            return false;
        }

        // Match the email string against the pattern
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
    
    // Checks if any integer is less than 0 (renamed for clarity)
    public static boolean isSelected(int... value) {
        for (int i : value) {
            if (i < 0) {
                return false;
            }
        }
        return true;
    }

    // Checks if all strings are numeric
    public static boolean isDigit(String... text) {
        for (String str : text) {
            if (str == null || !str.matches("\\d+")) {
                return false;
            }
        }
        return true;
    }

    // Checks if all strings contain only alphabetic characters, spaces, or hyphens
    public static boolean isText(String... text) {
        for (String str : text) {
            if (str == null || !str.matches("[a-zA-Z\\s\\-]+")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDigit(JTextField txtPatientId, JTextField txtToUser) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
