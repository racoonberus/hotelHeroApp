package com.racoonberus.hotelHero.web.page;

import com.racoonberus.hotelHero.domain.Person;
import com.racoonberus.hotelHero.service.CityService;
import com.racoonberus.hotelHero.service.CountryService;
import com.racoonberus.tpl_reg_helper.domain.IdentityDocument;
import com.racoonberus.tpl_reg_helper.domain.RightToStayConfirmingDocument;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RegistrationTemplatePage extends BasePage {

    @SpringBean
    private CountryService countryService;

    @SpringBean
    private CityService cityService;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Person person = new Person();
        person.setBirthday(new Date(1990, 10, 12));
        person.setArrivalDate(new Date());

        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        Form form = new Form("form") {
            @Override
            protected void onSubmit() {
                // TODO: merge XLS
                System.out.print("Hello");
            }

            @Override
            protected void onError() {
                super.onError();
            }
        };

        form.setDefaultModel(new CompoundPropertyModel<>(person));

        form.add(new TextField("lastName"));
        form.add(new TextField("firstName"));

        final AutoCompleteTextField<String> nationalityTextField =
                new AutoCompleteTextField<String>("nationality") {
                    @Override
                    protected Iterator<String> getChoices(String input) {
                        return countryService.getMatches(input).iterator();
                    }
                };
        form.add(nationalityTextField);


        form.add(new TextField("birthday", new DateTimeStringModel(person, "birthday")));
        form.add(new RadioChoice<>("gender",
                Model.of(Person.Genders.MALE),
                Arrays.asList(Person.Genders.values())));
        final AutoCompleteTextField<String> countyTextField =
                new AutoCompleteTextField<String>("placeOfBirth.county") {
                    @Override
                    protected Iterator<String> getChoices(String input) {
                        return countryService.getMatches(input).iterator();
                    }
                };
        form.add(countyTextField);
        final AutoCompleteTextField<String> cityTextField =
                new AutoCompleteTextField<String>("placeOfBirth.city") {
                    @Override
                    protected Iterator<String> getChoices(String input) {
                        return cityService.getMatches(input).iterator();
                    }
                };
        form.add(cityTextField);

        form.add(new RadioChoice<>("identityDocument.type",
                Model.of(IdentityDocument.Types.PASSPORT),
                Arrays.asList(IdentityDocument.Types.values())));
        form.add(new TextField("identityDocument.series"));
        form.add(new TextField("identityDocument.identifier"));
        form.add(new TextField("identityDocument.dateOfIssueDate"));
        form.add(new TextField("identityDocument.validityTillDate"));

        form.add(new RadioChoice<>("stayConfirmingDocument.type",
                Model.of(RightToStayConfirmingDocument.Types.NONE),
                Arrays.asList(RightToStayConfirmingDocument.Types.values())));
        form.add(new TextField("stayConfirmingDocument.series"));
        form.add(new TextField("stayConfirmingDocument.identifier"));
        form.add(new TextField("stayConfirmingDocument.dateOfIssueDate"));
        form.add(new TextField("stayConfirmingDocument.validityTillDate"));

        form.add(new RadioChoice<>("purpose",
                Model.of(Person.Purposes.ANOTHER),
                Arrays.asList(Person.Purposes.values())));
        form.add(new TextField("arrivalDate"));
        form.add(new TextField("durationOfStay"));

        form.add(new TextField("migrationCard.series"));
        form.add(new TextField("migrationCard.number"));

        add(form);
    }

    private static class DateTimeStringModel extends PropertyModel<String> {
        private String format = "Y-M-d";

        public DateTimeStringModel(Object modelObject, String expression) {
            super(modelObject, expression);
        }

        public DateTimeStringModel(Object modelObject, String expression, String format) {
            super(modelObject, expression);
            this.format = format;
        }

        @Override
        public String getObject() {
            try {
                Date dt = (Date) this.getPropertyGetter().invoke(this.getTarget());
                if (null == dt) {
                    dt = new Date();
                }
                return new SimpleDateFormat(format).format(dt);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        public void setObject(String object) {
            if (null == object) return;
            try {
                Date dt = new SimpleDateFormat(format).parse(object);
                this.getPropertySetter().invoke(this.getTarget(), dt);
            } catch (ParseException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
