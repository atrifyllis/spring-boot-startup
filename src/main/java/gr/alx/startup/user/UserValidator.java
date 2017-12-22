package gr.alx.startup.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Custom validations for {@link User} entity
 */
@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        // password change requested
        // if one of the fields is null we do not validate (issue when not sending password at all
        // because Spring Data Rest merges the Json from the UI with the persisted entity
        // which results in a User with password but not confirmPassword
        if (user.getPassword() != null && user.getConfirmPassword() != null) {
            if (!user.getPassword().equals(user.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "Diff.userForm.passwordConfirm");
            }
        }
    }
}
