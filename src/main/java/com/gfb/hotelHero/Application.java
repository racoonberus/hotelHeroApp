package com.gfb.hotelHero;

import com.gfb.hotelHero.web.page.*;
import com.gfb.hotelHero.web.security.SimpleAuthenticationSession;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class Application extends AuthenticatedWebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return IndexPage.class;
    }

    @Override
    protected void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        getSecuritySettings().setAuthorizationStrategy(new MetaDataRoleAuthorizationStrategy(this));
        MetaDataRoleAuthorizationStrategy.authorize(ProfilePage.class, Roles.USER);

//        getApplicationSettings().setAccessDeniedPage(MyCustomAccessDeniedPage.class);

        mountPage("sign-in", SignInPage.class);
        mountPage("sign-up", SignUpPage.class);
        mountPage("profile", ProfilePage.class);
        mountPage("forgot-password", ForgotPasswordPage.class);
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return SimpleAuthenticationSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return SignInPage.class;
    }

}
