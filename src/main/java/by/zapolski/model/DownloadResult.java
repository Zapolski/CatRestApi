package by.zapolski.model;

public class DownloadResult {
    private String name;
    private String location;

    public DownloadResult(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public DownloadResult() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
