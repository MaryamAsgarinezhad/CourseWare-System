package Logic;

public class Score {
    public int value;
    public Student student;
    public Course course;
    public boolean finaled;

    public Score(){

    }
    public void setFinaled(boolean finaled) {
        this.finaled = finaled;
    }

    public boolean isFinaled() {
        return finaled;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getValue() {
        return value;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Score(int value, Student student, Course course,boolean finaled) {
        this.value = value;
        this.student = student;
        this.course = course;
        this.finaled=finaled;
    }
}
