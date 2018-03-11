package markup;

import java.util.List;

public class Emphasis extends AbstractStyle {
    public Emphasis(List<Style> list) {
        super(list);
    }

    @Override
    public void toString(Visitor visitor, StringBuilder sb) {
        visitor.visitEmphasis(this, sb);
    }
}