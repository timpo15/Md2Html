package Md2Html;

import markup.Style;
import markup.Visitor;

import javax.activation.UnsupportedDataTypeException;
import java.io.*;

public class Md2Html {

    public static void main(String[] args) {


        if (args.length < 2) {
            System.out.println("Not enought count of files");
            return;
        }
        try (MdParser mdParser = new MdParser(args[0], "utf8")) {

            Style style;
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1])))) {
                while ((style = mdParser.getNext()) != null) {
                    Visitor visitor = Visitor.TO_HTML;
                    style.toString(visitor, stringBuilder);
                    bufferedWriter.write(stringBuilder.toString() + '\n');
                    stringBuilder = new StringBuilder();
                }
            } catch (IOException e) {
                System.out.println("Can't write in file " + args[1] + "\n" + e.getMessage());
            }

        } catch (UnsupportedDataTypeException e) {
            System.out.println("Wrong MarkDownFormat in input file\n" + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file " + args[0] + "\n" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Can't read file " + args[0] + "\n" + e.getMessage());
        }
    }

}

