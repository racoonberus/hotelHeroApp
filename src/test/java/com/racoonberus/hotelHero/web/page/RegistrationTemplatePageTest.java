package com.racoonberus.hotelHero.web.page;

import com.racoonberus.hotelHero.Application;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RegistrationTemplatePageTest {

    private WicketTester tester;

    @Before
    public void setUp() {
        ApplicationContext appctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Application app = new Application();
        app.setSpringComponentInjector(new SpringComponentInjector(app, appctx));
        tester = new WicketTester(app);
        tester.startPage(RegistrationTemplatePage.class);
    }

    @Test
    public void homepageRendersSuccessfully() {
        tester.assertRenderedPage(RegistrationTemplatePage.class);

        FormTester formTester = tester.newFormTester("form", false);

        formTester.setValue("lastName", "Петров");
        formTester.setValue("firstName", "Иван");
        formTester.setValue("nationality", "Нидерлайнды");
        formTester.setValue("birthday", "");
        formTester.setValue("gender", "");

        formTester.setValue("placeOfBirth.county", "");
        formTester.setValue("placeOfBirth.city", "");

        formTester.setValue("identityDocument.type", "");
        formTester.setValue("identityDocument.series", "");
        formTester.setValue("identityDocument.identifier", "");
        formTester.setValue("identityDocument.dateOfIssueDate", "");
        formTester.setValue("identityDocument.validityTillDate", "");

        formTester.setValue("stayConfirmingDocument.type", "");
        formTester.setValue("stayConfirmingDocument.series", "");
        formTester.setValue("stayConfirmingDocument.identifier", "");
        formTester.setValue("stayConfirmingDocument.dateOfIssueDate", "");
        formTester.setValue("stayConfirmingDocument.validityTillDate", "");

        formTester.setValue("purpose", "");
        formTester.setValue("arrivalDate", "");
        formTester.setValue("durationOfStay", "");

        formTester.setValue("migrationCard.series", "");
        formTester.setValue("migrationCard.number", "");

        formTester.submit();

        tester.assertInfoMessages("The value of 'birthday' is not a valid Date.");
//        tester.assertErrorMessages("The value of 'birthday' is not a valid Date.");
    }

}