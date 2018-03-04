package com.racoonberus.hotelHero.web.page;

import com.racoonberus.hotelHero.Application;
import com.racoonberus.hotelHero.domain.Person;
import com.racoonberus.hotelHero.service.CityService;
import com.racoonberus.hotelHero.service.CountryService;
import com.racoonberus.tplRegHelper.XlsDecorator;
import com.racoonberus.tplRegHelper.domain.IdentityDocument;
import com.racoonberus.tplRegHelper.domain.RightToStayConfirmingDocument;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RegistrationTemplatePage extends BasePage {

    @SpringBean
    private CountryService countryService;

    @SpringBean
    private CityService cityService;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        File proto = new File(RegistrationTemplatePage.class.getResource("/blank.xlsx").getFile());
        if (!proto.exists()) error("ZHOPA!");

        Person person = new Person();
        person.setArrivalDate(new Date());


        Form form = new Form("form") {
            @Override
            protected void onSubmit() {
                try {
                    String outDir = System.getProperty("user.home") + "/tpl-reg-helper";
                    String fn = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())
                            + "-"
                            + person.getFullName();
                    File outFile = getBlankCopy(proto, outDir, fn);
                    Workbook book = new XSSFWorkbook(new FileInputStream(outFile));
                    new XlsDecorator(book).write(person);
                    book.write(new FileOutputStream(outFile));
                    book.close();
                    info("Registration document created succesfully!");
                    info("See: " + outFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    error(e.getMessage());
                }
            }

            @Override
            protected void onError() {
                super.onError();
            }
        };

        CompoundPropertyModel<Person> model = new CompoundPropertyModel<>(person);
        form.setDefaultModel(model);

        form.add(new TextField("lastName"));
        form.add(new TextField("firstName"));
        form.add(new BoostedAutoCompleteTextField("nationality", countryService));
        form.add(new DateTextField("birthday", "yyyy-MM-dd"));
        form.add(new EnumRadioChoice("gender", Person.Genders.class));
        form.add(new BoostedAutoCompleteTextField("placeOfBirth.county", countryService));
        form.add(new BoostedAutoCompleteTextField("placeOfBirth.city", cityService));

        form.add(new EnumRadioChoice("identityDocument.type", IdentityDocument.Types.class));
        form.add(new TextField("identityDocument.series"));
        form.add(new TextField("identityDocument.identifier"));
        form.add(new DateTextField("identityDocument.dateOfIssueDate", "yyyy-MM-dd"));
        form.add(new DateTextField("identityDocument.validityTillDate", "yyyy-MM-dd"));

        form.add(new EnumRadioChoice(
                "stayConfirmingDocument.type", RightToStayConfirmingDocument.Types.class));
        form.add(new TextField("stayConfirmingDocument.series"));
        form.add(new TextField("stayConfirmingDocument.identifier"));
        form.add(new DateTextField("stayConfirmingDocument.dateOfIssueDate", "yyyy-MM-dd"));
        form.add(new DateTextField("stayConfirmingDocument.validityTillDate", "yyyy-MM-dd"));

        form.add(new EnumRadioChoice("purpose", Person.Purposes.class));
        form.add(new DateTextField("arrivalDate", "yyyy-MM-dd"));
        form.add(new DateTextField("durationOfStay", "yyyy-MM-dd"));

        form.add(new RequiredTextField<>("migrationCard.series"));
        form.add(new RequiredTextField<>("migrationCard.number"));

        add(form);
    }

    public interface TextSearchService {
        List<String> getMatches(String query);
    }

    private static class BoostedAutoCompleteTextField extends AutoCompleteTextField<String> {
        private TextSearchService service;

        public BoostedAutoCompleteTextField(String id, TextSearchService service) {
            super(id);
            this.service = service;
        }

        @Override
        protected Iterator<String> getChoices(String s) {
            return null;
        }
    }

    private static class EnumRadioChoice extends RadioChoice {
        public EnumRadioChoice(String id, Class<? extends Enum> e) {
            super(id, Model.of(e.getEnumConstants()[0]), Arrays.asList(e.getEnumConstants()));
        }

        public EnumRadioChoice(String id, IModel model, List choices) {
            super(id, model, choices);
        }
    }

    private static File getBlankCopy(File proto, String distDir, String docFileName) throws IOException {
        File bfile = new File(distDir + "/" + docFileName + ".xlsx");

        InputStream inStream = new FileInputStream(proto);
        OutputStream outStream = new FileOutputStream(bfile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inStream.read(buffer)) > 0) {
            outStream.write(buffer, 0, length);
        }

        inStream.close();
        outStream.close();

        return bfile;
    }
}
