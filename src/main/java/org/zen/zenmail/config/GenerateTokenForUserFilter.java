package org.zen.zenmail.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.zen.zenmail.identity.TokenUser;
import org.zen.zenmail.identity.TokenUtil;
import org.zen.zenmail.model.response.OperationResponse;
import org.zen.zenmail.model.session.SessionItem;
import org.zen.zenmail.model.session.SessionResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/* This filter maps to /session and tries to validate the username and password */
@Slf4j
public class GenerateTokenForUserFilter extends AbstractAuthenticationProcessingFilter {

    private TokenUtil tokenUtil;

    GenerateTokenForUserFilter(String urlMapping, AuthenticationManager authenticationManager, TokenUtil tokenUtil) {
        super(new AntPathRequestMatcher(urlMapping));
        setAuthenticationManager(authenticationManager);
        this.tokenUtil = tokenUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException, JSONException {
        try {
            String jsonString = IOUtils.toString(request.getInputStream(), "UTF-8");

            /* using org.json */
            JSONObject userJSON = new JSONObject(jsonString);
            String username = userJSON.getString("username");
            String password = userJSON.getString("password");
            log.info("username:{} and paswword:{} \n", username, password);

            final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            return getAuthenticationManager().authenticate(authToken); // This will take to successfulAuthentication or faliureAuthentication function
        } catch (JSONException | AuthenticationException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authToken) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authToken);

        TokenUser tokenUser = (TokenUser) authToken.getPrincipal();
        SessionResponse resp = new SessionResponse();
        SessionItem respItem = new SessionItem();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String tokenString = this.tokenUtil.createTokenForUser(tokenUser);

        respItem.setFirstName(tokenUser.getUser().getFirstName());
        respItem.setLastName(tokenUser.getUser().getLastName());
        respItem.setUserId(tokenUser.getUser().getUserId());
        respItem.setEmail(tokenUser.getUser().getEmail());
        respItem.setToken(tokenString);

        resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        resp.setOperationMessage("Login Success");
        resp.setItem(respItem);
        String jsonRespString = ow.writeValueAsString(resp);

        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write(jsonRespString);
        res.getWriter().flush();
        res.getWriter().close();


    }
}
