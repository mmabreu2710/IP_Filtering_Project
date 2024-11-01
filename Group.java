import java.io.File;

public class Group {
    private String name;
    private String path;
    private Domain domain;
    private DeviceList deviceList;
    public Group(String name, Domain domain) {
        this.name = name;
        this.path = domain.getPath() + File.separator + name;
        this.domain = domain;
        this.deviceList = new DeviceList();
    }
    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Domain getDomain() {
        return domain;
    }

  public DeviceList getDeviceList() {
    return deviceList;
  }

  public void setDeviceList(DeviceList deviceList) {
    this.deviceList = deviceList;
  }

  public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }


}
