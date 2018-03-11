package markup;

public class Text implements Style {
    private String inner;
    public Text(final String text) {
        inner = text;
    }

    public String getInner() {
        return inner;
    }
    public void toString(Visitor visitor, StringBuilder sb) {
        visitor.visitText(this, sb);
    }
}
