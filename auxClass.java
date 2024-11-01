import java.io.File;
import java.util.Random;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
public class auxClass {
    public static String basePath = System.getProperty("user.dir") + "/Database";
    private static String deviceFileName = "Devices.xml";
    private static String domainFileName= "Domains.xml";
    private static String groupsFileName= "Groups.xml";
    private static String currentDirectoryStr = System.getProperty("user.dir");
    private static String databaseName = "Database";
    private static DomainList domainList = new DomainList();
    /*private static GroupList groupList = new GroupList();
    private static DeviceList deviceList = new DeviceList(domainList, groupList);*/

    // Create a File object for the parent folder
    private static File currentDirectory = new File(currentDirectoryStr);

    // Create a File object for the new folder within the parent folder
    private static File dataBase = new File(currentDirectory, databaseName);
    private static File network = new File(dataBase, "Network");
    private static File domainsDirectory = new File(dataBase, "Domains");
    private static File domainFile = new File(domainsDirectory, domainFileName);
    public auxClass(){
    }
    public void setupDatabase(){
        // Check if the new folder already exists
        if (dataBase.exists()) {
            System.out.println("The database already exists.");
            if (!dataBase.isDirectory()) {
                System.out.println("Invalid directory path: " + databaseName);
                return;
            }
            if (network.exists()) {
                generateAuthorizedDomainAndDevicesXMLFilesForSubdirectories(network);
            }
            else{
                network.mkdir();
            }
            if (!domainsDirectory.exists()){
                domainsDirectory.mkdir();
            }


        } else {
            // Create the new folder
            boolean created = dataBase.mkdir();
            boolean createdNetwork = network.mkdir();
            boolean createdDomains = domainsDirectory.mkdir();

            if (created && createdNetwork && createdDomains) {
                System.out.println("The database was created successfully.");
            } else {
                System.out.println("Failed to create the database.");
            }
        }
    }
    public void generateAuthorizedDomainAndDevicesXMLFilesForSubdirectories(File directory) {
        File[] subdirectories = directory.listFiles(File::isDirectory);
        if (subdirectories == null) {
            return;
        }

        for (File subdirectory : subdirectories) {
            if (!hasDomainXMLFile(subdirectory)) {
                generateAuthorizedDomainAndDevicesXMLFileForDirectory(subdirectory);
                // XML file not found in a subdirectory
            }
            if (!hasDevicesXMLFile(subdirectory)) {
                generateDevicesXMLFileForDirectory(subdirectory);
                // XML file not found in a subdirectory
            }
            if (!hasGroupsXMLFile(subdirectory)) {
                generateGroupsXMLFileForDirectory(subdirectory);
                // XML file not found in a subdirectory
            }
            if (!hasAuthorizedDomainsFile(subdirectory)) {
                generateAuthorizedDomainsFileForDirectory(subdirectory);
                // XML file not found in a subdirectory
            }
            if (!hasAuthorizedIpsFile(subdirectory)) {
                generateAuthorizedIpsFileForDirectory(subdirectory);
                // XML file not found in a subdirectory
            }

            // Recursively process subdirectories
            generateAuthorizedDomainAndDevicesXMLFilesForSubdirectories(subdirectory);
        }
    }
    public void generateAuthorizedDomainAndDevicesXMLFileForDirectory(File directory) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Create root element
            Element rootElement = doc.createElement("deviceDomainAndGroupList");
            doc.appendChild(rootElement);

            // Generate XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(directory.getPath() + "/" + directory.getName() + ".xml"));
            transformer.transform(source, result);

