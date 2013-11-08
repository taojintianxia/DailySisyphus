package base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;

public class SeleniumUtils {

	public static List<String> verifyItemFormater(List<String> tableHeaders) {
		List<String> verifyItems = new ArrayList<String>();
		for (String header : tableHeaders) {
			verifyItems.add(header.trim().replaceAll(" ", "_"));
		}
		return verifyItems;
	}

	public static String giveUniqueIdAfter(String origin) {
		UUID uuid = UUID.randomUUID();
		StringBuffer converted = new StringBuffer();
		converted.append(origin);
		converted.append(uuid.toString().replace("-", ""));
		return converted.toString();
	}

	public static String giveUniqueIdBefore(String origin) {
		UUID uuid = UUID.randomUUID();
		StringBuffer converted = new StringBuffer();
		converted.append(uuid.toString().replace("-", ""));
		converted.append(origin);
		return converted.toString();
	}

	public static String cutCurrencySymbol(String origin) {
		return String.valueOf(origin.toCharArray(), 1, origin.toCharArray().length - 1);
	}

	public static String genRandomString(String origin) {
		return RandomStringUtils.randomAlphanumeric(Integer.parseInt(origin.split(" ")[1]));
	}

}
