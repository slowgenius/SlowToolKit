package com.mp.tools.generator.infrastructure;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import java.util.ResourceBundle;

public class MsgBundle {

    @NonNls
    private static final String BUNDLE_NAME = "MsgBundle";

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String message(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object ... params){
        return AbstractBundle.message(BUNDLE, key, params);
    }

}
