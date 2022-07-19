package my.btk.util;

import java.util.Collection;
import java.util.Map;
/**
 * ValidateUtil: properties identification
 *
 */
public final class ValidateUtil {


    /**
     * check object empty or not
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    /**
     * String empty or not
     * @param obj
     * @return
     */
    public static boolean isEmpty(String obj) {

        return obj == null || obj.length() == 0;
        //what if " " and "   " ?
    }

    /**
     * Array empty or not
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    /**
     * Collection is empty or not
     * @param obj
     * @return
     */
    public static boolean isEmpty(Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * Map is empty or not
     * @param obj Map
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return obj == null || obj.isEmpty();
    }


    /**
     * Object is not empty
     *
     * @param obj Object
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * String is not empty
     *
     * @param obj String
     * @return
     */
    public static boolean isNotEmpty(String obj) {
        return !isEmpty(obj);
    }

    /**
     * Array is not empty
     *
     * @param obj Array
     * @return
     */
    public static boolean isNotEmpty(Object[] obj) {
        return !isEmpty(obj);
    }

    /**
     * Collection is not empty
     *
     * @param obj Collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> obj) {
        return !isEmpty(obj);
    }

    /**
     * Map is not empty
     * @param obj Map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> obj) {
        return !isEmpty(obj);
    }


}
