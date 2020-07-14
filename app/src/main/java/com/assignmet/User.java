package com.assignmet;
public class User {
    private int user_id;
    private String Name;
    private String Age;
    private String Gender;
    private String Hobbies;
    private String DOB;
    private String Image;
    public User() {
    }


    public User(int user_id, String name, String age, String gender, String hobbies, String DOB, String image) {
        this.user_id = user_id;
        Name = name;
        Age = age;
        Gender = gender;
        Hobbies = hobbies;
        this.DOB = DOB;
        Image = image;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getHobbies() {
        return Hobbies;
    }

    public void setHobbies(String hobbies) {
        Hobbies = hobbies;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}