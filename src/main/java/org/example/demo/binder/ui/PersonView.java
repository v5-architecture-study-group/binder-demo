package org.example.demo.binder.ui;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.example.demo.binder.data.*;
import org.example.demo.binder.service.PersonDPO;
import org.example.demo.binder.service.PersonService;


@Route("")
public class PersonView extends HorizontalLayout {

    private final PersonService personService;
    private final UpdatePersonForm updateForm;
    private final CreatePersonForm createForm;
    private final Grid<PersonDPO> personGrid;

    public PersonView(PersonService personService) {
        this.personService = personService;

        updateForm = new UpdatePersonForm(personService, this::refresh);
        createForm = new CreatePersonForm(personService, this::refreshAndSelect);

        personGrid = new Grid<>();
        personGrid.addColumn(MappableValueProvider.create(PersonDPO::firstName).map(PersonName::value)).setHeader("First name");
        personGrid.addColumn(MappableValueProvider.create(PersonDPO::lastName).map(PersonName::value)).setHeader("Last name");
        personGrid.addColumn(MappableValueProvider.create(PersonDPO::phoneNumber).map(PhoneNumber::value)).setHeader("Phone Number");
        personGrid.addColumn(MappableValueProvider.create(PersonDPO::emailAddress).map(EmailAddress::value)).setHeader("E-mail Address");
        personGrid.addColumn(MappableValueProvider.create(PersonDPO::bankAccount).map(IBAN::value)).setHeader("Bank Account");
        personGrid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresentOrElse(updateForm::show, updateForm::hide));
        personGrid.setSizeFull();

        updateForm.setWidth("400px");
        updateForm.setHeightFull();

        createForm.setWidth("400px");
        createForm.setHeightFull();

        var addButton = new Button("Add Person", event -> addPeron());
        var gridLayout = new VerticalLayout(personGrid, addButton);
        gridLayout.setPadding(false);
        gridLayout.setSizeFull();

        add(gridLayout, updateForm, createForm);

        updateForm.hide();
        createForm.hide();

        setPadding(true);
        setSizeFull();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        refresh();
    }

    private void addPeron() {
        personGrid.deselectAll();
        createForm.show();
    }

    private void refresh() {
        personGrid.setItems(personService.findAllPeople());
    }

    private void refreshAndSelect(PersonId personId) {
        var items = personService.findAllPeople();
        personGrid.setItems(items);
        items.stream().filter(dpo -> dpo.id().equals(personId)).findFirst().ifPresent(personGrid::select);
    }
}
