package cool.scx.util;

import cool.scx.util.exception.ScxExceptionHelper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * 扫描类工具类
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class ScanClassUtils {

    /**
     * 默认 classLoader
     */
    private static final ClassLoader DEFAULT_CLASS_LOADER = ScanClassUtils.class.getClassLoader();

    /**
     * 读取 jar 包中的所有 class
     *
     * @param jarFileURI jar
     * @return r
     * @throws java.io.IOException r
     */
    public static List<Class<?>> getClassListByJar(URI jarFileURI) throws IOException {
        //获取 jarFile
        try (var jarFile = new JarFile(new File(jarFileURI)); var jarClassLoader = new URLClassLoader(new URL[]{jarFileURI.toURL()})) {
            //进行过滤处理
            return jarFile.stream().filter(jarEntry -> !jarEntry.isDirectory() && jarEntry.getName().endsWith(".class")).map(jarEntry -> loadClass(jarEntry, jarClassLoader)).collect(Collectors.toList());
        }
    }

    /**
     * <p>getClassListByDir.</p>
     *
     * @param classRootDir a {@link java.net.URI} object
     * @param classLoader  a {@link java.lang.ClassLoader} object
     * @return a {@link java.util.List} object
     * @throws java.io.IOException if any.
     */
    public static List<Class<?>> getClassListByDir(URI classRootDir, ClassLoader classLoader) throws IOException {
        var classList = new ArrayList<Class<?>>();
        var classRootPath = Path.of(classRootDir);
        Files.walkFileTree(classRootPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                //获取 class 的相对路径
                if (path.toString().endsWith(".class")) {
                    var classRealPath = classRootPath.relativize(path);
                    classList.add(loadClass(classRealPath, classLoader));
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return classList;
    }

    /**
     * 从 JarEntry 加载 class
     *
     * @param jarEntry       a
     * @param jarClassLoader a
     * @return a
     */
    private static Class<?> loadClass(JarEntry jarEntry, ClassLoader jarClassLoader) {
        var suffixLength = ".class".length();
        //这里是可以保证 path 最后一定是 .class 所以在此处可以放心移除
        var className = jarEntry.getName().substring(0, jarEntry.getName().length() - suffixLength).replace('/', '.');
        return loadClass0(className, jarClassLoader);
    }

    /**
     * 从 Path 加载 class
     *
     * @param classRealPath a
     * @param classLoader   a
     * @return a
     */
    private static Class<?> loadClass(Path classRealPath, ClassLoader classLoader) {
        var suffixLength = ".class.".length();
        var str = new StringBuilder();
        for (var path : classRealPath) {
            str.append(path.toString()).append(".");
        }
        //这里会在最后添加一个多余的 . 在这里移除
        var className = str.substring(0, str.length() - suffixLength);
        return loadClass0(className, classLoader);
    }

    /**
     * 根据 class 获取地址
     *
     * @param source a {@link java.lang.Class} object.
     * @return 可能是 目录 也可能是 jar 文件
     * @throws java.net.URISyntaxException if any.
     */
    public static URI getClassSource(Class<?> source) throws URISyntaxException {
        return source.getProtectionDomain().getCodeSource().getLocation().toURI();
    }

    /**
     * 简单封装 (在内部处理异常 )
     *
     * @param className   c
     * @param classLoader c
     * @return c
     */
    private static Class<?> loadClass0(String className, ClassLoader classLoader) {
        try {
            return DEFAULT_CLASS_LOADER.loadClass(className);
        } catch (Throwable t1) {
            try {
                return classLoader.loadClass(className);
            } catch (Throwable t2) {
                return null;
            }
        }
    }

    /**
     * 根据 basePackage 对 class 进行过滤
     *
     * @param classList       a {@link java.util.List} object
     * @param basePackageName a {@link java.lang.String} object
     * @return a {@link java.util.List} object
     */
    public static List<Class<?>> filterByBasePackage(List<Class<?>> classList, String basePackageName) {
        return classList.stream().filter(c -> c.getPackageName().startsWith(basePackageName)).collect(Collectors.toList());
    }

    /**
     * <p>isJar.</p>
     *
     * @param path a {@link java.io.File} object
     * @return a boolean
     */
    public static boolean isJar(Path path) {
        return !Files.isDirectory(path) && path.toString().endsWith(".jar");
    }

    /**
     * 如果类的构造函数是私有的 我们便假设此类不想让我们进行实例化
     *
     * @param c c
     * @return c
     */
    public static boolean isInstantiableClass(Class<?> c) {
        //既不是 接口也不是 抽象类
        return isNormalClass(c) && ScxExceptionHelper.noException(() -> c.getConstructor().newInstance());
    }

    /**
     * a
     *
     * @param c a
     * @return a
     */
    public static boolean isNormalClass(Class<?> c) {
        //既不是 接口也不是 抽象类
        return !c.isInterface() && !Modifier.isAbstract(c.getModifiers());
    }

}
