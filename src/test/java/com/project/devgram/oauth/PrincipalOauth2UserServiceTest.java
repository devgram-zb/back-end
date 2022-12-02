package com.project.devgram.oauth;

import com.project.devgram.oauth2.principal.PrincipalOauth2UserService;
import com.project.devgram.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@MockBeans({
        @MockBean(JpaMetamodelMappingContext.class),
        @MockBean(PrincipalOauth2UserService.class),
        @MockBean(UserRepository.class)
})
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
class PrincipalOauth2UserServiceTest {

    @Autowired
    private MockMvc mvc;


    @Test
    @DisplayName("접속가능 여부 test")
    void saveUsers() throws Exception {
        OAuth2User oauth2User = new DefaultOAuth2User(
                AuthorityUtils.createAuthorityList("SCOPE_message:read"),
                Collections.singletonMap("user_name", "foo_user"),
                "user_name");
        mvc.perform(get("/oauth2/authorization/github")
                        .with(oauth2Login().oauth2User(oauth2User))
                );


    }



}