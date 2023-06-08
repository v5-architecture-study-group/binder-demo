package org.example.demo.binder.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.function.SerializableRunnable;
import jakarta.annotation.Nullable;
import org.example.demo.binder.service.PersonDPO;
import org.example.demo.binder.service.PersonService;
import org.example.demo.binder.ui.converter.EmailAddressConverter;
import org.example.demo.binder.ui.converter.IBANConverter;
import org.example.demo.binder.ui.converter.PersonNameConverter;
import org.example.demo.binder.ui.converter.PhoneNumberConverter;
import org.example.demo.binder.ui.validator.BankAccountOwnershipValidator;
import org.example.demo.binder.ui.validator.DelegatingValidator;

import java.util.Objects;

import static org.example.demo.binder.ui.FormUtils.createFormLine;

public class UpdatePersonForm extends VerticalLayout {

    private final PersonService personService;
    private final SerializableRunnable onSaveCallback;
    private final Binder<PersonFormBean> binder = new Binder<>();
    private PersonDPO person;

    public UpdatePersonForm(PersonService personService, @Nullable SerializableRunnable onSaveCallback) {
        this.personService = Objects.requireNonNull(personService);
        this.onSaveCallback = onSaveCallback;
        var firstName = new TextField("First name");
        binder.forField(firstName)
                .asRequired()
                .withConverter(PersonNameConverter.INSTANCE)
                .bind(PersonFormBean::getFirstName, PersonFormBean::setFirstName);

        var lastName = new TextField("Last name");
        binder.forField(lastName)
                .asRequired()
                .withConverter(PersonNameConverter.INSTANCE)
                .bind(PersonFormBean::getLastName, PersonFormBean::setLastName);

        var phoneNumber = new TextField("Phone");
        binder.forField(phoneNumber)
                .withNullRepresentation("")
                .withConverter(PhoneNumberConverter.INSTANCE)
                .bind(PersonFormBean::getPhoneNumber, PersonFormBean::setPhoneNumber);

        var email = new TextField("E-mail");
        binder.forField(email)
                .withNullRepresentation("")
                .withConverter(EmailAddressConverter.INSTANCE)
                .bind(PersonFormBean::getEmailAddress, PersonFormBean::setEmailAddress);

        var pic = new TextField("Personal identity code");
        pic.setPlaceholder("Hidden");
        binder.forField(pic)
                .bindReadOnly(PersonFormBean::getPic);

        var bankAccount = new TextField("Bank Account");
        binder.forField(bankAccount)
                .withNullRepresentation("")
                .withConverter(IBANConverter.INSTANCE)
                .withValidator(DelegatingValidator.fromSupplier(this::getBankAccountOwnershipValidator))
                .bind(PersonFormBean::getBankAccount, PersonFormBean::setBankAccount);

        var showPic = new Button("Show", event -> showPIC());

        var save = new Button("Save", event -> save());
        var revert = new Button("Revert", event -> revert());

        var layout = new FormLayout(firstName, lastName, createFormLine(pic, showPic), phoneNumber, email, bankAccount);
        setPadding(false);
        add(new H2("Person Details"), layout, createFormLine(save, revert));
    }

    private @Nullable BankAccountOwnershipValidator getBankAccountOwnershipValidator() {
        return person == null ? null : new BankAccountOwnershipValidator(personService, person.id());
    }

    public void show(PersonDPO person) {
        this.person = Objects.requireNonNull(person, "person must not ben null");
        binder.setBean(PersonFormBean.fromPersonDPO(person));
        setVisible(true);
    }

    public void hide() {
        this.person = null;
        binder.setBean(null);
        setVisible(false);
    }

    private void showPIC() {
        if (person != null) {
            personService.findPersonalIdentityCode(person.id()).ifPresent(code -> {
                binder.getBean().setPic(code.value());
                binder.refreshFields();
            });
        }
    }

    private void save() {
        if (person != null && binder.validate().isOk()) {
            personService.updatePerson(binder.getBean().toUpdatePersonCommand(person.id()));
            if (onSaveCallback != null) {
                onSaveCallback.run();
            }
        }
    }

    private void revert() {
        binder.setBean(PersonFormBean.fromPersonDPO(person));
    }
}
