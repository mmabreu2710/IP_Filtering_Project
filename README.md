# IP Filtering Project

This project is designed for managing devices, groups, and domains across various networks using Java and XML files for data storage. The application includes functionality for filtering and organizing IP addresses, managing device information, and handling group memberships within network domains.

## Getting Started

To start the project, follow these steps:
1. **Compile** all Java files with:
   """
   javac *.java
   """
2. **Run** the main application with:
   """
   java Main
   """

## Project Structure

### Java Files

- **Device.java**: Defines the `Device` class representing each device in the network. Each device has a name, IP address, IPv6 address, and belongs to a domain and group.

- **DeviceList.java**: Manages a collection of `Device` objects, with methods for adding, removing, and fetching devices within a particular group or domain.

- **Domain.java**: Defines the `Domain` class, representing a network domain. A domain can contain multiple groups and devices, with fields for name, path, and sub-domains.

- **DomainList.java**: Manages a list of `Domain` objects, supporting operations for reading domain data from XML and managing domain hierarchies.

- **GUI.java**: Provides a graphical user interface for interacting with the application. This file includes basic UI elements for adding and managing devices, groups, and domains visually.

- **Group.java**: Defines the `Group` class, where each group is associated with a name and domain. A group contains a list of devices associated with that group.

- **GroupList.java**: Manages a list of `Group` objects, supporting operations like adding and removing groups and loading group data from XML files.

- **Main.java**: The entry point for the application, initializing components and launching the GUI. It sets up data structures and prepares the application for use.

- **auxClass.java**: Contains helper methods, especially for XML file handling, including methods to create, check, and update XML files for domains, devices, and groups.

### Database Directory

The `Database` directory contains XML files that represent different networks, domains, devices, and groups. These files are organized to match the domain structure.

- **Domains.xml**: Lists all available domains in the `Database`, with elements like `Domain`, `Path`, and `LowerDirectories`, describing hierarchical relationships between domains.

- **Network Directory**: Inside `Database`, the `Network` directory is divided into domain-based subdirectories. Each subdomain folder contains its own `Devices.xml`, `Groups.xml`, and domain-specific XML files. Each XML file represents a different configuration layer within the network.

### XML Structure

The XML files in the `Database` directory follow a structured format:

1. **Domains.xml**: Lists network domains, each described by:
    - `<Name>`: Domain name.
    - `<Path>`: Path within the network.
    - `<LowerDirectories>`: Subdirectories (or nested domains) under this domain.

2. **Alameda.xml**: Represents device information and grouping within the Alameda network.
    - **Domain** entries define subdomains.
    - **Device** entries contain fields like `Name`, `Password`, `ipAddress`, `ipv6Address`, and the associated `Group`.

3. **Tagus.xml**: Represents the Tagus network with similar fields and structure as Alameda.xml but configured for devices and groups specific to the Tagus domain.
