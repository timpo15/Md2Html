package markup;

import java.util.List;

public class Strong extends AbstractStyle {
    public Strong(List<Style> list) {
        super(list);
    }
    @Override
    public void toString(Visitor visitor, StringBuilder sb) {
        visitor.visitStrong(this, sb);
    }
}
