package dev.cjson.docs;

import nl.jworks.markdown_to_asciidoc.Converter;

public class Markdown2AsciiDoc {

    public static String convertMarkdown(String markdown) {
        return Converter.convertMarkdownToAsciiDoc(markdown);
    }

}
