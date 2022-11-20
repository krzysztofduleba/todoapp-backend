package app.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Current password cannot be blank")
    @Size(min = 8, max = 50, message = "Current password length must be between 8 and 50 characters.")
    private String currentPassword;
    @NotBlank(message = "New password cannot be blank")
    @Size(min = 8, max = 50, message = "New password length must be between 8 and 50 characters.")
    private String newPassword;
}