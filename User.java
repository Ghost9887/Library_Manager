import java.time.LocalDate;

public class User {

    private int op;
    private String name;
    private String surname;
    private LocalDate dob;

    public User(int op, String name, String surname, LocalDate dob){
        this.op = op;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
    }

    public void setOp(int op){
        this.op = op;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public void setDob(LocalDate dob){
        this.dob = dob;
    }

    public int getOp(){
        return op;
    }

    public String getName(){
        return name;
    }

    public String getUsername(){
        return surname;
    }

    public LocalDate getDob(){
        return dob;
    }

}
