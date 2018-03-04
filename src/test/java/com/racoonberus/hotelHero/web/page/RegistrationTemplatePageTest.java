package com.racoonberus.hotelHero.web.page;

import com.racoonberus.hotelHero.Application;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationTemplatePageTest {

    private WicketTester tester;

    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Application app = new Application();
        app.setSpringComponentInjector(new SpringComponentInjector(app, context));
        tester = new WicketTester(app);
        tester.startPage(RegistrationTemplatePage.class);
    }

    @Test
    public void homepageRendersSuccessfully() {

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        tester.assertRenderedPage(RegistrationTemplatePage.class);

        FormTester formTester = tester.newFormTester("form", false);

        formTester.setValue("lastName", "Петров");
        formTester.setValue("firstName", "Иван");
        formTester.setValue("nationality", "Нидерланды");
        formTester.setValue("birthday", "1991-10-05");
        formTester.select("gender", 0);

        formTester.setValue("placeOfBirth.county", "Китай");
        formTester.setValue("placeOfBirth.city", "Гонконг");

        formTester.select("identityDocument.type", 0);
        formTester.setValue("identityDocument.series", "");
        formTester.setValue("identityDocument.identifier", "");
        formTester.setValue("identityDocument.dateOfIssueDate", "");
        formTester.setValue("identityDocument.validityTillDate", "");

        formTester.select("stayConfirmingDocument.type", 0);
        formTester.setValue("stayConfirmingDocument.series", "");
        formTester.setValue("stayConfirmingDocument.identifier", "");
        formTester.setValue("stayConfirmingDocument.dateOfIssueDate", "");
        formTester.setValue("stayConfirmingDocument.validityTillDate", "");

        formTester.select("purpose", 1);
        String adDate = f.format(new Date(new Date().getTime() - 2 * 24 * 60 * 1000));
        formTester.setValue("arrivalDate", adDate);
        String dosDate = f.format(new Date(new Date().getTime() + 5 * 24 * 60 * 1000));
//        dosDate = "2018-01-01";
        formTester.setValue("durationOfStay", dosDate);

        formTester.setValue("migrationCard.series", "4567");
        formTester.setValue("migrationCard.number", "123456");

        formTester.submit();

        tester.assertInfoMessages("Registration document created succesfully!");
//        tester.assertErrorMessages("The value of 'birthday' is not a valid Date.");
    }

}