package base;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Nov 8, 2013 11:31:53 AM
 * 
 */

public enum Browser {

	FIREFOX(1, "firefox", ""),
	CHROME(2, "chrome", ""),
	// OPERA(4, "opera", "5"),
	IE6(3, "internet explorer", "6"),
	IE7(4, "internet explorer", "7"),
	IE8(5, "internet explorer", "8"),
	IE9(6, "internet explorer", "9"),
	NOCOOKIE(7, "nocookie", "");

	private int code;

	private String name;

	private String version;

	private Browser(int code, String name, String version) {
		this.code = code;
		this.name = name;
		this.version = version;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return version.length() == 0 ? name : name + " " + version;
	}

}
