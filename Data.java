import java.io.File;
import java.time.LocalDate;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Data {

    private Manager manager;

    public Data(Manager manager){
        this.manager = manager;
    }
    
    public void readData(String filePath){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));
            doc.getDocumentElement().normalize();

            //knihy
            NodeList bookList = doc.getElementsByTagName("kniha");
            for (int i = 0; i < bookList.getLength(); i++) {
                Element book = (Element) bookList.item(i);
                Book newBook = new Book(
                    Integer.parseInt(book.getElementsByTagName("id").item(0).getTextContent()),
                    book.getElementsByTagName("nazov").item(0).getTextContent(),
                    book.getElementsByTagName("autor").item(0).getTextContent(),
                    Boolean.parseBoolean(book.getElementsByTagName("pozicana").item(0).getTextContent())
                );
                manager.addBook(newBook);
            }

            //citatelia
            NodeList userList = doc.getElementsByTagName("citatel");
            for (int i = 0; i < userList.getLength(); i++) {
                Element user = (Element) userList.item(i);
                User newUser = new User(
                    Integer.parseInt(user.getElementsByTagName("op").item(0).getTextContent()),
                    user.getElementsByTagName("meno").item(0).getTextContent(),
                    user.getElementsByTagName("priezvisko").item(0).getTextContent(),
                    LocalDate.parse(user.getElementsByTagName("datum_narodenia").item(0).getTextContent())
                );
                manager.addUser(newUser);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
