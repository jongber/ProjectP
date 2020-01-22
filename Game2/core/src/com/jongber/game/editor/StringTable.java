package com.jongber.game.editor;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class StringTable {

    private static final Set<Character> charset = new HashSet<>();

    public static boolean contain(String words) {

        boolean result = true;

        for (int i = 0; i < words.length(); ++i) {
            char character = words.charAt(i);
            if (charset.contains(character) == false) {

                charset.add(character);

                result = false;
            }
        }

        return result;
    }

    public static String mergeStrings() {
        Class me = StringTable.class;

        Field[] fields = me.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAccessible()) {
                try {
                    Object object = field.get(null);
                    if (object instanceof String == false) {
                        continue;
                    }
                    if (field.getName().compareTo("charset") == 0) {
                        continue;
                    }

                    String value = (String)object;
                    for (Character c : value.toCharArray()) {
                        charset.add(c);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        StringBuilder builder = new StringBuilder();

        Object[] t = charset.toArray();

        for (Object obj : t) {
            builder.append((char)obj);
        }

        return builder.toString();
    }
}





