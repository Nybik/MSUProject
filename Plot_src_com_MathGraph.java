package com;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MathGraph extends JPanel {

    static final int POINTS_PER_AXIS = 4,
                     AXIS_X = 12, AXIS_Y = 12,
                     PIXELS_PER_X = 50, PIXELS_PER_Y = 50;
    static final Color LINE_COLOR = new Color(139, 60, 191),
                       AXIS_COLOR = new Color(0, 0, 0);

    public double EPS = 0.01;
    public double SCALE = 1.0;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int n = this.getWidth(), m = this.getHeight();
        Debugger.tryToDebug("[MathGraph] SIZES = " + Integer.toString(n) + " " + Integer.toString(m),
                2);
        g.setColor(AXIS_COLOR);
        drawAxis(g);
        g.setColor(LINE_COLOR);
        drawGraph(g);
    }

    private void drawGraph(Graphics g) {
        int n = this.getWidth(), m = this.getHeight();
        int midI = n / 2, midJ = m / 2;
        for (int pixelI = 0; pixelI < n; ++pixelI) {
            for (int pixelJ = 0; pixelJ < m; ++pixelJ) {

                double x = ((n - pixelI - 1) - midI) / 100.0 * SCALE;
                double y = ((m - pixelJ - 1) - midJ) / 100.0 * SCALE;

                if (satisfyEquation(x, y)) {
                    g.fillRect(pixelI, pixelJ, 3, 3);
                    Debugger.tryToDebug("[MathGraph] Printing " + Double.toString(x) + " " + Double.toString(y),
                            200);
                }
            }
        }
    }

    private void drawAxis(Graphics g) {
        int n = this.getWidth(), m = this.getHeight();
        int midI = n / 2, midJ = m / 2;

        /*
              x x x x
            y
            y
            y
            y
         */
        for (int offset = -1; offset <= 0; ++offset)
            g.drawLine(AXIS_X, midJ + offset, n - 1 - AXIS_X, midJ + offset); // OX
        for (int offset = -1; offset <= 0; ++offset)
            g.drawLine(midI + offset, AXIS_Y, midI + offset, m - 1 - AXIS_Y); // OY
        int x[] = {midI - 10, midI + 10, midI};
        int y[] = {AXIS_Y, AXIS_Y, AXIS_Y - 10};
        g.fillPolygon(x, y, 3);
        x = new int[]{n - AXIS_X - 1, n - AXIS_X - 1, n - AXIS_X + 9};
        y = new int[]{midJ - 10, midJ + 10, midJ};
        g.fillPolygon(x, y, 3);

        int nowOX = midI - 1, times = 1;
        while (nowOX < n - AXIS_X - 50) {
            nowOX += PIXELS_PER_X;
            g.drawLine(nowOX, midJ - 10, nowOX, midJ + 10);
            g.drawString(convert(SCALE * 0.5 * (times++)), nowOX, midJ - 11);
        }
        nowOX = midI + 1;
        times = 1;
        while (nowOX > AXIS_X + 49) {
            nowOX -= PIXELS_PER_X;
            g.drawLine(nowOX, midJ - 10, nowOX, midJ + 10);
            g.drawString(convert(SCALE * 0.5 * (times++)), nowOX, midJ - 11);
        }

        int nowOY = midJ - 1;
        times = 1;
        while (nowOY < m - AXIS_Y - 50) {
            nowOY += PIXELS_PER_Y;
            g.drawLine(midI - 10, nowOY, midI + 10, nowOY);
            g.drawString(convert(SCALE * 0.5 * (times++)), midI - 30, nowOY);
        }
        nowOY = midJ + 1;
        times = 1;
        while (nowOY > AXIS_Y + 49) {
            nowOY -= PIXELS_PER_Y;
            g.drawLine(midI - 10, nowOY, midI + 10, nowOY);
            g.drawString(convert(SCALE * 0.5 * (times++)), midI - 30, nowOY);
        }
    }

    private static double func(double x, double y) {
        return y - x;
    }

    public boolean satisfyEquation(double x, double y) {
        return func(x, y) <= EPS && func(x, y) >= -EPS;
    }

    static final private DecimalFormat formatter = new DecimalFormat("#.#####", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
    public static String convert(Double d) {
        return formatter.format(d);
    }
}
