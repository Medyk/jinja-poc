package dev.medynski.jinja_poc.util;


import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.loader.ResourceLocator;
import java.io.IOException;
import java.nio.charset.Charset;


public class EmptyResourceLocator implements ResourceLocator {
    @Override
    public String getString(
            String fullName,
            Charset encoding,
            JinjavaInterpreter interpreter
    )
            throws IOException {
        return "";
    }
}
