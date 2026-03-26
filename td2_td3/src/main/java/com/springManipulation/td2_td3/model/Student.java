package com.springManipulation.td2_td3.model;

public class Student {

    private String reference;
    private String firstName;
    private String lastName;
    private Integer age;

    public Student() {}

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}
