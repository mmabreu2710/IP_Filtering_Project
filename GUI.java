import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;

import java.nio.file.DirectoryStream;import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.w3c.dom.*;import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;




public class GUI extends JFrame implements ActionListener {
  private static String basePath;
  private auxClass auxClassGui;
    private DeviceList deviceList;
    private Device currentDevice;
    private Domain currentDomain;
    private Group currentGroup;
    private Domain currentMainNetwork;
    private Domain authDomain;
    private DomainList domainList;
    private GroupList groupList;
    private DomainList networkDomainList;
    private JButton[] deviceButtons;
    //private JButton[] domainButtons;
    //private JButton[] networkButtons;
    private JButton[] groupButtons;
    private JButton[] deviceOfGroupButtons;
    private JButton[] authorizedDevicesButtons;
    private JButton[] authorizedGroupsButtons;
    private JButton[] authorizedNetworksButtons;
    private JButton addNetworkButton;
    private JButton removeNetworkButton;
    private JButton removeAllNetworksButton;
    private JButton closeSecondaryWindowButton;
    private JButton addDomainButton;
    private JButton removeDomainButton;
    private JButton showDevices;
    private JButton showGroups;
    private JButton showAuthorizedDevices;
    private JButton showAuthorizedGroups;
    private JButton showAuthorizedNetworks;
    private JButton goBackButton;
    private JButton addDeviceButton;
    private JButton removeDeviceButton;
    private JButton editDeviceButton;
    private JButton addGroupButton;
    private JButton removeGroupButton;
    //private JButton editGroupButton;
    private JButton addDeviceToGroupButton;
    private JButton removeDeviceFromGroupButton;
    private JButton addAuthorizedDeviceButton;
    private JButton removeAuthorizedDeviceButton;
    private JButton addAuthorizedGroupButton;
    private JButton removeAuthorizedGroupButton;
    private JButton addAuthorizedNetworkButton;
    private JButton removeAuthorizedNetworkButton;

    private JButton goBackButtonToGroup;
    private JButton goBackButtonAuthorized;
    private JButton[] removeNetworkButtons;
    private JButton[] editDeviceButtons;
    private JButton[] removeDeviceButtons;
    private JButton[] removeGroupButtons;
    private JButton[] addToGroupButtons;
    private JButton[] removeFromGroupButtons;
    private JButton[] addDeviceToAuthorizedButtons;
    private JButton[] removeDeviceFromAuthorizedButtons;
    private JButton[] addGroupToAuthorizedButtons;
    private JButton[] removeGroupFromAuthorizedButtons;
    private JButton[] removeNetworkFromAuthorizedButtons;
    private JButton exampleButton;


    //private JButton exportButton;
    //private JButton chooseButton;
    private Color colorRed = new Color(255, 0, 0); //red
    private Color colorGreen = new Color(0,110,51); //dark green
    private Color colorBabyBlue = new Color(173, 216, 230); //baby blue
    private Color colorLightYellow = new Color(255, 255, 153); //light yellow
    private Color colorLightPink = new Color(255, 204, 204); //light pink
    private Color colorOrange = new Color(255, 128, 0); //orange
    private Color colorLightGray = new Color(211, 211, 211); //light gray
    private Color colorLightBrown = new Color(255, 204, 153); //light brown
    Font buttonFont =  new Font("Arial", Font.BOLD, 16);
    Font defaultFont = new Font("Arial", Font.PLAIN, 20);
    Font titleFont = new Font("Arial", Font.BOLD, 20);
    Font treeFont = new Font("Arial", Font.BOLD, 25);
    private JPanel mainPanel;
    private Boolean[] networkPressed;
    private Boolean anyNetworkPressed = false;
    //private Integer DomainsONScreen = 100;
    private Integer defaultButtonSize = 1465;
    private Integer secondaryButtonSize = 700;
    private Integer newDefaultButtonSize = 745;
    //private HashMap<Integer, Integer> networkPanels;
    //private Integer panelNumber;
    private HashMap<JButton, Domain> buttonOfDomain = new HashMap<JButton, Domain>();

    private JTree tree;
    private JLabel selectedLabel;
    ImageIcon imageIcon = new ImageIcon(GUI.class.getResource("network.png"));
    ImageIcon imageOpenIcon = new ImageIcon(GUI.class.getResource("networkOpen.png"));


    public GUI(auxClass auxAuxclass, String basePath, DomainList domainList, String title) {
        super(title);
        this.auxClassGui = auxAuxclass;
        this.basePath = basePath;
        this.domainList = domainList;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(3600, 3600);

        mainPanel = new JPanel(new GridLayout(1, 2));
        defaultWindow();
        allBlankSecondaryWindow();


        setVisible(true);
    }
    private void allBlankSecondaryWindow(){
        blankSecondaryWindow(colorOrange, "Network's Configurations");
        //blankSecondaryWindow(colorLightYellow, "Groups");
        //blankSecondaryWindow(colorLightPink, "Authorized Devices");
    }

