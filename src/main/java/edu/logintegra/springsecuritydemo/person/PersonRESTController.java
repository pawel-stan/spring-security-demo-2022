package edu.logintegra.springsecuritydemo.person;

import edu.logintegra.springsecuritydemo.authorities.Authority;
import edu.logintegra.springsecuritydemo.authorities.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/people")
public class PersonRESTController {

    private final PersonRepository personRepository;

    private final AuthorityRepository authorityRepository;

    @Autowired
    public PersonRESTController(PersonRepository personRepository, AuthorityRepository authorityRepository) {
        this.personRepository = personRepository;
        this.authorityRepository = authorityRepository;
    }

    @GetMapping("/")
    public Iterable<Person> list() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> find(@PathVariable Long id) {
        return personRepository.findById(id);
    }

    @PostMapping("/")
    public Person save(@RequestParam String username,
                       @RequestParam String password) {
        Person person = new Person(username,
                password,
                true);

        return personRepository.save(person);
    }

    @GetMapping("/show")
    public Optional<Person> show(@RequestParam String username) {
        return personRepository.findByUsername(username);
    }

    @GetMapping("/show-enabled")
    public Optional<Person> showEnabled(@RequestParam String username) {
        return personRepository.findByUsernameAndEnabled(username, true);
    }

    @GetMapping("/show-by-username-or-password")
    public Iterable<Person> showByUsernameOrPassword(@RequestParam String username, @RequestParam String password) {
        return personRepository.findAllByUsernameOrPassword(username, password);
    }

    /**
     * /people/list-created-after?dateString=2021-02-13
     *
     * @param dateString
     * @return
     */
    @GetMapping("/list-created-after")
    public Iterable<Person> listCreatedAfter(@RequestParam String dateString) throws ParseException {
        // 1. Tworzymy datę na podstawie przesłanego tekstu
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

        // 2. Wykonujemy zapytanie do bazy
        return personRepository.findAllByDateCreatedAfter(date);
    }

    @GetMapping("/authorities")
    public Iterable<Authority> getAuthorities(@RequestParam String username) {
        return authorityRepository.findAllByUsername(username);
    }

    @PostMapping("{username}/authorities")
    public Person addAuthority(@PathVariable String username, @RequestParam String authority) {
        // 1. Wyszukujemy użytkownika po username

        Optional<Person> optionalPerson = personRepository.findByUsername(username);

        if (optionalPerson.isEmpty()) {
            throw new InvalidParameterException("Nie znaleźliśmy użytkownika");
        }

        // 2. Wyszykujemy uprawnienie po authority

        Optional<Authority> optionalAuthorityInstance = authorityRepository.findByAuthority(authority);
        if (optionalAuthorityInstance.isEmpty()) {
            throw new InvalidParameterException("Nie znaleźliśmy uprawnienia!");
        }

        // 3. Dodajemy to uprawnienie do listy uprawnień użytkownika
        Person person = optionalPerson.get();
        person.authorities.add(optionalAuthorityInstance.get());

        // 4. Zapisujemy użytkownika
        personRepository.save(person);

        return person;
    }

    @DeleteMapping("{username}/authorities")
    public Person removeAuthority(@PathVariable String username, @RequestParam String authority) {
        // 1. Wyszukujemy użytkownika po username

        Optional<Person> optionalPerson = personRepository.findByUsername(username);

        if (optionalPerson.isEmpty()) {
            throw new InvalidParameterException("Nie znaleźliśmy użytkownika");
        }

        // 2. Wyszykujemy uprawnienie po authority

        Optional<Authority> optionalAuthorityInstance = authorityRepository.findByAuthority(authority);
        if (optionalAuthorityInstance.isEmpty()) {
            throw new InvalidParameterException("Nie znaleźliśmy uprawnienia!");
        }

        // 3. Dodajemy to uprawnienie do listy uprawnień użytkownika
        Person person = optionalPerson.get();
        person.authorities.remove(optionalAuthorityInstance.get());

        // 4. Zapisujemy użytkownika
        personRepository.save(person);

        return person;
    }
}
