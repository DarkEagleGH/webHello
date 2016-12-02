package test;

public class ContactFilter {

    private long id;
    private String name;

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

//    public boolean isEmpty() {
//        return id == null && name == null;
//    }

    public boolean accept(Contact contact) {
        return acceptName(contact.getName());
    }

    private boolean acceptName(String name) {
        return this.name == null || this.name.equals(name);
    }

}
