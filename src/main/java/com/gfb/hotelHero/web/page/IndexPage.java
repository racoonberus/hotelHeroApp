package com.gfb.hotelHero.web.page;

import org.apache.wicket.markup.html.basic.Label;

public class IndexPage extends BasePage {

    public IndexPage() {
        super();
        add(new Label("message", "Hello World!"));
    }

}