    private void blankSecondaryWindow(Color color, String text) {
        JPanel blankPanel = new JPanel(new GridLayout(15, 1));
        blankPanel.setPreferredSize(new Dimension(400, 600));
        blankPanel.setBackground(color);
        //domainListPanel.setBorder(BorderFactory.createTitledBorder("Domain"));
        Font font = new Font("Arial", Font.BOLD, 20);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), text);
        titledBorder.setTitleFont(font);
        blankPanel.setBorder(titledBorder);
        mainPanel.add(blankPanel);
        add(mainPanel);

    }

    private void defaultWindow(){
        JPanel networkListPanel = new JPanel(new BorderLayout());
        networkListPanel.setBackground(colorBabyBlue);
        Font font = new Font("Arial", Font.BOLD, 20);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Network");
        titledBorder.setTitleFont(font);
        networkListPanel.setBorder(titledBorder);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));




        addNetworkButton = new JButton("+ Add Network");
        addNetworkButton.setFont(buttonFont);
        addNetworkButton.setForeground(Color.BLACK);
        addNetworkButton.setBackground(Color.WHITE);
        addNetworkButton.addActionListener(this);
        addNetworkButton.setBounds(10, 10, 100,50);
        //buttonPanel.add(addNetworkButton);

        removeNetworkButton = new JButton("- Remove Network");
        removeNetworkButton.setFont(buttonFont);
        removeNetworkButton.setForeground(colorRed);
        removeNetworkButton.setBackground(Color.BLACK);
        removeNetworkButton.addActionListener(this);
        removeNetworkButton.setBounds(10, 30, 100, 50);
        //buttonPanel.add(removeNetworkButton);

        removeAllNetworksButton = new JButton("- Remove All Networks");
        removeAllNetworksButton.setFont(buttonFont);
        removeAllNetworksButton.setForeground(colorRed);
        removeAllNetworksButton.setBackground(Color.BLACK);
        removeAllNetworksButton.addActionListener(this);
        removeAllNetworksButton.setBounds(10, 50, 100, 50);
        //buttonPanel.add(removeAllNetworksButton);





        buttonPanel.setBackground(colorBabyBlue);
        buttonPanel.add(addNetworkButton);
        buttonPanel.add(removeNetworkButton);
        buttonPanel.add(removeAllNetworksButton);
        networkListPanel.add(buttonPanel, BorderLayout.NORTH);

        Component spacer = Box.createRigidArea(new Dimension(0, 1000));
        networkListPanel.add(spacer);


        // add a button for each network in the system

        networkDomainList = getNetworkInfo(domainList);



        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        addDomainObjects(root, networkDomainList);
        tree = new JTree(root);
        tree.setBackground(colorBabyBlue);
        tree.setShowsRootHandles(true);
        tree.setRootVisible(false);
        add(new JScrollPane(tree));


        selectedLabel = new JLabel();
        add(selectedLabel, BorderLayout.SOUTH);

        DefaultTreeCellRenderer cellRenderer = new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Component component = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                component.setFont(treeFont);
                return component;
            }
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(colorBabyBlue);
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }





        };

        Image resizedIcon = imageIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        Image resizedOpenIcon = imageOpenIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedIcon);
        imageOpenIcon = new ImageIcon(resizedOpenIcon);
        cellRenderer.setLeafIcon(imageIcon);
        cellRenderer.setOpenIcon(imageOpenIcon);
        cellRenderer.setClosedIcon(imageIcon);
        tree.setCellRenderer(cellRenderer);


        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    String domainName = selectedNode.getUserObject().toString();
                    System.out.println(domainName + " network button pressed");
                    Domain domain = getDomainFromPath(selectedNode);
                    currentDomain = domain;
                    currentMainNetwork = domain;
                    if(domain != null){
                        //System.out.println(domain.getName() + " accessed");
                        cleanSecondaryWindows();
                        secondaryWindow(domain);
                        //blankSecondaryWindow(colorLightYellow, "Groups");
                        //blankSecondaryWindow(colorLightPink, "Authorized Devices");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    } else {
                        System.out.println("Domain not found");
                    }
                }
            }
        });





        JPanel treePanel = new JPanel(new BorderLayout());
        JScrollPane treeScrollPane = new JScrollPane(tree);
        treeScrollPane.setPreferredSize(new Dimension(600, 600)); // Set preferred size here
        treePanel.add(treeScrollPane, BorderLayout.CENTER);
        networkListPanel.add(treePanel, BorderLayout.CENTER);

        // Add the networkListPanel to the mainPanel
        mainPanel.add(networkListPanel);
        add(mainPanel);

    }

    private void addDomainObjects(DefaultMutableTreeNode parent, Domain domain) {
        // Create a new node for the current domain
        DefaultMutableTreeNode domainNode = new DefaultMutableTreeNode(domain.getName());

        // Iterate over the subdomains and add them as child nodes
        for (Domain subDomain : getLowerDomainList(domain.getPath(), domain.getLowerDomains()).getDomains()) {
            addDomainObjects(domainNode, subDomain);
        }
        // Add the current domain node to the parent node
        parent.add(domainNode);
    }

    private void addDomainObjects(DefaultMutableTreeNode parent, DomainList networkList) {
        // Retrieve your domain structure dynamically and add the nodes to the parent

        // Iterate over the root domains and add them as child nodes
        for (Domain rootDomain : networkList.getDomains()) {
            addDomainObjects(parent, rootDomain);
        }
    }

    private Domain getDomainFromPath(DefaultMutableTreeNode node) {
        ArrayList<String> domainNames = new ArrayList<>();
        DefaultMutableTreeNode currentNode = node;
        while (currentNode != null) {
            String domainName = currentNode.getUserObject().toString();
            domainNames.add(0, domainName);
            currentNode = (DefaultMutableTreeNode) currentNode.getParent();
        }
        String path = String.join(File.separator, domainNames.subList(1, domainNames.size()));
        path = String.valueOf(File.separator).concat(path);
        return domainList.getDomainByPath(path);
    }

    private void secondaryWindow(Domain domain){
        UIManager.put("OptionPane.background", colorOrange);
        UIManager.put("Panel.background", colorOrange);
        JPanel domainListPanel = new JPanel();
        domainListPanel.setPreferredSize(new Dimension(400, 600));
        domainListPanel.setBackground(colorOrange);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), domain.getPath());
        titledBorder.setTitleFont(titleFont);
        domainListPanel.setBorder(titledBorder);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0 , 0));

        addDomainButton = new JButton("+ Add Subnetwork");
        addDomainButton.setBounds(10, 10, 100, 50);
        addDomainButton.setFont(buttonFont);
        addDomainButton.setForeground(Color.BLACK);
        addDomainButton.setBackground(Color.WHITE);
        addDomainButton.addActionListener(this);
        addDomainButton.putClientProperty("Domain", domain);
        menuPanel.add(addDomainButton);

        removeDomainButton = new JButton("- Remove this network");
        removeDomainButton.setBounds(10, 10, 100, 50);
        removeDomainButton.setFont(buttonFont);
        removeDomainButton.setForeground(colorRed);
        removeDomainButton.setBackground(Color.BLACK);
        removeDomainButton.addActionListener(this);
        menuPanel.add(removeDomainButton);

        closeSecondaryWindowButton = new JButton("X");
        closeSecondaryWindowButton.setForeground(Color.WHITE);
        closeSecondaryWindowButton.setBackground(colorRed);
        closeSecondaryWindowButton.setFont(buttonFont);
        closeSecondaryWindowButton.addActionListener(this);
        closeSecondaryWindowButton.setBounds(10, 10, 100, 50);

        menuPanel.add(closeSecondaryWindowButton, BorderLayout.WEST);
        domainListPanel.add(menuPanel);



        // Add spacer between the closeSecondaryWindowButton and the showDevices button
        Component spacer = Box.createRigidArea(new Dimension(0, 100));
        domainListPanel.add(spacer);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(colorOrange);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.add(menuPanel);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel domainButtonPanel = new JPanel(new GridBagLayout());
        domainButtonPanel.setBackground(colorOrange);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 0, 0);

        showDevices = new JButton("Devices");
        showDevices.setPreferredSize(new Dimension(newDefaultButtonSize, 50));
        showDevices.setFont(buttonFont);
        showDevices.setBackground(colorLightGray);
        showDevices.setForeground(Color.BLACK);
        showDevices.addActionListener(this);
        domainButtonPanel.add(showDevices,constraints);
        constraints.gridy++;


        showGroups = new JButton("Groups");
        showGroups.setPreferredSize(new Dimension(newDefaultButtonSize, 50));
        showGroups.setFont(buttonFont);
        showGroups.setBackground(colorLightYellow);
        showGroups.setForeground(Color.BLACK);
        showGroups.addActionListener(this);
        domainButtonPanel.add(showGroups,constraints);
        constraints.gridy++;

        showAuthorizedDevices = new JButton("Authorized Devices");
        showAuthorizedDevices.setPreferredSize(new Dimension(newDefaultButtonSize, 50));
        showAuthorizedDevices.setFont(buttonFont);
        showAuthorizedDevices.setBackground(colorLightPink);
        showAuthorizedDevices.setForeground(Color.BLACK);
        showAuthorizedDevices.addActionListener(this);
        domainButtonPanel.add(showAuthorizedDevices,constraints);
        constraints.gridy++;


        showAuthorizedGroups = new JButton("Authorized Groups");
        showAuthorizedGroups.setPreferredSize(new Dimension(newDefaultButtonSize, 50));
        showAuthorizedGroups.setFont(buttonFont);
        showAuthorizedGroups.setBackground(colorLightPink);
        showAuthorizedGroups.setForeground(Color.BLACK);
        showAuthorizedGroups.addActionListener(this);
        domainButtonPanel.add(showAuthorizedGroups,constraints);
        constraints.gridy++;


        showAuthorizedNetworks = new JButton("Authorized Networks");
        showAuthorizedNetworks.setPreferredSize(new Dimension(newDefaultButtonSize, 50));
        showAuthorizedNetworks.setFont(buttonFont);
        showAuthorizedNetworks.setBackground(colorLightPink);
        showAuthorizedNetworks.setForeground(Color.BLACK);
        showAuthorizedNetworks.addActionListener(this);
        domainButtonPanel.add(showAuthorizedNetworks,constraints);
        constraints.gridy++;

        mainPanel.add(domainButtonPanel,BorderLayout.CENTER);
        domainListPanel.add(mainPanel);

        this.mainPanel.add(domainListPanel);
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }

    

    private void addNetwork(){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JTextField name = new JTextField(5);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Name:")).setFont(defaultFont);
        panel.add(name).setFont(defaultFont);
        panel.add(Box.createVerticalStrut(3)); // a spacer
        panel.add(Box.createRigidArea(new Dimension(0, 3))); // fixed height spacer

        panel.setPreferredSize(new Dimension(300, 50));
        panel.setFont(defaultFont);

        int result;
        JOptionPane dialog = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialogWindow = dialog.createDialog(null, "Please Enter the Network's Name to be added");
        dialogWindow.setVisible(true);
        result = (Integer) dialog.getValue();


        if (result == JOptionPane.OK_OPTION) {
            if (name.getText().equals("")) {
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid name      "), "Invalid name", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addNetwork();
            }
            else if(domainList.verifyDomainName(name.getText(), File.separator + name.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Network already exists     "), "Network already exists", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addNetwork();
            }

            else{
                Domain newNetwork = new Domain(name.getText(),"" , File.separator + name.getText());
                domainList.addDomain(newNetwork);
                addNetworkToXML(newNetwork);
                removeEmptyLinesFromXML(basePath + File.separator + "Domains/Domains.xml");
                File currentDirectory = new File(basePath + File.separator + "Network");
                File newNetworkDir = new File(currentDirectory, newNetwork.getName());
                newNetworkDir.mkdir();
                auxClassGui.generateAuthorizedDomainAndDevicesXMLFileForDirectory(newNetworkDir);
                auxClassGui.generateDevicesXMLFileForDirectory(newNetworkDir);
                auxClassGui.generateGroupsXMLFileForDirectory(newNetworkDir);
                auxClassGui.generateAuthorizedDomainsFileForDirectory(newNetworkDir);
                auxClassGui.generateAuthorizedIpsFileForDirectory(newNetworkDir);


                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, name.getText() + " added successfully"), "Network added successfully", JOptionPane.INFORMATION_MESSAGE);
                resetAllWindows();

            }
        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Network not added.       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            dialogWindow.dispose();

        }


    }

    private void selectRemoveNetwork() {
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        //panel.setBackground(colorRed);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        DomainList networkList = getNetworkInfo(this.domainList);
        removeNetworkButtons = new JButton[domainList.getSize()];
        int i = 0;
        for (Domain domain : networkList.getDomains()) {
            System.out.println(domain.getLowerDomains() + " are the lower domains of " + domain.getName());
            removeNetworkButtons[i] = new JButton(domain.getName());
            removeNetworkButtons[i].setFont(buttonFont);
            removeNetworkButtons[i].setActionCommand(String.valueOf(i));
            removeNetworkButtons[i].addActionListener(this);
            panel.add(removeNetworkButtons[i]);
            i++;


        }
        panel.setPreferredSize(new Dimension(200, 35*networkList.getSize()));
        panel.setFont( defaultFont );


        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPanel.setPreferredSize(new Dimension(300, 300));
        JPanel removePanel = new JPanel(new BorderLayout());
        removePanel.add(scrollPanel, BorderLayout.CENTER);
        JPanel outherPanel = new JPanel(new BorderLayout());
        outherPanel.add(removePanel, BorderLayout.CENTER);
        outherPanel.setBackground(colorRed);

        UIManager.put("OptionPane.background", colorRed);
        UIManager.put("Panel.background", colorRed);


        JOptionPane dialog = new JOptionPane(outherPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        dialog.setBackground(colorRed);
        JDialog dialogWindow = dialog.createDialog(null, "Please Select the Network to be removed");
        dialogWindow.setBackground(colorRed);
        dialogWindow.setVisible(true);


    }

    private void removeNetwork(Domain domain){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to remove network " + domain.getName() + "?        "),
                "Are you sure you want to remove network " + domain.getName() + "?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            try{
                auxClassGui.readDatabase();
                for(Domain allDomain: auxClassGui.getDomainList().getDomains()){
                    if(allDomain.getAuthorizedDomainList().verifyPath(domain.getPath()) || allDomain.getAuthorizedDomainList().verifyPath2(domain.getPath())){
                        ArrayList<String> ipList = removeDomainDeviceAndGroupGivenDomainPathFromXML(allDomain, domain);
                        removeEmptyLinesFromXML(basePath + File.separator + "Network" + allDomain.getPath() + File.separator + allDomain.getName() + ".xml");
                        removeAuthorizedDeviceIPFromGroupFromXML(allDomain.getPath(), ipList);
                        removeEmptyLinesFromXML(basePath + File.separator + "Network" + allDomain.getPath() + File.separator + "AuthorizedIps.xml");
                        removeAllAuthorizedNetworkFromAuthorizedNetworkXML(allDomain,domain);
                        removeEmptyLinesFromXML(basePath + File.separator + "Network" + allDomain.getPath() + File.separator + "AuthorizedDomains.xml");
                        auxClassGui.readDatabase();
                        allDomain.getAuthorizedGroupList().setGroupList((auxClassGui.getDomainList().getDomainByPath(allDomain.getPath()).getAuthorizedGroupList().getGroups()));
                        allDomain.getAuhtorizedDeviceList().setDeviceList((auxClassGui.getDomainList().getDomainByPath(allDomain.getPath()).getAuhtorizedDeviceList().getDevices()));
                        allDomain.getAuthorizedDomainList().setDomainList((auxClassGui.getDomainList().getDomainByPath(allDomain.getPath()).getAuthorizedDomainList().getDomains()));
                    }
                }
                Path directoryPath = Paths.get(basePath + File.separator + "Network" + domain.getPath());
                deleteDirectory(directoryPath);
                removeDomainFromXML(domain.getPath());
                removeEmptyLinesFromXML(basePath + File.separator + "Domains/Domains.xml");
                auxClassGui.readDatabase();
                domainList = auxClassGui.getDomainList();
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, domain.getName() + " removed successfully"), "Network removed successfully", JOptionPane.INFORMATION_MESSAGE);
                resetAllWindows();
            } catch (IOException e) {
                System.out.println("Failed to delete the directory: " + e.getMessage());
            }
        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Network " + domain.getName() + " not removed.       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            selectRemoveNetwork();
        }

    }

    private void removeAllNetworks(){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to remove all networks?        "),"Are you sure you want to remove all networks?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            try{
                domainList = auxClassGui.getDomainList();
                for(Domain domain: domainList.getDomains()){
                    if(domain.getPath().equals(File.separator + domain.getName())){
                        Path directoryPath = Paths.get(basePath + File.separator + "Network" + domain.getPath());
                        deleteDirectory(directoryPath);
                        removeDomainFromXML(domain.getPath());
                        auxClassGui.readDatabase();
                        domainList = auxClassGui.getDomainList();
                    }
                }
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "All networks removed successfully"), "All networks removed successfully", JOptionPane.INFORMATION_MESSAGE);
                resetAllWindows();
            } catch (IOException e) {
                System.out.println("Failed to delete the directory: " + e.getMessage());
            }
        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Networks not removed.       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            resetAllWindows();
        }

    }



    private DomainList getNetworkInfo(DomainList domains) {
        DomainList domainList = new DomainList();
        for (Domain domain : domains.getDomains()) {
            String path = domain.getPath();
            String net;
            String name = domain.getName();
            net = File.separator + name;
	        if(path!= null && net != null && path.equals(net)){
                domainList.addDomain(domain);
		        //System.out.println(name + " is a network\n");
	        }

        }
        return domainList;

    }

    private DomainList getLowerDomainList(String path, String lowerDomains) {
        DomainList lowerDomainsList = new DomainList();
        String[] domainNames = lowerDomains.split(",");

        for (Domain domain : domainList.getDomains()) {
            if (Arrays.asList(domainNames).contains(domain.getName()) && domain.getPath().equals(path + File.separator + domain.getName())){
                //System.out.println(domain.getPath() + " equals " + path + File.separator + domain.getName());
                lowerDomainsList.addDomain(domain);
            }
        }

        return lowerDomainsList;
    }

    private Domain getParentDomain(String path) { //funciona mal
        String[] pathNames = path.split(File.separator);
        String parentPath = String.join(File.separator, Arrays.copyOfRange(pathNames, 0, pathNames.length - 1));
        System.out.println(parentPath + " is the parent path of " + path);
        return domainList.getDomainByPath(parentPath);
    }

    private GroupList getGroupsFromDomain(Domain domain) {
        GroupList groupList = new GroupList();
        for (Group group : this.groupList.getGroups()) {
            if (group.getDomain().getPath().equals(domain.getPath())) {
                groupList.addGroup(group);
            }
        }
        return groupList;
    }

    private DeviceList getDeviceListFromDomain(Domain domain) {
        DeviceList deviceList = new DeviceList(); //este construtor tem de ser revisto
        for (Device device : this.deviceList.getDevices()) {
            if (device.getDomain().getPath().equals(domain.getPath())) {
                domain.getDeviceList().addDevice(device);
            }
        }
        return deviceList;
    }



    private void addDomain(Domain domain, DomainList fraterDomainsList){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JTextField name = new JTextField(5);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Name:")).setFont(defaultFont);
        panel.add(name).setFont(defaultFont);
        panel.add(Box.createVerticalStrut(3)); // a spacer
        panel.add(Box.createRigidArea(new Dimension(0, 3))); // fixed height spacer

        panel.setPreferredSize(new Dimension(300, 50));
        panel.setFont(defaultFont);

        int result;
        JOptionPane dialog = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialogWindow = dialog.createDialog(null, "Please Enter the Subnetwork's Name to be added");
        dialogWindow.setVisible(true);
        result = (Integer) dialog.getValue();


        if (result == JOptionPane.OK_OPTION) {
            if (name.getText().equals("")) {
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid name      "), "Invalid name", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDomain(domain, fraterDomainsList);
            }
            else if (name.getText().contains(",")) {
                JOptionPane.showMessageDialog(null, getMessagePanel(defaultFont, "Commas ',' are not allowed in the name"), "Invalid name", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDomain(domain, fraterDomainsList);
            }


            else if(getLowerDomainList(domain.getPath(), domain.getLowerDomains()).verifyDomainName(name.getText(), domain.getPath() + File.separator + name.getText())){

                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Subnetwork " + name.getText() + " already exists     "), "Subnetwork already exists", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDomain(domain, fraterDomainsList);
            }

            else{
                Domain newNetwork = new Domain(name.getText(),"" , domain.getPath()+ File.separator + name.getText());
                domainList.addDomain(newNetwork);
                domain.addLowerDomains(newNetwork.getName());
                addDomainToXML(domain, newNetwork.getName());
                updateLowerDirectories(domain.getPath(), domain.getLowerDomains());
                removeEmptyLinesFromXML(basePath + File.separator + "Domains/Domains.xml");
                File currentDirectory = new File(basePath + File.separator + "Network" + domain.getPath());
                File newNetworkDir = new File(currentDirectory, newNetwork.getName());
                newNetworkDir.mkdir();
                auxClassGui.generateAuthorizedDomainAndDevicesXMLFileForDirectory(newNetworkDir);
                auxClassGui.generateDevicesXMLFileForDirectory(newNetworkDir);
                auxClassGui.generateGroupsXMLFileForDirectory(newNetworkDir);
                auxClassGui.generateAuthorizedDomainsFileForDirectory(newNetworkDir);
                auxClassGui.generateAuthorizedIpsFileForDirectory(newNetworkDir);


                for(Domain auxDomain: auxClassGui.getDomainList().getDomains()){
                    if(checkDomainExists(new File(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml"),domain.getName(),domain.getPath())){
                        auxDomain.getAuthorizedDomainList().addAuthorizedDomainToXML(newNetwork, basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                        removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                        auxDomain.getAuthorizedDomainList().addAuthorizedDomainToAuthorizedDomainsXML(newNetwork, basePath + File.separator + "Network" + auxDomain.getPath() + File.separator +  "AuthorizedDomains.xml");
                        removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + "AuthorizedDomains.xml");
                        auxClassGui.readDatabase();
                        auxDomain.getAuthorizedDomainList().addDomain(auxClassGui.getDomainList().getDomainByPath(newNetwork.getPath()));
                    }
                }

                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, name.getText() + " added successfully"), "Subnetwork added successfully", JOptionPane.INFORMATION_MESSAGE);
                cleanSecondaryWindows();
                mainPanel.remove(0);
                defaultWindow();
                secondaryWindow(domain);
                mainPanel.revalidate();
                mainPanel.repaint();

            }
        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Subnetwork not added.       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            dialogWindow.dispose();

        }



    }
    public static void addDomainToXML(Domain domain, String name) {
        try {
            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(basePath + File.separator + "Domains/Domains.xml"));

            // Find the parent element (Database)
            NodeList parentNodes = document.getElementsByTagName("Database");
            Element parent = (Element) parentNodes.item(0);

            // Create the new Domain element
            Element domainElement = document.createElement("Domain");

            // Create and append the Name element
            Element nameElement = document.createElement("Name");
            nameElement.appendChild(document.createTextNode(name));
            domainElement.appendChild(nameElement);

            // Create and append the Path element
            Element pathElement = document.createElement("Path");
            pathElement.appendChild(document.createTextNode(domain.getPath() + File.separator + name));
            domainElement.appendChild(pathElement);

            // Create and append the LowerDirectories element
            Element lowerDirectoriesElement = document.createElement("LowerDirectories");
            lowerDirectoriesElement.appendChild(document.createTextNode(""));
            domainElement.appendChild(lowerDirectoriesElement);

            // Add the new Domain element to the parent (Database)
            parent.appendChild(domainElement);

            // Write the updated XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(basePath + File.separator + "Domains/Domains.xml"));
            transformer.transform(source, result);

            System.out.println("Domain added successfully!");

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
    public static void addNetworkToXML(Domain domain) {
        try {
            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(basePath + File.separator + "Domains/Domains.xml"));

            // Find the parent element (Database)
            NodeList parentNodes = document.getElementsByTagName("Database");
            Element parent = (Element) parentNodes.item(0);

            // Create the new Domain element
            Element domainElement = document.createElement("Domain");

            // Create and append the Name element
            Element nameElement = document.createElement("Name");
            nameElement.appendChild(document.createTextNode(domain.getName()));
            domainElement.appendChild(nameElement);

            // Create and append the Path element
            Element pathElement = document.createElement("Path");
            pathElement.appendChild(document.createTextNode(domain.getPath()));
            domainElement.appendChild(pathElement);

            // Create and append the LowerDirectories element
            Element lowerDirectoriesElement = document.createElement("LowerDirectories");
            lowerDirectoriesElement.appendChild(document.createTextNode(""));
            domainElement.appendChild(lowerDirectoriesElement);

            // Add the new Domain element to the parent (Database)
            parent.appendChild(domainElement);

            // Write the updated XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(basePath + File.separator + "Domains/Domains.xml"));
            transformer.transform(source, result);

            System.out.println("Domain added successfully!");

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
    public static void updateLowerDirectories( String domainPath, String newLowerDirectories) {
        try {
            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(basePath + File.separator + "Domains/Domains.xml"));

            // Find the Domain element with the specified Name
            NodeList domainNodes = document.getElementsByTagName("Domain");
            for (int i = 0; i < domainNodes.getLength(); i++) {
                Element domainElement = (Element) domainNodes.item(i);
                String path = domainElement.getElementsByTagName("Path").item(0).getTextContent();

                if (path.equals(domainPath)) {
                    // Find the LowerDirectories element within the Domain
                    NodeList lowerDirsNodes = domainElement.getElementsByTagName("LowerDirectories");
                    if (lowerDirsNodes.getLength() > 0) {
                        Element lowerDirsElement = (Element) lowerDirsNodes.item(0);

                        // Update the value of the LowerDirectories element
                        lowerDirsElement.setTextContent(newLowerDirectories);

                        // Write the updated XML back to the file
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                        DOMSource source = new DOMSource(document);
                        StreamResult result = new StreamResult(new File(basePath + File.separator + "Domains/Domains.xml"));
                        transformer.transform(source, result);

                        System.out.println("LowerDirectories updated successfully!");
                        return;
                    }
                }
            }

            System.out.println("Domain or LowerDirectories element not found in the XML file.");

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
    public static void removeEmptyLinesFromXML(String filePath) {
        try {
            // Read the XML file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder content = new StringBuilder();
            String line;

            // Remove empty lines
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    content.append(line).append(System.lineSeparator());
                }
            }
            reader.close();

            // Write the modified content back to the XML file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(content.toString());
            writer.close();

            System.out.println("Empty lines removed from XML successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void removeDomain(Domain domain, DomainList fraterDomainList){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JTextField info = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        panel.setPreferredSize(new Dimension(200, 50));
        panel.setFont(defaultFont);
        panel.add(new JLabel("Do you want to remove this network?")).setFont(defaultFont);
        panel.add(info).setFont(defaultFont);



        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to remove this network,  " + domain.getName() + "?        "),
                "Are you sure you want to remove this network ?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            try{
                auxClassGui.readDatabase();
                for(Domain allDomain: auxClassGui.getDomainList().getDomains()){
                    if(allDomain.getAuthorizedDomainList().verifyPath(domain.getPath()) || allDomain.getAuthorizedDomainList().verifyPath2(domain.getPath())){
                        ArrayList<String> ipList = removeDomainDeviceAndGroupGivenDomainPathFromXML(allDomain, domain);
                        removeEmptyLinesFromXML(basePath + File.separator + "Network" + allDomain.getPath() + File.separator + allDomain.getName() + ".xml");
                        removeAuthorizedDeviceIPFromGroupFromXML(allDomain.getPath(), ipList);
                        removeEmptyLinesFromXML(basePath + File.separator + "Network" + allDomain.getPath() + File.separator + "AuthorizedIps.xml");
                        removeAllAuthorizedNetworkFromAuthorizedNetworkXML(allDomain,domain);
                        removeEmptyLinesFromXML(basePath + File.separator + "Network" + allDomain.getPath() + File.separator + "AuthorizedDomains.xml");
                        auxClassGui.readDatabase();
                        allDomain.getAuthorizedGroupList().setGroupList((auxClassGui.getDomainList().getDomainByPath(allDomain.getPath()).getAuthorizedGroupList().getGroups()));
                        allDomain.getAuhtorizedDeviceList().setDeviceList((auxClassGui.getDomainList().getDomainByPath(allDomain.getPath()).getAuhtorizedDeviceList().getDevices()));
                        allDomain.getAuthorizedDomainList().setDomainList((auxClassGui.getDomainList().getDomainByPath(allDomain.getPath()).getAuthorizedDomainList().getDomains()));
                    }
                }

                Path directoryPath = Paths.get(basePath + File.separator + "Network" + domain.getPath());
                deleteDirectory(directoryPath);
                removeDomainFromXML(domain.getPath());
                removeEmptyLinesFromXML(basePath + File.separator + "Domains/Domains.xml");
                auxClassGui.readDatabase();
                domainList = auxClassGui.getDomainList();
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, domain.getName() + " removed successfully"), "Network removed successfully", JOptionPane.INFORMATION_MESSAGE);
                resetAllWindows();
            } catch (IOException e) {
                System.out.println("Failed to delete the directory: " + e.getMessage());
            }
        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Network " + domain.getName() + " not removed.       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);

        }


    }

    private void deviceSecondaryWindow(Domain domain) {
        UIManager.put("OptionPane.background", colorLightGray);
        UIManager.put("Panel.background", colorLightGray);
        JPanel devicePanel = new JPanel();
        devicePanel.setPreferredSize(new Dimension(400, 600));
        devicePanel.setBackground(colorLightGray);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), domain.getPath() + "'s Devices");
        titledBorder.setTitleFont(titleFont);
        devicePanel.setBorder(titledBorder);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

        goBackButton = new JButton("←");
        goBackButton.setBounds(10, 10, 100, 50);
        goBackButton.setFont(buttonFont);
        goBackButton.setForeground(Color.BLACK);
        goBackButton.setBackground(Color.WHITE);
        goBackButton.addActionListener(this);
        menuPanel.add(goBackButton);

        addDeviceButton = new JButton("+ Add");
        addDeviceButton.setFont(buttonFont);
        addDeviceButton.setForeground(Color.BLACK);
        addDeviceButton.setBackground(Color.WHITE);
        addDeviceButton.setBounds(10, 10, 100, 50);
        addDeviceButton.addActionListener(this);
        menuPanel.add(addDeviceButton);

        editDeviceButton = new JButton("Edit");
        editDeviceButton.setBounds(10, 10, 100, 50);
        editDeviceButton.setFont(buttonFont);
        editDeviceButton.setForeground(Color.BLACK);
        editDeviceButton.setBackground(colorLightBrown);
        editDeviceButton.addActionListener(this);
        menuPanel.add(editDeviceButton);

        removeDeviceButton = new JButton("- Remove");
        removeDeviceButton.setBounds(10, 10, 100, 50);
        removeDeviceButton.setFont(buttonFont);
        removeDeviceButton.setForeground(colorRed);
        removeDeviceButton.setBackground(Color.BLACK);
        removeDeviceButton.addActionListener(this);
        menuPanel.add(removeDeviceButton);

        closeSecondaryWindowButton = new JButton("x");
        closeSecondaryWindowButton.setForeground(Color.WHITE);
        closeSecondaryWindowButton.setBackground(colorRed);
        closeSecondaryWindowButton.setFont(buttonFont);
        closeSecondaryWindowButton.addActionListener(this);
        closeSecondaryWindowButton.setBounds(10, 10, 100, 50);

        menuPanel.add(closeSecondaryWindowButton);
        devicePanel.add(menuPanel, BorderLayout.NORTH);

        // Add spacer between the closeSecondaryWindowButton and the showDevices button
        Component spacer = Box.createRigidArea(new Dimension(0, 100));
        devicePanel.add(spacer);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(colorLightGray);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.add(menuPanel);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel deviceButtonPanel = new JPanel(new GridBagLayout());
        deviceButtonPanel.setBackground(colorLightGray);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 0, 0);

        auxClassGui.readDatabase();

        deviceButtons = new JButton[domain.getDeviceList().getSize()];
        for (int i = 0; i < domain.getDeviceList().getSize(); i++) {
            JButton deviceButton = new JButton(domain.getDeviceList().getDevices().get(i).getDeviceName());
            deviceButton.setPreferredSize(new Dimension(newDefaultButtonSize, 50));
            deviceButton.setFont(buttonFont);
            deviceButton.setBackground(Color.WHITE);
            deviceButton.setForeground(Color.BLACK);
            deviceButton.setActionCommand(String.valueOf(i));
            deviceButton.addActionListener(this);
            deviceButton.putClientProperty("Device", domain.getDeviceList().getDevices().get(i));
            deviceButtonPanel.add(deviceButton, constraints);
            deviceButtons[i] = deviceButton;

            constraints.gridy++;
        }

        mainPanel.add(deviceButtonPanel, BorderLayout.CENTER);
        devicePanel.add(mainPanel);

        this.mainPanel.add(devicePanel);
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }




    private void groupsSecondaryWindow(Domain domain){
        UIManager.put("OptionPane.background", colorLightYellow);
        UIManager.put("Panel.background", colorLightYellow);
        JPanel groupPanel = new JPanel();
        groupPanel.setPreferredSize(new Dimension(400, 600));
        groupPanel.setBackground(colorLightYellow);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), domain.getPath() + " 's Groups");
        titledBorder.setTitleFont(titleFont);
        groupPanel.setBorder(titledBorder);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0 , 0));

        goBackButton = new JButton("←");
        goBackButton.setBounds(10, 10, 100, 50);
        goBackButton.setFont(buttonFont);
        goBackButton.setForeground(Color.BLACK);
        goBackButton.setBackground(Color.WHITE);
        goBackButton.addActionListener(this);
        menuPanel.add(goBackButton);

        addGroupButton = new JButton("+ Add");
        addGroupButton.setBounds(10, 10, 100, 50);
        addGroupButton.setFont(buttonFont);
        addGroupButton.setForeground(Color.BLACK);
        addGroupButton.setBackground(Color.WHITE);
        addGroupButton.addActionListener(this);
        menuPanel.add(addGroupButton);


        removeGroupButton = new JButton("- Remove");
        removeGroupButton.setBounds(10, 10, 100, 50);
        removeGroupButton.setFont(buttonFont);
        removeGroupButton.setForeground(colorRed);
        removeGroupButton.setBackground(Color.BLACK);
        removeGroupButton.addActionListener(this);
        menuPanel.add(removeGroupButton);


        closeSecondaryWindowButton = new JButton("x");
        closeSecondaryWindowButton.setForeground(Color.WHITE);
        closeSecondaryWindowButton.setBackground(colorRed);
        closeSecondaryWindowButton.setFont(buttonFont);
        closeSecondaryWindowButton.addActionListener(this);
        closeSecondaryWindowButton.setBounds(10, 10, 100, 50);

        menuPanel.add(closeSecondaryWindowButton, BorderLayout.WEST);
        groupPanel.add(menuPanel);


        // Add spacer between the closeSecondaryWindowButton and the showDevices button
        Component spacer = Box.createRigidArea(new Dimension(0, 100));
        groupPanel.add(spacer);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(colorLightYellow);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.add(menuPanel);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel groupButtonPanel = new JPanel(new GridBagLayout());
        groupButtonPanel.setBackground(colorLightYellow);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 0, 0);

        groupButtons = new JButton[domain.getGroupList().getSize()];
        for (int i = 0; i < domain.getGroupList().getSize(); i++) {
            JButton groupButton = new JButton(domain.getGroupList().getGroups().get(i).getName());
            groupButton.setPreferredSize(new Dimension(newDefaultButtonSize, 50));
            groupButton.setFont(buttonFont);
            groupButton.setBackground(Color.WHITE);
            groupButton.setForeground(Color.BLACK);
            groupButton.setActionCommand(String.valueOf(i));
            groupButton.addActionListener(this);
            groupButton.putClientProperty("Group", domain.getGroupList().getGroups().get(i));
            groupButtonPanel.add(groupButton, constraints);
            groupButtons[i] = groupButton;

            constraints.gridy++;
        }

        mainPanel.add(groupButtonPanel, BorderLayout.CENTER);
        groupPanel.add(mainPanel);

        this.mainPanel.add(groupPanel);
        this.mainPanel.revalidate();
        this.mainPanel.repaint();

    }

    private void oneGroupSecondaryWindow(Group group){
        UIManager.put("OptionPane.background", colorLightYellow);
        UIManager.put("Panel.background", colorLightYellow);
        JPanel groupPanel = new JPanel();
        groupPanel.setPreferredSize(new Dimension(400, 600));
        groupPanel.setBackground(colorLightYellow);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), group.getName() + " from " + currentDomain.getPath() + "'s Groups ");
        titledBorder.setTitleFont(titleFont);
        groupPanel.setBorder(titledBorder);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0 , 0));

        goBackButtonToGroup = new JButton("←");
        goBackButtonToGroup.setBounds(10, 10, 100, 50);
        goBackButtonToGroup.setFont(buttonFont);
        goBackButtonToGroup.setForeground(Color.BLACK);
        goBackButtonToGroup.setBackground(Color.WHITE);
        goBackButtonToGroup.addActionListener(this);
        menuPanel.add(goBackButtonToGroup);

        addDeviceToGroupButton = new JButton("+ Add");
        addDeviceToGroupButton.setBounds(10, 10, 100, 50);
        addDeviceToGroupButton.setFont(buttonFont);
        addDeviceToGroupButton.setForeground(Color.BLACK);
        addDeviceToGroupButton.setBackground(Color.WHITE);
        addDeviceToGroupButton.addActionListener(this);
        menuPanel.add(addDeviceToGroupButton);

        removeDeviceFromGroupButton = new JButton("- Remove");
        removeDeviceFromGroupButton.setBounds(10, 10, 100, 50);
        removeDeviceFromGroupButton.setFont(buttonFont);
        removeDeviceFromGroupButton.setForeground(colorRed);
        removeDeviceFromGroupButton.setBackground(Color.BLACK);
        removeDeviceFromGroupButton.addActionListener(this);
        menuPanel.add(removeDeviceFromGroupButton);

        closeSecondaryWindowButton = new JButton("X");
        closeSecondaryWindowButton.setForeground(Color.WHITE);
        closeSecondaryWindowButton.setBackground(colorRed);
        closeSecondaryWindowButton.setFont(buttonFont);
        closeSecondaryWindowButton.addActionListener(this);
        closeSecondaryWindowButton.setBounds(10, 10, 100, 50);

        menuPanel.add(closeSecondaryWindowButton);
        groupPanel.add(menuPanel, BorderLayout.NORTH);

        // Add spacer between the closeSecondaryWindowButton and the showDevices button
        Component spacer = Box.createRigidArea(new Dimension(0, 100));
        groupPanel.add(spacer);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(colorLightYellow);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.add(menuPanel);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel groupButtonPanel = new JPanel(new GridBagLayout());
        groupButtonPanel.setBackground(colorLightYellow);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 0, 0);

        deviceOfGroupButtons = new JButton[group.getDeviceList().getSize()];
        for (int i = 0; i < group.getDeviceList().getSize(); i++){
            JButton deviceButton = new JButton(group.getDeviceList().getDevices().get(i).getDeviceName());
            deviceButton.setPreferredSize(new Dimension(newDefaultButtonSize, 50));
            deviceButton.setFont(buttonFont);
            deviceButton.setBackground(Color.WHITE);
            deviceButton.setForeground(Color.BLACK);
            deviceButton.setActionCommand(String.valueOf(i));
            deviceButton.addActionListener(this);
            deviceButton.putClientProperty("Device", group.getDeviceList().getDevices().get(i));
            groupButtonPanel.add(deviceButton, constraints);
            deviceOfGroupButtons[i] = deviceButton;

            constraints.gridy++;
        }
        mainPanel.add(groupButtonPanel, BorderLayout.CENTER);
        groupPanel.add(mainPanel);

        this.mainPanel.add(groupPanel);
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }



    private void authorizedDevicesSecondaryWindow(Domain domain){
        UIManager.put("OptionPane.background", colorLightPink);
        UIManager.put("Panel.background", colorLightPink);
        JPanel authorizationsPanel = new JPanel();
        authorizationsPanel.setPreferredSize(new Dimension(400, 600));
        authorizationsPanel.setBackground(colorLightPink);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), domain.getPath() + " 's Authorized Devices");
        titledBorder.setTitleFont(titleFont);
        authorizationsPanel.setBorder(titledBorder);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0 , 0));

        goBackButton = new JButton("←");
        goBackButton.setBounds(10, 10, 100, 50);
        goBackButton.setFont(buttonFont);
        goBackButton.setForeground(Color.BLACK);
        goBackButton.setBackground(Color.WHITE);
        goBackButton.addActionListener(this);
        menuPanel.add(goBackButton);

        addAuthorizedDeviceButton = new JButton("+ Add");
        addAuthorizedDeviceButton.setBounds(10, 10, 100, 50);
        addAuthorizedDeviceButton.setFont(buttonFont);
        addAuthorizedDeviceButton.setForeground(Color.BLACK);
        addAuthorizedDeviceButton.setBackground(Color.WHITE);
        addAuthorizedDeviceButton.addActionListener(this);
        menuPanel.add(addAuthorizedDeviceButton);

        removeAuthorizedDeviceButton = new JButton("- Remove");
        removeAuthorizedDeviceButton.setBounds(10, 10, 100, 50);
        removeAuthorizedDeviceButton.setFont(buttonFont);
        removeAuthorizedDeviceButton.setForeground(colorRed);
        removeAuthorizedDeviceButton.setBackground(Color.BLACK);
        removeAuthorizedDeviceButton.addActionListener(this);
        menuPanel.add(removeAuthorizedDeviceButton);


        closeSecondaryWindowButton = new JButton("x");
        closeSecondaryWindowButton.setForeground(Color.WHITE);
        closeSecondaryWindowButton.setBackground(colorRed);
        closeSecondaryWindowButton.setFont(buttonFont);
        closeSecondaryWindowButton.addActionListener(this);
        closeSecondaryWindowButton.setBounds(10, 10, 100, 50);

        menuPanel.add(closeSecondaryWindowButton, BorderLayout.WEST);
        authorizationsPanel.add(menuPanel);
        // Add spacer between the closeSecondaryWindowButton and the showDevices button
        Component spacer = Box.createRigidArea(new Dimension(0, 100));
        authorizationsPanel.add(spacer);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(colorLightPink);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.add(menuPanel);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel deviceButtonPanel = new JPanel(new GridBagLayout());
        deviceButtonPanel.setBackground(colorLightPink);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 0, 0);

        auxClassGui.readDatabase();
        domain.getAuhtorizedDeviceList().setDeviceList((auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getAuhtorizedDeviceList().getDevices()));

        authorizedDevicesButtons = new JButton[domain.getAuhtorizedDeviceList().getSize()];
        for (int i = 0; i < domain.getAuhtorizedDeviceList().getSize(); i++) {
            JButton deviceButton = new JButton(domain.getAuhtorizedDeviceList().getDevices().get(i).getDeviceName());
            deviceButton.setPreferredSize(new Dimension(newDefaultButtonSize, 50));
            deviceButton.setFont(buttonFont);
            deviceButton.setBackground(Color.WHITE);
            deviceButton.setForeground(Color.BLACK);
            deviceButton.setActionCommand(String.valueOf(i));
            deviceButton.addActionListener(this);
            deviceButton.putClientProperty("Device", domain.getAuhtorizedDeviceList().getDevices().get(i));
            deviceButtonPanel.add(deviceButton, constraints);
            authorizedDevicesButtons[i] = deviceButton;

            constraints.gridy++;
        }

        mainPanel.add(deviceButtonPanel, BorderLayout.CENTER);
        authorizationsPanel.add(mainPanel);

        this.mainPanel.add(authorizationsPanel);
        this.mainPanel.revalidate();
        this.mainPanel.repaint();

    }

    private void authorizedGroupsSecondaryWindow(Domain domain){
        UIManager.put("OptionPane.background", colorLightPink);
        UIManager.put("Panel.background", colorLightPink);
        JPanel authorizationsPanel = new JPanel();
        authorizationsPanel.setPreferredSize(new Dimension(400, 600));
        authorizationsPanel.setBackground(colorLightPink);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), domain.getPath() + " 's Authorized Groups");
        titledBorder.setTitleFont(titleFont);
        authorizationsPanel.setBorder(titledBorder);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0 , 0));

        goBackButton = new JButton("←");
        goBackButton.setBounds(10, 10, 100, 50);
        goBackButton.setFont(buttonFont);
        goBackButton.setForeground(Color.BLACK);
        goBackButton.setBackground(Color.WHITE);
        goBackButton.addActionListener(this);
        menuPanel.add(goBackButton);

        addAuthorizedGroupButton = new JButton("+ Add");
        addAuthorizedGroupButton.setBounds(10, 10, 100, 50);
        addAuthorizedGroupButton.setFont(buttonFont);
        addAuthorizedGroupButton.setForeground(Color.BLACK);
        addAuthorizedGroupButton.setBackground(Color.WHITE);
        addAuthorizedGroupButton.addActionListener(this);
        menuPanel.add(addAuthorizedGroupButton);

        removeAuthorizedGroupButton = new JButton("- Remove");
        removeAuthorizedGroupButton.setBounds(10, 10, 100, 50);
        removeAuthorizedGroupButton.setFont(buttonFont);
        removeAuthorizedGroupButton.setForeground(colorRed);
        removeAuthorizedGroupButton.setBackground(Color.BLACK);
        removeAuthorizedGroupButton.addActionListener(this);
        menuPanel.add(removeAuthorizedGroupButton);

        closeSecondaryWindowButton = new JButton("x");
        closeSecondaryWindowButton.setForeground(Color.WHITE);
        closeSecondaryWindowButton.setBackground(colorRed);
        closeSecondaryWindowButton.setFont(buttonFont);
        closeSecondaryWindowButton.addActionListener(this);
        closeSecondaryWindowButton.setBounds(10, 10, 100, 50);

        menuPanel.add(closeSecondaryWindowButton, BorderLayout.WEST);
        authorizationsPanel.add(menuPanel);

        // Add spacer between the closeSecondaryWindowButton and the showDevices button
        Component spacer = Box.createRigidArea(new Dimension(0, 100));
        authorizationsPanel.add(spacer);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(colorLightPink);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.add(menuPanel);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel groupsButtonPanel = new JPanel(new GridBagLayout());
        groupsButtonPanel.setBackground(colorLightPink);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 0, 0);

        auxClassGui.readDatabase();
        domain.getAuthorizedGroupList().setGroupList(auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getAuthorizedGroupList().getGroups());
        authorizedGroupsButtons = new JButton[domain.getAuthorizedGroupList().getSize()];
        for (int i = 0; i < domain.getAuthorizedGroupList().getSize(); i++) {
            JButton groupButton = new JButton(domain.getAuthorizedGroupList().getGroups().get(i).getName());
            groupButton.setPreferredSize(new Dimension(newDefaultButtonSize, 50));
            groupButton.setFont(buttonFont);
            groupButton.setBackground(Color.WHITE);
            groupButton.setForeground(Color.BLACK);
            groupButton.setActionCommand(String.valueOf(i));
            groupButton.addActionListener(this);
            groupButton.putClientProperty("Group", domain.getAuthorizedGroupList().getGroups().get(i));
            groupsButtonPanel.add(groupButton, constraints);
            authorizedGroupsButtons[i] = groupButton;

            constraints.gridy++;
        }

        mainPanel.add(groupsButtonPanel, BorderLayout.CENTER);
        authorizationsPanel.add(mainPanel);

        this.mainPanel.add(authorizationsPanel);
        this.mainPanel.revalidate();
        this.mainPanel.repaint();

    }

    private void authorizedNetworksSecondaryWindow(Domain domain){
        UIManager.put("OptionPane.background", colorLightPink);
        UIManager.put("Panel.background", colorLightPink);
        JPanel authorizationsPanel = new JPanel();
        authorizationsPanel.setPreferredSize(new Dimension(400, 600));
        authorizationsPanel.setBackground(colorLightPink);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), domain.getPath() + " 's Authorized Networks");
        titledBorder.setTitleFont(titleFont);
        authorizationsPanel.setBorder(titledBorder);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0 , 0));

        goBackButton = new JButton("←");
        goBackButton.setBounds(10, 10, 100, 50);
        goBackButton.setFont(buttonFont);
        goBackButton.setForeground(Color.BLACK);
        goBackButton.setBackground(Color.WHITE);
        goBackButton.addActionListener(this);
        menuPanel.add(goBackButton);

        addAuthorizedNetworkButton = new JButton("+ Add");
        addAuthorizedNetworkButton.setBounds(10, 10, 100, 50);
        addAuthorizedNetworkButton.setFont(buttonFont);
        addAuthorizedNetworkButton.setForeground(Color.BLACK);
        addAuthorizedNetworkButton.setBackground(Color.WHITE);
        addAuthorizedNetworkButton.addActionListener(this);
        menuPanel.add(addAuthorizedNetworkButton);

        removeAuthorizedNetworkButton = new JButton("- Remove");
        removeAuthorizedNetworkButton.setBounds(10, 10, 100, 50);
        removeAuthorizedNetworkButton.setFont(buttonFont);
        removeAuthorizedNetworkButton.setForeground(colorRed);
        removeAuthorizedNetworkButton.setBackground(Color.BLACK);
        removeAuthorizedNetworkButton.addActionListener(this);
        menuPanel.add(removeAuthorizedNetworkButton);


        closeSecondaryWindowButton = new JButton("x");
        closeSecondaryWindowButton.setForeground(Color.WHITE);
        closeSecondaryWindowButton.setBackground(colorRed);
        closeSecondaryWindowButton.setFont(buttonFont);
        closeSecondaryWindowButton.addActionListener(this);
        closeSecondaryWindowButton.setBounds(10, 10, 100, 50);

        menuPanel.add(closeSecondaryWindowButton, BorderLayout.WEST);
        authorizationsPanel.add(menuPanel);

        // Add spacer between the closeSecondaryWindowButton and the showDevices button
        Component spacer = Box.createRigidArea(new Dimension(0, 100));
        authorizationsPanel.add(spacer);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(colorLightPink);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.add(menuPanel);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel networksButtonPanel = new JPanel(new GridBagLayout());
        networksButtonPanel.setBackground(colorLightPink);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 0, 0);

        auxClassGui.readDatabase();
        domain.getAuthorizedDomainList().setDomainList(auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getAuthorizedDomainList().getDomains());
        authorizedNetworksButtons = new JButton[domain.getAuthorizedDomainList().getSize()];
        for (int i = 0; i < domain.getAuthorizedDomainList().getSize(); i++) {
            JButton domainButton = new JButton(domain.getAuthorizedDomainList().getDomains().get(i).getPath());
            domainButton.setPreferredSize(new Dimension(newDefaultButtonSize, 50));
            domainButton.setFont(buttonFont);
            domainButton.setBackground(Color.WHITE);
            domainButton.setForeground(Color.BLACK);
            domainButton.setActionCommand(String.valueOf(i));
            domainButton.addActionListener(this);
            domainButton.putClientProperty("Domain", domain.getAuthorizedDomainList().getDomains().get(i));
            networksButtonPanel.add(domainButton, constraints);
            authorizedNetworksButtons[i] = domainButton;

            constraints.gridy++;
        }
        mainPanel.add(networksButtonPanel, BorderLayout.CENTER);
        authorizationsPanel.add(mainPanel);

        this.mainPanel.add(authorizationsPanel);
        this.mainPanel.revalidate();
        this.mainPanel.repaint();

    }







    private void addDevice(Domain domain){
        auxClassGui.readDatabase();
        ArrayList<Device> allDevices = new ArrayList<>();
        for(Domain allDomain: auxClassGui.getDomainList().getDomains()){
            for(Device device: allDomain.getDeviceList().getDevices()){
                allDevices.add(device);
            }
        }
        DeviceList allDeviceList = new DeviceList();
        allDeviceList.setDeviceList(allDevices);
        deviceList = allDeviceList;
        UIManager.put("OptionPane.background", colorLightGray);
        UIManager.put("Panel.background", colorLightGray);
        JTextField name = new JTextField(5);
        JTextField pass = new JTextField(5);
        JTextField ip = new JTextField(5);
        JTextField ipv6 = new JTextField(5);
        JTextField domainName = new JTextField(5);
        name.setText("Device" + (deviceList.getSize() + 1));
        pass.setText("abcd1234");
        ip.setText("192.168.1." + (deviceList.getSize() + 1));//will have to verify better
        ipv6.setText("2001:0db8:85a3:0000:0000:8a2e:0370:733" + (deviceList.getSize() + 1));//will have to verify better
        domainName.setText("Subrede" + domainList.getSize());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Name:")).setFont(defaultFont);
        panel.add(name).setFont(defaultFont);
        panel.add(new JLabel("Password:")).setFont(defaultFont);
        panel.add(pass).setFont(defaultFont);
        panel.add(new JLabel("IPv4 address:")).setFont(defaultFont);
        panel.add(ip).setFont(defaultFont);
        panel.add(new JLabel("IPv6 address:")).setFont(defaultFont);
        panel.add(ipv6).setFont(defaultFont);
        panel.setPreferredSize(new Dimension(800, 350));
        panel.setFont(defaultFont);

        int result;
        JOptionPane dialog = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialogWindow = dialog.createDialog(null, "Please Enter the Password, IP address, and Domain");
        dialogWindow.setVisible(true);
        result = (Integer) dialog.getValue();

        if (result == JOptionPane.OK_OPTION) {
            if (name.getText().equals("")) {
                JOptionPane.showMessageDialog(this, getMessagePanel(defaultFont, "Invalid device name"),"Invalid device name.", JOptionPane.ERROR_MESSAGE);
                addDevice(domain);
            }
            else if (name.getText().indexOf(".") != -1 || name.getText().indexOf(" ") != -1){  //deviceName can't contain a space or a period

                JOptionPane.showMessageDialog(this, getMessagePanel(defaultFont, "Invalid format"), "Invalid format", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDevice(domain);
            }
            /*else if (domain.getDeviceList().getDevices().stream().anyMatch(device -> device.getDeviceName().equals(name.getText()))){
                //currentDevice = deviceList.getDevice(name.getText());
                JOptionPane.showMessageDialog(this,getMessagePanel(defaultFont, name.getText() + " already exists"),name.getText() + " already exists\n", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDevice(domain);
            }*/

            else if (domain.getDeviceList().verifyDeviceName(name.getText())){
                //currentDevice = deviceList.getDevice(name.getText());
                JOptionPane.showMessageDialog(this,getMessagePanel(defaultFont, name.getText() + " already exists"),name.getText() + " already exists\n", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDevice(domain);
            }
            
            else if(domain.getGroupList().verifyGroupName(name.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, name.getText()+ " name already used in this Domain     "), "Name already used in this Domain", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDevice(domain);
            }

            else if (pass.getText().equals("")) {
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid password"), "Invalid password", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDevice(domain);
            }

            else if (ip.getText().equals("") && ipv6.getText().equals("")) {
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid IPv4 andd IPv6 addresses"), "Invalid IP addresses", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDevice(domain);
            }
            else if (ip.getText().equals("")) {
                if (!verifyIPv6AddressFormat(ipv6.getText())) {
                    JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid IPv6 address"), "Invalid IPv6 address", JOptionPane.ERROR_MESSAGE);
                    dialogWindow.dispose();
                    addDevice(domain);
                }
                else{
                    Device newDevice = new Device(name.getText(), pass.getText(), ip.getText(), ipv6.getText(),domain);
                    domain.getDeviceList().addDeviceToXML(newDevice, basePath + File.separator + "Network" + domain.getPath() + File.separator + "Devices.xml");
                    for(Domain auxDomain: auxClassGui.getDomainList().getDomains()){
                        if(checkDomainExists(new File(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml"),domain.getName(),domain.getPath())){
                            if(!checkDeviceExists(new File(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml"), newDevice.getDeviceName(), newDevice.getDomain().getPath())){
                                auxDomain.getAuhtorizedDeviceList().addAuthorizedDeviceToXML(newDevice, basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                                removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                                auxDomain.getAuhtorizedDeviceList().addAuthorizedIpsToXML(newDevice,basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + "AuthorizedIps.xml");
                                removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + "AuthorizedIps.xml");
                                auxDomain.getAuhtorizedDeviceList().addDevice(newDevice);
                                }
                        }
                    }
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "Devices.xml");
                    domain.getDeviceList().addDevice(newDevice);
                    JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, name.getText() + " added successfully"), "Device added successfully", JOptionPane.INFORMATION_MESSAGE);
                    cleanSecondaryWindows();
                    deviceSecondaryWindow(domain);
                }
            }
            else if (ipv6.getText().equals("")) {
                if (!verifyIPAddressFormat(ip.getText())){
                    JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid IPv4 address"), "Invalid IP address", JOptionPane.ERROR_MESSAGE);
                    dialogWindow.dispose();
                    addDevice(domain);
                }
                else{
                    Device newDevice = new Device(name.getText(), pass.getText(), ip.getText(), ipv6.getText(),domain);
                    domain.getDeviceList().addDeviceToXML(newDevice, basePath + File.separator + "Network" + domain.getPath() + File.separator + "Devices.xml");
                    for(Domain auxDomain: auxClassGui.getDomainList().getDomains()){
                        if(checkDomainExists(new File(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml"),domain.getName(),domain.getPath())){
                            if(!checkDeviceExists(new File(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml"), newDevice.getDeviceName(), newDevice.getDomain().getPath())){
                                auxDomain.getAuhtorizedDeviceList().addAuthorizedDeviceToXML(newDevice, basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                                removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                                auxDomain.getAuhtorizedDeviceList().addAuthorizedIpsToXML(newDevice,basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + "AuthorizedIps.xml");
                                removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + "AuthorizedIps.xml");
                                auxDomain.getAuhtorizedDeviceList().addDevice(newDevice);
                                }
                        }
                    }
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "Devices.xml");
                    domain.getDeviceList().addDevice(newDevice);
                    JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, name.getText() + " added successfully"), "Device added successfully", JOptionPane.INFORMATION_MESSAGE);
                    cleanSecondaryWindows();
                    deviceSecondaryWindow(domain);
                }
            }

            else if (!verifyIPAddressFormat(ip.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid IPv4 address"), "Invalid IP address", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDevice(domain);
            }
            else if (!verifyIPv6AddressFormat(ipv6.getText())) {
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid IPv6 address"), "Invalid IPv6 address", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDevice(domain);
            }

            else if (domainName.getText().equals("")) {
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid domain"), "Invalid IP address", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDevice(domain);
            }

            else if (allDeviceList.verifyIPAddress(ip.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "IP address already exists"), "IP address already exists", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDevice(domain);
            }

            else if(allDeviceList.verifyIPv6Address(ipv6.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "IPv6 address already exists"), "IPv6 address already exists", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addDevice(domain);
            }

            
            else{
                Device newDevice = new Device(name.getText(), pass.getText(), ip.getText(), ipv6.getText(),domain);
                domain.getDeviceList().addDeviceToXML(newDevice, basePath + File.separator + "Network" + domain.getPath() + File.separator + "Devices.xml");
                for(Domain auxDomain: auxClassGui.getDomainList().getDomains()){
                    if(checkDomainExists(new File(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml"),domain.getName(),domain.getPath())){
                        if(!checkDeviceExists(new File(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml"), newDevice.getDeviceName(), newDevice.getDomain().getPath())){
                            auxDomain.getAuhtorizedDeviceList().addAuthorizedDeviceToXML(newDevice, basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                            removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                            auxDomain.getAuhtorizedDeviceList().addAuthorizedIpsToXML(newDevice,basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + "AuthorizedIps.xml");
                            removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + "AuthorizedIps.xml");
                            auxDomain.getAuhtorizedDeviceList().addDevice(newDevice);
                            }
                    }
                }
                removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "Devices.xml");
                domain.getDeviceList().addDevice(newDevice);
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, name.getText() + " added successfully"), "Device added successfully", JOptionPane.INFORMATION_MESSAGE);
                cleanSecondaryWindows();
                deviceSecondaryWindow(domain);
            }
                
        }
        else{
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Device not added.       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            dialogWindow.dispose();
        }
    }

    private void selectRemoveDevice(Domain domain){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        removeDeviceButtons = new JButton[domain.getDeviceList().getSize()];
        for (int i = 0; i< domain.getDeviceList().getSize(); i++) {
            System.out.println(domain.getDeviceList().getDevices().get(i).getDeviceName() + " are the devices of domain " + domain.getName());
            removeDeviceButtons[i] = new JButton(domain.getDeviceList().getDevices().get(i).getDeviceName());
            removeDeviceButtons[i].setFont(buttonFont);
            removeDeviceButtons[i].setActionCommand(String.valueOf(i));
            removeDeviceButtons[i].addActionListener(this);
            removeDeviceButtons[i].putClientProperty("Device", domain.getDeviceList().getDevices().get(i));
            panel.add(removeDeviceButtons[i]);
        }

        panel.setPreferredSize(new Dimension(200, 35*domain.getDeviceList().getSize()));
        //panel.setPreferredSize(new Dimension(200, 35*getDeviceListFromDomain(domain).getSize()));   //ESTE É O CORRETO A USAR
        panel.setFont( defaultFont );


        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPanel.setPreferredSize(new Dimension(300, 300));
        JPanel removePanel = new JPanel(new BorderLayout());
        removePanel.add(scrollPanel, BorderLayout.CENTER);
        JPanel outherPanel = new JPanel(new BorderLayout());
        outherPanel.add(removePanel, BorderLayout.CENTER);
        outherPanel.setBackground(colorRed);

        UIManager.put("OptionPane.background", colorRed);
        UIManager.put("Panel.background", colorRed);


        JOptionPane dialog = new JOptionPane(outherPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        dialog.setBackground(colorRed);
        JDialog dialogWindow = dialog.createDialog(null, "Please Select Device to be removed");
        dialogWindow.setVisible(true);

    }

    private void removeDevice(Domain domain, Device device){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);

        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to remove device " + device.getDeviceName() + "?        "),
                "Are you sure you want to remove device " + device.getDeviceName() + "?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);


        if (result == JOptionPane.YES_OPTION) {
            removeDeviceFromXML(domain.getPath(), device);
            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "Devices.xml");
            for(Domain auxDomain: auxClassGui.getDomainList().getDomains()){
                if(checkDomainExists(new File(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml"),domain.getName(),domain.getPath())){
                    removeAuthorizedDeviceFromXML(auxDomain.getPath(),auxDomain, device);
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                    removeAuthorizedIpsFromXML(auxDomain.getPath(),auxDomain, device);
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator +  "AuthorizedIps.xml");
                    auxClassGui.readDatabase();
                    auxDomain.getAuhtorizedDeviceList().setDeviceList(auxClassGui.getDomainList().getDomainByPath(auxDomain.getPath()).getAuhtorizedDeviceList().getDevices());
                }
                if(checkDeviceExists(new File(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml"),device.getDeviceName(),domain.getPath())){
                    removeAuthorizedDeviceFromXML(auxDomain.getPath(),auxDomain, device);
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                    removeAuthorizedIpsFromXML(auxDomain.getPath(),auxDomain, device);
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator +  "AuthorizedIps.xml");
                    auxClassGui.readDatabase();
                    auxDomain.getAuhtorizedDeviceList().setDeviceList(auxClassGui.getDomainList().getDomainByPath(auxDomain.getPath()).getAuhtorizedDeviceList().getDevices());
                }
            }
            auxClassGui.readDatabase();
            domain.getDeviceList().setDeviceList(auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getDeviceList().getDevices());
            if (device.getGroupList().getSize() != 0)
                for( Group auxGroup: device.getGroupList().getGroups()){
                    auxGroup.setDeviceList(auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getGroupList().getGroup(auxGroup.getPath()).getDeviceList());
                }
            deviceList = auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getDeviceList();
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, device.getDeviceName() + " removed successfully"), "Device removed successfully", JOptionPane.INFORMATION_MESSAGE);
            cleanSecondaryWindows();
            deviceSecondaryWindow(domain);


        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "No Device Removed.       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            selectRemoveDevice(domain);
        }
    }
    private static void removeDeviceFromXML(String domainPath, Device device) {
        String xmlFilePath = basePath + File.separator + "Network" + domainPath + File.separator + "Devices.xml";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));
            NodeList domainList = document.getElementsByTagName("Device");

            for (int i = 0; i < domainList.getLength(); i++) {
                Node domainNode = domainList.item(i);
                if (domainNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element domainElement = (Element) domainNode;
                    String name = domainElement.getElementsByTagName("Name").item(0).getTextContent();
                    String ip = domainElement.getElementsByTagName("ipAddress").item(0).getTextContent();
                    String ipv6 = domainElement.getElementsByTagName("ipv6Address").item(0).getTextContent();

                    if (name.equals(device.getDeviceName())) {
                        // Remove the domain node
                        domainNode.getParentNode().removeChild(domainNode);
                    }
                    else if (ip.equals(device.getIPAddress())){
                        // Remove the domain node
                        domainNode.getParentNode().removeChild(domainNode);
                    }
                    else if (ipv6.equals(device.getIPv6Address())){
                        // Remove the domain node
                        domainNode.getParentNode().removeChild(domainNode);
                    }
                }
            }
            // Save the modified document back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("Device removed successfully.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            System.out.println("Failed to remove the domain: " + e.getMessage());
        }
    }
    private static void removeAuthorizedDeviceFromXML(String domainPath, Domain domain, Device device) {
        String xmlFilePath = basePath + File.separator + "Network" + domainPath + File.separator + domain.getName() + ".xml";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));
            NodeList domainList = document.getElementsByTagName("Device");

            for (int i = 0; i < domainList.getLength(); i++) {
                Node domainNode = domainList.item(i);
                if (domainNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element domainElement = (Element) domainNode;
                    String name = domainElement.getElementsByTagName("Name").item(0).getTextContent();
                    String domainStringPath = domainElement.getElementsByTagName("Domain").item(0).getTextContent();

                    if (name.equals(device.getDeviceName())) {
                        // Remove the domain node
                        if(domainStringPath.equals(device.getDomain().getPath())){
                            domainNode.getParentNode().removeChild(domainNode);
                        }
                    }

                }
            }
            // Save the modified document back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("Device removed successfully.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            System.out.println("Failed to remove the domain: " + e.getMessage());
        }
    }
    private static void removeAuthorizedIpsFromXML(String domainPath, Domain domain, Device device) {
        String xmlFilePath = basePath + File.separator + "Network" + domainPath + File.separator + "AuthorizedIps.xml";
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFilePath);

            removeSpecificElement(doc, "ipAddress", device.getIPAddress());
            removeSpecificElement(doc, "ipv6Address", device.getIPv6Address());
            // Save the modified document back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("Ips removed successfully.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            System.out.println("Failed to remove the domain: " + e.getMessage());
        }
    }
    private static void removeSpecificElement(Document doc, String elementName, String valueToRemove) {
        NodeList nodeList = doc.getElementsByTagName(elementName);
        for (int i = nodeList.getLength() - 1; i >= 0; i--) {
            Element element = (Element) nodeList.item(i);
            if (element.getTextContent().equals(valueToRemove)) {
                element.getParentNode().removeChild(element);
            }
        }
    }
    private static ArrayList<String> removeAuthorizedDeviceFromGroupFromXML(String domainPath, Domain domain, Group group) {
        String xmlFilePath = basePath + File.separator + "Network" + domainPath + File.separator + domain.getName() + ".xml";
        ArrayList<String> ipList = new ArrayList<>();
        try {
            // Parse the XML file
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFilePath);

            // Find the devices to remove
            NodeList deviceList = doc.getElementsByTagName("Device");
            for (int i = deviceList.getLength() - 1; i >= 0; i--) {
                Element device = (Element) deviceList.item(i);
                Node groupNode = device.getElementsByTagName("Group").item(0);
                String groupText = groupNode.getTextContent();
                if (groupText.equals(group.getPath())) {
                    // Remove the device
                    device.getParentNode().removeChild(device);
                    Node ipNode = device.getElementsByTagName("ipAddress").item(0);
                    String ipText = ipNode.getTextContent();
                    Node ipv6Node = device.getElementsByTagName("ipv6Address").item(0);
                    String ipv6Text = ipv6Node.getTextContent();
                    ipList.add(ipText);
                    ipList.add(ipv6Text);
                }
            }


            // Save the modified XML back to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("Devices removed successfully.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
        return ipList;
    }
    private static void removeAuthorizedDeviceIPFromGroupFromXML(String domainPath, ArrayList<String> addressesToRemove) {
        String xmlFilePath = basePath + File.separator + "Network" + domainPath + File.separator + "AuthorizedIps.xml";
        try {
            // Parse the XML file
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFilePath);

            // Get the root element
            Element root = doc.getDocumentElement();

            // Get the list of ip and ipv6 addresses
            NodeList addressList = root.getChildNodes();

            // Iterate over the addresses
            for (int i = addressList.getLength() - 1; i >= 0; i--) {
                Node addressNode = addressList.item(i);
                if (addressNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element addressElement = (Element) addressNode;
                    String address = addressElement.getTextContent();

                    // Check if the address should be removed
                    if (addressesToRemove.contains(address)) {
                        // Remove the address
                        root.removeChild(addressNode);
                    }
                }
            }

            // Save the modified XML back to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("Addresses removed successfully.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void selectEditDevice(Domain domain){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        //panel.setBackground(colorLightBrown);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        DomainList networkList = getNetworkInfo(this.domainList);
        editDeviceButtons = new JButton[domainList.getSize()];
        for (int i = 0; i< domain.getDeviceList().getSize(); i++) {
            System.out.println(domain.getDeviceList().getDevices().get(i).getDeviceName() + " are the devices of domain " + domain.getName());
            editDeviceButtons[i] = new JButton(domain.getDeviceList().getDevices().get(i).getDeviceName());
            editDeviceButtons[i].setFont(buttonFont);
            editDeviceButtons[i].setActionCommand(String.valueOf(i));
            editDeviceButtons[i].addActionListener(this);
            editDeviceButtons[i].putClientProperty("Device", domain.getDeviceList().getDevices().get(i));
            panel.add(editDeviceButtons[i]);

        }
        panel.setPreferredSize(new Dimension(200, 35*networkList.getSize()));
        panel.setFont(defaultFont);


        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPanel.setPreferredSize(new Dimension(300, 300));
        JPanel editPanel = new JPanel(new BorderLayout());
        editPanel.add(scrollPanel, BorderLayout.CENTER);;
        JPanel outherPanel = new JPanel(new BorderLayout());
        outherPanel.add(editPanel, BorderLayout.CENTER);
        outherPanel.setBackground(colorLightBrown);

        UIManager.put("OptionPane.background", colorLightBrown);
        UIManager.put("Panel.background", colorLightBrown);

        JOptionPane dialog = new JOptionPane(outherPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        dialog.setBackground(colorLightBrown);
        JDialog dialogWindow = dialog.createDialog(null, "Please Select the Device to be Edited");
        dialogWindow.setBackground(colorLightBrown);
        dialogWindow.setVisible(true);
    }

    private void editDevice(Device editedDevice){
        auxClassGui.readDatabase();
        ArrayList<Device> allDevices = new ArrayList<>();
        for(Domain allDomain: auxClassGui.getDomainList().getDomains()){
            for(Device device: allDomain.getDeviceList().getDevices()){
                allDevices.add(device);
            }
        }
        DeviceList allDeviceList = new DeviceList();
        allDeviceList.setDeviceList(allDevices);
        deviceList = allDeviceList;
        UIManager.put("OptionPane.background", colorLightBrown);
        UIManager.put("Panel.background", colorLightBrown);
        JTextField name = new JTextField(5);
        JTextField pass = new JTextField(5);
        JTextField ip = new JTextField(5);
        JTextField ipv6 = new JTextField(5);

        name.setText(editedDevice.getDeviceName());
        pass.setText(editedDevice.getPassword());
        ip.setText(editedDevice.getIPAddress());//will have to verify better
        if (editedDevice.getIPv6Address() != null){
            ipv6.setText(editedDevice.getIPv6Address()); //will have to verify better
        }
        else{
            ipv6.setText("2001:0db8:85a3:0000:0000:8a2e:0370:733" + (editedDevice.getDomain().getDeviceList().getSize() + 1));//will have to verify better
        }



        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Name:")).setFont(defaultFont);
        panel.add(name).setFont(defaultFont);
        panel.add(new JLabel("Password:")).setFont(defaultFont);
        panel.add(pass).setFont(defaultFont);
        panel.add(new JLabel("IPv4 address:")).setFont(defaultFont);
        panel.add(ip).setFont(defaultFont);
        panel.add(new JLabel("IPv6 address:")).setFont(defaultFont);
        panel.add(ipv6).setFont(defaultFont);
        panel.setPreferredSize(new Dimension(800, 350));
        panel.setFont(defaultFont);

        UIManager.put("OptionPane.background", colorLightBrown);
        UIManager.put("Panel.background", colorLightBrown);


        int result;
        JOptionPane dialog = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialogWindow = dialog.createDialog(null, "Please Enter the Password, IP address, and Domain");
        dialogWindow.setVisible(true);
        result = (Integer) dialog.getValue();

        if (result == JOptionPane.OK_OPTION) {
            if (name.getText().equals("")) {
                JOptionPane.showMessageDialog(this, getMessagePanel(defaultFont, "Invalid device name"),"Invalid device name.", JOptionPane.ERROR_MESSAGE);
                editDevice(editedDevice);
            }
            else if (name.getText().indexOf(".") != -1 || name.getText().indexOf(" ") != -1){  //deviceName can't contain a space or a period

                JOptionPane.showMessageDialog(this, getMessagePanel(defaultFont, "Invalid format"), "Invalid format", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                editDevice(editedDevice);
            }
            //if device name is different from the current one and the new name already exists, it should abort editing
            else if (!editedDevice.getDeviceName().equals(name.getText()) && currentDomain.getDeviceList().verifyDeviceName(name.getText())){
                System.out.println("editedDevice = " + editedDevice.getDeviceName() + " name.getText() = " + name.getText() +".");
                JOptionPane.showMessageDialog(this,getMessagePanel(defaultFont, name.getText() + " already exists"),name.getText() + " already exists\n", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                editDevice(editedDevice);
            }
            else if(currentDomain.getGroupList().verifyGroupName(name.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, name.getText()+ " name already used in this Domain     "), "Name already used in this Domain", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                editDevice(editedDevice);
            }


            else if (!editedDevice.getDeviceName().equals(name.getText()) && editedDevice.getDomain().getDeviceList().getDevices().stream().anyMatch(device -> device.getDeviceName().equals(name.getText()))){
                //currentDevice = deviceList.getDevice(name.getText());
                JOptionPane.showMessageDialog(this,getMessagePanel(defaultFont, name.getText() + " already exists"),name.getText() + " already exists\n", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
            }
            else if (pass.getText().equals("")) {
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid password"), "Invalid password", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                editDevice(editedDevice);
            }
            else if (ip.getText().equals("") && ipv6.getText().equals("")) {
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid IPv4 andd IPv6 addresses"), "Invalid IP addresses", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                editDevice(editedDevice);
            }
            //verifies if the ip and ipv6 address is different from the current one and if it already exists
            /*else if (!editedDevice.getIPAddress().equals(ip.getText()) && allDeviceList.verifyIPAddress(ip.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "IP address already exists"), "IP address already exists", JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(this, "IP address already exists");
                dialogWindow.dispose();
                editDevice(editedDevice);
            }

            else if(!editedDevice.getIPv6Address().equals(ipv6.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "IPv6 address already exists"), "IPv6 address already exists", JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(this, "IPv6 address already exists");
                dialogWindow.dispose();
                editDevice(editedDevice);
            }*/


            else if (ip.getText().equals("")) {
                if (!verifyIPv6AddressFormat(ipv6.getText())) {
                    JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid IPv6 address"), "Invalid IPv6 address", JOptionPane.ERROR_MESSAGE);
                    dialogWindow.dispose();
                    editDevice(editedDevice);
                }
                else{
                    Device newDevice = new Device(name.getText(), pass.getText(), editedDevice.getGroupList(), ip.getText(), ipv6.getText(),editedDevice.getDomain());
                    removeDeviceFromXML(editedDevice.getDomain().getPath(), editedDevice);
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + editedDevice.getDomain().getPath() + File.separator + "Devices.xml");
                    editedDevice.getDomain().getDeviceList().removeDevice(editedDevice);
                    editedDevice.getDomain().getDeviceList().addDeviceToXML(newDevice, basePath + File.separator + "Network" + editedDevice.getDomain().getPath() + File.separator + "Devices.xml");
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + editedDevice.getDomain().getPath() + File.separator + "Devices.xml");
                    editedDevice.getDomain().getDeviceList().addDevice(newDevice);

                    JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, name.getText() + " edited successfully"), "Device edited successfully", JOptionPane.INFORMATION_MESSAGE);
                    cleanSecondaryWindows();
                    deviceSecondaryWindow(editedDevice.getDomain());
                }
            }
            else if (ipv6.getText().equals("")) {
                if (!verifyIPAddressFormat(ip.getText())){
                    JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid IPv4 address"), "Invalid IP address", JOptionPane.ERROR_MESSAGE);
                    dialogWindow.dispose();
                    editDevice(editedDevice);
                }
                else{
                Device newDevice = new Device(name.getText(), pass.getText(), editedDevice.getGroupList(), ip.getText(), ipv6.getText(),editedDevice.getDomain());
                removeDeviceFromXML(editedDevice.getDomain().getPath(), editedDevice);
                removeEmptyLinesFromXML(basePath + File.separator + "Network" + editedDevice.getDomain().getPath() + File.separator + "Devices.xml");
                editedDevice.getDomain().getDeviceList().removeDevice(editedDevice);
                editedDevice.getDomain().getDeviceList().addDeviceToXML(newDevice, basePath + File.separator + "Network" + editedDevice.getDomain().getPath() + File.separator + "Devices.xml");
                removeEmptyLinesFromXML(basePath + File.separator + "Network" + editedDevice.getDomain().getPath() + File.separator + "Devices.xml");
                editedDevice.getDomain().getDeviceList().addDevice(newDevice);

                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, name.getText() + " edited successfully"), "Device edited successfully", JOptionPane.INFORMATION_MESSAGE);
                cleanSecondaryWindows();
                deviceSecondaryWindow(editedDevice.getDomain());
                }
            }

            else if (!verifyIPAddressFormat(ip.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid IPv4 address"), "Invalid IP address", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                editDevice(editedDevice);
            }
            else if (!verifyIPv6AddressFormat(ipv6.getText())) {
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid IPv6 address"), "Invalid IPv6 address", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                editDevice(editedDevice);
            }



            /*else if (!domainList.verifyDomainName(domainName.getText(), )){       //NÃO SEI O QUE É ISTO
                Domain newDomain =new Domain(domainName.getText());
                deviceList.getDomainList().addDomain(newDomain);
                domainList.saveDomainListToXML("allDomainsList.xml");
                //aqui
            }*/
            //Device newDevice = new Device(name, pass.getText(), deviceList.getGroupList().getGroup(group.getText()), ip.getText(), ipv6.getText(), deviceList.getDomainList().getDomain(domain.getName()), deviceList.getDomainList(), deviceList.getGroupList());
            //deviceList.addDevice(newDevice);
            /*for (Device auxDevice : deviceList.getDevices()){
                auxDevice.setAuthorizedDevicesGivenListOfDevices(deviceList);
                auxDevice.setAuthorizedDomainsGivenListOfDevices(domainList);
            }*/
            else if (!editedDevice.getIPAddress().equals(ip.getText()) && allDeviceList.verifyIPAddress(ip.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "IPv4 address already exists"), "IP address already exists", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                editDevice(editedDevice);
            }

            else if(!editedDevice.getIPv6Address().equals(ipv6.getText()) && allDeviceList.verifyIPv6Address(ipv6.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "IPv6 address already exists"), "IPv6 address already exists", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                editDevice(editedDevice);
            }

            else if(editedDevice.getDeviceName().equals(name.getText()) && editedDevice.getPassword().equals(pass.getText()) &&
            editedDevice.getIPAddress().equals(ip.getText()) && editedDevice.getIPv6Address().equals(ipv6.getText()) ){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Device not edited       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
                dialogWindow.dispose();
            }

            else{
                Device newDevice = new Device(name.getText(), pass.getText(), editedDevice.getGroupList(), ip.getText(), ipv6.getText(),editedDevice.getDomain());
                removeDeviceFromXML(editedDevice.getDomain().getPath(), editedDevice);
                removeEmptyLinesFromXML(basePath + File.separator + "Network" + editedDevice.getDomain().getPath() + File.separator + "Devices.xml");
                editedDevice.getDomain().getDeviceList().removeDevice(editedDevice);
                editedDevice.getDomain().getDeviceList().addDeviceToXML(newDevice, basePath + File.separator + "Network" + editedDevice.getDomain().getPath() + File.separator + "Devices.xml");
                removeEmptyLinesFromXML(basePath + File.separator + "Network" + editedDevice.getDomain().getPath() + File.separator + "Devices.xml");
                editedDevice.getDomain().getDeviceList().addDevice(newDevice);

                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, name.getText() + " edited successfully"), "Device edited successfully", JOptionPane.INFORMATION_MESSAGE);
                cleanSecondaryWindows();
                deviceSecondaryWindow(editedDevice.getDomain());
            }
        }
        else{
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Device not edited       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            dialogWindow.dispose();
        }

    }

    private void addGroup(Domain domain){
        UIManager.put("OptionPane.background", colorLightYellow);
        UIManager.put("Panel.background", colorLightYellow);
        JTextField name = new JTextField(5);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Name:")).setFont(defaultFont);
        panel.add(name).setFont(defaultFont);
        panel.add(Box.createVerticalStrut(3)); // a spacer
        panel.add(Box.createRigidArea(new Dimension(0, 3))); // fixed height spacer

        panel.setPreferredSize(new Dimension(300, 50));
        panel.setFont(defaultFont);



        int result;
        JOptionPane dialog = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialogWindow = dialog.createDialog(null, "Please Enter the Group's Name to be added");
        dialogWindow.setVisible(true);
        result = (Integer) dialog.getValue();


        if (result == JOptionPane.OK_OPTION) {
            if (name.getText().equals("")) {
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Invalid name      "), "Invalid name", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addGroup(domain);
            }
            else if (name.getText().contains(",")) {
                JOptionPane.showMessageDialog(null, getMessagePanel(defaultFont, "Commas are not allowed in the name"), "Invalid name", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addGroup(domain);
            }


            else if(domain.getGroupList().verifyGroupName(name.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Group already exists     "), "Group already exists", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addGroup(domain);
            }
            else if(domain.getDeviceList().verifyDeviceName(name.getText())){
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, name.getText()+ " name already used in this Domain     "), "Name already used in this Domain", JOptionPane.ERROR_MESSAGE);
                dialogWindow.dispose();
                addGroup(domain);
            }



            else{
                Group newgroup = new Group(name.getText(), domain);
                domain.getGroupList().addGroupToXML(newgroup, basePath + File.separator + "Network" + domain.getPath() + File.separator + "Groups.xml");
                for(Domain auxDomain: auxClassGui.getDomainList().getDomains()){
                    if(checkDomainExists(new File(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml"),domain.getName(),domain.getPath())){
                        if(!checkGroupExists(new File(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml"), newgroup.getName(), newgroup.getPath())){
                            auxDomain.getAuthorizedGroupList().addAuthorizedGroupToXML(newgroup, basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                            removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                            auxClassGui.readDatabase();
                            auxDomain.getAuthorizedGroupList().setGroupList(auxClassGui.getDomainList().getDomainByPath(auxDomain.getPath()).getAuthorizedGroupList().getGroups());
                        }
                    }
                }
                removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "Groups.xml");
                domain.getGroupList().addGroup(newgroup);


                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, name.getText() + " added successfully"), "Group added successfully", JOptionPane.INFORMATION_MESSAGE);
                cleanSecondaryWindows();
                groupsSecondaryWindow(domain);

            }
        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Group not added       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            dialogWindow.dispose();

        }

    }

    private void selectRemoveGroup(Domain domain){
        groupList = domain.getGroupList();
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);


        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        removeGroupButtons = new JButton[domain.getGroupList().getSize()];
        for (int i= 0; i < domain.getGroupList().getSize(); i++) {
            System.out.println(domain.getGroupList().getGroups().get(i).getName() + " is a group of domain " + domain.getName());
            removeGroupButtons[i] = new JButton(domain.getGroupList().getGroups().get(i).getName());
            removeGroupButtons[i].setFont(buttonFont);
            removeGroupButtons[i].setActionCommand(String.valueOf(i));
            removeGroupButtons[i].addActionListener(this);
            removeGroupButtons[i].putClientProperty("Group", domain.getGroupList().getGroups().get(i));
            panel.add(removeGroupButtons[i]);
        }

        panel.setPreferredSize(new Dimension(200, 35*domain.getGroupList().getSize()));
        //panel.setPreferredSize(new Dimension(200, 35*getGroupsFromDomain(domain).getSize()));     //ESTE É O CORRETO A USAR

        panel.setFont( defaultFont );


        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPanel.setPreferredSize(new Dimension(300, 300));
        JPanel removePanel = new JPanel(new BorderLayout());
        removePanel.add(scrollPanel, BorderLayout.CENTER);
        JPanel outherPanel = new JPanel(new BorderLayout());
        outherPanel.add(removePanel, BorderLayout.CENTER);
        outherPanel.setBackground(colorRed);

        UIManager.put("OptionPane.background", colorRed);
        UIManager.put("Panel.background", colorRed);

        JOptionPane dialog = new JOptionPane(outherPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        dialog.setBackground(colorRed);
        JDialog dialogWindow = dialog.createDialog(null, "Please Select a Group to be removed");
        dialogWindow.setVisible(true);

    }

    private void removeGroup(Domain domain, Group group){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);

        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to remove group " + group.getName() + "?        "),
                "Are you sure you want to remove device " + group.getName() + "?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);


        if (result == JOptionPane.YES_OPTION) {
            for(Domain auxDomain: auxClassGui.getDomainList().getDomains()){
                if(checkDomainExists(new File(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml"),domain.getName(),domain.getPath())){
                    ArrayList<String> ipsToRemove = removeAuthorizedDeviceFromGroupFromXML(auxDomain.getPath(), auxDomain,group);
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                    removeAuthorizedDeviceIPFromGroupFromXML(auxDomain.getPath(), ipsToRemove);
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + "AuthorizedIps.xml");
                    removeAuthorizedGroupFromXML(auxDomain.getPath(),auxDomain, group);
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                    auxClassGui.readDatabase();
                    auxDomain.getAuthorizedGroupList().setGroupList(auxClassGui.getDomainList().getDomainByPath(auxDomain.getPath()).getAuthorizedGroupList().getGroups());
                    }
                ArrayList<String> ipsToRemove = removeAuthorizedDeviceFromGroupFromXML(auxDomain.getPath(), auxDomain,group);
                removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                removeAuthorizedDeviceIPFromGroupFromXML(auxDomain.getPath(), ipsToRemove);
                removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + "AuthorizedIps.xml");
                removeAuthorizedGroupFromXML(auxDomain.getPath(),auxDomain, group);
                removeEmptyLinesFromXML(basePath + File.separator + "Network" + auxDomain.getPath() + File.separator + auxDomain.getName() + ".xml");
                auxClassGui.readDatabase();
                auxDomain.getAuthorizedGroupList().setGroupList(auxClassGui.getDomainList().getDomainByPath(auxDomain.getPath()).getAuthorizedGroupList().getGroups());

            }
            removeGroupFromXML(domain.getPath(), group);
            modifyDevicesFromGroupinXMLToNothing(group);
            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "Groups.xml");
            auxClassGui.readDatabase();
            domain.getGroupList().setGroupList(auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getGroupList().getGroups());
            groupList = auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getGroupList();
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, group.getName() + " removed successfully"), "Group removed successfully", JOptionPane.INFORMATION_MESSAGE);
            cleanSecondaryWindows();
            groupsSecondaryWindow(domain);
        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Group Not Removed       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            selectRemoveGroup(domain);

        }
    }
    private void removeGroupFromXML(String domainPath, Group group) {
        String xmlFilePath = basePath + File.separator + "Network" + domainPath + File.separator + "Groups.xml";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));
            NodeList groupList = document.getElementsByTagName("Group");

            for (int i = 0; i < groupList.getLength(); i++) {
                Node groupNode = groupList.item(i);
                if (groupNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element groupElement = (Element) groupNode;
                    String path = groupElement.getElementsByTagName("Path").item(0).getTextContent();

                    if (path.equals(group.getPath())) {
                        // Remove the domain node
                        groupNode.getParentNode().removeChild(groupNode);
                    }
                }
            }
            // Save the modified document back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("Group removed successfully.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            System.out.println("Failed to remove the group: " + e.getMessage());
        }
    }

    private void selectAddToGroup(Domain domain, Group group){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addToGroupButtons = new JButton[domain.getDeviceList().getSize()];
        for (int i = 0; i< domain.getDeviceList().getSize(); i++) {
            System.out.println(domain.getDeviceList().getDevices().get(i).getDeviceName() + " are the devices of domain " + domain.getName());
            addToGroupButtons[i] = new JButton(domain.getDeviceList().getDevices().get(i).getDeviceName());
            addToGroupButtons[i].setFont(buttonFont);
            addToGroupButtons[i].setActionCommand(String.valueOf(i));
            addToGroupButtons[i].addActionListener(this);
            addToGroupButtons[i].putClientProperty("Device", domain.getDeviceList().getDevices().get(i));
            panel.add(addToGroupButtons[i]);


        }

        panel.setPreferredSize(new Dimension(200, 35*domain.getDeviceList().getSize()));
        //panel.setPreferredSize(new Dimension(200, 35*getDeviceListFromDomain(domain).getSize()));     //ESTE É O CORRETO A USAR

        panel.setFont(defaultFont);

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPanel.setPreferredSize(new Dimension(300, 300));
        JPanel addPanel = new JPanel(new BorderLayout());
        addPanel.add(scrollPanel, BorderLayout.CENTER);
        JPanel outherPanel = new JPanel(new BorderLayout());
        outherPanel.add(addPanel, BorderLayout.CENTER);
        outherPanel.setBackground(colorLightYellow);

        UIManager.put("OptionPane.background", colorLightYellow);
        UIManager.put("Panel.background", colorLightYellow);

        JOptionPane dialog = new JOptionPane(outherPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE);
        dialog.setBackground(colorLightYellow);
        JDialog dialogWindow = dialog.createDialog(null, "Please Select a Device to be added to the Group "+ group.getName());
        dialogWindow.setVisible(true);
    }

    private void addToGroup(Domain domain, Group group, Device device){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);

        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to add device " + device.getDeviceName() + " to group " + group.getName() +"?        "),
                "Are you sure you want to add device " + device.getDeviceName() + " to group " + group.getName() +"?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);


        if (result == JOptionPane.YES_OPTION) {
            auxClassGui.readDatabase();
            modifyDeviceGroupinXML(device,group);
            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "Devices.xml");
            for(Domain allDomain: auxClassGui.getDomainList().getDomains()){
                if(allDomain.getAuhtorizedDeviceList().getDevice(device.getDeviceName()) != null){
                    modifyAuthorizedDeviceGroupinXML(device,group, allDomain);
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + allDomain.getPath() + File.separator + "Devices.xml");
                    allDomain.getAuhtorizedDeviceList().getDevice(device.getDeviceName()).getGroupList().addGroup(group);
                }
                if(allDomain.getAuthorizedGroupList().getGroup(group.getPath()) != null){
                    allDomain.getAuhtorizedDeviceList().addAuthorizedDeviceToXML(device, basePath + File.separator + "Network" + allDomain.getPath() + File.separator + allDomain.getName() + ".xml");
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + allDomain.getPath() + File.separator + allDomain.getName() + ".xml");
                    allDomain.getAuhtorizedDeviceList().addAuthorizedIpsToXML(device, basePath + File.separator + "Network" + allDomain.getPath() + File.separator + "AuthorizedIps.xml");
                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + allDomain.getPath() + File.separator + "AuthorizedIps.xml");
                    allDomain.getAuhtorizedDeviceList().addDevice(device);
                }
            }
            group.getDeviceList().addDevice(device);
            device.getGroupList().addGroup(group);
            auxClassGui.readDatabase();
            for(Group auxGroup: domain.getGroupList().getGroups()){
                domain.getGroupList().getGroup(auxGroup.getPath()).setDeviceList(auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getGroupList().getGroup(auxGroup.getPath()).getDeviceList());
                }

            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, device.getDeviceName() + " added to group " + group.getName()+ " successfully"), "Device added to group successfully", JOptionPane.INFORMATION_MESSAGE);
            cleanSecondaryWindows();
            oneGroupSecondaryWindow(group);


        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "No Device Added to Group " + group.getName()+ "       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            selectAddToGroup(domain, group);
        }


    }

    public void modifyDeviceGroupinXML(Device device, Group group) {
        try {
            // Load the XML file
            File xmlFile = new File(basePath + File.separator + "Network" + device.getDomain().getPath() + File.separator + "Devices.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // Find the device element with the specified name
            NodeList deviceListNodes = document.getElementsByTagName("Device");
            for (int i = 0; i < deviceListNodes.getLength(); i++) {
                Element deviceElement = (Element) deviceListNodes.item(i);
                String name = deviceElement.getElementsByTagName("Name").item(0).getTextContent();
                if (name.equals(device.getDeviceName())) {
                    // Modify the Group element
                    Element groupElement = (Element) deviceElement.getElementsByTagName("Group").item(0);
                    String groupsNames = deviceElement.getElementsByTagName("Group").item(0).getTextContent();
                    if (groupsNames.equals("")){
                        groupElement.setTextContent(group.getPath());
                        break;
                    }
                    else{
                        groupsNames = groupsNames + "," + group.getPath();
                        groupElement.setTextContent(groupsNames);
                        break;
                    }
                }
            }

            // Save the modified XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println("Device group modified successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modifyAuthorizedDeviceGroupinXML(Device device, Group group, Domain domain) {
        try {
            // Load the XML file
            File xmlFile = new File(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() +".xml");
            System.out.println(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() +".xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // Find the device element with the specified name
            NodeList deviceListNodes = document.getElementsByTagName("Device");
            for (int i = 0; i < deviceListNodes.getLength(); i++) {
                Element deviceElement = (Element) deviceListNodes.item(i);
                String name = deviceElement.getElementsByTagName("Name").item(0).getTextContent();
                if (name.equals(device.getDeviceName())) {
                    // Modify the Group element
                    Element groupElement = (Element) deviceElement.getElementsByTagName("Group").item(0);
                    String groupsNames = deviceElement.getElementsByTagName("Group").item(0).getTextContent();
                    if (groupsNames.equals("")){
                        groupElement.setTextContent(group.getPath());
                        break;
                    }
                    else{
                        groupsNames = groupsNames + "," + group.getPath();
                        groupElement.setTextContent(groupsNames);
                        break;
                    }
                }
            }

            // Save the modified XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println("Device group modified successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String removeSpecificPartOfStringDividedByCommas(String inputString, String wordToRemove){
        String updatedString = inputString.replace(wordToRemove + ",", "").replace("," + wordToRemove, "").replace(wordToRemove, "");
        return updatedString;
    }
    public void modifyAuthorizedDeviceGroupinXMLToNothing(Device device, Group group, Domain domain) {
        try {
            // Load the XML file
            File xmlFile = new File(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() +".xml");
            System.out.println(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() +".xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // Find the device element with the specified name
            NodeList deviceListNodes = document.getElementsByTagName("Device");
            for (int i = 0; i < deviceListNodes.getLength(); i++) {
                Element deviceElement = (Element) deviceListNodes.item(i);
                String name = deviceElement.getElementsByTagName("Name").item(0).getTextContent();
                if (name.equals(device.getDeviceName())) {
                    // Modify the Group element
                    Element groupElement = (Element) deviceElement.getElementsByTagName("Group").item(0);
                    String groupsNames = deviceElement.getElementsByTagName("Group").item(0).getTextContent();
                    String updatedGroups = removeSpecificPartOfStringDividedByCommas(groupsNames, group.getPath());
                    groupElement.setTextContent(updatedGroups);
                    break;
                }
            }

            // Save the modified XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println("Device group modified successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifyDeviceGroupinXMLToNothing(Device device, Group group) {
        try {
            // Load the XML file
            File xmlFile = new File(basePath + File.separator + "Network" + device.getDomain().getPath() + File.separator + "Devices.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // Find the device element with the specified name
            NodeList deviceListNodes = document.getElementsByTagName("Device");
            for (int i = 0; i < deviceListNodes.getLength(); i++) {
                Element deviceElement = (Element) deviceListNodes.item(i);
                String name = deviceElement.getElementsByTagName("Name").item(0).getTextContent();
                if (name.equals(device.getDeviceName())) {
                    // Modify the Group element
                    Element groupElement = (Element) deviceElement.getElementsByTagName("Group").item(0);
                    String groupsNames = deviceElement.getElementsByTagName("Group").item(0).getTextContent();
                    String updatedGroups = removeSpecificPartOfStringDividedByCommas(groupsNames, group.getPath());
                    groupElement.setTextContent(updatedGroups);
                    break;
                }
            }

            // Save the modified XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println("Device group modified successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modifyDevicesFromGroupinXMLToNothing(Group group) {
        try {
            // Load the XML file
            File xmlFile = new File(basePath + File.separator + "Network" + group.getDomain().getPath() + File.separator + "Devices.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // Find the device element with the specified name
            NodeList deviceListNodes = document.getElementsByTagName("Device");
            for (int i = 0; i < deviceListNodes.getLength(); i++) {
                Element deviceElement = (Element) deviceListNodes.item(i);
                String groupPath = deviceElement.getElementsByTagName("Group").item(0).getTextContent();
                String[] wordsArray = groupPath.split(",");

                ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(wordsArray));
                for(String auxString : wordsList){
                    if (auxString.equals(group.getPath())) {
                        // Modify the Group element
                        Element groupElement = (Element) deviceElement.getElementsByTagName("Group").item(0);
                        String groupsNames = deviceElement.getElementsByTagName("Group").item(0).getTextContent();
                        String updatedGroups = removeSpecificPartOfStringDividedByCommas(groupsNames, group.getPath());
                        groupElement.setTextContent(updatedGroups);
                        break;
                    }
                }
            }

            // Save the modified XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println("Device group modified successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void selectRemoveFromGroup(Domain domain, Group group){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        removeFromGroupButtons = new JButton[domain.getGroupList().getGroup(group.getPath()).getDeviceList().getSize()];
        for (int i = 0; i < domain.getGroupList().getGroup(group.getPath()).getDeviceList().getSize(); i++) {
            System.out.println(domain.getGroupList().getGroup(group.getPath()).getDeviceList().getDevices().get(i).getDeviceName() + " are the devices of domain " + domain.getName());
            removeFromGroupButtons[i] = new JButton(domain.getGroupList().getGroup(group.getPath()).getDeviceList().getDevices().get(i).getDeviceName());
            removeFromGroupButtons[i].setFont(buttonFont);
            removeFromGroupButtons[i].setActionCommand(String.valueOf(i));
            removeFromGroupButtons[i].addActionListener(this);
            removeFromGroupButtons[i].putClientProperty("Device", domain.getGroupList().getGroup(group.getPath()).getDeviceList().getDevices().get(i));
            panel.add(removeFromGroupButtons[i]);

        }

        panel.setPreferredSize(new Dimension(200, 35*domain.getDeviceList().getSize()));
        //panel.setPreferredSize(new Dimension(200, 35*getDeviceListFromGroup(group).getSize()));     //ESTE É O CORRETO A USAR

        panel.setFont(defaultFont);

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPanel.setPreferredSize(new Dimension(300, 300));
        JPanel removePanel = new JPanel(new BorderLayout());
        removePanel.add(scrollPanel, BorderLayout.CENTER);
        JPanel outherPanel = new JPanel(new BorderLayout());
        outherPanel.add(removePanel, BorderLayout.CENTER);
        outherPanel.setBackground(colorRed);

        UIManager.put("OptionPane.background", colorRed);
        UIManager.put("Panel.background", colorRed);

        JOptionPane dialog = new JOptionPane(outherPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE);
        dialog.setBackground(colorRed);
        JDialog dialogWindow = dialog.createDialog(null, "Please Select a Device to be removed from the Group" );
        dialogWindow.setVisible(true);

    }

    private void removeFromGroup(Domain domain, Group group, Device device){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);

        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to remove device " + device.getDeviceName() + " from group " + group.getName() +"?        "),
                "Are you sure you want to remove device " + device.getDeviceName() + " from group " + group.getName() +"?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);


        if (result == JOptionPane.YES_OPTION) {
            auxClassGui.readDatabase();
            modifyDeviceGroupinXMLToNothing(device,group);
            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "Devices.xml");
            for(Domain allDomain: auxClassGui.getDomainList().getDomains()){
                if(allDomain.getAuhtorizedDeviceList().getDevice(device.getDeviceName()) != null){
                    modifyAuthorizedDeviceGroupinXMLToNothing(device,group, allDomain);
                    allDomain.getAuhtorizedDeviceList().getDevice(device.getDeviceName()).getGroupList().removeGroup(group);
                }

            }
            group.getDeviceList().removeDevice(device);
            auxClassGui.readDatabase();
            for(Group auxGroup: domain.getGroupList().getGroups()){
                domain.getGroupList().getGroup(auxGroup.getPath()).setDeviceList(auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getGroupList().getGroup(auxGroup.getPath()).getDeviceList());

            }


            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, device.getDeviceName() + " removed from group " + group.getName()+ " successfully"), "Device removed from group successfully", JOptionPane.INFORMATION_MESSAGE);
            cleanSecondaryWindows();
            oneGroupSecondaryWindow(group);


        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "No Device Removed from Group " + group.getName()+ "       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            selectRemoveFromGroup(domain, group);
        }


    }


    private void selectAddAuthorizedDevice(){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        DeviceList allDevices = new DeviceList();
        for (Domain auxDomain : domainList.getDomains()){
            for(Device auxDevice: auxDomain.getDeviceList().getDevices()){
                allDevices.addDevice(auxDevice);
            }
        }
        addDeviceToAuthorizedButtons = new JButton[allDevices.getSize()];

        int i = 0;
        for (Device device :allDevices.getDevices()) {
            addDeviceToAuthorizedButtons[i] = new JButton(device.getDeviceName()+ " - " + device.getDomain().getPath());
            addDeviceToAuthorizedButtons[i].setFont(buttonFont);
            addDeviceToAuthorizedButtons[i].setActionCommand(String.valueOf(i));
            addDeviceToAuthorizedButtons[i].addActionListener(this);
            addDeviceToAuthorizedButtons[i].putClientProperty("Device", device);
            panel.add(addDeviceToAuthorizedButtons[i]);
            i++;


        }

        panel.setPreferredSize(new Dimension(400, 35*allDevices.getSize()));  //ESTE É O CORRETO A USAR


        panel.setFont(defaultFont);

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPanel.setPreferredSize(new Dimension(500, 300));
        JPanel addPanel = new JPanel(new BorderLayout());
        addPanel.add(scrollPanel, BorderLayout.CENTER);
        JPanel outherPanel = new JPanel(new BorderLayout());
        outherPanel.add(addPanel, BorderLayout.CENTER);
        outherPanel.setBackground(colorLightPink);

        UIManager.put("OptionPane.background", colorLightPink);
        UIManager.put("Panel.background", colorLightPink);

        JOptionPane dialog = new JOptionPane(outherPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE);
        dialog.setBackground(colorLightPink);
        JDialog dialogWindow = dialog.createDialog(null, "Please Select a Device to be Authorized to this Network ");
        dialogWindow.setVisible(true);
    }

    private void addAuthorizedDevice(Domain domain, Device device){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);

        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to authorize device " + device.getDeviceName() + " in network " + domain.getName() +"?        "),
                "Are you sure you want to authorize device " + device.getDeviceName() + " in network " + domain.getName() +"?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);


        if (result == JOptionPane.YES_OPTION) {
            if(!checkDeviceExists(new File(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml"), device.getDeviceName(), device.getDomain().getPath())){
                domain.getAuhtorizedDeviceList().addAuthorizedDeviceToXML(device, basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                domain.getAuhtorizedDeviceList().addAuthorizedIpsToXML(device, basePath + File.separator + "Network" + domain.getPath() + File.separator + "AuthorizedIps.xml");
                removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "AuthorizedIps.xml");
                domain.getAuhtorizedDeviceList().addDevice(device);
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, device.getDeviceName() + " authorized in network " + domain.getName()+ " successfully"), "Device authorized in network successfully", JOptionPane.INFORMATION_MESSAGE);
                cleanSecondaryWindows();
                authorizedDevicesSecondaryWindow(domain);
            }
            else {
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Device already Authorized in network " + domain.getName()+ "       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
                selectAddAuthorizedDevice();
            }

        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "No Device Authorized in network " + domain.getName()+ "       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            selectAddAuthorizedDevice();
        }


    }
    public static boolean checkDeviceExists(File file, String deviceName, String deviceDomain) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            NodeList deviceElements = doc.getElementsByTagName("Device");

            for (int i = 0; i < deviceElements.getLength(); i++) {
                Element deviceElement = (Element) deviceElements.item(i);
                String name = deviceElement.getElementsByTagName("Name").item(0).getTextContent();
                String domain = deviceElement.getElementsByTagName("Domain").item(0).getTextContent();

                if (name.equals(deviceName) && domain.equals(deviceDomain)) {
                    return true; // Device element with matching name and domain found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Device element not found
    }
    public static boolean checkGroupExists(File file, String groupName, String groupPath) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            NodeList deviceElements = doc.getElementsByTagName("group");

            for (int i = 0; i < deviceElements.getLength(); i++) {
                Element deviceElement = (Element) deviceElements.item(i);
                String name = deviceElement.getElementsByTagName("Name").item(0).getTextContent();
                String path = deviceElement.getElementsByTagName("Path").item(0).getTextContent();

                if (name.equals(groupName) && path.equals(groupPath)) {
                    return true; // Device element with matching name and domain found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Device element not found
    }
    public static boolean checkDomainExists(File file, String domainName, String domainPath) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            NodeList deviceElements = doc.getElementsByTagName("domain");

            for (int i = 0; i < deviceElements.getLength(); i++) {
                Element deviceElement = (Element) deviceElements.item(i);
                String name = deviceElement.getElementsByTagName("Name").item(0).getTextContent();
                String path = deviceElement.getElementsByTagName("Path").item(0).getTextContent();

                if (name.equals(domainName) && path.equals(domainPath)) {
                    return true; // Device element with matching name and domain found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Device element not found
    }

    private void selectRemoveAuthorizedDevice(Domain domain){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //removeDeviceFromAuthorizedButtons = new JButton[domain.getAuthorizedDevicesList().getSize()];  //exemplo somewhat correto
        removeDeviceFromAuthorizedButtons = new JButton[domain.getAuhtorizedDeviceList().getSize()]; //só pra ver o funcionamento

        int i = 0;
        for (Device device :domain.getAuhtorizedDeviceList().getDevices()) {     //domain.getAuthorizedDevicesList().getDevices()  //exemplo correto
            removeDeviceFromAuthorizedButtons[i] = new JButton(device.getDeviceName()+ " - " + device.getDomain().getPath());
            removeDeviceFromAuthorizedButtons[i].setFont(buttonFont);
            removeDeviceFromAuthorizedButtons[i].setActionCommand(String.valueOf(i));
            removeDeviceFromAuthorizedButtons[i].addActionListener(this);
            removeDeviceFromAuthorizedButtons[i].putClientProperty("Device", device);
            panel.add(removeDeviceFromAuthorizedButtons[i]);
            i++;


        }

        //panel.setPreferredSize(new Dimension(400, 35*domain.getAuthorizedDevicesList().getSize()));  //ESTE É O CORRETO A USAR
        panel.setPreferredSize(new Dimension(400, 35*domain.getAuhtorizedDeviceList().getSize()));

        panel.setFont(defaultFont);

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPanel.setPreferredSize(new Dimension(500, 300));
        JPanel removePanel = new JPanel(new BorderLayout());
        removePanel.add(scrollPanel, BorderLayout.CENTER);
        JPanel outherPanel = new JPanel(new BorderLayout());
        outherPanel.add(removePanel, BorderLayout.CENTER);
        outherPanel.setBackground(colorRed);

        UIManager.put("OptionPane.background", colorRed);
        UIManager.put("Panel.background", colorRed);

        JOptionPane dialog = new JOptionPane(outherPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE);
        dialog.setBackground(colorRed);
        JDialog dialogWindow = dialog.createDialog(null, "Please Select a Device to be De-Authorized to this Network ");
        dialogWindow.setVisible(true);


    }

    private void removeAuthorizedDevice(Domain domain, Device device){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);

        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to de-authorize device " + device.getDeviceName() + " in network " + domain.getName() +"?        "),
                "Are you sure you want to de-authorize device " + device.getDeviceName() + " in network " + domain.getName() +"?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);


        if (result == JOptionPane.YES_OPTION) {
            removeAuthorizedDeviceFromXML(domain.getPath() ,domain, device);
            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
            removeAuthorizedIpsFromXML(domain.getPath(),domain, device);
            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator +  "AuthorizedIps.xml");
            auxClassGui.readDatabase();
            domain.getAuhtorizedDeviceList().setDeviceList((auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getAuhtorizedDeviceList().getDevices()));
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, device.getDeviceName() + " de-authorized in network " + domain.getName()+ " successfully"), "Device de-authorized in network successfully", JOptionPane.INFORMATION_MESSAGE);
            cleanSecondaryWindows();
            authorizedDevicesSecondaryWindow(domain);


        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "No Device De-Authorized in network " + domain.getName()+ "       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            selectRemoveAuthorizedDevice(domain);
        }

    }

    private void selectAddAuthorizedGroup(){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        GroupList allGroups = new GroupList();
        for (Domain auxDomain : domainList.getDomains()){
            for (Group auxGroup : auxDomain.getGroupList().getGroups()) {
                allGroups.addGroup(auxGroup);
            }
        }
        addGroupToAuthorizedButtons = new JButton[allGroups.getSize()];

        int i = 0;
        for (Group group :allGroups.getGroups()) {
            addGroupToAuthorizedButtons[i] = new JButton(group.getName()+ " - " + group.getPath());
            addGroupToAuthorizedButtons[i].setFont(buttonFont);
            addGroupToAuthorizedButtons[i].setActionCommand(String.valueOf(i));
            addGroupToAuthorizedButtons[i].addActionListener(this);
            addGroupToAuthorizedButtons[i].putClientProperty("Group", group);
            panel.add(addGroupToAuthorizedButtons[i]);
            i++;


        }

        panel.setPreferredSize(new Dimension(400, 35*allGroups.getSize()));  //ESTE É O CORRETO A USAR

        panel.setFont(defaultFont);

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPanel.setPreferredSize(new Dimension(500, 300));
        JPanel addPanel = new JPanel(new BorderLayout());
        addPanel.add(scrollPanel, BorderLayout.CENTER);
        JPanel outherPanel = new JPanel(new BorderLayout());
        outherPanel.add(addPanel, BorderLayout.CENTER);
        outherPanel.setBackground(colorLightPink);

        UIManager.put("OptionPane.background", colorLightPink);
        UIManager.put("Panel.background", colorLightPink);

        JOptionPane dialog = new JOptionPane(outherPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE);
        dialog.setBackground(colorLightPink);
        JDialog dialogWindow = dialog.createDialog(null, "Please Select a Group to be Authorized to this Network ");
        dialogWindow.setVisible(true);

    }

    private void addAuthorizedGroup(Domain domain, Group group){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);

        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to authorize group " + group.getName() + " in network " + domain.getName() +"?        "),
                "Are you sure you want to authorize group " + group.getName() + " in network " + domain.getName() +"?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);


        if (result == JOptionPane.YES_OPTION) {
            if(!domain.getAuthorizedGroupList().verifyPath(group.getPath())){
                domain.getAuthorizedGroupList().addAuthorizedGroupToXML(group,basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                domain.getAuthorizedGroupList().addGroup(group);
                for(Device device: group.getDeviceList().getDevices()){
                    if(!checkDeviceExists(new File(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml"), device.getDeviceName(),device.getDomain().getPath())){
                        domain.getAuhtorizedDeviceList().addAuthorizedDeviceToXML(device, basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                        removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                        domain.getAuhtorizedDeviceList().addAuthorizedIpsToXML(device, basePath + File.separator + "Network" + domain.getPath() + File.separator + "AuthorizedIps.xml");
                        removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "AuthorizedIps.xml");
                        domain.getAuhtorizedDeviceList().addDevice(device);
                    }
                }
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, group.getName() + " authorized in network " + domain.getName()+ " successfully"), "Group authorized in network successfully", JOptionPane.INFORMATION_MESSAGE);
                cleanSecondaryWindows();
                authorizedGroupsSecondaryWindow(domain);
            }
            else{
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, group.getName() + " already authorized in network " + domain.getName()), "Group authorized in network successfully", JOptionPane.INFORMATION_MESSAGE);
                cleanSecondaryWindows();
                authorizedGroupsSecondaryWindow(domain);
            }


        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "No Group Authorized in network " + domain.getName()+ "       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            selectAddAuthorizedGroup();
        }

    }

    private void selectRemoveAuthorizedGroup(Domain domain){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //removeGroupFromAuthorizedButtons = new JButton[domain.getAuthorizedGroupsList().getSize()];  //exemplo somewhat correto
        removeGroupFromAuthorizedButtons = new JButton[domain.getAuthorizedGroupList().getSize()]; //só pra ver o funcionamento

        int i = 0;
        for (Group group :domain.getAuthorizedGroupList().getGroups()) {     //domain.getAuthorizedGroupsList().getGroups()  //exemplo correto
            removeGroupFromAuthorizedButtons[i] = new JButton(group.getName() + " - " + group.getPath());
            removeGroupFromAuthorizedButtons[i].setFont(buttonFont);
            removeGroupFromAuthorizedButtons[i].setActionCommand(String.valueOf(i));
            removeGroupFromAuthorizedButtons[i].addActionListener(this);
            removeGroupFromAuthorizedButtons[i].putClientProperty("Group", group);
            panel.add(removeGroupFromAuthorizedButtons[i]);
            i++;


        }

        //panel.setPreferredSize(new Dimension(400, 35*domain.getAuthorizedGroupsList().getSize()));  //ESTE É O CORRETO A USAR
        panel.setPreferredSize(new Dimension(400, 35*domain.getAuthorizedGroupList().getSize()));

        panel.setFont(defaultFont);

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPanel.setPreferredSize(new Dimension(500, 300));
        JPanel removePanel = new JPanel(new BorderLayout());
        removePanel.add(scrollPanel, BorderLayout.CENTER);
        JPanel outherPanel = new JPanel(new BorderLayout());
        outherPanel.add(removePanel, BorderLayout.CENTER);
        outherPanel.setBackground(colorRed);

        UIManager.put("OptionPane.background", colorRed);
        UIManager.put("Panel.background", colorRed);

        JOptionPane dialog = new JOptionPane(outherPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE);
        dialog.setBackground(colorRed);
        JDialog dialogWindow = dialog.createDialog(null, "Please Select a Group to be De-Authorized to this Network ");
        dialogWindow.setVisible(true);


    }

    private void removeAuthorizedGroup(Domain domain, Group group){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        auxClassGui.readDatabase();
        group.setDeviceList(auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getAuthorizedGroupList().getGroup(group.getPath()).getDeviceList());

        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to de-authorize group " + group.getName() + " in network " + domain.getName() +"?        "),
                "Are you sure you want to de-authorize group " + group.getName() + " in network " + domain.getName() +"?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);


        if (result == JOptionPane.YES_OPTION) {
            ArrayList<String> ipsToRemove = removeAuthorizedDeviceFromGroupFromXML(domain.getPath(), domain,group);
            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
            removeAuthorizedDeviceIPFromGroupFromXML(domain.getPath(), ipsToRemove);
            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "AuthorizedIps.xml");
            removeAuthorizedGroupFromXML(domain.getPath(),domain, group);
            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
            domain.getAuthorizedGroupList().removeGroup(group);
            auxClassGui.readDatabase();
            domain.getAuthorizedGroupList().setGroupList((auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getAuthorizedGroupList().getGroups()));
            domain.getAuhtorizedDeviceList().setDeviceList((auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getAuhtorizedDeviceList().getDevices()));
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, group.getName() + " de-authorized in network " + domain.getName()+ " successfully"), "Group de-authorized in network successfully", JOptionPane.INFORMATION_MESSAGE);
            cleanSecondaryWindows();
            authorizedGroupsSecondaryWindow(domain);


        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "No Group De-Authorized in network " + domain.getName()+ "       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            selectRemoveAuthorizedGroup(domain);
        }

    }
    private void removeAuthorizedGroupFromXML(String domainPath, Domain domain, Group group) {
        String xmlFilePath = basePath + File.separator + "Network" + domainPath + File.separator + domain.getName() + ".xml";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));
            NodeList domainList = document.getElementsByTagName("group");

            for (int i = 0; i < domainList.getLength(); i++) {
                Node domainNode = domainList.item(i);
                if (domainNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element domainElement = (Element) domainNode;
                    String name = domainElement.getElementsByTagName("Name").item(0).getTextContent();
                    String Path = domainElement.getElementsByTagName("Path").item(0).getTextContent();

                    if (name.equals(group.getName())) {
                        // Remove the domain node
                        if(Path.equals(group.getPath())){
                            domainNode.getParentNode().removeChild(domainNode);
                        }
                    }

                }
            }
            // Save the modified document back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("Group removed successfully.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            System.out.println("Failed to remove the domain: " + e.getMessage());
        }
    }
    private void removeAuthorizedNetworkFromXML(String domainPath, Domain domain, Domain authDomain) {
        String xmlFilePath = basePath + File.separator + "Network" + domainPath + File.separator + domain.getName() + ".xml";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));
            NodeList domainList = document.getElementsByTagName("domain");

            for (int i = 0; i < domainList.getLength(); i++) {
                Node domainNode = domainList.item(i);
                if (domainNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element domainElement = (Element) domainNode;
                    String Path = domainElement.getElementsByTagName("Path").item(0).getTextContent();
                    if(Path.startsWith(authDomain.getPath())){
                        domainNode.getParentNode().removeChild(domainNode);
                        break;
                    }
                }
            }
            // Save the modified document back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("Device removed successfully.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            System.out.println("Failed to remove the domain: " + e.getMessage());
        }
    }

    private void removeAuthorizedNetworkFromAuthorizedNetworkXML(String domainPath, Domain domain, Domain authDomain) {
        String xmlFilePath = basePath + File.separator + "Network" + domainPath + File.separator + "AuthorizedDomains.xml";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));
            NodeList domainList = document.getElementsByTagName("domain");

            for (int i = 0; i < domainList.getLength(); i++) {
                Node domainNode = domainList.item(i);
                if (domainNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element domainElement = (Element) domainNode;
                    String Path = domainElement.getElementsByTagName("Path").item(0).getTextContent();
                    if(Path.startsWith(authDomain.getPath())){
                        domainNode.getParentNode().removeChild(domainNode);
                        break;
                    }
                }
            }
            // Save the modified document back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("Device removed successfully.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            System.out.println("Failed to remove the domain: " + e.getMessage());
        }
    }

    private void selectAddAuthorizedNetwork(){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        networkDomainList = getNetworkInfo(domainList);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        addDomainObjects(root, networkDomainList);
        tree = new JTree(root);
        tree.setShowsRootHandles(true);
        tree.setRootVisible(false);
        add(new JScrollPane(tree));


        selectedLabel = new JLabel();
        add(selectedLabel, BorderLayout.SOUTH);
        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    String domainName = selectedNode.getUserObject().toString();
                    //System.out.println(domainName + " network button pressed");
                    authDomain = getDomainFromPath(selectedNode);

                }
            }
        });

        DefaultTreeCellRenderer cellRenderer = new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Component component = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                component.setFont(titleFont);
                return component;
            }
        };
        tree.setCellRenderer(cellRenderer);
        int iconSize = 10;
        Image resizedIcon = imageIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        Image resizedOpenIcon = imageOpenIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedIcon);
        imageOpenIcon = new ImageIcon(resizedOpenIcon);
        cellRenderer.setLeafIcon(imageIcon);
        cellRenderer.setOpenIcon(imageOpenIcon);
        cellRenderer.setClosedIcon(imageIcon);

        JPanel treePanel = new JPanel(new BorderLayout());
        JScrollPane treeScrollPane = new JScrollPane(tree);
        treeScrollPane.setPreferredSize(new Dimension(600, 600)); // Set preferred size here
        treePanel.add(treeScrollPane, BorderLayout.CENTER);
        panel.add(treePanel, BorderLayout.CENTER);




        panel.setPreferredSize(new Dimension(400, 35*domainList.getSize()));  //ESTE É O CORRETO A USAR

        panel.setFont(defaultFont);

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPanel.setPreferredSize(new Dimension(500, 300));
        JPanel addPanel = new JPanel(new BorderLayout());
        addPanel.add(scrollPanel, BorderLayout.CENTER);
        JPanel outherPanel = new JPanel(new BorderLayout());
        outherPanel.add(addPanel, BorderLayout.CENTER);
        outherPanel.setBackground(colorLightPink);

        UIManager.put("OptionPane.background", colorLightPink);
        UIManager.put("Panel.background", colorLightPink);

        JOptionPane dialog = new JOptionPane(outherPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        dialog.setBackground(colorLightPink);
        JDialog dialogWindow = dialog.createDialog(null, "Please Select a Network to be Authorized to this Network ");
        dialogWindow.setVisible(true);

        int result = (Integer) dialog.getValue();

        System.out.println("authDomain: " + authDomain);
        System.out.println("currentDomain: " + currentDomain);


        if (result == JOptionPane.OK_OPTION) {
            if(authDomain != null && !authDomain.getPath().equals(currentDomain.getPath())){
                System.out.println(authDomain.getName() + " accessed");
                cleanAllDialogWindows();
                addAuthorizedNetwork(currentDomain, authDomain);


            } else {
                JOptionPane.showMessageDialog(this, getMessagePanel(defaultFont, "Please select a valid Network"),"Try Again", JOptionPane.ERROR_MESSAGE);
                selectAddAuthorizedNetwork();
                System.out.println("Domain not found");
            }
        }

    }

    private void addAuthorizedNetwork(Domain domain, Domain authDomain){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);

        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to authorize network " + authDomain.getPath() + " in network " + domain.getName() +"?        "),
                "Are you sure you want to authorize network " + authDomain.getName() + " in network " + domain.getName() +"?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);


        if (result == JOptionPane.YES_OPTION) {
            if(!domain.getAuthorizedDomainList().verifyPath(authDomain.getPath())){
                auxClassGui.readDatabase();
                for (Domain allDomain: auxClassGui.getDomainList().getDomains()){
                    if (allDomain.getPath().startsWith(authDomain.getPath())){
                        if(!domain.getAuthorizedDomainList().verifyPath(allDomain.getPath())){
                            domain.getAuthorizedDomainList().addAuthorizedDomainToXML(allDomain,basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                            domain.getAuthorizedDomainList().addAuthorizedDomainToAuthorizedDomainsXML(allDomain,basePath + File.separator + "Network" + domain.getPath() + File.separator + "AuthorizedDomains.xml");
                            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "AuthorizedDomains.xml");
                            domain.getAuthorizedDomainList().addDomain(allDomain);
                            for(Device device: allDomain.getDeviceList().getDevices()){
                                if(!checkDeviceExists(new File(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml"), device.getDeviceName(),device.getDomain().getPath())){
                                    domain.getAuhtorizedDeviceList().addAuthorizedDeviceToXML(device, basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                                    domain.getAuhtorizedDeviceList().addAuthorizedIpsToXML(device, basePath + File.separator + "Network" + domain.getPath() + File.separator + "AuthorizedIps.xml");
                                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "AuthorizedIps.xml");
                                    domain.getAuhtorizedDeviceList().addDevice(device);
                                }
                            }
                            for(Group group: allDomain.getGroupList().getGroups()){
                                if(!checkGroupExists(new File(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml"), group.getName(),group.getPath())){
                                    domain.getAuthorizedGroupList().addAuthorizedGroupToXML(group, basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                                    domain.getAuthorizedGroupList().addGroup(group);
                                }
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, authDomain.getName() + " authorized in network " + domain.getName()+ " successfully"), "Domain authorized in network successfully", JOptionPane.INFORMATION_MESSAGE);
                resetAllWindows();
                cleanSecondaryWindows();
                authorizedNetworksSecondaryWindow(domain);
            }
            else{
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, authDomain.getName() + " already authorized in network " + domain.getName()), "Domain already authorized in network successfully", JOptionPane.INFORMATION_MESSAGE);
                resetAllWindows();
                cleanSecondaryWindows();
                authorizedNetworksSecondaryWindow(domain);
            }
        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "No Network Authorized in network " + domain.getName()+ "       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            resetAllWindows();
            cleanSecondaryWindows();
            authorizedNetworksSecondaryWindow(domain);

        }


    }

    private void selectRemoveAuthorizedNetwork(Domain domain){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //removeNetworkFromAuthorizedButtons = new JButton[domain.getAuthorizedDomainsList().getSize()];  //exemplo somewhat correto
        removeNetworkFromAuthorizedButtons = new JButton[domain.getAuthorizedDomainList().getSize()]; //só pra ver o funcionamento

        int i = 0;
        for (Domain authDomain :domain.getAuthorizedDomainList().getDomains()) {     //domain.getAuthorizedDomainsList().getDomains()  //exemplo futuramente correto
            removeNetworkFromAuthorizedButtons[i] = new JButton(authDomain.getName() + " - " + authDomain.getPath());
            removeNetworkFromAuthorizedButtons[i].setFont(buttonFont);
            removeNetworkFromAuthorizedButtons[i].setActionCommand(String.valueOf(i));
            removeNetworkFromAuthorizedButtons[i].addActionListener(this);
            removeNetworkFromAuthorizedButtons[i].putClientProperty("Domain", authDomain);
            panel.add(removeNetworkFromAuthorizedButtons[i]);
            i++;


        }
        //panel.setPreferredSize(new Dimension(400, 35*domain.getAuthorizedDomainsList().getSize()));  //ESTE É O CORRETO A USAR
        panel.setPreferredSize(new Dimension(400, 35*domain.getAuthorizedDomainList().getSize()));

        panel.setFont(defaultFont);

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollPanel.setPreferredSize(new Dimension(500, 300));
        JPanel removePanel = new JPanel(new BorderLayout());
        removePanel.add(scrollPanel, BorderLayout.CENTER);
        JPanel outherPanel = new JPanel(new BorderLayout());
        outherPanel.add(removePanel, BorderLayout.CENTER);
        outherPanel.setBackground(colorRed);

        UIManager.put("OptionPane.background", colorRed);
        UIManager.put("Panel.background", colorRed);

        JOptionPane dialog = new JOptionPane(outherPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE);
        dialog.setBackground(colorRed);
        JDialog dialogWindow = dialog.createDialog(null, "Please Select a Network to be De-Authorized to this Network ");
        dialogWindow.setVisible(true);
    }

    private void removeAuthorizedNetwork(Domain domain, Domain authDomain){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);

        int result = JOptionPane.showConfirmDialog(null, getMessagePanel(defaultFont, "Are you sure you want to de-authorize network " + authDomain.getName() + " in network " + domain.getName() +"?        "),
                "Are you sure you want to de-authorize network " + authDomain.getName() + " in network " + domain.getName() +"?        ", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);


        if (result == JOptionPane.YES_OPTION) {
            if(domain.getAuthorizedDomainList().verifyPath(authDomain.getPath())){
                auxClassGui.readDatabase();
                for (Domain allDomain: auxClassGui.getDomainList().getDomains()){
                    if (allDomain.getPath().startsWith(authDomain.getPath())){
                        if(domain.getAuthorizedDomainList().verifyPath(allDomain.getPath())){
                            for(Group group: allDomain.getGroupList().getGroups()){
                                if(checkGroupExists(new File(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml"), group.getName(),group.getPath())){
                                    removeAuthorizedGroupFromXML(domain.getPath(), domain, group);
                                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                                }
                            }
                            for(Device device: allDomain.getDeviceList().getDevices()){
                                if(checkDeviceExists(new File(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml"), device.getDeviceName(),device.getDomain().getPath())){
                                    removeAuthorizedDeviceFromXML(domain.getPath(),domain, device);
                                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                                    removeAuthorizedIpsFromXML(domain.getPath(),domain, device);
                                    removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator +  "AuthorizedIps.xml");

                                }
                            }
                            removeAuthorizedNetworkFromXML(domain.getPath(), domain, allDomain);
                            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml");
                            removeAuthorizedNetworkFromAuthorizedNetworkXML(domain.getPath(), domain, allDomain);
                            removeEmptyLinesFromXML(basePath + File.separator + "Network" + domain.getPath() + File.separator + "AuthorizedDomains.xml");
                            auxClassGui.readDatabase();
                            domain.getAuthorizedGroupList().setGroupList((auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getAuthorizedGroupList().getGroups()));
                            domain.getAuhtorizedDeviceList().setDeviceList((auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getAuhtorizedDeviceList().getDevices()));
                            domain.getAuthorizedDomainList().setDomainList((auxClassGui.getDomainList().getDomainByPath(domain.getPath()).getAuthorizedDomainList().getDomains()));

                        }
                    }
                }
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, authDomain.getName() + " de-authorized in network " + domain.getName()+ " successfully"), "Network de-authorized in network successfully", JOptionPane.INFORMATION_MESSAGE);
                resetAllWindows();
                cleanSecondaryWindows();
                authorizedNetworksSecondaryWindow(domain);
            }
            else{
                JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, authDomain.getName() + " already authorized in network " + domain.getName()), "Domain already authorized in network successfully", JOptionPane.INFORMATION_MESSAGE);
                resetAllWindows();
                cleanSecondaryWindows();
                authorizedNetworksSecondaryWindow(domain);
            }

        }
        else {
            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "No Network De-Authorized in network " + domain.getName()+ "       "), "Exiting", JOptionPane.INFORMATION_MESSAGE);
            resetAllWindows();
            cleanSecondaryWindows();
            authorizedNetworksSecondaryWindow(domain);
        }


    }


    private void deviceInformation(Device device, Color color){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        String title = "Device Information";
        String name;
        String pass;
        String ip;
        String ipv6;
        //String group;
        String domainName;

        if (device != null){
            //fazer o suposto
            title = device.getDeviceName() +" Information";
            name = device.getDeviceName();
            pass = device.getPassword();
            if (device.getIPAddress() != null)
                ip = device.getIPAddress();
            else
                ip = "--------";
            if (device.getIPv6Address()!= null)
                ipv6 = device.getIPv6Address();
            else
                ipv6 = "--------";
            /*if(device.getGroup() != null)
                group = device.getGroup().getName();
            else
                group = "--------";*/
            domainName = device.getDomain().getPath();
        }
        else{
            title = "Device Information";
            name = "Device 1000" ;
            pass = "abcd1234";
            ip = "192.168.1.1000";
            ipv6 = "2001:0db8:85a3:0000:0000:8a2e:0370:7334";
            //group = "Group1000";
            domainName = "Piso1000";
        }

        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.add(new JLabel(" ")).setFont(defaultFont);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("      Name:                "+ name)).setFont(defaultFont);
        panel.add(new JLabel(" ")).setFont(defaultFont);
        panel.add(new JLabel("      Password:          "+pass)).setFont(defaultFont);
        panel.add(new JLabel(" ")).setFont(defaultFont);

        //panel.add(new JLabel("      Group:                " + group)).setFont(defaultFont);
        panel.add(new JLabel(" ")).setFont(defaultFont);
        panel.add(new JLabel("      IPv4 address:     "+ip)).setFont(defaultFont);
        panel.add(new JLabel(" ")).setFont(defaultFont);
        panel.add(new JLabel("      IPv6 address:     "+ipv6)).setFont(defaultFont);
        panel.add(new JLabel(" ")).setFont(defaultFont);
        panel.add(new JLabel("      Domain:              " + domainName)).setFont(defaultFont);
        panel.setPreferredSize(new Dimension(800, 350));
        panel.setFont(defaultFont);

        UIManager.put("OptionPane.background", color);
        UIManager.put("Panel.background", color);

        JOptionPane dialog = new JOptionPane(panel, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialogWindow = dialog.createDialog(null, title);
        dialogWindow.setVisible(true);
    }

    private void groupInformation(Group group, Color color){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        String title = "Group Information";
        String name;
        String domainName;

        if (group != null){
            //fazer o suposto
            title = group.getName() +" Information";
            name = group.getName();
            domainName = group.getDomain().getPath();

        }
        else{
            title = "Group Information";
            name = "Group ----" ;
            domainName = "Piso----";

        }

        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.add(new JLabel(" ")).setFont(defaultFont);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("      Name:                "+ name)).setFont(defaultFont);
        panel.add(new JLabel(" ")).setFont(defaultFont);
        panel.add(new JLabel("      Domain:              " + domainName)).setFont(defaultFont);
        panel.setPreferredSize(new Dimension(600, 200));
        panel.setFont(defaultFont);

        UIManager.put("OptionPane.background", color);
        UIManager.put("Panel.background", color);

        JOptionPane dialog = new JOptionPane(panel, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialogWindow = dialog.createDialog(null, title);
        dialogWindow.setVisible(true);
    }

    private void domainInformation(Domain domain, Color color){
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        String title = "Domain Information";
        String name;
        String domainName;

        if (domain != null){
            //fazer o suposto
            title = domain.getName() +" Information";
            name = domain.getName();
            domainName = domain.getPath();

        }
        else{
            title = "Domain Information";
            name = "domain----" ;
            domainName = "Domain----";

        }

        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.add(new JLabel(" ")).setFont(defaultFont);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("      Name:                "+ name)).setFont(defaultFont);
        panel.add(new JLabel(" ")).setFont(defaultFont);
        panel.add(new JLabel("      Domain:              " + domainName)).setFont(defaultFont);
        panel.setPreferredSize(new Dimension(600, 200));
        panel.setFont(defaultFont);

        UIManager.put("OptionPane.background", color);
        UIManager.put("Panel.background", color);

        JOptionPane dialog = new JOptionPane(panel, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialogWindow = dialog.createDialog(null, title);
        dialogWindow.setVisible(true);
    }


    /*private JPanel configureDevice(Device device){   //ERA PARA EVITAR REPETICAÇÃO DE CÓDIGO, MAS NÃO É BOA IDEIA
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JPanel panel = new JPanel();
        JTextField name = new JTextField(5);
        JTextField pass = new JTextField(5);
        JTextField group = new JTextField(5);
        JTextField ip = new JTextField(5);
        JTextField ipv6 = new JTextField(5);
        JTextField domainName = new JTextField(5);

        if(device == null){
            name.setText("Device" + (deviceList.getSize() + 1));
            pass.setText("abcd1234");
            ip.setText("192.168.1." + (deviceList.getSize() + 1));//will have to
            domainName.setText("Subrede" + domainList.getSize());

        }

        else{
            name.setText(device.getDeviceName());
            pass.setText(device.getPassword());
            ip.setText(device.getIPAddress());
            ipv6.setText(device.getIPv6Address());
            domainName.setText(device.getDomain().getName());
            if(device.getGroup() != null)
                group.setText(device.getGroup().getName());
            else
                group.setText("--------");


        }

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Name:")).setFont(defaultFont);
        panel.add(name).setFont(defaultFont);
        panel.add(new JLabel("Password:")).setFont(defaultFont);
        panel.add(pass).setFont(defaultFont);
        panel.add(Box.createVerticalStrut(15)); // a spacer
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // fixed height spacer
        panel.add(new JLabel("Group:")).setFont(defaultFont);
        panel.add(group).setFont(defaultFont);
        panel.add(new JLabel("IPv4 address:")).setFont(defaultFont);
        panel.add(ip).setFont(defaultFont);
        panel.add(new JLabel("IPv6 address:")).setFont(defaultFont);
        panel.add(ipv6).setFont(defaultFont);
        panel.add(new JLabel("Domain:")).setFont(defaultFont);
        panel.add(domainName).setFont(defaultFont);
        panel.setPreferredSize(new Dimension(800, 350));
        panel.setFont(defaultFont);
        return panel;
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();

        if (source == addNetworkButton) {
            addNetwork();
        }

        if(source == removeNetworkButton){
            selectRemoveNetwork();
        }

        if(source == removeAllNetworksButton){
            removeAllNetworks();
        }

        if (source == closeSecondaryWindowButton) {
            cleanSecondaryWindows();
            allBlankSecondaryWindow();
            mainPanel.revalidate();
            mainPanel.repaint();
        }
        if (source == addDomainButton){
            addDomain(currentDomain, getLowerDomainList(currentDomain.getPath(), currentDomain.getLowerDomains()));
        }

        if (source == removeDomainButton){
            removeDomain(currentDomain, getLowerDomainList(currentDomain.getPath(), currentDomain.getLowerDomains()));
        }

        if (source == showDevices){
            cleanSecondaryWindows();
            deviceSecondaryWindow(currentDomain);
        }

        if (source == showGroups){
            cleanSecondaryWindows();
            groupsSecondaryWindow(currentDomain);
        }

        if (source == showAuthorizedDevices){
            cleanSecondaryWindows();
            //mainPanel.remove(0);
            authorizedDevicesSecondaryWindow(currentDomain);
        }

        if(source == showAuthorizedGroups){
            cleanSecondaryWindows();
            //mainPanel.remove(0);
            authorizedGroupsSecondaryWindow(currentDomain);
        }

        if(source == showAuthorizedNetworks){
            cleanSecondaryWindows();
            //mainPanel.remove(0);
            authorizedNetworksSecondaryWindow(currentDomain);
        }

        if (source == goBackButton){
            cleanSecondaryWindows();
            secondaryWindow(currentDomain);
            mainPanel.revalidate();
            mainPanel.repaint();
        }

        if (source == addDeviceButton){
            addDevice(currentDomain);
        }

        if (source == removeDeviceButton){
            selectRemoveDevice(currentDomain);
        }

        if (source == editDeviceButton){
            //editDevice(currentDomain);
            //deviceInformation(null);
            selectEditDevice(currentDomain);
        }

        if (source == addGroupButton){
            addGroup(currentDomain);
        }

        if (source == removeGroupButton){
            //removeGroup(currentDomain);
            selectRemoveGroup(currentDomain);
        }

        if(source == goBackButtonToGroup){
            cleanSecondaryWindows();
            groupsSecondaryWindow(currentDomain);
            mainPanel.revalidate();
            mainPanel.repaint();
        }

        if(source == addDeviceToGroupButton){
            selectAddToGroup(currentDomain, currentGroup);
        }

        if(source == removeDeviceFromGroupButton){
            selectRemoveFromGroup(currentDomain, currentGroup);
        }

        if (source == addAuthorizedDeviceButton){
            //addAuthorizedDevice(currentDomain);
            selectAddAuthorizedDevice();
        }

        if (source == removeAuthorizedDeviceButton){
            selectRemoveAuthorizedDevice(currentDomain);
        }

        if (source == addAuthorizedGroupButton){
            selectAddAuthorizedGroup();
        }

        if (source == removeAuthorizedGroupButton){
            selectRemoveAuthorizedGroup(currentDomain);
        }

        if (source == addAuthorizedNetworkButton){
            selectAddAuthorizedNetwork();
        }

        if (source == removeAuthorizedNetworkButton){
            selectRemoveAuthorizedNetwork(currentDomain);
        }

        if(removeNetworkButtons != null){
            for (int i = 0; i < removeNetworkButtons.length ; i++) {
                if(removeNetworkButtons[i] != null){
                    if (source == removeNetworkButtons[i]) {
                        System.out.println(i + " remove network button pressed");
                        Domain domain = getNetworkInfo(domainList).getDomains().get(i);
                        cleanAllDialogWindows();
                        removeNetwork(domain);
                    }
                }
            }
        }


        if( deviceButtons!= null){
            for (int i = 0; i < deviceButtons.length ; i++) {
                if(deviceButtons[i] != null){
                    if (source == deviceButtons[i]) {
                        System.out.println(i + " device button pressed");
                        Device device = (Device) deviceButtons[i].getClientProperty("Device");
                        deviceInformation(device, colorLightGray);
                        cleanAllDialogWindows();
                        mainPanel.revalidate();
                        mainPanel.repaint();

                    }
                }

            }
        }

        if( editDeviceButtons != null){
            for (int i = 0; i < editDeviceButtons.length ; i++) {
                if(editDeviceButtons[i] != null){
                    if (source == editDeviceButtons[i]) {
                        System.out.println(i + " edit device button pressed");
                        Device device = (Device)editDeviceButtons[i].getClientProperty("Device");
                        cleanAllDialogWindows();
                        editDevice(device);
                        mainPanel.revalidate();
                        mainPanel.repaint();

                    }
                }

            }
        }

        if( removeDeviceButtons != null){
            for (int i = 0; i < removeDeviceButtons.length ; i++) {
                if(removeDeviceButtons[i] != null){
                    if (source == removeDeviceButtons[i]) {
                        System.out.println(i + " remove device button pressed");
                        Device device = (Device)removeDeviceButtons[i].getClientProperty("Device");
                        cleanAllDialogWindows();
                        removeDevice(currentDomain, device);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    }
                }

            }
        }


        if(groupButtons != null){
            for (int i = 0; i < groupButtons.length ; i++) {
                if(groupButtons[i] != null){
                    if (source == groupButtons[i]) {
                        System.out.println(i + " group button pressed");
                        Group group = (Group) groupButtons[i].getClientProperty("Group");
                        currentGroup = group;
                        cleanSecondaryWindows();
                        oneGroupSecondaryWindow(group);
                        mainPanel.revalidate();
                        mainPanel.repaint();

                    }
                }

            }
        }

        if(deviceOfGroupButtons != null){
            for (int i = 0; i < deviceOfGroupButtons.length ; i++) {
                if(deviceOfGroupButtons[i] != null){
                    if (source == deviceOfGroupButtons[i]) {
                        System.out.println(i + " device button of Group pressed /");
                        Device device = (Device) deviceOfGroupButtons[i].getClientProperty("Device");
                        //currentGroup = device.getGroup();
                        deviceInformation(device, colorLightGray);
                        cleanAllDialogWindows();
                        //cleanSecondaryWindows();
                        //oneGroupSecondaryWindow(device.getGroup());
                        mainPanel.revalidate();
                        mainPanel.repaint();

                    }
                }

            }
        }


        if (removeGroupButtons!=null) {
            for (int i = 0; i < removeGroupButtons.length ; i++) {
                if(removeGroupButtons[i] != null){
                    if (source == removeGroupButtons[i]) {
                        System.out.println(i + " remove group button pressed");
                        Group group = (Group) groupButtons[i].getClientProperty("Group");
                        cleanAllDialogWindows();
                        removeGroup(currentDomain, group);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    }
                }
            }
        }

        if(addToGroupButtons != null){
            for (int i = 0; i < addToGroupButtons.length ; i++) {
                if(addToGroupButtons[i] != null){
                    if (source == addToGroupButtons[i]) {
                        System.out.println(i + " add to group button pressed");
                        Device device = (Device) addToGroupButtons[i].getClientProperty("Device");
                        cleanAllDialogWindows();
                        if (currentGroup.getDeviceList().verifyDeviceName(device.getDeviceName()) || currentGroup.getDeviceList().verifyIPAddress(device.getIPAddress()) || currentGroup.getDeviceList().verifyIPv6Address(device.getIPv6Address())){
                            JOptionPane.showMessageDialog(null,getMessagePanel(defaultFont, "Device already added"), "Device already added", JOptionPane.ERROR_MESSAGE);
                            cleanSecondaryWindows();
                            oneGroupSecondaryWindow(currentGroup);
                        }
                        else{
                            addToGroup(currentDomain, currentGroup, device);
                        }
                    }
                }
            }
        }

        if(removeFromGroupButtons != null){
            for (int i = 0; i < removeFromGroupButtons.length ; i++) {
                if(removeFromGroupButtons[i] != null){
                    if (source == removeFromGroupButtons[i]) {
                        System.out.println(i + " remove from group button pressed");
                        Device device = (Device) removeFromGroupButtons[i].getClientProperty("Device");
                        cleanAllDialogWindows();
                        removeFromGroup(currentDomain, currentGroup, device);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    }
                }
            }
        }

        if(authorizedDevicesButtons != null){
            for (int i = 0; i < authorizedDevicesButtons.length ; i++) {
                if(authorizedDevicesButtons[i] != null){
                    if (source == authorizedDevicesButtons[i]) {
                        System.out.println(i + " authorized device button pressed");
                        Device device = (Device) authorizedDevicesButtons[i].getClientProperty("Device");
                        deviceInformation(device, colorLightPink);
                        cleanAllDialogWindows();
                        mainPanel.revalidate();
                        mainPanel.repaint();

                    }
                }

            }
        }

        if(addDeviceToAuthorizedButtons != null){
            for (int i = 0; i < addDeviceToAuthorizedButtons.length ; i++) {
                if(addDeviceToAuthorizedButtons[i] != null){
                    if (source == addDeviceToAuthorizedButtons[i]) {
                        System.out.println(i + " add authorized device button pressed");
                        Device device = (Device)addDeviceToAuthorizedButtons[i].getClientProperty("Device");
                        cleanAllDialogWindows();
                        addAuthorizedDevice(currentDomain, device);
                    }
                }
            }
        }

        if(removeDeviceFromAuthorizedButtons != null){
            for (int i = 0; i < removeDeviceFromAuthorizedButtons.length ; i++) {
                if(removeDeviceFromAuthorizedButtons[i] != null){
                    if (source == removeDeviceFromAuthorizedButtons[i]) {
                        System.out.println(i + " remove authorized device button pressed");
                        Device device = (Device) removeDeviceFromAuthorizedButtons[i].getClientProperty("Device");
                        cleanAllDialogWindows();
                        removeAuthorizedDevice(currentDomain, device);
                    }
                }
            }
        }



        if(authorizedGroupsButtons != null){
            for (int i = 0; i < authorizedGroupsButtons.length ; i++) {
                if(authorizedGroupsButtons[i] != null){
                    if (source == authorizedGroupsButtons[i]) {
                        System.out.println(i + " authorized group button pressed");
                        Group group = (Group) authorizedGroupsButtons[i].getClientProperty("Group"); //novamente, é um exemplo
                        System.out.println(group.getName() + "  " + group.getPath());
                        groupInformation(group, colorLightPink);
                        mainPanel.revalidate();
                        mainPanel.repaint();

                    }
                }

            }
        }

        if(addGroupToAuthorizedButtons != null){
            for (int i = 0; i < addGroupToAuthorizedButtons.length ; i++) {
                if(addGroupToAuthorizedButtons[i] != null){
                    if (source == addGroupToAuthorizedButtons[i]) {
                        System.out.println(i + " add authorized group button pressed");
                        Group group = (Group) addGroupToAuthorizedButtons[i].getClientProperty("Group");
                        cleanAllDialogWindows();
                        addAuthorizedGroup(currentDomain, group);
                    }
                }
            }
        }

        if(removeGroupFromAuthorizedButtons != null){
            for (int i = 0; i < removeGroupFromAuthorizedButtons.length ; i++) {
                if(removeGroupFromAuthorizedButtons[i] != null){
                    if (source == removeGroupFromAuthorizedButtons[i]) {
                        System.out.println(i + " remove authorized group button pressed");
                        Group group = (Group) removeGroupFromAuthorizedButtons[i].getClientProperty("Group");cleanAllDialogWindows();
                        cleanAllDialogWindows();
                        removeAuthorizedGroup(currentDomain, group);
                    }
                }
            }
        }

        if(authorizedNetworksButtons != null){
            for (int i = 0; i < authorizedNetworksButtons.length ; i++) {
                if(authorizedNetworksButtons[i] != null){
                    if (source == authorizedNetworksButtons[i]) {
                        System.out.println(i + " authorized Network button pressed");
                        Domain domain = (Domain) authorizedNetworksButtons[i].getClientProperty("Domain"); //novamente, é um exemplo
                        System.out.println(domain.getName() + "  " + domain.getPath());
                        domainInformation(domain, colorLightPink);
                        mainPanel.revalidate();
                        mainPanel.repaint();

                    }
                }

            }
        }

        if(removeNetworkFromAuthorizedButtons != null){
            for (int i = 0; i < removeNetworkFromAuthorizedButtons.length ; i++) {
                if(removeNetworkFromAuthorizedButtons[i] != null){
                    if (source == removeNetworkFromAuthorizedButtons[i]) {
                        System.out.println(i + " remove authorized network button pressed");
                        Domain domain = (Domain) removeNetworkFromAuthorizedButtons[i].getClientProperty("Domain");            //maneira que será correta de fazer
                        cleanAllDialogWindows();
                        removeAuthorizedNetwork(currentDomain, domain);
                    }
                }
            }
        }

        /*if(source == goBackButtonAuthorized){  //if the authorized windows were in fullscreen mode, defucnt funcionality
            mainPanel.remove(0);
            defaultWindow();
            secondaryWindow(currentDomain);
            mainPanel.revalidate();
            mainPanel.repaint();
        }*/

    }

    private void cleanSecondaryWindows(){
        //mainPanel.remove(1); if there were 4 panels instead of 3
        //mainPanel.remove(1);
        mainPanel.remove(1);

    }

    private void resetAllWindows(){
        cleanSecondaryWindows();
        mainPanel.remove(0);
        defaultWindow();
        allBlankSecondaryWindow();
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void cleanAllDialogWindows(){
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof Dialog) {
                window.dispose();
            }
        }
    }



    public boolean verifyIPAddressFormat(String ip){

        final String IPV4_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

        Pattern pattern = Pattern.compile(IPV4_PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();


    }

    public boolean verifyIPv6AddressFormat(String ipv6){

        final String IPV6_PATTERN =
            "^([0-9a-fA-F]{1,4}:){7}([0-9a-fA-F]){1,4}$";

        Pattern pattern = Pattern.compile(IPV6_PATTERN);
        Matcher matcher = pattern.matcher(ipv6);
        return matcher.matches();
    }


    public JPanel getMessagePanel(Font font, String msg){  //to avoid code repetition
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        JPanel msgPanel = new JPanel();
        JLabel msgLabel = new JLabel(msg);
        msgLabel.setFont(font);
        msgPanel.add(msgLabel);
        msgPanel.setPreferredSize(new Dimension(15*msg.length(), 75));
        return msgPanel;
    }

    /*public void setColourOfdevices(){
        int i = 0;
        for (Device device : deviceList.getDevices()) {
            if (!currentDevice.equals(device)) {
                if (currentDevice.deviceIsAuthorized(device)) {
                    deviceButtons[i].setBackground(colorGreen);
                    deviceButtons[i].setForeground(Color.BLACK);
                } else {
                    deviceButtons[i].setBackground(colorRed);
                    deviceButtons[i].setForeground(Color.WHITE);
                }
            }
            i++;
        }
    }*/


    private static void deleteDirectory(Path directory) throws IOException {
        if (Files.isDirectory(directory)) {
            // Delete all files and subdirectories recursively
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(directory)) {
                for (Path entry : entries) {
                    deleteDirectory(entry);
                }
            }
        }

        // Delete the directory itself
        Files.delete(directory);
    }
    private static void removeDomainFromXML(String domainPath) {
        String xmlFilePath = basePath + File.separator + "Domains/Domains.xml";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));
            NodeList domainList = document.getElementsByTagName("Domain");

            for (int i = 0; i < domainList.getLength(); i++) {
                Node domainNode = domainList.item(i);
                if (domainNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element domainElement = (Element) domainNode;
                    String path = domainElement.getElementsByTagName("Path").item(0).getTextContent();

                    if (path.equals(domainPath)) {
                        // Remove the domain node
                        domainNode.getParentNode().removeChild(domainNode);
                    }
                    else if (path.startsWith(domainPath)) {
                        // Remove the domain node
                        domainNode.getParentNode().removeChild(domainNode);
                    }
                }
            }
            // Save the modified document back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("Domain removed successfully.");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            System.out.println("Failed to remove the domain: " + e.getMessage());
        }
    }


    private static ArrayList<String> removeDomainDeviceAndGroupGivenDomainPathFromXML(Domain domain, Domain targetDomain) {
        String xmlFilePath = basePath + File.separator + "Network" + domain.getPath() + File.separator + domain.getName() + ".xml";
        System.out.println(xmlFilePath);
        ArrayList<String> ipList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));

            NodeList domainList = document.getElementsByTagName("domain");
            if(domainList.getLength() != 0){
                for (int i = domainList.getLength() - 1; i >= 0; i--) {
                    Node domainNode = domainList.item(i);
                    if (domainNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element domainElement = (Element) domainNode;
                        String path = domainElement.getElementsByTagName("Path").item(0).getTextContent();
                        System.out.println(path);
                        System.out.println(domain.getPath());
                        if (path.startsWith(targetDomain.getPath())) {
                            // Remove the domain node
                            System.out.println(path + "1");
                            domainNode.getParentNode().removeChild(domainNode);
                        }
                        else if (path.equals(targetDomain.getPath())) {
                            // Remove the domain node
                            System.out.println(path + "2");
                            domainNode.getParentNode().removeChild(domainNode);
                        }
                    }
                }
            }
            NodeList groupList = document.getElementsByTagName("group");
            if(groupList.getLength() != 0){
                for (int i = groupList.getLength() - 1; i >= 0; i--) {
                    Node groupNode = groupList.item(i);
                    if (groupNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element groupElement = (Element) groupNode;
                        String path = groupElement.getElementsByTagName("Path").item(0).getTextContent();

                        if (path.startsWith(targetDomain.getPath())) {
                            // Remove the domain node
                            groupNode.getParentNode().removeChild(groupNode);
                        }
                    }
                }
            }
            NodeList deviceList = document.getElementsByTagName("Device");
            if(deviceList.getLength() != 0){
                for (int i = deviceList.getLength() - 1; i >= 0; i--) {
                    Node deviceNode = deviceList.item(i);
                    if (deviceNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element deviceElement = (Element) deviceNode;
                        String path = deviceElement.getElementsByTagName("Domain").item(0).getTextContent();
                        if (path.startsWith(targetDomain.getPath())) {
                            // Remove the domain node
                            deviceNode.getParentNode().removeChild(deviceNode);
                            Node ipNode = deviceElement.getElementsByTagName("ipAddress").item(0);
                            String ipText = ipNode.getTextContent();
                            Node ipv6Node = deviceElement.getElementsByTagName("ipv6Address").item(0);
                            String ipv6Text = ipv6Node.getTextContent();
                            ipList.add(ipText);
                            ipList.add(ipv6Text);
                        }
                        else if (path.equals(targetDomain.getPath())) {
                            // Remove the domain node
                            deviceNode.getParentNode().removeChild(deviceNode);
                        }
                    }
                }
            }
            // Save the modified document back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            System.out.println("Failed to remove the domain: " + e.getMessage());
        }
        return ipList;
    }
    private static void removeAllAuthorizedNetworkFromAuthorizedNetworkXML(Domain domain, Domain targetDomain) {
        String xmlFilePath = basePath + File.separator + "Network" + domain.getPath() + File.separator + "AuthorizedDomains.xml";
        System.out.println(xmlFilePath);
        ArrayList<String> ipList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));

            NodeList domainList = document.getElementsByTagName("domain");
            if(domainList.getLength() != 0){
                for (int i = domainList.getLength() - 1; i >= 0; i--) {
                    Node domainNode = domainList.item(i);
                    if (domainNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element domainElement = (Element) domainNode;
                        String path = domainElement.getElementsByTagName("Path").item(0).getTextContent();
                        System.out.println(path);
                        System.out.println(domain.getPath());
                        if (path.startsWith(targetDomain.getPath())) {
                            // Remove the domain node
                            System.out.println(path + "1");
                            domainNode.getParentNode().removeChild(domainNode);
                        }
                        else if (path.equals(targetDomain.getPath())) {
                            // Remove the domain node
                            System.out.println(path + "2");
                            domainNode.getParentNode().removeChild(domainNode);
                        }
                    }
                }
            }

            // Save the modified document back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            System.out.println("Failed to remove the domain: " + e.getMessage());
        }
    }
}


//path: GUIDevices.java