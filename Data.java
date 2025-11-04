import java.io.File;
import java.time.LocalDate;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Data {

  private Manager manager;
  private final String filePath = "data.xml";

  public Data(Manager manager) {
    this.manager = manager;
  }

  public void readData() {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new File(filePath));
      doc.getDocumentElement().normalize();

      // knihy
      NodeList bookList = doc.getElementsByTagName("kniha");
      for (int i = 0; i < bookList.getLength(); i++) {
        Element book = (Element) bookList.item(i);
        Book newBook = new Book(
            Integer.parseInt(book.getElementsByTagName("id").item(0).getTextContent()),
            book.getElementsByTagName("nazov").item(0).getTextContent(),
            book.getElementsByTagName("autor").item(0).getTextContent(),
            Boolean.parseBoolean(book.getElementsByTagName("pozicana").item(0).getTextContent()));
        manager.addBook(newBook);
      }

      // citatelia
      NodeList userList = doc.getElementsByTagName("citatel");
      for (int i = 0; i < userList.getLength(); i++) {
        Element user = (Element) userList.item(i);
        User newUser = new User(
            Long.parseLong(user.getElementsByTagName("op").item(0).getTextContent()),
            user.getElementsByTagName("meno").item(0).getTextContent(),
            user.getElementsByTagName("priezvisko").item(0).getTextContent(),
            LocalDate.parse(user.getElementsByTagName("datum_narodenia").item(0).getTextContent()));
        manager.addUser(newUser);
      }

      // historia
      NodeList historyList = doc.getElementsByTagName("vypozicka");
      for (int i = 0; i < historyList.getLength(); i++) {
        Element history = (Element) historyList.item(i);
        int id = Integer.parseInt(history.getElementsByTagName("id").item(0).getTextContent());
        long op = Long.parseLong(history.getElementsByTagName("op").item(0).getTextContent());
        LocalDate borrowDate = null;
        LocalDate returnDate = null;
        Node borrowNode = history.getElementsByTagName("datum_vypozicania").item(0);
        if (borrowNode != null && borrowNode.getTextContent() != null && !borrowNode.getTextContent().isEmpty()) {
          borrowDate = LocalDate.parse(borrowNode.getTextContent());
        }
        Node returnNode = history.getElementsByTagName("datum_vratenia").item(0);
        if (returnNode != null && returnNode.getTextContent() != null && !returnNode.getTextContent().isEmpty()) {
          returnDate = LocalDate.parse(returnNode.getTextContent());
        }
        History newHistory = new History(id, op, borrowDate, returnDate);
        manager.addHistory(newHistory);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void writeHistory(History history) {
    try {
      System.out.println(filePath);
      File xmlFile = new File(filePath);
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(xmlFile);
      doc.getDocumentElement().normalize();

      Element newHistory = doc.createElement("vypozicka");

      Element id = doc.createElement("id");
      id.appendChild(doc.createTextNode(String.valueOf(history.getBookId())));
      newHistory.appendChild(id);

      Element op = doc.createElement("op");
      op.appendChild(doc.createTextNode(String.valueOf(history.getUserOp())));
      newHistory.appendChild(op);

      Element borrowDate = doc.createElement("datum_vypozicania");
      borrowDate.appendChild(doc.createTextNode(String.valueOf(history.getBorrowDate())));
      newHistory.appendChild(borrowDate);

      Element returnDate = doc.createElement("datum_vratenia");
      String returnDateValue = history.getReturnDate() != null ? String.valueOf(history.getReturnDate()) : "";
      returnDate.appendChild(doc.createTextNode(returnDateValue));
      newHistory.appendChild(returnDate);

      Node library = doc.getElementsByTagName("vypozicky").item(0);
      library.appendChild(newHistory);

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(xmlFile);
      transformer.transform(source, result);

      System.out.println("New history added");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void writeBook(Book book) {
    try {
      System.out.println(filePath);
      File xmlFile = new File(filePath);
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(xmlFile);
      doc.getDocumentElement().normalize();

      Element newBook = doc.createElement("kniha");

      Element id = doc.createElement("id");
      id.appendChild(doc.createTextNode(String.valueOf(book.getId())));
      newBook.appendChild(id);

      Element name = doc.createElement("nazov");
      name.appendChild(doc.createTextNode(book.getBookName()));
      newBook.appendChild(name);

      Element author = doc.createElement("autor");
      author.appendChild(doc.createTextNode(book.getAuthor()));
      newBook.appendChild(author);

      Element taken = doc.createElement("pozicana");
      taken.appendChild(doc.createTextNode(String.valueOf(book.getTaken())));
      newBook.appendChild(taken);

      Node library = doc.getElementsByTagName("citatelia").item(0);
      library.appendChild(newBook);

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(xmlFile);
      transformer.transform(source, result);
      System.out.println("New Book added");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void writeUser(User user) {
    try {
      System.out.println(filePath);
      File xmlFile = new File(filePath);
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(xmlFile);
      doc.getDocumentElement().normalize();

      Element newUser = doc.createElement("citatel");

      Element op = doc.createElement("op");
      op.appendChild(doc.createTextNode(String.valueOf(user.getOp())));
      newUser.appendChild(op);

      Element name = doc.createElement("meno");
      name.appendChild(doc.createTextNode(user.getName()));
      newUser.appendChild(name);

      Element surname = doc.createElement("priezvisko");
      surname.appendChild(doc.createTextNode(user.getSurname()));
      newUser.appendChild(surname);

      Element dob = doc.createElement("datum_narodenia");
      dob.appendChild(doc.createTextNode(String.valueOf(user.getDob())));
      newUser.appendChild(dob);

      Node library = doc.getElementsByTagName("citatelia").item(0);
      library.appendChild(newUser);

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(xmlFile);
      transformer.transform(source, result);

      System.out.println("New user added");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void deleteHistory(int id, Long op) {
    try {
      File xmlFile = new File(filePath);
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(xmlFile);
      doc.getDocumentElement().normalize();
      NodeList citatelList = doc.getElementsByTagName("vypozicka");
      for (int i = 0; i < citatelList.getLength(); i++) {
        Element book = (Element) citatelList.item(i);
        String newid = book.getElementsByTagName("id").item(0).getTextContent();
        String newOp = book.getElementsByTagName("op").item(0).getTextContent();
        if (newid.equals(String.valueOf(id)) && newOp.equals(String.valueOf(op))) {
          book.getParentNode().removeChild(book);
          break;
        }
      }

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(xmlFile);
      transformer.transform(source, result);

      System.out.println("History removed");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void deleteBook(int id) {
    try {
      File xmlFile = new File(filePath);
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(xmlFile);
      doc.getDocumentElement().normalize();

      NodeList citatelList = doc.getElementsByTagName("kniha");
      for (int i = 0; i < citatelList.getLength(); i++) {
        Element book = (Element) citatelList.item(i);
        String newid = book.getElementsByTagName("id").item(0).getTextContent();
        if (newid.equals(String.valueOf(id))) {
          book.getParentNode().removeChild(book);
          break;
        }
      }

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(xmlFile);
      transformer.transform(source, result);

      System.out.println("Book removed");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void deleteUser(Long op) {
    try {
      File xmlFile = new File(filePath);
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(xmlFile);
      doc.getDocumentElement().normalize();

      NodeList citatelList = doc.getElementsByTagName("citatel");
      for (int i = 0; i < citatelList.getLength(); i++) {
        Element citatel = (Element) citatelList.item(i);
        String newop = citatel.getElementsByTagName("op").item(0).getTextContent();
        if (newop.equals(String.valueOf(op))) {
          citatel.getParentNode().removeChild(citatel);
          break;
        }
      }

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(xmlFile);
      transformer.transform(source, result);

      System.out.println("User removed");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
