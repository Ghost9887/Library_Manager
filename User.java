import java.time.LocalDate;

public class User {

  private Long op;
  private String name;
  private String surname;
  private LocalDate dob;

  public User(Long op, String name, String surname, LocalDate dob) {
    this.op = op;
    this.name = name;
    this.surname = surname;
    this.dob = dob;
  }

  public void setOp(Long op) {
    this.op = op;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public Long getOp() {
    return op;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public LocalDate getDob() {
    return dob;
  }

}
