package org.zen.zenmail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.zen.zenmail.crypt.Hash;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@Configuration
@ComponentScan(
        basePackages = {"org.zen.zenmail.crypt",

        })
class hashTestContext{

}

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = testContext.class)
public class HashTests {
    private static final String PASWWORD = "test";
    private static final String SAULT = "2d12f14d21b4baea";
    private static final String HASH = "{SSHA256}wkdZE0lk731xx6IrUaRLQ5756nUwwRB4vYlZdw0QbeUyZDEyZjE0ZDIxYjRiYWVh";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getHashTest() throws Exception {
        String testHash = Hash.getSHA256password(PASWWORD, SAULT);


        assertEquals(HASH, Hash.getSHA256password(PASWWORD, SAULT));
        assertEquals(HASH, Hash.hashWitDBSalt(PASWWORD, testHash));
        assertEquals(16, Hash.getRandomHex8Byte().length());
    }
}
