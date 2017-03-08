package lindar.sergent.util;

import java.io.File;

/**
 *
 * @author iulian.dafinoiu
 */
public class ClasspathUtil {
    
    public static File getFileFromResources(String fileName) {
        ClassLoader classLoader = ClasspathUtil.class.getClassLoader();
	return new File(classLoader.getResource(fileName).getFile());
    }

    private ClasspathUtil() {
    }
    
}
