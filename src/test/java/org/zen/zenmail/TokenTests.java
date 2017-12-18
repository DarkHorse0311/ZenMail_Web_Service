package org.zen.zenmail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.zen.zenmail.identity.TokenUser;
import org.zen.zenmail.identity.TokenUtil;

import static org.junit.Assert.assertEquals;

@Configuration
@ComponentScan(
        basePackages = {"org.zen.zenmail.identity",

        })
class tokenTestsContext {

}

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = testContext.class)
public class TokenTests {
    public static final String USER_NAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String ROLES = "USER";
    public static final String NAME = "NAME";

    @Test
    public void createAndParseTokenTest(){

        TokenUser tokenUser = new TokenUser(USER_NAME, PASSWORD, ROLES, NAME);
        TokenUtil tokenUtil = new TokenUtil();
        String string = tokenUtil.createTokenForUser(tokenUser);
        TokenUser testUser = tokenUtil.parseUserFromToken(string);
        assertEquals(USER_NAME, testUser.getUserName());
        assertEquals(PASSWORD, testUser.getPassword());
        assertEquals(ROLES, testUser.getRoles());
        assertEquals(NAME, testUser.getName());
    }
}
