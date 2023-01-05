package server.database;

public class Writer {
    private String facs;
    private String courses;
    private String profs;
    private String stus;

    public void setFacs(String facs) {
        this.facs = facs;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public void setProfs(String profs) {
        this.profs = profs;
    }

    public void setStus(String stus) {
        this.stus = stus;
    }

    public String getFacs() {
        return facs;
    }

    public String getCourses() {
        return courses;
    }

    public String getProfs() {
        return profs;
    }

    public String getStus() {
        return stus;
    }

    public Writer(String facs, String courses, String profs, String stus) {
        this.facs = facs;
        this.courses = courses;
        this.profs = profs;
        this.stus = stus;
    }
}
