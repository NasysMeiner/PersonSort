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
        return "ID: " + this.id + " name: " + this.name + ", mail: " + this.mail;
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
            if (DataValidator.validateName(name)){
                this.name = name;
                return this;
            }
            throw new InvalidDataException("Введенное имя \"" + name + "\" состоит не только из букв");
        }

        public Builder setMail(String mail) {
            if (DataValidator.validateEmail(mail)){
                this.mail = mail;
                return this;
            }
            throw new InvalidDataException("Введенная почта \"" + mail + "\" не похожа на почту");
        }

        public Builder setPassword(String password) {
            if (DataValidator.validatePassword(password)){
                this.password = password;
                return this;
            }
            throw new InvalidDataException("""
                    Введенный пароль не соответствует требованиям:\n
                    1. Пароль должен быть длиннее 8 символов;
                    2. В пароле должны быть строчные и прописные буквы;
                    3. В пароле должны быть спецсимволы.
                    """);
        }

        public Person build() {
            if (name == null || mail == null || password == null) {
                throw new IllegalStateException("Все поля должны быть заполнены");
            }
            return new Person(this);
        }
    }
}
