package com.gfb.hotelHero.web.page;

import com.gfb.hotelHero.domain.User;
import com.gfb.hotelHero.service.UserService;
import com.gfb.hotelHero.web.security.SimpleAuthenticationSession;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;

public class SignUpPage extends WebPage {

    @SpringBean
    private UserService userService;

    public SignUpPage() {

        Injector.get().inject(this);

        if (BasePage.getUser() != null)
            redirectToInterceptPage(new IndexPage());

        Form form = new Form("form") {
            @Override
            protected void onSubmit() {
                SignUpModel m = (SignUpModel) this.getModel().getObject();

                if (!m.password1.equals(m.password2)) {
                    error("Password fields has a different values");
                }

                User user = new User(m.email, userService.hash(m.password1));
                user.setLastName(m.lastName);
                user.setFirstName(m.firstName);
                userService.add(user);

                SimpleAuthenticationSession.get().signIn(m.email, m.password1);
                redirectToInterceptPage(new IndexPage());
            }
        };
        form.setDefaultModel(new CompoundPropertyModel<>(new SignUpModel()));

        form.add(new TextField("firstName"));
        form.add(new TextField("lastName"));
        form.add(new EmailTextField("email"));
        form.add(new PasswordTextField("password1"));
        form.add(new PasswordTextField("password2"));

        add(form);
    }

    private class SignUpModel implements Serializable {
        private String firstName;
        private String lastName;
        private String email;
        private String password1;
        private String password2;
    }

}
