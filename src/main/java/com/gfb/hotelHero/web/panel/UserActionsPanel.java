package com.gfb.hotelHero.web.panel;

import com.gfb.hotelHero.domain.User;
import com.gfb.hotelHero.web.page.IndexPage;
import com.gfb.hotelHero.web.security.SimpleAuthenticationSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class UserActionsPanel extends Panel {

    public UserActionsPanel(String id) {
        super(id);

        User u = ((SimpleAuthenticationSession) SimpleAuthenticationSession.get()).getUser();
        add(new Label("username", u != null ? u.getUsername() : "anon."));

        Link logout = new Link("logout") {
            @Override
            public void onClick() {
                getSession().invalidate();
                redirectToInterceptPage(new IndexPage());
            }
        };
        add(logout);
    }

}
