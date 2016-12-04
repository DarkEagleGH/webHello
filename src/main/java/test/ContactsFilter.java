package test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactsFilter {

    private LinkedList<Contact> contacts;
    private Pattern pattern;

    ContactsFilter () {
    }

    ContactsFilter (LinkedList<Contact> contacts) {
        this.contacts = contacts;
    }

    public void setFilter (Pattern pattern) {
        this.pattern = pattern;
    }

    public LinkedList<Contact> getContacts () {
        return contacts;
    }

    public void setContacts (LinkedList<Contact> contacts) {
        this.contacts = contacts;
    }

    public void applyFilter () {
        if (pattern == null) {
            return;
        }
        Matcher matcher;
        final Iterator<Contact> i = contacts.iterator();
        while (i.hasNext()) {
            matcher = pattern.matcher(i.next().getName());
            if (matcher.matches()){
                i.remove();
            }
        }
    }
}
