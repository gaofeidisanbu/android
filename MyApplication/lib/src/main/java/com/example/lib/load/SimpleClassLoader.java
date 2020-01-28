package com.example.lib.load;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SimpleClassLoader extends ClassLoader {

    @Override
    protected Class<?> loadClass(String s, boolean b) throws ClassNotFoundException {
        return super.loadClass(s, b);
    }

    @Override
    public Class<?> loadClass(String s) throws ClassNotFoundException {
        return super.loadClass(s);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] classByte = getClassData(name);
            if (classByte == null) {
                throw new ClassNotFoundException("s");
            } else {
               return defineClass(name, classByte, 0, classByte.length);
            }
    }

    private byte[] getClassData(String className) {
        String path = classNameToPath(className);

        try {
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead = 0;
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String className) {
        return "/Users/gaofei/android/MyApplication/lib/src" + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
    }

}
