package com.springManipulation.td2_td3;

public class Student {
    private String reference;
    private String firstName;
    private String lastName;
    private int age;

    public Student() {}

    public String getReference() { return reference; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }

    public void setReference(String reference) { this.reference = reference; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAge(int age) { this.age = age; }
}
