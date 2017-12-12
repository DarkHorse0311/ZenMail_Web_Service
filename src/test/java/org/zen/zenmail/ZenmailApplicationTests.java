package org.zen.zenmail;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zen.zenmail.api.MainController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZenmailApplicationTests {
    @Autowired
    private MainController mainController;

	@Test
	public void contextLoads() throws Exception {
        assertThat(mainController).isNotNull();
	}
}
