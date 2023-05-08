package com.example.recyclerviewdemo.bean;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-05-08 下午2:34
 */
public class Group {
    private String name;
    private List<Contacts> contacts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
    }

    public Group(String name, List<Contacts> contacts) {
        this.name = name;
        this.contacts = contacts;
    }

    public static class Contacts{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Contacts(String name) {
            this.name = name;
        }
    }
}
