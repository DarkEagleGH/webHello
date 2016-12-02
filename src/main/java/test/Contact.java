package test;

public class Contact {
    private long id;
    private String name;

        public void setId(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
/*
    public Contact(long id, String name) {
        this.id = id;
        this.name = name;
    }
    */
    @Override
    public String toString() {
        return String.format(
                "Contact[id=%d, firstName='%s']",
                id, name);
    }

    // getters & setters опущены для краткости
}