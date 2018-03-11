package markup;

import java.util.List;

public class Underline extends AbstractStyle{
   public Underline(List<Style> children) {
        super(children);
    }
    public void toString(Visitor visitor, StringBuilder sb) {
        visitor.visitUnderline(this, sb);
    }
}
