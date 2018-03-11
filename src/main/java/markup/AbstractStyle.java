package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStyle implements Style {
    private List<Style> children;

    public AbstractStyle(List<Style> children) {
        this.children = new ArrayList<>();
        this.children.addAll(children);
    }

    public AbstractStyle() {
        children = new ArrayList<>();
    }
    public List<Style> getChildren() {
        return children;
    }

    public void addInner(Style style) {
        children.add(style);
    }
}
