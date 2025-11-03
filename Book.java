public class Book {
    
    private int id;
    private String bookName;
    private String author;
    private boolean taken;

    public Book(int id, String bookName, String author, boolean taken){
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.taken = taken;
    }

    public void setTaken(boolean taken){
        this.taken = taken;
    }

    public int getId(){
        return id;
    }
    
    public String getBookName(){
        return bookName;
    }

    public String getAuthor(){
        return author;
    }
    
    public boolean getTaken(){
        return taken;
    }

}
