package com.racoonberus.hotelHero.service;

import com.racoonberus.hotelHero.web.page.RegistrationTemplatePage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService implements RegistrationTemplatePage.TextSearchService {

    public List<String> getMatches(String pattern) {
        return new ArrayList<>(); // TODO: implement me
    }

}
