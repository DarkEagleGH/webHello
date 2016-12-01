package test;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactsController {

//    private static final String template = "%s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/contacts")
    public Contacts contacts(@RequestParam(value="nameFilter", defaultValue="^.*[aei].*$") String nameFilter) {
        return new Contacts(counter.incrementAndGet(), nameFilter);
    }
}