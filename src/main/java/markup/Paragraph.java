package markup;

import java.util.List;

public class Paragraph extends AbstractStyle {
    public Paragraph(List<Style> list) {
        super(list);
    }
    public void toString(Visitor visitor, StringBuilder sb) {
        visitor.visitParagraph(this, sb);
    }
}