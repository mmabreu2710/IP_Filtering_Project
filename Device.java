import java.awt.List;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.*;

public class Device {
    private String devicename;
    private String password;
    private GroupList groupList ;
    private String ipAddress; //ipv4
    private String ipv6Address; //ipv6
    private Domain domain;

    public Device(String devicename, String password, Group group, String ipAddress, String ipv6Address, Domain domain) {
        this.devicename = devicename;
        this.password = password;
        this.ipAddress = ipAddress;
        this.ipv6Address = ipv6Address;
        this.domain = domain;
        this.groupList = new GroupList();
        if (group != null){
            this.groupList.addGroup(group);
        }
        else{
            this.groupList = new GroupList();
        }
    }
    public Device(String devicename, String password, GroupList groupList, String ipAddress, String ipv6Address, Domain domain) {
        this.devicename = devicename;
        this.password = password;
        this.ipAddress = ipAddress;
        this.ipv6Address = ipv6Address;
        this.domain = domain;
        if (groupList != null){
            this.groupList = groupList;
        }
        else{
            this.groupList = new GroupList();
        }
    }
    public Device(String devicename, String password, String ipAddress, String ipv6Address, Domain domain) {
        this.devicename = devicename;
        this.password = password;
        this.ipAddress = ipAddress;
        this.ipv6Address = ipv6Address;
        this.domain = domain;
        this.groupList = new GroupList();
    }
    
    

    // Getters and setters for devicename and password and ipAddress
    
    public Domain getDomain() {
        return domain;
    }

    public String getDeviceName() {
        return devicename;
    }

  public String getIPv6Address() {return ipv6Address;}


  public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        this.password = password;
        return password;
    }

    public String setDeviceName(String devicename) {
        this.devicename = devicename;
        return devicename;
    }

    public String getIPAddress() {
        return ipAddress;
    }

    public GroupList getGroupList() {
        return groupList;
    }
    public String getGroupsForString() {
        String auxstring = "";
        if(groupList.getSize() == 0)
          return "";
        else{
          for (int i = 0; i < groupList.getSize(); i++){
              auxstring += groupList.getGroups().get(i).getPath();
              if (i != groupList.getSize()- 1) {
                  auxstring += ",";
              }

          }
          return auxstring;
       }
     }
}
