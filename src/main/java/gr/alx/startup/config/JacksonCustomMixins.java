package gr.alx.startup.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import gr.alx.startup.user.User;
import gr.alx.startup.user.UserMixin;

/**
 * Rregister all mixins for domain objects
 */
public class JacksonCustomMixins extends SimpleModule {


    public JacksonCustomMixins() {
        setMixInAnnotation(User.class, UserMixin.class);
    }
}
