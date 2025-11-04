import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class GUI {

  // Iba jeden Manager musime pouzivat
  private Manager manager;

  public GUI(Manager manager) {
    this.manager = manager;
  }

  private Data data = new Data(manager);

  // Velkost obrazovky(da sa zvacsit a zmensit)
  private final int SCREEN_WIDTH = 1200;
  private final int SCREEN_HEIGHT = 800;

  // Buttons
  private JButton bookAddButton = new JButton("Pridať knihu");
  private JButton bookEditButton = new JButton("Upraviť knihu");
  private JButton bookDeleteButton = new JButton("Vymazať knihu");
  private JButton borrowBookButton = new JButton("Požičať knihu");
  private JButton returnBookButton = new JButton("Vrátiť knihu");

  private JButton userAddButton = new JButton("Pridať čitatela");
  private JButton userEditButton = new JButton("Upraviť čitatela");
  private JButton userDeleteButton = new JButton("Vymazať čitatela");

  private JFrame mainFrame;
  private JPanel mainPanel;
  private JPanel navPanel;
  private JScrollPane bookScrollPanel;
  private JScrollPane userScrollPanel;
  private JScrollPane historyScrollPanel;
  private DefaultTableModel bookModel;
  private JTable bookTable;
  private DefaultTableModel userModel;
  private JTable userTable;
  private DefaultTableModel historyModel;
  private JTable historyTable;
  private JFormattedTextField dobTextField;
  private JFormattedTextField opTextField = new JFormattedTextField();

  // hlavna funkcia
  public void run() {
    createFrame();
  }

  // nakreslit a nastylovat UI
  public void createFrame() {
    mainFrame = new JFrame();
    mainFrame.setLayout(new BorderLayout());
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    navPanel = new JPanel();
    navPanel.setBackground(Color.LIGHT_GRAY);
    navPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

    // NAV PANEL
    JLabel heading = new JLabel("Knižnica");
    heading.setFont(new Font("Times New Roman", heading.getFont().getStyle(), 20));
    navPanel.add(heading);

    // BOOKS
    JPanel bookPanel = new JPanel();
    bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));

    JLabel bookLabel = new JLabel("Knihy");
    bookLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    bookLabel.setFont(new Font(bookLabel.getFont().getName(), bookLabel.getFont().getStyle(), 20));

    String[] bookColoumnNames = { "ID", "Názov", "Autor", "Požičaná" };
    bookModel = new DefaultTableModel(bookColoumnNames, 0);
    for (int i = 0; i < manager.books.size(); i++) {
      Book tempBook = manager.books.get(i);
      bookModel.addRow(new Object[] {
          tempBook.getId(),
          tempBook.getBookName(),
          tempBook.getAuthor(),
          tempBook.getTaken()
      });
    }
    bookTable = new JTable(bookModel);
    bookTable.setFont(new Font("Times New Roman", bookTable.getFont().getStyle(), 14));
    bookScrollPanel = new JScrollPane(bookTable);
    bookScrollPanel.setBorder(new EmptyBorder(10, 100, 10, 100));

    JPanel bookButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    bookButtonPanel.add(borrowBookButton);
    bookButtonPanel.add(returnBookButton);
    bookButtonPanel.add(bookAddButton);
    bookButtonPanel.add(bookDeleteButton);
    bookButtonPanel.add(bookEditButton);

    bookPanel.add(bookLabel);
    bookPanel.add(Box.createVerticalStrut(10));
    bookPanel.add(bookButtonPanel);
    bookPanel.add(Box.createVerticalStrut(10));
    bookPanel.add(bookScrollPanel);

    // USERS
    JPanel userPanel = new JPanel();
    userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

    JLabel userLabel = new JLabel("Čitatelia");
    userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    userLabel.setFont(new Font(userLabel.getFont().getName(), userLabel.getFont().getStyle(), 20));

    System.out.println("creating user table");
    String[] userColumns = { "Číslo OP", "Meno", "Priezvisko", "Dátum narodenia" };
    userModel = new DefaultTableModel(userColumns, 0);
    for (int i = 0; i < manager.users.size(); i++) {
      User temp = manager.users.get(i);
      userModel.addRow(new Object[] {
          temp.getOp(),
          temp.getName(),
          temp.getSurname(),
          temp.getDob()
      });
    }
    userTable = new JTable(userModel);
    userTable.setFont(new Font("Times New Roman", userTable.getFont().getStyle(), 14));
    userScrollPanel = new JScrollPane(userTable);
    userScrollPanel.setBorder(new EmptyBorder(10, 100, 10, 100));

    JPanel userButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    userButtonPanel.add(userAddButton);
    userButtonPanel.add(userEditButton);
    userButtonPanel.add(userDeleteButton);

    userPanel.add(userLabel);
    userPanel.add(Box.createVerticalStrut(10));
    userPanel.add(userButtonPanel);
    userPanel.add(Box.createVerticalStrut(10));
    userPanel.add(userScrollPanel);

    // HISTORIA
    JPanel historyPanel = new JPanel();
    historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
    JLabel historyLabel = new JLabel("História");
    historyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    historyLabel.setFont(new Font(historyLabel.getFont().getName(), historyLabel.getFont().getStyle(), 20));

    String[] histroyColoumns = { "ID", "Číslo OP", "Dátum Požičania", "Dátum Vrátenia" };
    historyModel = new DefaultTableModel(histroyColoumns, 0);
    for (int i = 0; i < manager.histories.size(); i++) {
      History temp = manager.histories.get(i);
      historyModel.addRow(new Object[] {
          temp.getBookId(),
          temp.getUserOp(),
          temp.getBorrowDate(),
          temp.getReturnDate()
      });
    }
    historyTable = new JTable(historyModel);
    historyTable.setFont(new Font("Times New Roman", historyTable.getFont().getStyle(), 14));

    historyScrollPanel = new JScrollPane(historyTable);
    historyScrollPanel.setBorder(new EmptyBorder(10, 100, 10, 100));

    historyPanel.add(historyLabel);
    historyPanel.add(Box.createVerticalStrut(10));
    historyPanel.add(historyScrollPanel);

    mainPanel.add(bookPanel);
    mainPanel.add(userPanel);
    mainPanel.add(historyPanel);

    mainFrame.add(navPanel, BorderLayout.NORTH);
    mainFrame.add(mainPanel, BorderLayout.CENTER);
    mainFrame.setVisible(true);
    mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // spusitit eventy
    handelEvents();
  }

  private void handelEvents() {

    // BUTTON EVENTS
    bookAddButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        showAddBookFrame();
      }
    });
    bookDeleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        // ak nemame oznaceny ziadny stlpec tak vyhody chybu
        if (bookTable.getSelectedRow() == -1) {
          JOptionPane.showMessageDialog(null, "Prosím vyberte knihu");
          return;
        } else {
          int res = JOptionPane.showConfirmDialog(null, "Naozaj chcete odstrániť túto knihu?", "Potvrdnie",
              JOptionPane.OK_CANCEL_OPTION);
          if (res == JOptionPane.OK_OPTION) {
            // dostat id knihy zo stlpca
            int id = (int) bookTable.getValueAt(bookTable.getSelectedRow(), 0);
            // pouzivat id knihy na zmazanie knihy s XML suboru
            data.deleteBook(id);
            // dostat index v liste a zmazat knihu aj tam(index potrebujeme nato aby sme
            // mohli zmazat aj v tabulke kedze maju vzdy rovnaky)
            int index = manager.removeBook(id);
            if (index != -1) {
              // zmazat s tabulky
              bookModel.removeRow(index);
            } else {
              // keby nahodou sa nieco pokazilo tak sa to zrusi a upozorni uzivatela
              JOptionPane.showMessageDialog(null, "Stala sa chyba pri odstránovaní knihy!");
              return;
            }
          } else {
            return;
          }
        }
      }
    });
    bookEditButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        // ak nemame oznaceny ziadny stlpec tak vyhody chybu
        if (bookTable.getSelectedRow() == -1) {
          JOptionPane.showMessageDialog(null, "Prosím vyberte knihu");
          return;
        } else {
          // dostat id knihy
          int id = (int) bookTable.getValueAt(bookTable.getSelectedRow(), 0);
          // vytvorit object a posunut o do dalsej funkcie
          Book book = manager.getBookById(id);
          // zobrazi sa okienko
          showEditBookFrame(book);
        }
      }
    });
    borrowBookButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        if (bookTable.getSelectedRow() == -1 || userTable.getSelectedRow() == -1) {
          JOptionPane.showMessageDialog(null, "Prosím vyberte knihu na požičanie a čitateľa");
          return;
        } else {
          // musime sa vyfiltrovat meno v tomto poli kedze aj to tam mam (true (meno) <-
          // dame prec) aby sme to mohli konvertovat na boolean a pouzit

          String rawString = String.valueOf(bookTable.getValueAt(bookTable.getSelectedRow(), 3));
          boolean value = Boolean.parseBoolean(rawString.substring(0, 5).trim());

          // pozreme ci tato kniha neni uz nahodou pozicana ak ano tak upozornime
          // uzivatela
          if (value) {
            JOptionPane.showMessageDialog(null, "Kniha je už požičaná");
            return;
          } else {
            // ak nie tak mu vytvorime novu historiu v ktorej dame dnesny datum pomocou
            // LocalDate.now()
            int id = (int) bookTable.getValueAt(bookTable.getSelectedRow(), 0);
            Long op = (Long) userTable.getValueAt(userTable.getSelectedRow(), 0);
            Book book = manager.getBookById(id);
            User user = manager.getUserByOp(op);
            // Vytvorit novu historiu(log)
            History newHistory = new History(book.getId(),
                user.getOp(),
                LocalDate.now(),
                null);
            manager.addHistory(newHistory);
            data.writeHistory(newHistory);
            historyModel.addRow(new Object[] {
                newHistory.getBookId(),
                newHistory.getUserOp(),
                newHistory.getBorrowDate(),
                newHistory.getReturnDate() });

            // kedze potrebujeme aj upravit knihu ze uz je pozicana musime spravit to
            // iste ako aj ked dame upravit ze vymazeme staru knihu a nahradime to istou s
            // upravenmi udajmi
            data.deleteBook(book.getId());
            manager.removeBook(book.getId());
            bookModel.removeRow(bookTable.getSelectedRow());

            // vytvorit kopiu starej a zmenime ze je pozicana
            Book newBook = new Book(
                book.getId(),
                book.getBookName(),
                book.getAuthor(),
                true);

            // nahrame do XML
            data.writeBook(newBook);
            // nahrame do listu ktory pouzivame pre tabulky
            manager.addBook(newBook);
            if (newBook.getTaken()) {
              bookModel.addRow(new Object[] {
                  newBook.getId(),
                  newBook.getBookName(),
                  newBook.getAuthor(),
                  newBook.getTaken() + "  ( " + user.getName() + " " + user.getSurname() + " )"
              });
            } else {
              bookModel.addRow(new Object[] {
                  newBook.getId(),
                  newBook.getBookName(),
                  newBook.getAuthor(),
                  newBook.getTaken() + "  ( " + user.getName() + " " + user.getSurname() + " )"
              });
            }
          }
        }
      }
    });
    returnBookButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        // skontrolovat ci mame vybrate vyzadovane polia
        if (bookTable.getSelectedRow() == -1 || userTable.getSelectedRow() == -1) {
          JOptionPane.showMessageDialog(null, "Prosím vyberte knihu na vrátenie a čitateľa");
          return;
        } else {
          // ak ano
          int id = (int) bookTable.getValueAt(bookTable.getSelectedRow(), 0);
          Long op = (Long) userTable.getValueAt(userTable.getSelectedRow(), 0);
          Book book = manager.getBookById(id);
          // pozreme sa ci vybrate udaje sedia (kniha -> citatel)
          History history = manager.getHistoryByIdAndOp(id, op);
          // ak nie tak vyhodime chybu
          if (history == null || book == null) {
            JOptionPane.showMessageDialog(null, "Nenašla sa táto kniha pri tomto čitateľovi");
            return;
          }
          // ak ano tak zase vymazame staru knihu a nahradime kopiou novou
          data.deleteBook(book.getId());
          int index1 = manager.removeBook(book.getId());
          if (index1 != -1) {
            bookModel.removeRow(bookTable.getSelectedRow());
          } else {
            JOptionPane.showMessageDialog(null, "Nastala chyba");
            return;
          }

          // upravit a vytvorit kopiu
          Book newBook = new Book(
              book.getId(),
              book.getBookName(),
              book.getAuthor(),
              false);

          data.writeBook(newBook);
          manager.addBook(newBook);
          bookModel.addRow(new Object[] {
              newBook.getId(),
              newBook.getBookName(),
              newBook.getAuthor(),
              newBook.getTaken()
          });

          // zmazat stary zaznam s historie a nahradime novym s dnesnym datumom
          data.deleteHistory(book.getId(), op);
          int index = manager.removeHistory(history);
          if (index != -1) {
            historyModel.removeRow(index);
          } else {
            JOptionPane.showMessageDialog(null, "Nastala chyba");
            return;
          }

          History newHistory = new History(
              history.getBookId(),
              history.getUserOp(),
              history.getBorrowDate(),
              LocalDate.now());
          data.writeHistory(newHistory);
          manager.addHistory(newHistory);
          historyModel.addRow(new Object[] {
              newHistory.getBookId(),
              newHistory.getUserOp(),
              newHistory.getBorrowDate(),
              newHistory.getReturnDate()
          });
        }
      }
    });
    userAddButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        // zobrazi sa okienko
        showUserAddFrame();
      }
    });
    userDeleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        // skontrolovat ci mame vybrate pozadovane polia
        if (userTable.getSelectedRow() == -1) {
          JOptionPane.showMessageDialog(null, "Prosím vyberte čitateľa");
          return;
        } else {
          /// ak ano
          int res = JOptionPane.showConfirmDialog(null, "Naozaoj chcete odstrániť tohto čitateľa?", "Povrdenie",
              JOptionPane.OK_CANCEL_OPTION);
          if (res == JOptionPane.OK_OPTION) {
            // najdeme op oznaceneho citatela
            Long op = (Long) userTable.getValueAt(userTable.getSelectedRow(), 0);
            // vymazeme pomocou op
            data.deleteUser(op);
            int index = manager.removeUser(op);
            if (index != -1) {
              userModel.removeRow(index);
            } else {
              JOptionPane.showMessageDialog(null, "Stala sa chyba pri odstránovaní čitateľa!");
              return;
            }
          } else {
            return;
          }
        }
      }
    });
    userEditButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        if (userTable.getSelectedRow() == -1) {
          JOptionPane.showMessageDialog(null, "Prosím vyberte čitateľa");
          return;
        } else {
          // najdeme citatela ktoreho mame oznaceny
          Long op = (Long) userTable.getValueAt(userTable.getSelectedRow(), 0);
          User user = manager.getUserByOp(op);
          showEditUserFrame(user);
        }
      }
    });
  }

  private void showAddBookFrame() {
    // vytvorit nove okienko
    JFrame frame = new JFrame("Pridať Knihu");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
    frame.setLocationRelativeTo(null);

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel heading = new JLabel("Pridať Knihu");
    heading.setFont(new Font("Serif", Font.BOLD, 20));
    heading.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    panel.add(heading, gbc);

    gbc.gridwidth = 1;

    JLabel idLabel = new JLabel("ID:");
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(idLabel, gbc);

    JSpinner idTextField = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
    gbc.gridx = 1;
    panel.add(idTextField, gbc);

    JLabel nameLabel = new JLabel("Názov:");
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(nameLabel, gbc);

    JTextField nameTextField = new JTextField(15);
    gbc.gridx = 1;
    panel.add(nameTextField, gbc);

    JLabel authorLabel = new JLabel("Autor:");
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(authorLabel, gbc);

    JTextField authorTextField = new JTextField(15);
    gbc.gridx = 1;
    panel.add(authorTextField, gbc);

    JButton addButton = new JButton("Pridať");
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        // vytvorime novu knihu s udajmi s polia
        Book newBook = new Book(
            (int) idTextField.getValue(),
            nameTextField.getText(),
            authorTextField.getText(),
            false);
        // zapiseme do XML
        data.writeBook(newBook);
        // zapiseme do nasho listu
        manager.addBook(newBook);
        // pridat do tabulky
        bookModel.addRow(new Object[] {
            newBook.getId(),
            newBook.getBookName(),
            newBook.getAuthor(),
            newBook.getTaken()
        });
        frame.dispose();
      }
    });
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(addButton, gbc);
    frame.add(panel);
    frame.setVisible(true);
  }

  private void showEditBookFrame(Book book) {
    // vytvorit nove okienko
    JFrame frame = new JFrame("Pridať Knihu");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
    frame.setLocationRelativeTo(null);

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel heading = new JLabel("Pridať Knihu");
    heading.setFont(new Font("Serif", Font.BOLD, 20));
    heading.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    panel.add(heading, gbc);

    gbc.gridwidth = 1;

    JLabel idLabel = new JLabel("ID:");
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(idLabel, gbc);

    JSpinner idTextField = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
    idTextField.setValue((int) book.getId());
    gbc.gridx = 1;
    panel.add(idTextField, gbc);

    JLabel nameLabel = new JLabel("Názov:");
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(nameLabel, gbc);

    JTextField nameTextField = new JTextField(15);
    nameTextField.setText(book.getBookName());
    gbc.gridx = 1;
    panel.add(nameTextField, gbc);

    JLabel authorLabel = new JLabel("Autor:");
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(authorLabel, gbc);

    JTextField authorTextField = new JTextField(15);
    authorTextField.setText(book.getAuthor());
    gbc.gridx = 1;
    panel.add(authorTextField, gbc);

    JButton saveButton = new JButton("Uložiť");
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {

        // editaciu som spravil tak ze najskor vymazame stary a potom vytvorime novy na
        // jeho mieste
        // Vymazat stary
        data.deleteBook(book.getId());
        int index = manager.removeBook(book.getId());
        if (index != -1) {
          bookModel.removeRow(index);
        } else {
          JOptionPane.showMessageDialog(null, "Stala sa chyba pri upravovaní knihy!");
          return;
        }
        // vytvorit novy s udajmi si objektov ktore vyplnil pouzivatel
        Book newBook = new Book(
            (int) idTextField.getValue(),
            nameTextField.getText(),
            authorTextField.getText(),
            false);
        data.writeBook(newBook);
        manager.addBook(newBook);
        bookModel.addRow(new Object[] {
            newBook.getId(),
            newBook.getBookName(),
            newBook.getAuthor(),
            newBook.getTaken()
        });
        // zavret okno nakoniec
        frame.dispose();
      }
    });
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(saveButton, gbc);
    frame.add(panel);
    frame.setVisible(true);
  }

  private void showUserAddFrame() {
    // to iste ako showAddBookFrame len pre citatela
    JFrame frame = new JFrame("Pridať Čitatela");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
    frame.setLocationRelativeTo(null);

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel heading = new JLabel("Pridať Čitatela");
    heading.setFont(new Font("Serif", Font.BOLD, 20));
    heading.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    panel.add(heading, gbc);

    gbc.gridwidth = 1;

    JLabel opLabel = new JLabel("OP:");
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(opLabel, gbc);

    try {
      MaskFormatter formatter = new MaskFormatter("##########");
      formatter.setPlaceholderCharacter('_');
      opTextField = new JFormattedTextField(formatter);
      opTextField.setColumns(10);
    } catch (ParseException e) {
      JOptionPane.showMessageDialog(null, "Prosím, zadajte OP vo formáte 0123456789.");
    }
    gbc.gridx = 1;
    panel.add(opTextField, gbc);

    JLabel nameLabel = new JLabel("Meno: ");
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(nameLabel, gbc);

    JTextField nameTextField = new JTextField(15);
    gbc.gridx = 1;
    panel.add(nameTextField, gbc);

    JLabel surnameLabel = new JLabel("Priezvisko:");
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(surnameLabel, gbc);

    JTextField surnameTextField = new JTextField(15);
    gbc.gridx = 1;
    panel.add(surnameTextField, gbc);

    JLabel dobLabel = new JLabel("Dátum Narodenia: ");
    gbc.gridx = 0;
    gbc.gridy = 4;
    panel.add(dobLabel, gbc);

    try {
      MaskFormatter formatter = new MaskFormatter("####-##-##");
      formatter.setPlaceholderCharacter('_');
      dobTextField = new JFormattedTextField(formatter);
      dobTextField.setColumns(10);
    } catch (ParseException e) {
      JOptionPane.showMessageDialog(null, "Prosím, zadajte dátum vo formáte YYYY-MM-DD.");
    }

    gbc.gridx = 1;
    panel.add(dobTextField, gbc);

    JButton addButton = new JButton("Pridať");
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(addButton, gbc);

    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {

        // musime si pozriet ci zadane info su spravne ak ne tak vyhodit chyby
        LocalDate dob = null;
        try {
          dob = LocalDate.parse(dobTextField.getText());
        } catch (DateTimeParseException e) {
          JOptionPane.showMessageDialog(null, "Prosím, zadajte správny dátum.");
          return;
        }
        Long op = null;
        try {
          op = Long.parseLong(opTextField.getText());
        } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Prosím, zadajte správne OP.");
          return;
        }
        User newUser = new User(
            op,
            nameTextField.getText(),
            surnameTextField.getText(),
            dob);
        data.writeUser(newUser);
        manager.addUser(newUser);
        userModel.addRow(new Object[] {
            newUser.getOp(),
            newUser.getName(),
            newUser.getSurname(),
            newUser.getDob()
        });
        frame.dispose();
      }
    });

    frame.add(panel);
    frame.setVisible(true);
  }

  private void showEditUserFrame(User user) {
    // to iste ako showEditBookFrame len pre knihy
    JFrame frame = new JFrame("Upraviť Čitatela");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
    frame.setLocationRelativeTo(null);

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel heading = new JLabel("Upraviť Čitatela");
    heading.setFont(new Font("Serif", Font.BOLD, 20));
    heading.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    panel.add(heading, gbc);

    gbc.gridwidth = 1;

    JLabel opLabel = new JLabel("OP:");
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(opLabel, gbc);

    try {
      MaskFormatter formatter = new MaskFormatter("##########");
      formatter.setPlaceholder(String.valueOf(user.getOp()));
      opTextField = new JFormattedTextField(formatter);
      opTextField.setColumns(10);
    } catch (ParseException e) {
      JOptionPane.showMessageDialog(null, "Prosím, zadajte OP vo formáte 0123456789.");
    }
    gbc.gridx = 1;
    panel.add(opTextField, gbc);

    JLabel nameLabel = new JLabel("Meno: ");
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(nameLabel, gbc);

    JTextField nameTextField = new JTextField(15);
    nameTextField.setText(user.getName());
    gbc.gridx = 1;
    panel.add(nameTextField, gbc);

    JLabel surnameLabel = new JLabel("Priezvisko:");
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(surnameLabel, gbc);

    JTextField surnameTextField = new JTextField(15);
    surnameTextField.setText(user.getSurname());
    gbc.gridx = 1;
    panel.add(surnameTextField, gbc);

    JLabel dobLabel = new JLabel("Dátum Narodenia: ");
    gbc.gridx = 0;
    gbc.gridy = 4;
    panel.add(dobLabel, gbc);

    try {
      MaskFormatter formatter = new MaskFormatter("####-##-##");
      formatter.setPlaceholder(String.valueOf(user.getDob()));
      dobTextField = new JFormattedTextField(formatter);
      dobTextField.setColumns(10);
    } catch (ParseException e) {
      JOptionPane.showMessageDialog(null, "Prosím, zadajte dátum vo formáte YYYY-MM-DD.");
    }

    gbc.gridx = 1;
    panel.add(dobTextField, gbc);

    JButton saveButton = new JButton("Uložiť");
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(saveButton, gbc);

    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {

        // check for errors
        LocalDate dob = null;
        try {
          dob = LocalDate.parse(dobTextField.getText());
        } catch (DateTimeParseException e) {
          JOptionPane.showMessageDialog(null, "Prosím, zadajte správny dátum.");
          return;
        }
        Long op = null;
        try {
          op = Long.parseLong(opTextField.getText());
        } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Prosím, zadajte správne OP.");
          return;
        }
        data.deleteUser(user.getOp());
        int index = manager.removeUser(user.getOp());
        if (index != -1) {
          userModel.removeRow(index);
        } else {
          JOptionPane.showMessageDialog(null, "Stala sa chyba pri upravovaní čitateľa!");
          return;
        }

        User newUser = new User(
            op,
            nameTextField.getText(),
            surnameTextField.getText(),
            dob);

        data.writeUser(newUser);
        manager.addUser(newUser);
        userModel.addRow(new Object[] {
            newUser.getOp(),
            newUser.getName(),
            newUser.getSurname(),
            newUser.getDob()
        });
        frame.dispose();
      }
    });

    frame.add(panel);
    frame.setVisible(true);
  }

}
