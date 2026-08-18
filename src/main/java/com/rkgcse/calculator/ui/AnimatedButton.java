package com.rkgcse.calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/** Rounded Swing button with hover and press animation. */
public class AnimatedButton extends JButton {
    private final Color normal;
    private final Color hover;
    private boolean pressed;

    public AnimatedButton(String text, Color normal, Color hover, Color foreground) {
        super(text);
        this.normal = normal;
        this.hover = hover;
        setForeground(foreground);
        setFont(new Font("SansSerif", Font.BOLD, 17));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(10, 8, 10, 8));
        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { repaint(); }
            @Override public void mouseExited(MouseEvent e) { pressed = false; repaint(); }
            @Override public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
            @Override public void mouseReleased(MouseEvent e) { pressed = false; repaint(); }
        });
    }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color fill = getModel().isRollover() ? hover : normal;
        if (pressed) fill = fill.darker();
        int inset = pressed ? 3 : 1;
        g2.setColor(new Color(80, 65, 110, 22));
        g2.fillRoundRect(inset + 1, inset + 3, getWidth() - inset * 2 - 1, getHeight() - inset * 2 - 1, 18, 18);
        g2.setColor(fill);
        g2.fillRoundRect(inset, inset, getWidth() - inset * 2, getHeight() - inset * 2 - 2, 18, 18);
        g2.dispose();
        super.paintComponent(g);
    }
}
