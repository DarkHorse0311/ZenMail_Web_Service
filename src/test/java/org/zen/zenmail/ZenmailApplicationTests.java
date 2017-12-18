package org.zen.zenmail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.zen.zenmail.api.MainController;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(loader =  AnnotationConfigWebContextLoader.class)
public class ZenmailApplicationTests {
    @Configuration
    static class t{

        @Bean
        public MainController mainController(){
            MainController mainController = new MainController();
            return mainController;
        }
    }

    @Autowired
    private MainController mainController;

	@Test
	public void contextLoads() throws Exception {
        assertThat(mainController).isNotNull();
	}
    @Test
    public void testAdd(){
        assertEquals(1, 1);
    }
}
