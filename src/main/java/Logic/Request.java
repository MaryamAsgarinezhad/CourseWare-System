package Logic;

public class Request {
    public requestType requestType;
    public Student student;
    public Professor professor;
    public boolean accepted;

    public Request(){

    }
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setRequestType(Logic.requestType requestType) {
        this.requestType = requestType;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Logic.requestType getRequestType() {
        return requestType;
    }

    public Student getStudent() {
        return student;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Request(Logic.requestType requestType, Student student, Professor professor) {
        this.requestType = requestType;
        this.student = student;
        this.professor = professor;
    }
}
