package org.example.demo.binder.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

final class FormUtils {

    private FormUtils() {
    }

    public static HorizontalLayout createFormLine(Component... components) {
        var layout = new HorizontalLayout(components);
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);
        return layout;
    }
}
