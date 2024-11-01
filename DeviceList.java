import java.io.File;
import java.io.FileWriter;
import java.io.IOException;import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;import javax.xml.parsers.ParserConfigurationException;import javax.xml.transform.OutputKeys;import javax.xml.transform.Transformer;import javax.xml.transform.TransformerException;import javax.xml.transform.TransformerFactory;import javax.xml.transform.dom.DOMSource;import javax.xml.transform.stream.StreamResult;
import java.util.Arrays;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;import org.xml.sax.SAXException;



import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class DeviceList {
    private static final String IPV6_PATTERN =
            "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$";

    private ArrayList<Device> deviceList;
    private GroupList groupList = new GroupList();

    public DeviceList() {
        deviceList = new ArrayList<>();

    }

  public void removeDevice(Device device) {
        if(deviceList.contains(device))
            deviceList.remove(device);
        else
            throw new IllegalArgumentException("Device not found");
       
    }
    

    public int getSize() {
        return deviceList.size();
    }

    public ArrayList<Device> getDevices() {
        return deviceList;
    }

  public void setDeviceList(ArrayList<Device> deviceList) {
    this.deviceList = deviceList;
  }

  public void addDevice(Device device) {
    deviceList.add(device);

 }
    public static boolean isValidIPv6(String ipAddress) {
        Pattern pattern = Pattern.compile(IPV6_PATTERN);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    public boolean readableFile(File file) {   //check if it has the correct format, should be extended
        return file.exists() && file.canRead();     
    }


    public void readDeviceListFromXML(File file, Domain auxDomain,DomainList domainList) {
        try {
            deviceList = new ArrayList<>();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            NodeList directoryElements = doc.getElementsByTagName("Device");

            for (int i = 0; i < directoryElements.getLength(); i++) {
                Element directoryElement = (Element) directoryElements.item(i);
                String devicename = directoryElement.getElementsByTagName("Name").item(0).getTextContent();
                String password = directoryElement.getElementsByTagName("Password").item(0).getTextContent();
                String group = directoryElement.getElementsByTagName("Group").item(0).getTextContent();
                String ipAddress = directoryElement.getElementsByTagName("ipAddress").item(0).getTextContent();
                String ipv6Address = directoryElement.getElementsByTagName("ipv6Address").item(0).getTextContent();
                String domainPath = directoryElement.getElementsByTagName("Domain").item(0).getTextContent();
                Device device = new Device(devicename, password, auxDomain.getGroupList().getGroup(group),ipAddress, ipv6Address, domainList.getDomainByPath(domainPath));
                String[] wordsArray = group.split(",");
                ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(wordsArray));
                for(String auxString: wordsList){
                    Group auxgroup = auxDomain.getGroupList().getGroup(auxString);
                    if (auxgroup!= null){
                        auxgroup.getDeviceList().addDevice(device);
                    }
                }
                addDevice(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void readAuthorizedDeviceListFromXML(File file, Domain auxDomain,DomainList domainList, GroupList groupList) {
        try {
            deviceList = new ArrayList<>();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            NodeList directoryElements = doc.getElementsByTagName("Device");

            for (int i = 0; i < directoryElements.getLength(); i++) {
                Element directoryElement = (Element) directoryElements.item(i);
                String devicename = directoryElement.getElementsByTagName("Name").item(0).getTextContent();
                String password = directoryElement.getElementsByTagName("Password").item(0).getTextContent();
                String group = directoryElement.getElementsByTagName("Group").item(0).getTextContent();
                String ipAddress = directoryElement.getElementsByTagName("ipAddress").item(0).getTextContent();
                String ipv6Address = directoryElement.getElementsByTagName("ipv6Address").item(0).getTextContent();
                String domainPath = directoryElement.getElementsByTagName("Domain").item(0).getTextContent();
                Device device = new Device(devicename, password, groupList.getGroup(group),ipAddress, ipv6Address, domainList.getDomainByPath(domainPath));
                Group auxgroup = auxDomain.getGroupList().getGroup(group);
                if (auxgroup!= null){
                    auxgroup.getDeviceList().addDevice(device);
                }
                addDevice(device);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDeviceToXML(Device auxDevice, String filePath) { // temos de mudar estas funçoes
        try {
            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            // Find the parent element (Database)
            NodeList parentNodes = document.getElementsByTagName("deviceList");
            Element parent = (Element) parentNodes.item(0);

            // Create the new Device element
            Element deviceElement = document.createElement("Device");

            // Create and append the Name element
            Element nameElement = document.createElement("Name");
            nameElement.appendChild(document.createTextNode(auxDevice.getDeviceName()));
            deviceElement.appendChild(nameElement);

            // Create and append the Password element
            Element passwordElement = document.createElement("Password");
            passwordElement.appendChild(document.createTextNode(auxDevice.getPassword()));
            deviceElement.appendChild(passwordElement);

            // Create and append the group element
            Element groupElement = document.createElement("Group");
            groupElement.appendChild(document.createTextNode(auxDevice.getGroupsForString()));
            deviceElement.appendChild(groupElement);

            // Create and append the ip element
            Element ipAdressElement = document.createElement("ipAddress");
            ipAdressElement.appendChild(document.createTextNode(auxDevice.getIPAddress()));
            deviceElement.appendChild(ipAdressElement);

            // Create and append the ipv6 element
            Element ipv6AdressElement = document.createElement("ipv6Address");
            ipv6AdressElement.appendChild(document.createTextNode(auxDevice.getIPv6Address()));
            deviceElement.appendChild(ipv6AdressElement);

            // Create and append the ipv6 element
            Element domainElement = document.createElement("Domain");
            domainElement.appendChild(document.createTextNode(auxDevice.getDomain().getPath()));
            deviceElement.appendChild(domainElement);

            // Add the new Domain element to the parent (Database)
            parent.appendChild(deviceElement);

            // Write the updated XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Device added successfully!");

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
    public void addAuthorizedDeviceToXML(Device auxDevice, String filePath) { // temos de mudar estas funçoes
        try {
            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            // Find the parent element (Database)
            NodeList parentNodes = document.getElementsByTagName("deviceDomainAndGroupList");
            Element parent = (Element) parentNodes.item(0);

            // Create the new Device element
            Element deviceElement = document.createElement("Device");

            // Create and append the Name element
            Element nameElement = document.createElement("Name");
            nameElement.appendChild(document.createTextNode(auxDevice.getDeviceName()));
            deviceElement.appendChild(nameElement);

            // Create and append the Password element
            Element passwordElement = document.createElement("Password");
            passwordElement.appendChild(document.createTextNode(auxDevice.getPassword()));
            deviceElement.appendChild(passwordElement);

            // Create and append the group element
            Element groupElement = document.createElement("Group");
            groupElement.appendChild(document.createTextNode(auxDevice.getGroupsForString()));
            deviceElement.appendChild(groupElement);

            // Create and append the ip element
            Element ipAdressElement = document.createElement("ipAddress");
            ipAdressElement.appendChild(document.createTextNode(auxDevice.getIPAddress()));
            deviceElement.appendChild(ipAdressElement);

            // Create and append the ipv6 element
            Element ipv6AdressElement = document.createElement("ipv6Address");
            ipv6AdressElement.appendChild(document.createTextNode(auxDevice.getIPv6Address()));
            deviceElement.appendChild(ipv6AdressElement);

            // Create and append the ipv6 element
            Element domainElement = document.createElement("Domain");
            domainElement.appendChild(document.createTextNode(auxDevice.getDomain().getPath()));
            deviceElement.appendChild(domainElement);

            // Add the new Domain element to the parent (Database)
            parent.appendChild(deviceElement);

            // Write the updated XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Device added successfully!");

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
    public void addAuthorizedIpsToXML(Device auxDevice, String filePath) { // temos de mudar estas funçoes
        try {
            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            // Find the parent element (Database)
            NodeList parentNodes = document.getElementsByTagName("AuthorizedIpList");
            Element parent = (Element) parentNodes.item(0);

            // Create and append the ip element
            if (auxDevice.getIPAddress() != "" && auxDevice.getIPAddress() != null){
                Element ipAdressElement = document.createElement("ipAddress");
                ipAdressElement.appendChild(document.createTextNode(auxDevice.getIPAddress()));
                parent.appendChild(ipAdressElement);
            }

            // Create and append the ipv6 element
            if (auxDevice.getIPv6Address() != "" && auxDevice.getIPv6Address() != null){
                Element ipv6AdressElement = document.createElement("ipv6Address");
                ipv6AdressElement.appendChild(document.createTextNode(auxDevice.getIPv6Address()));
                parent.appendChild(ipv6AdressElement);
            }
            removeEmptyElements(parent);

            // Write the updated XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Ips added successfully!");

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
    private static void removeEmptyElements(Element parent) {
        NodeList childNodes = parent.getChildNodes();
        for (int i = childNodes.getLength() - 1; i >= 0; i--) {
            Node child = childNodes.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) child;
                if (element.getTextContent().trim().isEmpty()) {
                    parent.removeChild(element);
                } else {
                    removeEmptyElements(element);
                }
            }
        }
    }
    public boolean verifyName(String ipAddress) {
        return this.getDevices().stream().anyMatch(device -> device.getDeviceName().equals(ipAddress));

    }
    public boolean verifyDomainPath(String ipAddress) {
        return this.getDevices().stream().anyMatch(device -> device.getDomain().getPath().equals(ipAddress));

    }
    
    //verifies if the IP is already in use
    public boolean verifyIPAddress(String ipAddress) {
        return this.getDevices().stream().anyMatch(device -> device.getIPAddress().equals(ipAddress));
        
    }

    public boolean verifyIPv6Address(String ipv6Address) {
        return this.getDevices().stream().anyMatch(device -> device.getIPv6Address().equals(ipv6Address));
        
    }

    public Device getDevice(String deviceName) {
        return this.getDevices().stream().filter(device -> device.getDeviceName().equals(deviceName)).findFirst().orElse(null);
    }

    //verifies if the name is already in use
    public boolean verifyDeviceName(String name) {
        return this.getDevices().stream().anyMatch(device -> device.getDeviceName().equals(name));

    }
}


// Path: DeviceList.java
