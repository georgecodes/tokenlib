package com.elevenware.util.tokenlib;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class Version {

    static {
        Class clazz = Version.class;
        String className = clazz.getSimpleName() + ".class";
        String classPath = clazz.getResource(className).toString();
        if (classPath.startsWith("jar")) {

            String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) +
                    "/META-INF/MANIFEST.MF";
            Manifest manifest = null;
            try {
                manifest = new Manifest(new URL(manifestPath).openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Attributes attr = manifest.getMainAttributes();
            VERSION = attr.getValue("Version");
        }

    }

    public static String VERSION;

}
