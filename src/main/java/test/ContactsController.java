package test;

import java.io.IOException;
import java.time.LocalTime;
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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ContactsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Pattern pattern;
    private final long LIM = 200000;

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping("/hello/contacts")
    public ResponseEntity<List<Contact>> contacts(@RequestParam(value="nameFilter", defaultValue="") String nameFilter,
                                                  HttpServletResponse response) {
        long time = System.currentTimeMillis();

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

        ContactsFilter contactsFilter = new ContactsFilter();
        contactsFilter.setFilter(pattern);
        long cnt = contactRepository.getCount();
        long pos = 0;
        try {
            ServletOutputStream os = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            os.write('[');
            while (pos < cnt) {
                contactsFilter.setContacts(new LinkedList<>(contactRepository.findInRange(pos, LIM)));
                contactsFilter.applyFilter();
                pos += LIM;
                if (contactsFilter.getContacts().isEmpty()) {
                    continue;
                }
                String jsonInString = mapper.writeValueAsString(contactsFilter.getContacts());
                jsonInString = jsonInString.substring(1,jsonInString.length()-1);
                os.write(jsonInString.getBytes());
                os.flush();
            }
            os.write(']');
            os.flush();
            os.close();
        } catch (IOException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e.getMessage());
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Exec time: {} ms", System.currentTimeMillis() - time);
            logger.debug("Response 200 OK");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}