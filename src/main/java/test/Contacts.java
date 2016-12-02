package test;

import java.util.LinkedList;

class Contacts {
    private LinkedList<Contact> contactList;

    Contacts(LinkedList<Contact> contactList) {
        this.contactList = contactList;
    }
    public LinkedList<Contact> getContacts() {
        return contactList;
    }
}