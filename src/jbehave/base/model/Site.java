package jbehave.base.model;

/**
 * @author nianjun
 * 
 */

public enum Site {

    OD(1, "od", "OfficeDEPOT"),
    BSD(2, "bsd", "BSD"),
    IBSD(3, "ibsd", "iBSD"),
    VKG(4, "vkg", "Viking"),
    GMIL(5, "gmil", "GMillennia"),
    EUROPA(3, "ibsd", "europa");

    private int code;

    private String name;

    private String description;

    private Site(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
