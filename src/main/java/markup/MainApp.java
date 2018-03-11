package markup;

import Md2Html.MdParser;

import java.io.IOException;

public class MainApp {

    public static void main(String... args) throws IOException {


        if (args.length < 2) {
            System.out.println("Not enought count of files");
            return;
        }
        MdParser mdParser = new MdParser(args[0],"utf8");
        Style style;
        StringBuilder stringBuilder = new StringBuilder();
        while ((style = mdParser.getNext()) != null) {
            Visitor visitor = Visitor.TO_HTML;
            style.toString(visitor, stringBuilder);
            System.out.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
        }
    }
}

