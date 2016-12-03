package test;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping("/hello/contacts")
    public ResponseEntity<LinkedList<Contact>> contacts(@RequestParam(value="nameFilter", defaultValue="") String nameFilter) {
        if (logger.isDebugEnabled()) {
            logger.debug("nameFilter: \"{}\"", nameFilter);
        }
        ContactsFilter contactsFilter = new ContactsFilter(new LinkedList<>(contactRepository.findWithLimit(1500)));
//        ContactsFilter contactsFilter = new ContactsFilter(new LinkedList<>(contactRepository.findAll()));

        if (nameFilter.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Empty nameFilter. Return all data without filtering");
            }

            return new ResponseEntity<>(contactsFilter.getFiltered(), HttpStatus.PARTIAL_CONTENT);
//            return new ResponseEntity<>(contactsFilter.getFiltered(), HttpStatus.OK);
        }
        if (contactsFilter.setFilter(nameFilter)){
            contactsFilter.applyFilter();
            if (contactsFilter.getFiltered().isEmpty()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("All data was filtered. Response 204 No content");
                }
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Return 200 OK");
                }
                return new ResponseEntity<>(contactsFilter.getFiltered(), HttpStatus.OK);
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("NameFilter: \"{}\" is not valid regex ({}). Response 400 Bad request", nameFilter,
                            contactsFilter.getLastActionError());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}