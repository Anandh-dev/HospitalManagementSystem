package hospital_backend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PatientRequest {

    @NotBlank(message = "Full name is required")
    @Size(
        max = 100,
        message = "Full name cannot exceed 100 characters"
    )
    private String fullName;

    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Mobile number is required")
    @Pattern(
        regexp = "^[0-9]{10,15}$",
        message = "Mobile number must contain 10 to 15 digits"
    )
    private String mobileNumber;

    @Pattern(
        regexp = "^[0-9]{10,15}$",
        message = "WhatsApp number must contain 10 to 15 digits"
    )
    private String whatsappNumber;

    @Email(message = "Please provide a valid email address")
    private String email;

    @Size(
        max = 255,
        message = "Address cannot exceed 255 characters"
    )
    private String address;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}