package com.racoonberus.hotelHero.web.page;

import com.racoonberus.hotelHero.domain.User;
import com.racoonberus.hotelHero.web.panel.AnonActionsPanel;
import com.racoonberus.hotelHero.web.panel.UserActionsPanel;
import com.racoonberus.hotelHero.web.security.SimpleAuthenticationSession;
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
