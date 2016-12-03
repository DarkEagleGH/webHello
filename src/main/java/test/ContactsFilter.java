package test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ContactsFilter {

    private LinkedList<Contact> filtered;
    private Pattern pattern;

    private ContactsFilter () {
    }

    public ContactsFilter (LinkedList<Contact> contacts) {
        this.filtered = contacts;
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
    public LinkedList<Contact> getFiltered () {
        return filtered;
    }

    public void applyFilter () {
        if (pattern == null) {
            return;
        }
        Matcher matcher;
        final Iterator<Contact> i = filtered.iterator();
        while (i.hasNext()) {
            matcher = pattern.matcher(i.next().getName());
            if (matcher.matches()){
                i.remove();
            }
        }
    }
}
