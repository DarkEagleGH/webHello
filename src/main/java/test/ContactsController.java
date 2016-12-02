package test;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping("/contacts")
    public Contacts contacts(@RequestParam(value="nameFilter", defaultValue="^.*[aei].*$") String nameFilter) {

        Contacts contactList = new Contacts(contactRepository.findAll());

        if (contactList != null) {
            return contactList;
        } else {
            System.out.println("contactList is null");
            System.exit(0);
        }
        return null;
//        return new Contacts(counter.incrementAndGet(), nameFilter);
    }
}