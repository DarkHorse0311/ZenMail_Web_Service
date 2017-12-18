package org.zen.zenmail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.zen.zenmail.api.user.UserService;
import org.zen.zenmail.model.user.User;
import org.zen.zenmail.repository.UserRepository;

import javax.annotation.Resource;

import static org.mockito.Mockito.when;

@Configuration
@ComponentScan(
        {
                "org.zen.zenmail.api",

        })
class userServiceTestContext {
}


@WebAppConfiguration
@ContextConfiguration(classes = {userServiceTestContext.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTests {
    final private static String USER_NAME = "testUserName";
    final private static String PASSWORD = "password";

    @Mock
    UserRepository userRepository;

    @InjectMocks
    @Resource
    UserService userService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void getUserTest() {
        User user = new User();
        user.setUsername(USER_NAME);
        user.setPassword(PASSWORD);
        when(userRepository.findOneByUsername(USER_NAME).orElseGet(() -> new User())).thenReturn(user);
        User resultUser = userService.getUserInfoByUserId(USER_NAME);
    }
}
