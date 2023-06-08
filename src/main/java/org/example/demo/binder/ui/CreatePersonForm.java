package org.example.demo.binder.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.function.SerializableConsumer;
import jakarta.annotation.Nullable;
import org.example.demo.binder.data.PersonId;
import org.example.demo.binder.service.PersonService;
import org.example.demo.binder.ui.converter.EmailAddressConverter;
import org.example.demo.binder.ui.converter.IBANConverter;
import org.example.demo.binder.ui.converter.PersonNameConverter;
import org.example.demo.binder.ui.converter.PhoneNumberConverter;
import org.example.demo.binder.ui.validator.PersonalIdentityCodeUniquenessValidator;
import org.example.demo.binder.ui.validator.PersonalIdentityCodeValidator;

import java.util.Objects;

import static org.example.demo.binder.ui.FormUtils.createFormLine;

public class CreatePersonForm extends VerticalLayout {

    private final PersonService personService;
    private final SerializableConsumer<PersonId> onSaveCallback;
    private final Binder<PersonFormBean> binder = new Binder<>();

    public CreatePersonForm(PersonService personService, @Nullable SerializableConsumer<PersonId> onSaveCallback) {
        this.personService = Objects.requireNonNull(personService);
        this.onSaveCallback = onSaveCallback;

        var firstName = new TextField("First name");
        binder.forField(firstName)
                .asRequired()
                .withNullRepresentation("")
                .withConverter(PersonNameConverter.INSTANCE)
                .bind(PersonFormBean::getFirstName, PersonFormBean::setFirstName);

        var lastName = new TextField("Last name");
        binder.forField(lastName)
                .asRequired()
                .withNullRepresentation("")
                .withConverter(PersonNameConverter.INSTANCE)
                .bind(PersonFormBean::getLastName, PersonFormBean::setLastName);

        var pic = new TextField("Personal identity code");
        binder.forField(pic)
                .asRequired()
                .withNullRepresentation("")
                .withValidator(PersonalIdentityCodeValidator.INSTANCE)
                .withValidator(new PersonalIdentityCodeUniquenessValidator(personService))
                .bind(PersonFormBean::getPic, PersonFormBean::setPic);

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

        var bankAccount = new TextField("Bank Account");
        binder.forField(bankAccount)
                .withNullRepresentation("")
                .withConverter(IBANConverter.INSTANCE)
                .bind(PersonFormBean::getBankAccount, PersonFormBean::setBankAccount);
        // TODO BankAccountOwnershipValidator!

        var save = new Button("Create", event -> save());
        var cancel = new Button("Cancel", event -> hide());

        var layout = new FormLayout(firstName, lastName, pic, phoneNumber, email, bankAccount);
        setPadding(false);
        add(new H2("Create Person"), layout, createFormLine(save, cancel));

    }

    private void save() {
        if (binder.validate().isOk()) {
            var personId = personService.createPerson(binder.getBean().toCreatePersonCommand());
            if (onSaveCallback != null) {
                onSaveCallback.accept(personId);
            }
            Notification.show("Person created successfully");
            hide();
        }
    }

    public void show() {
        binder.setBean(new PersonFormBean());
        setVisible(true);
    }

    public void hide() {
        binder.setBean(null);
        setVisible(false);
    }
}
