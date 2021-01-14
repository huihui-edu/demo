package myboot.myblog.utils;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class MarkDownUtil {
    /**
     * 将Markdown形式转换成Html
     * @return
     */
    public static String mdToHtml(String markDownString){
        //判断参数是否为空
        if (StringUtils.isEmpty(markDownString)){
            return "";
        }
        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markDownString);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String content = renderer.render(document);
        return content;
    }

}
