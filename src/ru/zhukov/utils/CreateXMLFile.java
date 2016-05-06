package ru.zhukov.utils;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ru.zhukov.domain.AccrualEmployee;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Gukov on 06.05.2016.
 */
public class CreateXMLFile {

    private final List<AccrualEmployee> employeeList;
    private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder documentBuilder;

    public CreateXMLFile(List<AccrualEmployee> employeeList){

        this.employeeList = employeeList;
    }

    public void createFileForBank(){
        try{
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("СчетаПК");
            rootElement.setAttribute("ДатаФормирования", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            rootElement.setAttribute("НомерДоговора","---" );
            rootElement.setAttribute("НаименованиеОрганизации","---" );
            rootElement.setAttribute("ИНН","---" );
            rootElement.setAttribute("РасчетныйСчетОрганизации","---" );
            document.appendChild(rootElement);
                    //document.createAttribute("СчетаПК ДатаФормирования");
            Element pay = document.createElement("ЗачислениеЗарплаты");
            rootElement.appendChild(pay);

            Element employee = document.createElement("");



            TransformerFactory transformerFactory =
                    SAXTransformerFactory.newInstance();
            Transformer transformer =
                    transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "Windows-1251");
            //transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);

            StreamResult result =
                    new StreamResult(new File("C:\\cars.xml"));
            transformer.transform(source, result);
            // Output to console for testing
            StreamResult consoleResult =
                    new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        }catch (ParserConfigurationException e){

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
    private Node createEmployeeSection(AccrualEmployee employee){
          return  null;
    }


    public static void main(String... args){
        new CreateXMLFile(null).createFileForBank();
    }
}

