package org.example.demo.binder.data;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class TestDataGenerator {

    public static final String[] FIRST_NAMES = {
            "Emma", "Liam", "Olivia", "Noah", "Ava", "Isabella", "Sophia", "Mia", "Charlotte", "Amelia",
            "Harper", "Evelyn", "Abigail", "Emily", "Elizabeth", "Mila", "Ella", "Avery", "Sofia", "Camila",
            "Aria", "Scarlett", "Victoria", "Madison", "Luna", "Grace", "Chloe", "Penelope", "Layla", "Riley",
            "Zoey", "Nora", "Lily", "Eleanor", "Hannah", "Lillian", "Addison", "Aubrey", "Ellie", "Stella",
            "Natalie", "Zoe", "Leah", "Hazel", "Violet", "Aurora", "Savannah", "Audrey", "Brooklyn", "Bella",
            "Claire", "Skylar", "Lucy", "Paisley", "Everly", "Anna", "Caroline", "Nova", "Genesis", "Emilia",
            "Kennedy", "Samantha", "Maya", "Willow", "Kinsley", "Naomi", "Aaliyah", "Elena", "Sarah", "Ariana",
            "Allison", "Gabriella", "Alice", "Madelyn", "Cora", "Ruby", "Eva", "Serenity", "Autumn", "Adeline",
            "Hailey", "Gianna", "Valentina", "Isla", "Eliana", "Quinn", "Nevaeh", "Ivy", "Sadie", "Piper",
            "Lydia", "Alexa", "Josephine", "Emery", "Julia", "Delilah", "Arianna", "Vivian", "Kaylee", "Sophie"
            // Add more names here if needed
    };

    public static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
            "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson",
            "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King",
            "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter",
            "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins",
            "Stewart", "Sanchez", "Morris", "Rogers", "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey",
            "Rivera", "Cooper", "Richardson", "Cox", "Howard", "Ward", "Torres", "Peterson", "Gray", "Ramirez",
            "James", "Watson", "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross",
            "Henderson", "Coleman", "Jenkins", "Perry", "Powell", "Long", "Patterson", "Hughes", "Flores", "Washington"
            // Add more last names here if needed
    };

    public static final String[] PHONE_NUMBERS = {
            "5551234", "5555678", "5559876", "5554321", "5558765", "5552468", "5551357", "5559753", "5558642", "5553179",
            "5556902", "5558147", "5552376", "5556389", "5557214", "5554897", "5559642", "5553265", "5555704", "5558123",
            "5556392", "5557426", "5551984", "5553068", "5557152", "5558213", "5556748", "5552907", "5557364", "5559046",
            "5558132", "5554629", "5552813", "5556372", "5554167", "5557823", "5555926", "5557304", "5558162", "5554793",
            "5556348", "5555712", "5551973", "5558137", "5552609", "5559702", "5553629", "5555967", "5554708", "5552853",
            "5557392", "5556147", "5552836", "5554097", "5555916", "5557324", "5556197", "5551843", "5559706", "5556823",
            "5558392", "5555274", "5556309", "5552748", "5551976", "5554062", "5556927", "5558236", "5559483", "5557316",
            "5556184", "5552906", "5557249", "5555326", "5558391", "5552064", "5553618", "5557913", "5554726", "5556381",
            "5559072", "5553184", "5554276", "5556907", "5558169", "5553426", "5555809", "5557418", "5559162", "5556783",
            "5551986", "5552947", "5557136", "5558209", "5554376", "5559652", "5556237", "5554827", "5557369", "5551892",
            "5556298", "5553907", "5558134", "5551749", "5554698", "5556198", "5558276", "5552938", "5557429", "5559063"
            // Add more phone numbers here if needed
    };

    public static final String[] DOMAIN_NAMES = {
            "example1.com", "sampledomain.net", "mywebsite.org", "testsite.com", "dummydomain.net", "demo-site.org", "webdevpro.com", "techblog.net", "code-ninja.org", "myportfolio.com",
            "innovateweb.net", "digitalbuzz.org", "geekguru.com", "techsavvy.net", "webwizard.org", "cyberworld.com", "codecrunch.net", "webdesignpro.org", "futuretech.com", "byteburst.net",
            "innovationlab.org", "datadomain.com", "webmasterpro.net", "designit.org", "techhub.com", "thecodingnet.net", "creativeminds.org", "mywebstore.com", "programmerlife.net", "webappdev.org",
            "digitaltrends.com", "webgenius.net", "codecrafters.org", "technoexpert.com", "smarttech.net", "webpreneur.org", "innovativetech.com", "cybermaster.net", "designstudio.org", "techstart.com",
            "webdevgenius.net", "programmershub.org", "digitalinnovators.com", "webdesignguru.net", "techvisionary.org", "webmaestro.com", "codegenius.net", "cybercreators.org", "webtechpro.com", "innovationzone.net"
            // Add more domain names here if needed
    };

    private static final int DATASET_SIZE = 100;

    private final PersonRepository personRepository;
    private final Random rnd = new Random();

    private Map<LocalDate, Integer> identityNumbers = new HashMap<>();

    public TestDataGenerator(PersonRepository personRepository) {
        this.personRepository = personRepository;

        for (int i = 0; i < DATASET_SIZE; ++i) {
            var personalIdentityCode = generatePersonalIdentityCode();
            var firstName = new PersonName(pickRandom(FIRST_NAMES));
            var lastName = new PersonName(pickRandom(LAST_NAMES));
            var phoneNumber = new PhoneNumber(pickRandom(PHONE_NUMBERS));
            var email = generateEmailAddress(firstName, lastName);
            var bankAccount = generateBankAccount();
            personRepository.save(new Person(personalIdentityCode, firstName, lastName, phoneNumber, email, bankAccount));
        }
    }

    private <T> T pickRandom(T[] values) {
        return values[rnd.nextInt(values.length)];
    }

    private EmailAddress generateEmailAddress(PersonName firstName, PersonName lastName) {
        return new EmailAddress("%s.%s@%s".formatted(firstName.value().toLowerCase(), lastName.value().toLowerCase(), pickRandom(DOMAIN_NAMES)));
    }

    private IBAN generateBankAccount() {
        // Note! If checksums are implemented in the IBAN class, this method will need to be fixed.
        return new IBAN(String.format("FI%016d", rnd.nextLong(10000000000000000L)));
    }

    private PersonalIdentityCode generatePersonalIdentityCode() {
        var ym = YearMonth.of(1900 + rnd.nextInt(110), 1 + rnd.nextInt(12));
        var day = 1 + rnd.nextInt(ym.atEndOfMonth().getDayOfMonth());
        var birthDate = ym.atDay(day);

        var identityNumber = identityNumbers.getOrDefault(birthDate, 2);
        identityNumbers.put(birthDate, identityNumber + 1); // In the real world, men would have an odd identity number and women an even. We don't care about that in the test data generator.

        return PersonalIdentityCode.create(birthDate, identityNumber);
    }
}
