package cn.rongcapital.sisyphus.base.model;

/**
 * @author nianjun
 * 
 */

public enum Browser {

	FIREFOX(1, "firefox", ""),
	MOBILE(2, "firefox", ""),
	CHROME(3, "chrome", ""),
	IE6(4, "internet explorer", "6"),
	IE7(5, "internet explorer", "7"),
	IE8(6, "internet explorer", "8"),
	IE9(7, "internet explorer", "9"),
	NOCOOKIE(8, "nocookie", ""),
	NOJS(9, "firefox", "");

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