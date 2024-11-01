import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class DomainList {
    private ArrayList<Domain> domainList;

    public DomainList() {
        domainList = new ArrayList<>();
    }

    public void addDomain(Domain domain) {
        
        if(domainList != null && !verifyDomainName(domain.getName(), domain.getPath())){
            domainList.add(domain);

        }
        else if (domainList == null){
            domainList.add(domain);

        }

    }
    public boolean verifyPath(String name) {
        return this.getDomains().stream().anyMatch(domain -> domain.getPath().equals(name));

    }
    public boolean verifyPath2(String name) {
        return this.getDomains().stream().anyMatch(domain -> domain.getPath().startsWith(name));

    }

  public void setDomainList(ArrayList<Domain> domainList) {
    this.domainList = domainList;
  }

  public void removeDomain(Domain domain) {
        if(domainList.contains(domain))
            domainList.remove(domain);
        else
            throw new IllegalArgumentException("Domain not found");

    }


    public int getSize() {
        return domainList.size();
    }

    public ArrayList<Domain> getDomains() {
        return domainList;
    }

    public boolean readableFile(File file) {
        return file.exists() && file.canRead();
    }

    public void readDomainListFromXMl(File file){

            try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            NodeList directoryElements = doc.getElementsByTagName("Domain");

            for (int i = 0; i < directoryElements.getLength(); i++) {
                Element directoryElement = (Element) directoryElements.item(i);
                String subdirectoryName = directoryElement.getElementsByTagName("Name").item(0).getTextContent();
                String subdirectoryPath = directoryElement.getElementsByTagName("Path").item(0).getTextContent();
                String lowerDirectories = directoryElement.getElementsByTagName("LowerDirectories").item(0).getTextContent();
                Domain domain = new Domain(subdirectoryName ,lowerDirectories, subdirectoryPath);
                addDomain(domain);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void readAuthorizedDomainListFromXMl(File file){

        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            NodeList directoryElements = doc.getElementsByTagName("domain");

            for (int i = 0; i < directoryElements.getLength(); i++) {
                Element directoryElement = (Element) directoryElements.item(i);
                String subdirectoryName = directoryElement.getElementsByTagName("Name").item(0).getTextContent();
                String subdirectoryPath = directoryElement.getElementsByTagName("Path").item(0).getTextContent();
                String lowerDirectories = directoryElement.getElementsByTagName("LowerDirectories").item(0).getTextContent();
                Domain domain = new Domain(subdirectoryName ,lowerDirectories, subdirectoryPath);
                addDomain(domain);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //verifies if the name is already in use
    public boolean verifyDomainName(String name, String path) {
        return this.getDomains().stream().anyMatch(domain -> domain.getName().equals(name) && domain.getPath().equals(path));

    }

    public Domain getDomain(String Name) {
        return this.getDomains().stream().filter(domain -> domain.getName().equals(Name)).findFirst().orElse(null);
    }

    public Domain getDomainByPath(String path) {
        return this.getDomains().stream()
            .filter(domain -> {
                String domainPath = domain.getPath();
                return domainPath != null && domainPath.equals(path);
            })
            .findFirst()
            .orElse(null);
    }
    public void addAuthorizedDomainToXML(Domain auxDomain, String filePath) {
        try {
            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            // Find the parent element (Database)
            NodeList parentNodes = document.getElementsByTagName("deviceDomainAndGroupList");
            Element parent = (Element) parentNodes.item(0);

            // Create the new Device element
            Element domainElement = document.createElement("domain");

            // Create and append the Name element
            Element nameElement = document.createElement("Name");
            nameElement.appendChild(document.createTextNode(auxDomain.getName()));
            domainElement.appendChild(nameElement);

            // Create and append the Path element
            Element passwordElement = document.createElement("Path");
            passwordElement.appendChild(document.createTextNode(auxDomain.getPath()));
            domainElement.appendChild(passwordElement);

            // Create and append the LowerDirectories element
            Element lowerDirectoriesElement = document.createElement("LowerDirectories");
            lowerDirectoriesElement.appendChild(document.createTextNode(auxDomain.getLowerDomains()));
            domainElement.appendChild(lowerDirectoriesElement);


            // Add the new Domain element to the parent (Database)
            parent.appendChild(domainElement);

            // Write the updated XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Domain added successfully!");

    } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
    public void addAuthorizedDomainToAuthorizedDomainsXML(Domain auxDomain, String filePath) {
        try {
            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            // Find the parent element (Database)
            NodeList parentNodes = document.getElementsByTagName("AuthorizedDomainsList");
            Element parent = (Element) parentNodes.item(0);

            // Create the new Device element
            Element domainElement = document.createElement("domain");

            // Create and append the Name element
            Element nameElement = document.createElement("Name");
            nameElement.appendChild(document.createTextNode(auxDomain.getName()));
            domainElement.appendChild(nameElement);

            // Create and append the Path element
            Element passwordElement = document.createElement("Path");
            passwordElement.appendChild(document.createTextNode(auxDomain.getPath()));
            domainElement.appendChild(passwordElement);

            // Create and append the LowerDirectories element
            Element lowerDirectoriesElement = document.createElement("LowerDirectories");
            lowerDirectoriesElement.appendChild(document.createTextNode(auxDomain.getLowerDomains()));
            domainElement.appendChild(lowerDirectoriesElement);


            // Add the new Domain element to the parent (Database)
            parent.appendChild(domainElement);

            // Write the updated XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Domain added successfully!");

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}