package test;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ContactsFilter {

    private LinkedList<Contact> contacts;
    private Pattern pattern;

    private ContactsFilter () {
    }

    public ContactsFilter (LinkedList<Contact> contacts) {
        this.contacts = contacts;
    }

    public boolean setFilter (String filter) {
        try {
            pattern = Pattern.compile(filter);
            return true;
        } catch (PatternSyntaxException exception) {
            System.err.println(exception.getDescription());
            return false;
        }
    }

    public Contacts getFiltered (Contacts contacts) {
        if (pattern == null) {
            pattern = Pattern.compile("");
        }

        return contacts;
    }

}
