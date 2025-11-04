import java.time.LocalDate;

public class History {

  private int bookId;
  private Long userOp;
  private LocalDate borrowDate;
  private LocalDate returnDate;

  public History(int bookId, Long userOp, LocalDate borrowDate, LocalDate returnDate) {
    this.bookId = bookId;
    this.userOp = userOp;
    this.borrowDate = borrowDate;
    this.returnDate = returnDate;
  }

  public int getBookId() {
    return bookId;
  }

  public Long getUserOp() {
    return userOp;
  }

  public LocalDate getBorrowDate() {
    return borrowDate;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }

}
