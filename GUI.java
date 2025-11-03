import javax.swing.*;
import java.awt.*;

public class GUI{

  private Manager manager;

  public GUI(Manager manager){
    this.manager = manager;
  }

  private final int SCREEN_WIDTH = 1200;
  private final int SCREEN_HEIGHT = 800;

  private JFrame mainFrame;
  private JPanel mainPanel;
  private JScrollPane bookScrollPanel;
  private JScrollPane userScrollPanel;
  private JScrollPane historyScrollPanel;
  private JTable bookTable;
  private JTable userTable;
  private JTable historyTable;
   
  public void run(){
    createFrame();
  }

  public void createFrame(){
    mainFrame = new JFrame();
    mainFrame.setLayout(new BorderLayout());
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    //BOOKS
    JPanel bookPanel = new JPanel();
    bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));

    JLabel bookLabel = new JLabel("Knihy"); 
    bookLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    Object[][] bookData = new Object[manager.books.size()][4];
    for(int i = 0; i < manager.books.size(); i++){
      Book temp = manager.books.get(i);
      bookData[i][0] = temp.getId();
      bookData[i][1] = temp.getBookName();
      bookData[i][2] = temp.getAuthor();
      bookData[i][3] = temp.getTaken();
    }

    String[] bookColoumnNames = {"ID", "Názov", "Autor", "Príznak"};

    bookTable = new JTable(bookData, bookColoumnNames);
    bookScrollPanel = new JScrollPane(bookTable);
    bookPanel.add(bookLabel);
    bookPanel.add(Box.createVerticalStrut(10));
    bookPanel.add(bookScrollPanel);
    

    //USERS
    JPanel userPanel = new JPanel();
    userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

    JLabel userLabel = new JLabel("Čitatelia");
    userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    Object[][] userData = new Object[manager.users.size()][4];
    for(int i = 0; i < manager.users.size(); i++){
      User temp = manager.users.get(i);
      userData[i][0] = temp.getOp();
      userData[i][1] = temp.getName();
      userData[i][2] = temp.getUsername();
      userData[i][3] = temp.getDob();
    }

    String[] userColumns = {"Číslo OP", "Meno", "Priezvisko", "Dátum narodenia"};
    userTable = new JTable(userData, userColumns);
    userScrollPanel = new JScrollPane(userTable);
    userPanel.add(userLabel);
    userPanel.add(Box.createVerticalStrut(10));
    userPanel.add(userScrollPanel);

    //HISTORIA
    JPanel historyPanel = new JPanel();
    historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
    JLabel historyLabel = new JLabel("História");
    historyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    String[][] historyData = {
      {"1", "1111", "21.5.2025", "-"},
      {"3", "3333", "22.5.2025", "22.6.2025"},
      {"7", "7777", "22.7.2025", "-"},
      {"6", "6666", "10.10.2025", "10.5.2026"}
    };
    String[] histroyColoumns = {"Id", "Číslo OP", "Dátum Požičania", "Dátum Vrátenia"};
    historyTable = new JTable(historyData, histroyColoumns);
    historyScrollPanel = new JScrollPane(historyTable);
    historyPanel.add(historyLabel);
    historyPanel.add(Box.createVerticalStrut(10));
    historyPanel.add(historyScrollPanel);

    mainPanel.add(bookPanel);
    mainPanel.add(userPanel);
    mainPanel.add(historyPanel);

    mainFrame.add(mainPanel, BorderLayout.CENTER);
    mainFrame.setVisible(true);
    mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
