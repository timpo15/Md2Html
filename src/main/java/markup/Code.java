package markup;

import java.util.List;

public class Code extends AbstractStyle {
    public Code(List<Style> list) {
        super(list);
    }

    public void toString(Visitor visitor, StringBuilder sb) {
        visitor.visitCode(this, sb);
    }
}