package com.racoonberus.hotelHero.service;

import com.racoonberus.hotelHero.web.page.RegistrationTemplatePage;
import org.apache.wicket.util.string.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class CountryService implements RegistrationTemplatePage.TextSearchService {

    public List<String> getMatches(String pattern) {

        if (Strings.isEmpty(pattern)) {
            List<String> emptyList = Collections.emptyList();
            return emptyList;
        }

        List<String> choices = new ArrayList<>(10);
        Locale[] locales = Locale.getAvailableLocales();
        for (final Locale locale : locales) {
            final String country = locale.getDisplayCountry();
            if (country.toUpperCase().startsWith(pattern.toUpperCase())) {
                choices.add(country);
                if (choices.size() == 10) {
                    break;
                }
            }
        }

        return choices; // TODO: implement me
    }

}
