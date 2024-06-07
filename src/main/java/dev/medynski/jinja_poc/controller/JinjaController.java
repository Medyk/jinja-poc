package dev.medynski.jinja_poc.controller;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.interpret.Context;
import dev.medynski.jinja_poc.util.EmptyResourceLocator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;
import java.util.Set;


@RestController
@RequestMapping(path = "/jinja")
public class JinjaController {
    public JinjaController() {
        Map<Context.Library, Set<String>> disabled = ImmutableMap.of(
                Context.Library.TAG,
                ImmutableSet.of("from", "import", "include", "extends")
        );

        JinjavaConfig jinjavaConfig = JinjavaConfig.newBuilder()
                .withFailOnUnknownTokens(true)
                .withDisabled(disabled)
                .build();

        jinjava = new Jinjava(jinjavaConfig);
        jinjava.setResourceLocator(new EmptyResourceLocator());
    }


    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRoleTemplates(
            HttpServletRequest request,
            @RequestParam(defaultValue = "") String param1,
            @RequestParam(defaultValue = "") String param2,
            @RequestParam(defaultValue = "") String param3,
            @RequestParam(defaultValue = "") String template
    ) throws Exception {
        Map<String, Object> context = Maps.newHashMap();
        context.put("param1", param1);
        context.put("param2", param2);
        context.put("param3", param3);

        // Handle FatalTemplateErrorsException
        String jinja = jinjava.render(template, context);

        Map<String, Object> result = Maps.newHashMap();
        result.put("template", template);
        result.put("context", context.toString());
        result.put("result", jinja);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(result);
    }


    private final Jinjava jinjava;
}
