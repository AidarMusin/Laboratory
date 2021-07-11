package ru.ufagkb21;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class ColorPrint {
    static ColoredPrinter cpRed = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.RED).build();
    static ColoredPrinter cpYellow = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.YELLOW).build();
    static ColoredPrinter cpCyan = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.CYAN).build();
    static ColoredPrinter cpMag = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.MAGENTA).build();
    static ColoredPrinter cpGreen = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.GREEN).build();
    static ColoredPrinter cpBlue = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.BLUE).build();
    static ColoredPrinter cpNone = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.NONE).build();
    static ColoredPrinter cpRedCyan = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.RED).background(Ansi.BColor.CYAN).build();


}
