package me.twhuang.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/***
 * ��ɨ�蹤��
 *
 */
public abstract class PackageScanner {
    private static final String FILE_PROTOCOL = "file";
    private static final String JAR_PROTOCOL = "jar";
    private static final String CLASS_SUFFIX = ".class";

    public List<Class<?>> scan(String packageName) throws IOException, ClassNotFoundException {
        if (StringUtils.isNotEmpty(packageName)) {
            // ��ȡ��Դ
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
                    .getResources(packageName.replaceAll("\\.", "/"));
            // ����ɨ�����е���
            List<Class<?>> classList = new ArrayList<Class<?>>();
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                // ��ȡЭ�����ļ�����jar��
                String protocol = url.getProtocol();
                if (FILE_PROTOCOL.equals(protocol)) {
                    String packagePath = url.getPath().replaceAll("%20", " ");
                    addClass(classList, packagePath, packageName);
                } else if (JAR_PROTOCOL.equals(protocol)) {
                    // ����Jar��
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    JarFile jarFile = jarURLConnection.getJarFile();
                    Enumeration<JarEntry> JarEntrys = jarFile.entries();
                    while (JarEntrys.hasMoreElements()) {
                        // ���ж�Ӧ�Ĵ������
                        JarEntry jarEntry = JarEntrys.nextElement();
                        String jarEntryName = jarEntry.getName();
                        if (jarEntryName.equals(CLASS_SUFFIX)) {
                            String className = jarEntryName.substring(0, jarEntryName.indexOf(".")).replaceAll("/",
                                    ".");
                            Class<?> cls = Class.forName(className, true,
                                    Thread.currentThread().getContextClassLoader());
                            // �ж����Ƿ���Ҫ����
                            if (checkAdd(cls)) {
                                classList.add(cls);
                            }
                        }
                    }
                }
            }
            // �������д������
            return classList;
        }

        return null;
    }

    /**
     * �������Ϣ
     */
    private void addClass(List<Class<?>> classList, String packagePath, String packageName)
            throws ClassNotFoundException {
        File[] files = new File(packagePath).listFiles(file -> (file.isFile() && file.getName().endsWith(CLASS_SUFFIX)) || file.isDirectory());
        if (files != null) {
            // �����ļ�����
            for (File file : files) {
                String fileName = file.getName();
                if (file.isFile()) {
                    // ��ȡ��Ӧ������
                    String className = fileName.substring(0, fileName.lastIndexOf("."));
                    if (!StringUtils.isEmpty(packageName)) {
                        className = packageName + "." + className;
                    }
                    Class<?> cls = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
                    // �ж����Ƿ���Ҫ����
                    if (checkAdd(cls)) {
                        classList.add(cls);
                    }
                } else {
                    // ���еݹ����,ΪĿ¼
                    String currentPackagePath = fileName;
                    if (!StringUtils.isEmpty(packagePath)) {
                        currentPackagePath = packagePath + "/" + currentPackagePath;
                    }

                    String currentPackageName = fileName;
                    if (!StringUtils.isEmpty(packageName)) {
                        currentPackageName = packageName + "." + currentPackageName;
                    }
                    addClass(classList, currentPackagePath, currentPackageName);
                }
            }
        }
    }

    /***
     * �ж���Щ����Ҫ����
     */
    public abstract boolean checkAdd(Class<?> cls);

}
