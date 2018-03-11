package Md2Html;

import markup.*;

import javax.activation.UnsupportedDataTypeException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

public class MdParser implements AutoCloseable {

    private int q;
    private AbstractStyle wrapper;
    private StringBuilder builder = new StringBuilder();
    private BufferedReader br;

    private ArrayDeque<AbstractStyle> stackStyle = new ArrayDeque<AbstractStyle>();
    private Deque<String> stack = new ArrayDeque<String>();

    public MdParser(String nameInputFile, String encoding) throws IOException {
        br = new BufferedReader(new InputStreamReader(new FileInputStream(nameInputFile), encoding));
    }

    private void addText(StringBuilder stringBuilder) {
        if (stack.isEmpty()) {
            wrapper.addInner(new Text(builder.toString()));
        } else {
            stackStyle.peekLast().addInner(new Text(builder.toString()));
        }
        builder = new StringBuilder();
    }

    private void close(String s) {
        addText(builder);
        stackStyle.pollLast();
        stack.pollLast();
    }

    private void open(String string, AbstractStyle style) {
        addText(builder);
        if (stackStyle.isEmpty()) {
            wrapper.addInner(style);
        } else {
            stackStyle.peekLast().addInner(style);
        }
        stackStyle.addLast(style);
        stack.addLast(string);
    }

    private void check(String s)  {
        if ((builder.length() == 0 || (builder.length() != 0 && builder.charAt(builder.length() - 1) != ' '))
                && !stack.isEmpty() && stack.peekLast().equals(s)) {

            close(s);
        } else if (q == ' ' || q == '\r' || q == '\n' || q == -1) {
            builder.append(s);
        } else {
            switch (s) {
                case "*":
                case "_":
                    open(s, new Emphasis(new ArrayList<Style>()));
                    break;
                case "**":
                case "__":
                    open(s, new Strong(new ArrayList<Style>()));
                    break;
                case "--":
                    open(s, new Strikeout(new ArrayList<Style>()));
                    break;
                case "++":
                    open(s, new Underline(new ArrayList<Style>()));
                    break;
                case "`":
                    open(s, new Code(new ArrayList<Style>()));
                    break;
            }
        }
    }

    public Style getNext() throws IOException {
        boolean first = true;
        builder = new StringBuilder();
        q = br.read();
        if (q == -1) {
            return null;
        }
        int count = 0;
        int q1;
        while (q == '\n' || q == '\r') {
            q = br.read();
        }
        if (q == -1) {
            return null;
        }
        if (q == '#') {
            while (q == '#') {
                q = br.read();
                count++;
            }
            if (q == ' ') {
                wrapper = new Heading(new ArrayList<Style>(), count);
                q = br.read();
            } else {
                wrapper = new Paragraph(Collections.emptyList());
                for (int i = 0; i < count; i++) {
                    builder.append('#');
                }
                wrapper.addInner(new Text(builder.toString()));
                builder = new StringBuilder();
            }
        } else {
            wrapper = new Paragraph(Collections.emptyList());
        }
        while (true) {
            if (!first) {
                q = br.read();
            }
            first = false;
            if (q == -1) {
                if (stackStyle.isEmpty()) {
                    wrapper.addInner(new Text(builder.toString()));
                } else {
                    throw new UnsupportedDataTypeException();
                }
                return wrapper;
            } else if (q == '\n') {
                q = br.read();
                if (q == '\n' || q == -1) {
                    if (stackStyle.isEmpty()) {
                        wrapper.addInner(new Text(builder.toString()));
                        return wrapper;
                    } else {
                        throw new UnsupportedDataTypeException("Wrong format of markdown input file");
                    }
                } else {
                    builder.append((char) q);
                }
            } else if (q == '\r') {
                q = br.read();
                if (q == '\r' || q == -1) {
                    if (stackStyle.isEmpty()) {
                        wrapper.addInner(new Text(builder.toString()));
                    } else {
                        throw new UnsupportedDataTypeException("Wrong format of markdown input file");
                    }
                    return wrapper;
                } else {
                    if (q == '\n') {
                        q = br.read();
                        if (q == '\r' || q == -1) {
                            if (stackStyle.isEmpty()) {
                                wrapper.addInner(new Text(builder.toString()));
                            } else {
                                throw new UnsupportedDataTypeException("Wrong format of markdown input file");
                            }
                            return wrapper;
                        } else {
                            builder.append('\n');
                            first = true;
                            continue;
                        }
                    } else {
                        builder.append('\n');
                        first = true;
                        continue;
                    }
                }
            } else if (q == '*' || q == '_') {
                int tempQ = br.read();
                if (q == tempQ) {
                    q = br.read();
                    check("" + (char) tempQ + (char) tempQ);
                } else {
                    int temp2 = q;
                    q = tempQ;
                    check("" + (char) temp2);
                }
                first = true;
                continue;
            } else if (q == '+' || q == '-') {
                int tempQ = br.read();
                if (q == tempQ) {
                    q = br.read();
                    check("" + (char) tempQ + (char) tempQ);
                } else {
                    builder.append((char) q);
                    q = tempQ;
                }
                first = true;
                continue;
            } else if (q == '`') {
                check("`");
                continue;

            } else if (q == '\\') {
                q = br.read();
                if (q == '*' || q == '+' || q == '_' || q == '-' || q == '`') {
                    builder.append((char) q);
                } else {
                    first = true;
                }
                continue;
            }
            if (q == '<') {
                builder.append("&lt;");
                continue;
            }
            if (q == '>') {
                builder.append("&gt;");
                continue;
            }
            if (q == '&') {
                builder.append("&amp;");
                continue;
            }
            if (q == -1) {
                continue;
            }
            builder.append((char) q);
        }
    }

    @Override
    public void close() throws IOException {
        br.close();
    }
}