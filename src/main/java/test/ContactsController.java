package test;

import java.util.LinkedList;
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
    public LinkedList<Contact> contacts(@RequestParam(value="nameFilter", defaultValue="^.*[aei].*$") String nameFilter) {

        LinkedList<Contact> contactList = new LinkedList<>(contactRepository.findWithLimit(1500));

        return contactList;
//        return new Contacts(counter.incrementAndGet(), nameFilter);
    }
}