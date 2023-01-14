package edu.logintegra.springsecuritydemo.person;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonRepository personRepository;
    private final PersonService personService;

    public PeopleController(PersonRepository personRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    @GetMapping("/")
    @Secured("ROLE_USERS_TAB")
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("people/index");
        modelAndView.addObject("people", personRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    @Secured("ROLE_CREATE_USER")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("people/create");
        modelAndView.addObject("person", new Person());
        return modelAndView;
    }

    @PostMapping("/save")
    @Secured("ROLE_CREATE_USER")
    ModelAndView save(@ModelAttribute Person person) {

        personService.savePerson(person);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/people/");

        return modelAndView;
    }
}
