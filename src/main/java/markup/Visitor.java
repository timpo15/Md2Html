package markup;

public interface Visitor {
    void visitText(Text style, StringBuilder sb);
    void visitCode(Code style, StringBuilder sb);
    void visitEmphasis(Emphasis style, StringBuilder sb);
    void visitParagraph(Paragraph style, StringBuilder sb);
    void visitStrong(Strong style, StringBuilder sb);
    void visitStrikeout(Strikeout style, StringBuilder sb);
    void visitUnderline(Underline style, StringBuilder sb);
    void visitHeading(Heading style, StringBuilder sb);


    Visitor TO_HTML = new Visitor() {
        void toHtml(final String mark, AbstractStyle style, StringBuilder sb) {
            sb.append("<").append(mark).append(">");
            for (Style s : style.getChildren()) {
                s.toString(this, sb);
            }
            sb.append("</").append(mark).append(">");
        }

        @Override
        public void visitText(Text style, StringBuilder sb) {
            sb.append(style.getInner());
        }

        @Override
        public void visitCode(Code style, StringBuilder sb) {
            toHtml("code", style , sb);
        }

        @Override
        public void visitEmphasis(Emphasis style, StringBuilder sb) {
            toHtml("em", style , sb);

        }

        @Override
        public void visitStrong(Strong style, StringBuilder sb) {
            toHtml("strong", style , sb);

        }

        @Override
        public void visitStrikeout(Strikeout style, StringBuilder sb) {
            toHtml("s", style , sb);
        }

        @Override
        public void visitParagraph(Paragraph style, StringBuilder sb) {
            toHtml("p", style , sb);
        }

        @Override
        public void visitUnderline(Underline style, StringBuilder sb) {
            toHtml("u", style , sb);
        }

        @Override
        public void visitHeading(Heading style, StringBuilder sb) {
            toHtml("h" + Integer.toString(style.getLevel()), style, sb);
        }
    };
}
