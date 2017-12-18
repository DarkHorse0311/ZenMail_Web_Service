package org.zen.zenmail;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.sun.mail.imap.IMAPMessage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.zen.zenmail.api.messages.MessagesController;
import org.zen.zenmail.api.messages.MessagesService;
import org.zen.zenmail.identity.UserAuthentication;
import org.zen.zenmail.model.messages.MessagesResponse;
import org.zen.zenmail.model.user.User;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
@ComponentScan(
        basePackages = {"org.zen.zenmail.api.messages",

        })
class testContext {

}

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = testContext.class)
public class MessageTests {
    private static final String USER_PASSWORD = "abcdef123";
    private static final String USER_NAME = "hascode";
    private static final String EMAIL_SUBJECT = "Test E-Mail";
    private static final String EMAIL_TEXT = "This is a test e-mail.";
    private static final String EMAIL_FROM = "testMail@testMail.com";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String EMAIL_HTML_TEXT = "<div>test msg </div>";

    @Mock
    MessagesService messagesService;

    @InjectMocks
    @Resource
    MessagesController messagesController;

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP_IMAP);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void initMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdd() {
        assertEquals(1, 1);
    }

    @Test
    public void getMessageFromImapTest() throws Exception {

        MimeMessage plainTextMessage = new MimeMessage((Session) null);
        plainTextMessage.setText(EMAIL_TEXT);
        plainTextMessage.setSubject(EMAIL_SUBJECT);
        plainTextMessage.setFrom(EMAIL_FROM);

        MimeMessage htmlMessage = new MimeMessage((Session) null);
        htmlMessage.setText(EMAIL_HTML_TEXT);
        htmlMessage.setSubject(EMAIL_SUBJECT);
        htmlMessage.setFrom(EMAIL_FROM);

        GreenMailUser user = greenMail.setUser(EMAIL_FROM, USER_NAME, USER_PASSWORD);
        user.deliver(plainTextMessage);
        user.deliver(htmlMessage);

        Properties props = new Properties();
        Session session = Session.getInstance(props);
        URLName urlName = new URLName("imap", LOCALHOST,
                ServerSetupTest.IMAP.getPort(), null, user.getLogin(),
                user.getPassword());
        Store store = session.getStore(urlName);
        store.connect();

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Message[] messages = folder.getMessages();
        Vector<IMAPMessage> imapMessages = new Vector<>();
        for (int i = 0; i < messages.length; ++i) {
            imapMessages.add((IMAPMessage) messages[i]);
        }

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        UserAuthentication userAuthentication = new UserAuthentication("", "");
        userAuthentication.setAuthenticated(true);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(userAuthentication);
        SecurityContextHolder.setContext(securityContext);

        when(messagesService.getMessages(any(User.class))).thenReturn(imapMessages);
        MessagesResponse res = messagesController.getUserInformation(request, response);

        assertEquals(res.getMsgs().size(), 2);

        assertEquals(res.getMsgs().elementAt(1).subject, EMAIL_SUBJECT);
        assertEquals(res.getMsgs().elementAt(1).msg, EMAIL_TEXT);
        assertEquals(res.getMsgs().elementAt(1).from, EMAIL_FROM);

        assertEquals(res.getMsgs().elementAt(0).subject, EMAIL_SUBJECT);
        assertEquals(res.getMsgs().elementAt(0).msg, EMAIL_HTML_TEXT);
        assertEquals(res.getMsgs().elementAt(0).from, EMAIL_FROM);
    }
}
