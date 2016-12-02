package test;

import java.util.List;

class Contacts {
    private List<Contact> contactList;

    Contacts(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public List<Contact> getContacts() {
        return contactList;
    }
}