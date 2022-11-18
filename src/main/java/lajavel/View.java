package lajavel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View {

    @SafeVarargs
    public static String make(String viewName, Map.Entry<String, Object>... entries) {
        String viewContent = View.getViewContentFromFilename("pages", viewName);
        StringBuffer sb = new StringBuffer();

        viewContent = View.replaceIncludes(viewContent, sb);
        View.clearBuffer(sb);

        viewContent = View.replaceAssets(viewName, viewContent, sb);
        View.clearBuffer(sb);

        viewContent = View.replaceForeach(viewContent, sb, entries);
        View.clearBuffer(sb);

        viewContent = View.replaceProperties(viewContent, sb, entries);

        return viewContent;
    }

    public static String replaceIncludes(String html, StringBuffer sb) {
        Matcher m = Pattern.compile("\\{%\\s*?include (\\S*)\\s*?%}").matcher(html);

        while (m.find()) {
            String component = View.getMatcherGroup(m, 1);

            String content = View.getViewContentFromFilename("components", component);

            m.appendReplacement(sb, content);
        }

        m.appendTail(sb);

        return sb.toString();
    }

    public static String replaceAssets(String viewName, String html, StringBuffer sb) {
        Matcher m = Pattern.compile("\\{\\{\\s*?assets\\(([^{{}}]*)\\)\\s*?\\}\\}").matcher(html);

        while (m.find()) {
            String type = View.getMatcherGroup(m, 1);

            String path = type + "/" + viewName + "." + type;

            URL resource = View.class.getClassLoader().getResource("public/" + path);

            if (resource == null) {
                Log.warn("No resource found at public/" + path);
                path = "";
            }

            m.appendReplacement(sb, path);
        }

        m.appendTail(sb);

        return sb.toString();
    }

    @SafeVarargs
    public static String replaceForeach(String html, StringBuffer sb, Map.Entry<String, Object>... entries) {
        Matcher m = Pattern.compile("\\{%\s*?for (\\S*) in (\\S*)\s*?%}(.*)\\{%\s*?endfor\s*?%}", Pattern.DOTALL).matcher(html);

        while (m.find()) {
            String item = View.getMatcherGroup(m, 1);
            String items = View.getMatcherGroup(m, 2);
            // We don't replace spaces in the content
            String content = m.group(3);

            for (Map.Entry<String, Object> entry : entries) {
                if (entry.getKey().equals(items)) {
                    List<?> models = (List<?>) entry.getValue();

                    StringBuffer tempSb = new StringBuffer();

                    for (Object model : models) {
                        View.replaceProperties(content, tempSb, Map.entry(item, model));
                    }

                    m.appendReplacement(sb, tempSb.toString());
                }
            }
        }

        m.appendTail(sb);

        return sb.toString();
    }

    public static String replaceProperties(String html, StringBuffer sb, Map.Entry<String, Object>... entries) {
        Matcher m = Pattern.compile("\\{\\{([^{{}}]*)\\}\\}").matcher(html);

        while (m.find()) {
            String rawStringOfObject = View.getMatcherGroup(m, 1);
            String[] objectAndProperty = rawStringOfObject.split("\\.");

            if (objectAndProperty.length <= 1) {
                throw new RuntimeException("You must specify the object and a property in your html if you pass an object");
            }

            String objectName = objectAndProperty[0];
            String propertyName = objectAndProperty[1];

            for (Map.Entry<String, Object> entry : entries) {
                if (entry.getKey().equals(objectName)) {
                    m.appendReplacement(sb, View.getValueOf(propertyName, entry.getValue()));
                    break;
                }
            }
        }

        m.appendTail(sb);

        return sb.toString();
    }

    private static String getViewContentFromFilename(String folder, String filename) {
        URL resource = View.class.getClassLoader().getResource(folder + "/" + filename + ".javel");

        if (resource == null) {
            Log.warn("No resource found at " + folder + "/" + filename + ".javel");
        }

        try {
            return Files.readString(Path.of(resource.toURI()));
        } catch (Exception e) {
            Log.warn(e.getMessage());
            return "";
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
            Log.error("La propriété " + property + " n'existe pas dans l'objet " + object.getClass().getName());
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

    private static String getMatcherGroup(Matcher m, int group) {
        return m.group(group).replaceAll("\\s+", "");
    }
}
