package test;

import java.util.List;

public interface ContactDao {
//    void insert(Contact contact);
//    void update(Contact contact);
//    void delete(Contact contact);

    List<Contact> getAll();
    List<Contact> findWithFilter(ContactFilter filter);

}