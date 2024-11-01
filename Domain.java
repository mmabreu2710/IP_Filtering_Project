public class Domain {
    private String name;
    private String path;
    private String lowerDomains;
    private GroupList groupList = new GroupList();
    private DeviceList deviceList = new DeviceList();
    private GroupList authorizedGroupList = new GroupList();
    private DeviceList auhtorizedDeviceList = new DeviceList();
    private DomainList authorizedDomainList = new DomainList();




    public Domain(String name, String lowerDomains, String path){
        this.name = name;
        this.lowerDomains = lowerDomains;
        this.path = path;


    }

  public GroupList getGroupList() {
    return groupList;
  }

  public DeviceList getDeviceList() {
    return deviceList;
  }

  public DeviceList getAuhtorizedDeviceList() {
    return auhtorizedDeviceList;
  }

  public DomainList getAuthorizedDomainList() {
    return authorizedDomainList;
  }

  public GroupList getAuthorizedGroupList() {
    return authorizedGroupList;
  }

  public String getName() {
        return name;
    }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
  public void setLowerDomains(String lowerDomains){this.lowerDomains = lowerDomains;}
    public void addLowerDomains(String aux){
        if (!lowerDomains.equals("")){
            lowerDomains +=",";
        }
        lowerDomains += aux;
    }

  public String getLowerDomains(){return lowerDomains;}

  public void setName(String name) {
      this.name = name;
  }

  


    
}





