package org.example.demo.binder.service;

import org.example.demo.binder.data.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDPO> findAllPeople() {
        return personRepository
                .findAll()
                .stream()
                .map(p -> new PersonDPO(p.id(), p.firstName(), p.lastName(), p.phoneNumber(), p.emailAddress(), p.bankAccount()))
                .toList();
    }

    public PersonId createPerson(CreatePersonCommand createPersonCommand) {
        return personRepository.saveAndFlush(new Person(
                createPersonCommand.pic(),
                createPersonCommand.firstName(),
                createPersonCommand.lastName(),
                createPersonCommand.phoneNumber(),
                createPersonCommand.emailAddress(),
                createPersonCommand.bankAccount()
        )).id();
    }

    public void updatePerson(UpdatePersonCommand updatePersonCommand) {
        var person = personRepository.findById(updatePersonCommand.personId().toLong()).orElseThrow(PersonNotFoundException::new);
        person.setFirstName(updatePersonCommand.firstName());
        person.setLastName(updatePersonCommand.lastName());
        person.setPhoneNumber(updatePersonCommand.phoneNumber());
        person.setEmailAddress(updatePersonCommand.emailAddress());
        person.setBankAccount(updatePersonCommand.bankAccount());
        personRepository.saveAndFlush(person);
    }

    public Optional<PersonalIdentityCode> findPersonalIdentityCode(PersonId personId) {
        return personRepository.findById(personId.toLong()).map(Person::personalIdentityCode);
    }

    public boolean isBankAccountOwnedByPerson(PersonId personId, IBAN bankAccountNumber) {
        return bankAccountNumber.value().endsWith("%016d".formatted(personId.toLong()));
    }

    public boolean personalIdentityCodeExists(PersonalIdentityCode pic) {
        return personRepository.existsByPersonalIdentityCode(pic.value());
    }
}
