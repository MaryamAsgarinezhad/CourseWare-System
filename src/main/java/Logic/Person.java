package Logic;

public class Person {

    public String FirstName;
    public String LastName;
    public String Email;
    public String faculty;
    public String username;
    public String password;
    public String lastEnterTime="";
    public boolean isProfessor;
    public String chats="";

    public void setChats(String chats) {
        this.chats = chats;
    }

    public String getChats() {
        return chats;
    }

    public void setProfessor(boolean professor) {
        isProfessor = professor;
    }

    public boolean isProfessor() {
        return isProfessor;
    }

    public void setLastEnterTime(String lastEnterTime) {
        this.lastEnterTime = lastEnterTime;
    }

    public String getLastEnterTime() {
        return lastEnterTime;
    }

    public Person(){

    }
    public Person(String firstName, String lastName, String email, String faculty, String username,String password,boolean isProfessor) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        this.faculty= faculty;
        this.username=username;
        this.password=password;
        this.isProfessor=isProfessor;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getEmail() {
        return Email;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
