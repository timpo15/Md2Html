package markup;

import java.util.List;

public class Heading extends AbstractStyle {
    private int level;
    public Heading(List<Style> list, int level) {
        super(list);
        this.level = level;
    }

    @Override
    public void toString(Visitor visitor, StringBuilder sb) {
        visitor.visitHeading(this, sb);
    }
    public int getLevel() {
        return level;
    }
}
