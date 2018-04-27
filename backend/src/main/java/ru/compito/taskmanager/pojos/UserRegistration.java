package ru.compito.taskmanager.pojos;

public class UserRegistration {
    private String username;
    private String fullname;
    private String password;
    private String passwordConfirmation;
    private String email;

    public UserRegistration() {
    }

    public UserRegistration(String username, String fullname, String password, String passwordConfirmation, String email) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
