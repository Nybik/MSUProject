package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Arrays;
import java.util.List;

public class PlotView extends JFrame implements MouseWheelListener, KeyListener {

    final static int FRAME_UPPER_MENU = 23;
    final static int WIDTH = 601;
    final static int HEIGHT = 601 + FRAME_UPPER_MENU;

    private MathGraph GraphView = new MathGraph();
    private JTextArea EpsArea = new JTextArea(Double.toString(GraphView.EPS)),
                      ScaleArea = new JTextArea(Double.toString(GraphView.SCALE));

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }


    private static void init(JFrame toInit, List<? extends Component> componentList) {
        toInit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (Component comp : componentList) {
            if (comp != null) {
                if (comp instanceof MathGraph)
                    toInit.add(comp);
                else
                    toInit.add(comp, BorderLayout.SOUTH);
            }
            else {
                Debugger.tryToDebug("[Init] Some of components are NULL", 1);
            }
        }

        toInit.pack();

        toInit.setVisible(true);

    }

    private PlotView() {
        super("Plot");
        addMouseWheelListener(this);
        addKeyListener(this);
        JPanel ScalePanel = new JPanel();
        ScalePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ScalePanel.add(EpsArea);
        ScalePanel.add(ScaleArea);
        EpsArea.addKeyListener(this);
        ScaleArea.addKeyListener(this);
        PlotView.init(this, Arrays.asList(GraphView, ScalePanel));
        Debugger.tryToDebug("[Init] Finished PlotView constructor", 0);
    }

    public static void main(String[] args) {
        new PlotView();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            GraphView.SCALE *= 2;
            GraphView.EPS = GraphView.EPS * 2;
        }
        else {
            GraphView.SCALE /= 2;
            GraphView.EPS = GraphView.EPS / 2;
        }
        EpsArea.setText(MathGraph.convert(GraphView.EPS));
        ScaleArea.setText(MathGraph.convert(GraphView.SCALE));
        GraphView.updateUI();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (e.getSource().equals(EpsArea)) {
                    GraphView.EPS = Double.parseDouble(EpsArea.getText());
                }
                else {
                    GraphView.SCALE = Double.parseDouble(ScaleArea.getText());
                }
                GraphView.updateUI();
            } catch (Exception ev) {
                Debugger.tryToDebug("[Options] Bad parse double from text", 0);
            }
            e.consume();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }
}
