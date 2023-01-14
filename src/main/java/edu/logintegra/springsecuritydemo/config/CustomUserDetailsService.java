package edu.logintegra.springsecuritydemo.config;

import edu.logintegra.springsecuritydemo.authorities.Authority;
import edu.logintegra.springsecuritydemo.person.Person;
import edu.logintegra.springsecuritydemo.person.PersonRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Pobieramy użytkownika z bazy

        Optional<Person> person = personRepository.findByUsername(username);

        if (person.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        Person existingPerson = person.get();

        List<GrantedAuthority> authorities = findUserAuthorities(existingPerson);

        // Wysyłamy go do Spring Security w odpowiedniej formie

        return new User(existingPerson.getUsername(),
                existingPerson.getPassword(),
                authorities);
    }

    private List<GrantedAuthority> findUserAuthorities(Person person) {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Authority authority : person.getAuthorities()) {
            String authName = authority.getAuthority().name();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authName);
            grantedAuthorities.add(grantedAuthority);
        }

        return new ArrayList<>(grantedAuthorities);
    }
}
