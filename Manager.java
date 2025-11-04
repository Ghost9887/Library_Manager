import java.util.ArrayList;

//Kominukacia medzi datami a frontendom 
public class Manager {

  // data s xml do tychto budeme pridavat a zmazat pocas programu
  public ArrayList<Book> books = new ArrayList<>();
  public ArrayList<User> users = new ArrayList<>();
  public ArrayList<History> histories = new ArrayList<>();

  public void addUser(User user) {
    users.add(user);
  }

  public int removeUser(Long op) {
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getOp() == op) {
        users.remove(i);
        return i;
      }
    }
    return -1;
  }

  public User getUserByOp(Long op) {
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getOp() == op) {
        return users.get(i);
      }
    }
    return null;
  }

  public void addBook(Book book) {
    books.add(book);
  }

  public Book getBookById(int id) {
    for (int i = 0; i < books.size(); i++) {
      if (books.get(i).getId() == id) {
        return books.get(i);
      }
    }
    return null;
  }

  public int removeBook(int id) {
    for (int i = 0; i < books.size(); i++) {
      if (books.get(i).getId() == id) {
        books.remove(i);
        return i;
      }
    }
    return -1;
  }

  public void addHistory(History history) {
    histories.add(history);
  }

  public History getHistoryByIdAndOp(int id, Long op) {
    for (int i = 0; i < histories.size(); i++) {
      if (histories.get(i).getBookId() == id && histories.get(i).getUserOp() == op) {
        return histories.get(i);
      }
    }
    return null;
  }

  public int removeHistory(History history) {
    for (int i = 0; i < histories.size(); i++) {
      if (histories.get(i).getBookId() == history.getBookId() && histories.get(i).getUserOp() == history.getUserOp()) {
        histories.remove(i);
        return i;
      }
    }
    return -1;
  }

}
