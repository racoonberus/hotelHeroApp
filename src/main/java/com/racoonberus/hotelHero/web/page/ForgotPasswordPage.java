package com.racoonberus.hotelHero.web.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

public class ForgotPasswordPage extends WebPage {

    private String username;

    public ForgotPasswordPage() {
        StatelessForm form = new StatelessForm("form") {
            @Override
            protected void onSubmit() {
                // TODO: send code to email
            }
        };

        form.setDefaultModel(new CompoundPropertyModel<>(this));

        form.add(new TextField("username"));

        add(form);
    }

}
