package com.racoonberus.hotelHero.web.form;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTextField extends TextField {
    private String format = "Y-M-d";

    public DateTextField(String id) {
        super(id);
    }

    public DateTextField(String id, IModel model) {
        super(id, model);
    }

    @Override
    protected Object convertValue(String[] value) throws ConversionException {
//        return super.convertValue(value);

        if (!(value != null && value.length > 0)) return null;

//        if (null == value[0] || value[0].isEmpty()) return;
        try {
            return new SimpleDateFormat(format).parse(value[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected String getModelValue() {
//        return super.getModelValue();
        if (null == this.getModelObject()) return "";

        return new SimpleDateFormat(format).format(this.getModelObject());
    }
}