            System.out.println("XML file created for directory: " + directory.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void generateAuthorizedIpsFileForDirectory(File directory) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Create root element
            Element rootElement = doc.createElement("AuthorizedIpList");
            doc.appendChild(rootElement);

            // Generate XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(directory.getPath() + "/AuthorizedIps" + ".xml"));
            transformer.transform(source, result);

            System.out.println("XML file created for directory: " + directory.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void generateAuthorizedDomainsFileForDirectory(File directory) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Create root element
            Element rootElement = doc.createElement("AuthorizedDomainsList");
            doc.appendChild(rootElement);

            // Generate XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(directory.getPath() + "/AuthorizedDomains" + ".xml"));
            transformer.transform(source, result);

            System.out.println("XML file created for directory: " + directory.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void generateDevicesXMLFileForDirectory(File directory) {
        try {
            if (!hasDevicesXMLFile(directory)) { // XML file not found in a subdirectory

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();

                // Create root element
                Element rootElement = doc.createElement("deviceList");
                doc.appendChild(rootElement);

                // Generate XML file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                DOMSource source = new DOMSource(doc);
                StreamResult result =
                        new StreamResult(new File(directory.getPath() + "/" + "Devices" + ".xml"));
                transformer.transform(source, result);

                System.out.println("devices XML file created for directory: " + directory.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void generateGroupsXMLFileForDirectory(File auxdirectory) {
        try {
            File directory = auxdirectory;
            if (!hasGroupsXMLFile(directory)) { // XML file not found in a subdirectory

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();

                // Create root element
                Element rootElement = doc.createElement("GroupList");
                doc.appendChild(rootElement);

                // Generate XML file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                DOMSource source = new DOMSource(doc);
                StreamResult result =
                        new StreamResult(new File(directory.getPath() + "/" + "Groups" + ".xml"));
                transformer.transform(source, result);

                System.out.println("groupXML file created for directory: " + directory.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasDomainXMLFile(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(directory.getName() + ".xml") ){
                    return true;  // XML file found in the directory
                }
            }
        }
        return false;  // XML file not found in the directory
    }
    public boolean hasDevicesXMLFile(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals("Devices.xml")) {
                    return true;  // XML file found in the directory
                }
            }
        }
        return false;  // XML file not found in the directory
    }
    public boolean hasGroupsXMLFile(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals("Groups.xml")) {
                    return true;  // XML file found in the directory
                }
            }
        }
        return false;  // XML file not found in the directory
    }
    public boolean hasAuthorizedIpsFile(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals("AuthorizedIps.xml")) {
                    return true;  // XML file found in the directory
                }
            }
        }
        return false;  // XML file not found in the directory
    }
    public boolean hasAuthorizedDomainsFile(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals("AuthorizedDomains.xml")) {
                    return true;  // XML file found in the directory
                }
            }
        }
        return false;  // XML file not found in the directory
    }

    public void readDatabase(){
        String directoryPath = System.getProperty("user.dir") + "/Database/Network";

        try {
            domainList = new DomainList();
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Database");
            doc.appendChild(rootElement);

            Files.walkFileTree(Paths.get(directoryPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    Element directoryElement = doc.createElement("Domain");
                    rootElement.appendChild(directoryElement);

                    String subdirectoryName = dir.getFileName().toString();
                    String subdirectoryPath = dir.toAbsolutePath().toString();
                    subdirectoryPath = deleteBeforeWord(subdirectoryPath, "Network");
                    String lowerDirectories = getLowerDirectories(dir);

                    appendElement(doc, directoryElement, "Name", subdirectoryName);
                    appendElement(doc, directoryElement, "Path", subdirectoryPath);
                    appendElement(doc, directoryElement, "LowerDirectories", lowerDirectories);

                    return FileVisitResult.CONTINUE;
                }
            });

            // Save the XML document to a file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);

            // Remove the first directory element
            NodeList directoryElements = doc.getElementsByTagName("Domain");
            if (directoryElements.getLength() > 0) {
                Node firstDirectoryElement = directoryElements.item(0);
                firstDirectoryElement.getParentNode().removeChild(firstDirectoryElement);
            }

            FileOutputStream fos = new FileOutputStream(domainFile);
            transformer.transform(source, new StreamResult(fos));
            fos.close();
            System.out.println("Subdirectory information saved successfully in XML format.");
            if(domainList.readableFile(domainFile)){
                domainList.readDomainListFromXMl(domainFile);
            }

            for (Domain auxDomain : domainList.getDomains()) {
                File auxDevicesFile = new File(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + "Devices.xml");
                File auxGroupsFile = new File(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + "Groups.xml");
                File auxAuthorizedFile = new File(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                if (auxDomain.getGroupList().readableFile(auxGroupsFile)){
                    auxDomain.getGroupList().readGroupListFromXMl(auxGroupsFile, auxDomain);
                }
                if (auxDomain.getDeviceList().readableFile(auxDevicesFile)) { // if a device file is not readable, create new
                    auxDomain.getDeviceList().readDeviceListFromXML(auxDevicesFile, auxDomain,domainList);
                }
                if (auxDomain.getAuthorizedGroupList().readableFile(auxAuthorizedFile)) { // if a auhthorizedGroup file is not readable, create new
                    auxDomain.getAuthorizedGroupList().readAuthorizedGroupListFromXMl(auxAuthorizedFile, auxDomain, domainList);
                }
                if (auxDomain.getAuthorizedDomainList().readableFile(auxAuthorizedFile)) { // if a auhthorizedDomain file is not readable, create new
                    auxDomain.getAuthorizedDomainList().readAuthorizedDomainListFromXMl(auxAuthorizedFile);
                }

            }
            GroupList groupList = new GroupList();
            for (Domain auxDomain : domainList.getDomains()) {
                for(Group group: auxDomain.getGroupList().getGroups()){
                    groupList.addGroup(group);
                }
            }
            for (Domain auxDomain : domainList.getDomains()) {
                File auxAuthorizedFile = new File(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                if (auxDomain.getAuhtorizedDeviceList().readableFile(auxAuthorizedFile)) { // if a auhthorizedDevice file is not readable, create new
                    auxDomain.getAuhtorizedDeviceList().readAuthorizedDeviceListFromXML(auxAuthorizedFile, auxDomain,domainList, groupList);
                }
            }

        } catch (IOException | ParserConfigurationException |  TransformerException e) {
            e.printStackTrace();
        }
    }
    public String deleteBeforeWord(String str, String word) {
        int index = str.indexOf(word);
        if (index != -1) {
            return str.substring(index + word.length());
        }
        return str;
    }
    public void readGroups(File groupsFile){
        String directoryPath = System.getProperty("user.dir") + "/Database/Network";

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Groups");
            doc.appendChild(rootElement);

            Files.walkFileTree(Paths.get(directoryPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    Element directoryElement = doc.createElement("Group");
                    rootElement.appendChild(directoryElement);

                    String subdirectoryName = dir.getFileName().toString();

                    appendElement(doc, directoryElement, "Name", subdirectoryName);

                    return FileVisitResult.CONTINUE;
                }
            });

            // Save the XML document to a file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);

            // Remove the first directory element
            NodeList directoryElements = doc.getElementsByTagName("Group");
            if (directoryElements.getLength() > 0) {
                Node firstDirectoryElement = directoryElements.item(0);
                firstDirectoryElement.getParentNode().removeChild(firstDirectoryElement);
            }

            FileOutputStream fos = new FileOutputStream(groupsFile);
            transformer.transform(source, new StreamResult(fos));
            fos.close();

        } catch (IOException | ParserConfigurationException |  TransformerException e) {
            e.printStackTrace();
        }
    }

    public void appendElement(Document doc, Element parentElement, String elementName, String elementValue) {
        Element element = doc.createElement(elementName);
        element.appendChild(doc.createTextNode(elementValue));
        parentElement.appendChild(element);
    }

    public String getLowerDirectories(Path directoryPath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
            for (Path subPath : stream) {
                if (Files.isDirectory(subPath)) {
                    sb.append(subPath.getFileName().toString()).append(",");
                }
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1); // Remove trailing comma
        }
        return sb.toString();
    }

  public DomainList getDomainList() {
    return domainList;
  }

  public String getBasePath() {
    return basePath;
  }
}
