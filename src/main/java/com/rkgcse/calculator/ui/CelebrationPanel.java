package com.rkgcse.calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/** Lightweight celebration overlay drawn with Swing; no external assets required. */
public class CelebrationPanel extends JPanel {
    private final Random random = new Random();
    private final Timer timer;
    private int frame;
    private boolean celebrating;

    public CelebrationPanel() {
        setOpaque(false);
        timer = new Timer(35, e -> {
            frame++;
            if (frame > 45) {
                celebrating = false;
                timer.stop();
            }
            repaint();
        });
    }

    public void celebrate() {
        frame = 0;
        celebrating = true;
        timer.restart();
        repaint();
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!celebrating) return;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < 32; i++) {
            int x = random.nextInt(Math.max(1, getWidth()));
            int y = (frame * 9 + i * 19) % Math.max(1, getHeight());
            int size = 5 + random.nextInt(8);
            g2.setColor(new Color(150 + random.nextInt(90), 120 + random.nextInt(100), 180 + random.nextInt(70)));
            g2.fillOval(x, y, size, size);
        }
        g2.setColor(new Color(80, 60, 120, Math.max(0, 180 - frame * 3)));
        g2.setFont(new Font("SansSerif", Font.BOLD, 28));
        String text = "🎉 Great job!";
        int x = Math.max(10, (getWidth() - g2.getFontMetrics().stringWidth(text)) / 2);
        g2.drawString(text, x, 45);
        g2.dispose();
    }
}
