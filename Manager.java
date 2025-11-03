import java.util.ArrayList;

public class Manager {

    public ArrayList<Book> books = new ArrayList<>();
    public ArrayList<User> users = new ArrayList<>();

    public void addBook(Book book){
        books.add(book);
    }

    public Book getBookById(int id){
        for(int i = 0; i < books.size(); i++){
            if(books.get(i).getId() == id){
                return books.get(i);
            }
        }
        return null;
    }

    public void addUser(User user){
        users.add(user);
    }

    public User getUserByOp(int op){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getOp() == op){
                return users.get(i);
            }
        }
        return null;
    }
}
