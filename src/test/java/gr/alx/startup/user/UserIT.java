package gr.alx.startup.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// this is used because we can't use @WebMvcTest slice since we also needed access to repositories
@AutoConfigureMockMvc
public class UserIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private OAuthHelper helper;

    @Test
    // needed only for user repository direct calls, not for the rest calls
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    // needed for lazy relationships to be loaded (user roles)
    @Transactional
    public void shouldNotChangeOtherFieldsWhenUpdatingUsername() throws Exception {

        User user = userRepository.findByUsername("admin").get();
        RequestPostProcessor token = helper.bearerToken("sampleClientId", user);

        User copyUser = helper.cloneUser(user);
        copyUser.setUsername("newUsername");
        // NOTE: setting to null then serializing removes the field from json string completely
        copyUser.setEmail(null);
        String updatedUser = helper.serializeObject(copyUser);

        mvc.perform(patch("/api/users/" + user.getId())
                .with(csrf())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUser))
                .andExpect(content().json("{'username':'newUsername','email':'admin@admin.com'}"))
        ;
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Transactional
    public void shouldRetrieveUsersIfUserIsAdmin() throws Exception {
        User user = userRepository.findByUsername("admin").get();
        RequestPostProcessor token = helper.bearerToken("sampleClientId", user);

        mvc.perform(get("/api/users")
                .with(csrf())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.users", hasSize(3)))
        ;
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Transactional
    public void shouldNotRetrieveUsersIfUserIsNotAdmin() throws Exception {
        User user = userRepository.findByUsername("alex").get();
        RequestPostProcessor token = helper.bearerToken("sampleClientId", user);

        mvc.perform(get("/api/users")
                .with(csrf())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
        ;
    }
}

