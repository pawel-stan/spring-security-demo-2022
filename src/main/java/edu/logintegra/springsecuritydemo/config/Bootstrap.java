package edu.logintegra.springsecuritydemo.config;

import edu.logintegra.springsecuritydemo.authorities.Authority;
import edu.logintegra.springsecuritydemo.authorities.AuthorityName;
import edu.logintegra.springsecuritydemo.authorities.AuthorityRepository;
import edu.logintegra.springsecuritydemo.person.PersonService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Bootstrap implements InitializingBean {

    private final AuthorityRepository authorityRepository;
    private final PersonService personService;

    public Bootstrap(AuthorityRepository authorityRepository,
                     PersonService personService) {
        this.authorityRepository = authorityRepository;
        this.personService = personService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (AuthorityName authorityName : AuthorityName.values()) {
            Optional<Authority> authority = authorityRepository.findByAuthority(authorityName);

            if (authority.isEmpty()) {
                Authority a = new Authority(authorityName);
                authorityRepository.save(a);
            }
        }

        personService.prepareAdminUser();
    }
}
