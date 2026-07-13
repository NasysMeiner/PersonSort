package model;

import java.io.Serializable;

public class Person implements Comparable<Person>, Serializable {

    private String name;
    private String mail;
    private String password;
    private Long id;

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

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString(){
        return "ID-" + this.id + " Name: " + this.name + ", Password: " + this.password + ", Mail: " + this.mail;
    }

    @Override
    public int compareTo(Person o) {
        return compareByName(o) + compareByMail(o) + compareByPassword(o);
    }

    public int compareByName(Person o){
        return o.getName().compareTo(this.getName());
    }

    public int compareByMail(Person o){
        return o.getMail().compareTo(this.getMail());
    }

    public int compareByPassword(Person o){
        return o.getPassword().compareTo(this.getPassword());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
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
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("Имя не может быть пустым");
            }
            if (!name.matches("[A-Za-zА-Яа-яЁё\\s-]+")) {
                throw new IllegalStateException("Имя содержит недопустимые символы: " + name);
            }
            if (mail == null || mail.isBlank()) {
                throw new IllegalStateException("Email не может быть пустым");
            }
            if (!mail.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
                throw new IllegalStateException("Некорректный формат email: " + mail);
            }
            if (password == null || password.length() < 6) {
                throw new IllegalStateException("Пароль должен быть не менее 6 символов");
            }

            return new Person(this);
        }
    }
}
