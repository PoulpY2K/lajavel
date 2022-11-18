package lajavel;

import app.Main;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View {

    @SafeVarargs
    public static String make(String viewName, Map.Entry<String, Object>... entries) {
        String viewContent = View.getViewContentFromFilename(viewName);
        StringBuffer sb = new StringBuffer();

        viewContent = View.replaceAssets(viewContent, sb);

        View.clearBuffer(sb);

        viewContent = View.replaceProperties(viewContent, sb, entries);

        return viewContent;
    }

    public static String replaceProperties(String html, StringBuffer sb, Map.Entry<String, Object>... entries) {
        Matcher m = Pattern.compile("\\{\\{([^{{}}]*)\\}\\}").matcher(html);

        while (m.find()) {
            String rawStringOfObject = m.group(1).replaceAll("\\s+", "");
            String[] objectAndProperty = rawStringOfObject.split("\\.");

            if (objectAndProperty.length <= 1) {
                throw new RuntimeException("You must specify the object and a property in your html if you pass an object");
            }

            String objectName = objectAndProperty[0];
            String propertyName = objectAndProperty[1];

            for (Map.Entry<String, Object> entry : entries) {

                Log.warn(entry.getValue().toString());

                if (entry.getKey().equals(objectName)) {
                    m.appendReplacement(sb, View.getValueOf(propertyName, entry.getValue()));
                    break;
                }
            }
        }

        m.appendTail(sb);

        return sb.toString();
    }

    public static String replaceAssets(String html, StringBuffer sb) {
        Matcher m = Pattern.compile("\\{\\{\\s*?asset\\('([^{{}}]*)'\\)\\s*?\\}\\}").matcher(html);

        while (m.find()) {
            String path = m.group(1).replaceAll("\\s+", "");

            URL resource = Main.class.getClassLoader().getResource("public/" + path);

            if (resource == null) {
                Log.error("No resource found at public/" + path);
                throw new RuntimeException("No resource found at public/" + path);
            }

            m.appendReplacement(sb, path);
        }

        m.appendTail(sb);

        return sb.toString();
    }

    private static String getViewContentFromFilename(String filename) {
        URL resource = Main.class.getClassLoader().getResource("views/" + filename + ".javel");

        if (resource == null) {
            Log.error("No resource found at views/" + filename + ".javel");
            throw new RuntimeException("No resource found at views/" + filename + ".javel");
        }

        try {
            return Files.readString(Path.of(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            Log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static String getValueOf(String propertyName, Object object) {
        if (propertyName.endsWith("()")) {
            propertyName = propertyName.replace("()", "");
            return getMethod(String.class, object, propertyName);
        } else {
            return getProperty(String.class, object, propertyName);
        }
    }

    private static <T> T getProperty(Class<T> clazz, Object object, String property) {
        Object returnValue = null;

        try {
            Field field = object.getClass().getDeclaredField(property);
            field.setAccessible(true);
            returnValue = clazz.cast(field.get(object));
        } catch (Exception e) {
            // Do nothing, we'll return the default value
            Log.error(e.getMessage());
        }

        return clazz.cast(returnValue);
    }

    private static <T> T getMethod(Class<T> clazz, Object object, String methodName) {
        Object returnValue = null;

        try {
            Method method = object.getClass().getMethod(methodName);
            method.setAccessible(true);
            returnValue = clazz.cast(method.invoke(object));
        } catch (Exception e) {
            // Do nothing, we'll return the default value
            Log.error(e.getMessage());
        }

        return clazz.cast(returnValue);
    }

    private static void clearBuffer(StringBuffer sb) {
        sb.delete(0, sb.length());
    }
}
