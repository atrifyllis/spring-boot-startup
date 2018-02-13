package gr.alx.startup.user;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Instead of adding Jackson annotations inside the domain object, use mixins
 */
public abstract class UserMixin {

    /**
     * Never send the password to the UI!
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    abstract String getPassword();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    abstract String getConfirmPassword();
}
