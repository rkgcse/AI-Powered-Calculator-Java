package com.rkgcse.calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/** Animated confetti celebration overlay. */
public class CelebrationPanel extends JPanel {
    private static final int PIECES = 70;
    private final Random random = new Random();
    private final Piece[] pieces = new Piece[PIECES];
    private final Timer timer;
    private int frame;
    private boolean celebrating;

    public CelebrationPanel() {
        setOpaque(false);
        for (int i = 0; i < PIECES; i++) pieces[i] = new Piece();
        timer = new Timer(25, e -> advanceAnimation());
    }

    private void advanceAnimation() {
        frame++;
        for (Piece p : pieces) p.update();
        if (frame > 95) {
            celebrating = false;
            timer.stop();
        }
        repaint();
    }

    public void celebrate() {
        frame = 0;
        celebrating = true;
        for (Piece p : pieces) p.reset(getWidth(), getHeight(), random);
        timer.restart();
        repaint();
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!celebrating) return;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int alpha = Math.min(210, 90 + frame * 2);
        g2.setColor(new Color(255, 255, 255, Math.max(0, 35 - frame / 3)));
        g2.fillRoundRect(getWidth() / 2 - 170, 22, 340, 55, 28, 28);
        g2.setColor(new Color(80, 65, 115, alpha));
        g2.setFont(new Font("SansSerif", Font.BOLD, 26));
        String message = "✨ Calculation complete! ✨";
        int textX = (getWidth() - g2.getFontMetrics().stringWidth(message)) / 2;
        g2.drawString(message, textX, 58);

        for (Piece p : pieces) {
            g2.setColor(new Color(p.color.getRed(), p.color.getGreen(), p.color.getBlue(), Math.max(0, 230 - frame * 2)));
            g2.translate(p.x, p.y);
            g2.rotate(p.angle);
            g2.fillRoundRect(-p.size / 2, -p.size / 3, p.size, p.size / 2, 4, 4);
            g2.rotate(-p.angle);
            g2.translate(-p.x, -p.y);
        }
        g2.dispose();
    }

    private static class Piece {
        double x, y, vx, vy, angle, spin;
        int size;
        Color color;
        int width, height;

        void reset(int width, int height, Random r) {
            this.width = Math.max(width, 1);
            this.height = Math.max(height, 1);
            x = this.width / 2.0 + (r.nextDouble() - 0.5) * Math.min(260, this.width);
            y = 75 + r.nextDouble() * 35;
            vx = (r.nextDouble() - 0.5) * 4.5;
            vy = 1.5 + r.nextDouble() * 4.0;
            angle = r.nextDouble() * Math.PI;
            spin = (r.nextDouble() - 0.5) * 0.25;
            size = 7 + r.nextInt(9);
            color = new Color(105 + r.nextInt(130), 90 + r.nextInt(130), 145 + r.nextInt(105));
        }

        void update() {
            x += vx;
            y += vy;
            vy += 0.055;
            angle += spin;
            if (y > height + 30) {
                y = 72;
                x = Math.random() * width;
                vy = 1.5 + Math.random() * 3.0;
            }
        }
    }
}
