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

public class GroupList {
    private ArrayList<Group> groupList;
    public GroupList() {
        groupList = new ArrayList<>();
    }
    public void addGroup(Group group) {
        if(groupList != null && !verifyGroupName(group.getName())){
            groupList.add(group);

        }
        else if (groupList == null){
            groupList.add(group);

        }

    }

    public void removeGroup(Group group) {
        if(groupList.contains(group))
            groupList.remove(group);
        else
            throw new IllegalArgumentException("Group not found");

    }


    public int getSize() {
        return groupList.size();
    }

    public ArrayList<Group> getGroups() {
        return groupList;
    }

    public boolean readableFile(File file) {
        return file.exists() && file.canRead();
    }

    public void readGroupListFromXMl(File file,Domain auxDomain){

        try {
            groupList = new ArrayList<>();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            NodeList directoryElements = doc.getElementsByTagName("Group");

            for (int i = 0; i < directoryElements.getLength(); i++) {
                Element directoryElement = (Element) directoryElements.item(i);
                String subdirectoryName = directoryElement.getElementsByTagName("Name").item(0).getTextContent();
                Group group = new Group(subdirectoryName, auxDomain); //temos de mudar isto
                addGroup(group);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String eraseStringFromEnd(String originalString, char targetChar) {
        int lastIndex = originalString.length() - 1;
        while (lastIndex >= 0 && originalString.charAt(lastIndex) != targetChar) {
            lastIndex--;
        }

        if (lastIndex >= 0) {
            return originalString.substring(0, lastIndex);
        } else {
            return originalString;
        }
    }
    public void readAuthorizedGroupListFromXMl(File file,Domain auxDomain, DomainList domainList){

        try {
            groupList = new ArrayList<>();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            Element rootElement = doc.getDocumentElement();
            NodeList directoryElements = rootElement.getElementsByTagName("group");

            for (int i = 0; i < directoryElements.getLength(); i++) {
                Element directoryElement = (Element) directoryElements.item(i);
                String subdirectoryName = directoryElement.getElementsByTagName("Name").item(0).getTextContent();
                String subdirectoryPath = directoryElement.getElementsByTagName("Path").item(0).getTextContent();
                Group group = new Group(subdirectoryName, domainList.getDomainByPath(eraseStringFromEnd(subdirectoryPath, File.separatorChar))); //temos de mudar isto
                addGroup(group);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addGroupToXML(Group auxGroup, String filePath) { //temos de mudar estas funçoes de save
        try {
            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            // Find the parent element (Database)
            NodeList parentNodes = document.getElementsByTagName("GroupList");
            Element parent = (Element) parentNodes.item(0);

            // Create the new Device element
            Element groupElement = document.createElement("Group");

            // Create and append the Name element
            Element nameElement = document.createElement("Name");
            nameElement.appendChild(document.createTextNode(auxGroup.getName()));
            groupElement.appendChild(nameElement);

            // Create and append the Password element
            Element passwordElement = document.createElement("Path");
            passwordElement.appendChild(document.createTextNode(auxGroup.getPath()));
            groupElement.appendChild(passwordElement);


            // Add the new Domain element to the parent (Database)
            parent.appendChild(groupElement);

            // Write the updated XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Group added successfully!");

    } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
    public void addAuthorizedGroupToXML(Group auxGroup, String filePath) { //temos de mudar estas funçoes de save
        try {
            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            // Find the parent element (Database)
            NodeList parentNodes = document.getElementsByTagName("deviceDomainAndGroupList");
            Element parent = (Element) parentNodes.item(0);

            // Create the new Device element
            Element groupElement = document.createElement("group");

            // Create and append the Name element
            Element nameElement = document.createElement("Name");
            nameElement.appendChild(document.createTextNode(auxGroup.getName()));
            groupElement.appendChild(nameElement);

            // Create and append the Password element
            Element passwordElement = document.createElement("Path");
            passwordElement.appendChild(document.createTextNode(auxGroup.getPath()));
            groupElement.appendChild(passwordElement);


            // Add the new Domain element to the parent (Database)
            parent.appendChild(groupElement);

            // Write the updated XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Group added successfully!");

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    //verifies if the name is already in use
    public boolean verifyGroupName(String name) {
        return this.getGroups().stream().anyMatch(group -> group.getName().equals(name));

    }
    public boolean verifyPath(String name) {
        return this.getGroups().stream().anyMatch(group -> group.getPath().equals(name));

    }

    public Group getGroup(String path) {
        return this.getGroups().stream().filter(group -> group.getPath().equals(path)).findFirst().orElse(null);
    }
    public String getGroupForString(Group group) {
        if(group == null)
            return "";
        else{
            return group.getPath();
        }
    }
    public void setGroupList(ArrayList<Group> groupList) {
            this.groupList = groupList;
    }
}
