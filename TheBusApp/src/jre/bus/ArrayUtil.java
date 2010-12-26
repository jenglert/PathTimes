package jre.bus;

public class ArrayUtil {

	/**
	 * Determines whether the specified array contains the specified item.
	 */
	public static <T> boolean contains(T[] array, T item) {
		for (T t : array) {
			if (item.equals(t)) {
				return true;
			}
		}
		
		return false;
	}
}
