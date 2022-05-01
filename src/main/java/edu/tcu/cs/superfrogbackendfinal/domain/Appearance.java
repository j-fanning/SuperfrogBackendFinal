package edu.tcu.cs.superfrogbackendfinal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Appearance {
    //First, last, number, email, date, timerange, address, theme
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String firstName;
    private String lastName;
    private String number;
    private String email;
    private String date;
    //   @ManyToMany(cascade = CascadeType.ALL)
    //   @JsonIgnore
    //    private List<User> students = new ArrayList<>();
    //all appearances are pending initially.
    @Column(nullable = false, columnDefinition = "BOOLEAN")
    private boolean pending = true;
    private String theme;
    private String milageRange;
    private float price;
    private String startTime;
    private String endTime;
    private String address;
    //    @ManyToOne
    //    private User customer;

    public Appearance() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

 /* public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }
*/
    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getMilageRange() {
        return milageRange;
    }

    public void setMilageRange(String milageRange) {
        this.milageRange = milageRange;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
/*
    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void addStudent(User student){
        //add appearance to students appearances
        student.addAppearance(this);
        //add this student to this apperances student list
        this.students.add(student);
    }

    public void removeStudent(User student){
        //remove student
        student.removeApperance(this);
        // remove from students list
        this.students.remove(student);
    }
*/
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
