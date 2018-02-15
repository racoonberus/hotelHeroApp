package com.racoonberus.hotelHero.web.security;

import com.racoonberus.hotelHero.domain.User;
import com.racoonberus.hotelHero.service.UserService;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class SimpleAuthenticationSession extends AuthenticatedWebSession {

    @SpringBean
    private UserService userService;

    private User user;

    public User getUser() {
        return user;
    }

    public SimpleAuthenticationSession(Request request) {
        super(request);
        Injector.get().inject(this);
    }

    @Override
    protected boolean authenticate(String username, String password) {
        if (null == password || password.isEmpty())
            return false;

        user = userService.get(username);
        return user != null && userService.hash(password).equals(user.getPassword());
    }

    @Override
    public Roles getRoles() {
        Roles roles = new Roles();

        if (isSignedIn()) {
            roles.add("SIGNED_IN");
            roles.add(Roles.USER);
        }

        return roles;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        user = null;
    }
}
