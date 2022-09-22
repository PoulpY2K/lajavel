package org.lajavel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class View {
    public static String make(String viewName) {
        String rawHtml = View.getViewContentFromFilename(viewName);

        Matcher matcher = Pattern.compile("\\{\\{(.*?)}}").matcher(rawHtml);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            matcher.appendReplacement(sb, "<h1>" + matcher.group(1) + "</h1>");
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    private static String getViewContentFromFilename(String filename) {
        URL resource = Main.class.getClassLoader().getResource("views/" + filename + ".javel");

        if(resource == null) {
            throw new RuntimeException("No resource found at views/" + filename + ".javel");
        }

        try {
            return Files.readString(Path.of(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
