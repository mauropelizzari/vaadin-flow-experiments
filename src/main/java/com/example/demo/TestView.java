package com.example.demo;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author mpelizzari on 2019-10-12
 * @project apptracking
 */
@Slf4j
@Route(value = "test")
public class TestView extends VerticalLayout {

    private Grid<TestPojo> personGrid;

    @Autowired
    private TestService testService;

    public TestView() {
        initGui();
    }

    private void initGui() {
        personGrid = new TestGrid(10);

        SerializableFunction<TestPojo,?> rendererFn = (tp) -> {
            Div div = new Div();
            div.setHeight("220px");
            Label l = new Label();
            l.setText(tp.getTitle() + " - " + tp.getDescription());
            div.add(l);
            return div;
        };
        personGrid.addColumn(new ComponentRenderer(rendererFn)).setHeader("Test");
        add(personGrid);
    }

    @Override
    public void onAttach(AttachEvent beforeEnterEvent) {
        CallbackDataProvider<TestPojo, Void> dataProvider = DataProvider.fromCallbacks(
                query -> {
                    // The index of the first item to load
                    int offset = query.getOffset();

                    // The number of items to load
                    int limit = query.getLimit();

                    log.info("offset = {} ; limit = {}",offset,limit);

                    List<TestPojo> elements = testService.getElements(offset, limit);
                    return elements.stream();
                },
                // Second callback fetches the number of items for a query
                query -> {
                    int count = testService.countTotalElements();
                    return count;
                }
        );
        personGrid.setDataProvider(dataProvider);

    }


}
