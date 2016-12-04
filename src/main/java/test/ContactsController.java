package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ContactsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Pattern pattern;
    int mb = 1024 * 1024;
    Runtime instance = Runtime.getRuntime();


    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping("/hello/contacts")
    public ResponseEntity<List<Contact>> contacts(@RequestParam(value="nameFilter", defaultValue="") String nameFilter,
                                                  HttpServletResponse response) {
        logger.debug("Used memory: \"{}\"", (instance.totalMemory() - instance.freeMemory()) / mb);

        if (logger.isDebugEnabled()) {
            logger.debug("nameFilter: \"{}\"", nameFilter);
        }
        try {
            pattern = Pattern.compile(nameFilter);
        } catch (PatternSyntaxException exception) {
            if (logger.isDebugEnabled()) {
                logger.debug("NameFilter: \"{}\" is not valid regex ({}). Response 400 Bad request", nameFilter,
                        exception.getDescription());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            ServletOutputStream os = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            long cnt = contactRepository.getCount();
            long pos = 0;
            long lim = 1000000;
            while (pos < cnt) {
                String jsonInString = mapper.writeValueAsString(chunk(pos, lim, pattern));
                System.out.println(jsonInString);
                pos += lim;
                if (jsonInString.isEmpty()) {
                    continue;
                }
                os.write(jsonInString.getBytes());
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

//        ContactsFilter contactsFilter = new ContactsFilter(new LinkedList<>(contactRepository.findWithLimit(1500)));
//        ContactsFilter contactsFilter = new ContactsFilter(new LinkedList<>(contactRepository.findAll()));

        logger.debug("Used memory: \"{}\"", (instance.totalMemory() - instance.freeMemory()) / mb);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    private LinkedList<Contact> chunk (long from, long limit, Pattern filter) {
        ContactsFilter contactsFilter = new ContactsFilter(new LinkedList<>(contactRepository.findInRange(from, limit)));
        contactsFilter.setFilter(filter);
        contactsFilter.applyFilter();
        return contactsFilter.getContacts();
    }
}