package hello;

import com.vaadin.flow.router.Route;


@Route
public class MainView extends PageView {

    public MainView() {

        initHeaders();
        initViewbody();
        initFooters();

    }
}



