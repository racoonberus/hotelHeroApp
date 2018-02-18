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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.*;

public class RegistrationTemplatePage extends BasePage {

    @SpringBean
    private CountryService countryService;

    @SpringBean
    private CityService cityService;

    private Person person;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        person = new Person();
        person.setArrivalDate(new Date());

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


        form.add(new TextField("birthday"));
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
}
