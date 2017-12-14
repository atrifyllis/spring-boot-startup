package gr.alx.startup.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import java.util.HashSet;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

    @Mock
    private Errors errors;

    @Test
    public void shouldFlagInvalid() {
        UserValidator userValidator = new UserValidator();
        User user = new User("1", "", "pass1", "pass2", new HashSet<>());
        userValidator.validate(user, errors);

        verify(errors).rejectValue("confirmPassword", "Diff.userForm.passwordConfirm");
    }

    @Test
    public void shouldFlagValidWhenPasswordIsNull() {
        UserValidator userValidator = new UserValidator();
        User user = new User("1", "", null, "pass2", new HashSet<>());
        userValidator.validate(user, errors);

        verify(errors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void shouldFlagValidWhenConfirmPasswordIsNull() {
        UserValidator userValidator = new UserValidator();
        User user = new User("1", "", "pass", null, new HashSet<>());
        userValidator.validate(user, errors);

        verify(errors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void shouldFlagValidWhenBothPasswordsNull() {
        UserValidator userValidator = new UserValidator();
        User user = new User("1", "", null, null, new HashSet<>());
        userValidator.validate(user, errors);

        verify(errors, never()).rejectValue(anyString(), anyString());
    }

    @Test
    public void shouldFlagValidWhenBothPasswordsEqual() {
        UserValidator userValidator = new UserValidator();
        User user = new User("1", "", "pass", "pass", new HashSet<>());
        userValidator.validate(user, errors);

        verify(errors, never()).rejectValue(anyString(), anyString());
    }
}