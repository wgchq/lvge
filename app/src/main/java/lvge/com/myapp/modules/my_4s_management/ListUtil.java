/**
 * 
 */
package lvge.com.myapp.modules.my_4s_management;

import java.util.Collection;

/**
 * @author jgchen
 *
 */
public class ListUtil {

	public static boolean hasValue(Collection<?> collection) {
		if (collection == null || collection.size() == 0) {
			return false;
		}

		return true;
	}

	public static boolean hasNoValue(Collection<?> collection) {
		if (collection == null || collection.size() == 0) {
			return true;
		}

		return false;
	}
}
