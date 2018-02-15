package com.gfb.hotelHero.web.page;

import com.gfb.hotelHero.domain.User;
import com.gfb.hotelHero.web.panel.AnonActionsPanel;
import com.gfb.hotelHero.web.panel.UserActionsPanel;
import com.gfb.hotelHero.web.security.SimpleAuthenticationSession;
import org.apache.wicket.markup.html.WebPage;

public abstract class BasePage extends WebPage {

    public BasePage() {
        if (getUser() != null)
            add(new UserActionsPanel("userPanel"));
        else
            add(new AnonActionsPanel("userPanel"));
    }

    static User getUser() {
        return ((SimpleAuthenticationSession) SimpleAuthenticationSession.get()).getUser();
    }

}
