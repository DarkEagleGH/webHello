package test;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping("/contacts")
    public ResponseEntity<LinkedList<Contact>> contacts(@RequestParam(value="nameFilter", defaultValue="^.*[aei].*$") String nameFilter) {
        ContactsFilter contactsFilter = new ContactsFilter(new LinkedList<>(contactRepository.findWithLimit(1500)));
        HttpHeaders headers = new HttpHeaders();
        if (contactsFilter.setFilter(nameFilter)){
            contactsFilter.applyFilter();
            if (contactsFilter.getFiltered().isEmpty()) {
                headers.set("Reason", "All data was filtered");
                return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(contactsFilter.getFiltered(), HttpStatus.OK);
            }
        } else {
            headers.set("Reason", String.format("Wrong filter \'%s\'",  nameFilter));
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }
}