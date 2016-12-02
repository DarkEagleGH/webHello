package test;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactsController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/contacts")
    public Contacts contacts(@RequestParam(value="nameFilter", defaultValue="^.*[aei].*$") String nameFilter) {

        ContactDaoImpl contactDaoImpl = new ContactDaoImpl();
        ContactFilter filter = new ContactFilter();
        Contacts contactList = new Contacts(contactDaoImpl.findWithFilter(filter));
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