package com.regexfromassigment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.regexfromassigment.R;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    // Define regex patterns for validation
    private static final String STUDENT_ID_PATTERN = "^018\\d{13}$";  // Starts with 018 and has 16 digits
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@lus.ac.bd$";  // Email must match this domain
    private static final String NAME_PATTERN = "^[a-zA-Z\\s]+$";  // Name should contain only letters and spaces
    private static final String USERNAME_PATTERN = "^[a-z]+$";  // Username should be lowercase only
    private static final String PHONE_PATTERN = "^\\d{11}$";  // Phone number must have exactly 11 digits

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Find views by ID
        EditText studentIdField = findViewById(R.id.et_LastName);  // Assuming 'et_LastName' is for student ID
        EditText emailField = findViewById(R.id.et_email);
        EditText nameField = findViewById(R.id.et_firstName);  // Assuming 'et_firstName' is for the name
        EditText usernameField = findViewById(R.id.et_username);  // Assuming 'et_username' is for username
        EditText phoneField = findViewById(R.id.et_phoneNumber);  // Phone number field
        EditText passwordField = findViewById(R.id.et_password);  // Password field
        EditText confirmPasswordField = findViewById(R.id.et_ConfirmPassword);  // Confirm password field
        Button signUpButton = findViewById(R.id.btn_signUp);

        // Set onClickListener for sign up button
        signUpButton.setOnClickListener(view -> {
            String studentId = studentIdField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String name = nameField.getText().toString().trim();
            String username = usernameField.getText().toString().trim();
            String phone = phoneField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String confirmPassword = confirmPasswordField.getText().toString().trim();

            // Call validation methods
            if (validateStudentId(studentId, studentIdField) &&
                    validateEmail(email, emailField) &&
                    validateName(name, nameField) &&
                    validateUsername(username, usernameField) &&
                    validatePhone(phone, phoneField) &&
                    validatePasswords(password, confirmPassword, passwordField, confirmPasswordField)) {
                // If all validations pass, show success toast
                Toast.makeText(RegisterActivity.this, "Validation successful! Registration complete.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Validate student ID with regex
    private boolean validateStudentId(String studentId, EditText field) {
        if (TextUtils.isEmpty(studentId)) {
            showError(field, "Student ID is required");
            return false;
        }
        if (!Pattern.matches(STUDENT_ID_PATTERN, studentId)) {
            showError(field, "Invalid Student ID. Must start with 018 and be 16 digits long.");
            return false;
        }
        clearError(field);
        return true;
    }

    // Validate email with regex
    private boolean validateEmail(String email, EditText field) {
        if (TextUtils.isEmpty(email)) {
            showError(field, "Email is required");
            return false;
        }
        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            showError(field, "Invalid Email. Must be a CSE student email (e.g., cse_student_id@lus.ac.bd).");
            return false;
        }
        clearError(field);
        return true;
    }

    // Validate name (no numbers allowed)
    private boolean validateName(String name, EditText field) {
        if (TextUtils.isEmpty(name)) {
            showError(field, "Name is required");
            return false;
        }
        if (!Pattern.matches(NAME_PATTERN, name)) {
            showError(field, "Invalid Name. Name should not contain numbers.");
            return false;
        }
        clearError(field);
        return true;
    }

    // Validate username (lowercase only)
    private boolean validateUsername(String username, EditText field) {
        if (TextUtils.isEmpty(username)) {
            showError(field, "Username is required");
            return false;
        }
        if (!Pattern.matches(USERNAME_PATTERN, username)) {
            showError(field, "Username must be in lowercase without spaces.");
            return false;
        }
        clearError(field);
        return true;
    }

    // Validate phone number (must contain exactly 11 digits)
    private boolean validatePhone(String phone, EditText field) {
        if (TextUtils.isEmpty(phone)) {
            showError(field, "Phone number is required");
            return false;
        }
        if (!Pattern.matches(PHONE_PATTERN, phone)) {
            showError(field, "Invalid phone number. Must be exactly 11 digits.");
            return false;
        }
        clearError(field);
        return true;
    }

    // Validate passwords (must match)
    private boolean validatePasswords(String password, String confirmPassword, EditText passwordField, EditText confirmPasswordField) {
        if (TextUtils.isEmpty(password)) {
            showError(passwordField, "Password is required");
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            showError(confirmPasswordField, "Confirm Password is required");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            showError(confirmPasswordField, "Passwords do not match");
            return false;
        }
        clearError(passwordField);
        clearError(confirmPasswordField);
        return true;
    }

    // Show error by changing background color and focusing on the field
    private void showError(EditText field, String message) {
        field.setError(message);  // Show message in the EditText
        field.requestFocus();  // Focus the field with the error
        field.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light));  // Highlight in red
    }

    // Clear error and reset background color
    private void clearError(EditText field) {
        field.setError(null);  // Clear error message
        field.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));  // Reset background color
    }
}
