package com.gfb.hotelHero.web.page;

import com.gfb.hotelHero.domain.User;
import com.gfb.hotelHero.service.UserService;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

//@AuthorizeInstantiation(Roles.USER)
public class ProfilePage extends BasePage {

    @SpringBean
    private UserService userService;

    private User user;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        user = getUser();

        Form form = new Form("form") {
            @Override
            protected void onSubmit() {
                userService.edit(user);
            }
        };

        form.setDefaultModel(new CompoundPropertyModel<>(user));

        form.add(new TextField("lastName"));
        form.add(new TextField("firstName"));
        form.add(new TextField("middleName"));
        form.add(new EmailTextField("email"));
        form.add(new TextField("mobilePhone"));

        add(form);
    }
}
