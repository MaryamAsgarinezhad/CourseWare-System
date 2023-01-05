package Logic;

public class Objectt {
    public int student;
    public int course;
    public String ObjectText;

    public Objectt(){

    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public void setStudent(int student) {
        this.student = student;
    }

    public void setObjectText(String objectText) {
        ObjectText = objectText;
    }

    public int getStudent() {
        return student;
    }


    public String getObjectText() {
        return ObjectText;
    }

    public Objectt(int student, int course, String objectText) {
        this.student = student;
        this.course=course;
        ObjectText = objectText;
    }
}
