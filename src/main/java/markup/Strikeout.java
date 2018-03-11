package markup;

import java.util.List;

public class Strikeout extends AbstractStyle {
    public Strikeout(List<Style> list) {
        super(list);
    }

    @Override
    public void toString(Visitor visitor, StringBuilder sb) {
        visitor.visitStrikeout(this, sb);
    }
}
