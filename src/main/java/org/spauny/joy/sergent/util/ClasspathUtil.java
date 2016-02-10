package org.spauny.joy.sergent.util;

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
    
    public static void main (String args[]) {
        System.out.println(ClasspathUtil.getFileFromResources("soundfile.aiff").getName());
    }
}
