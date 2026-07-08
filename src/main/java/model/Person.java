package model;

public class Person implements Comparable<Person>{

    private final String name;
    private final String mail;
    private final String password;

    private Person(Builder builder) {
        this.name = builder.name;
        this.mail = builder.mail;
        this.password = builder.password;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString(){
        return "name: " + this.name + ", mail: " + this.mail;
    }

    @Override
    public int compareTo(Person o) {
        return 0;
    }

    public static class Builder {
        private String name;
        private String mail;
        private String password;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Person build() {
            if (name == null || mail == null || password == null) {
                throw new IllegalStateException("Все поля должны быть заполнены");
            }
            return new Person(this);
        }
    }
}
