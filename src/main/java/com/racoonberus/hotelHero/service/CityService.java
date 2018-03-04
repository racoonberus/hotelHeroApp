package com.racoonberus.hotelHero.service;

import com.racoonberus.hotelHero.FileFetcher;
import com.racoonberus.hotelHero.web.page.RegistrationTemplatePage;
import org.apache.wicket.util.string.Strings;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class CityService implements RegistrationTemplatePage.TextSearchService {

    public static final int HOLDER_LIMIT = 10;

    public List<String> getMatches(String pattern) {
        List<String> choices = new ArrayList<>(HOLDER_LIMIT);

        if (Strings.isEmpty(pattern)) {
            return choices;
        }

        File in = new File(getClass().getResource("/cities.txt").getFile());

        FileFetcher.fetch(in, pattern, HOLDER_LIMIT, choices);

        return choices;
    }

}
