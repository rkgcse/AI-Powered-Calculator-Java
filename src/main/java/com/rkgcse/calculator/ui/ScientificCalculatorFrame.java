package com.rkgcse.calculator.ui;

import com.rkgcse.calculator.core.CalculatorEngine;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/** Scientific mode with dedicated scientific operations. */
public class ScientificCalculatorFrame extends JFrame {
    private final JTextField input = new JTextField();
    private final JLabel result = new JLabel("Result: 0", SwingConstants.CENTER);
    private final CalculatorEngine calculator = new CalculatorEngine();
    private final DecimalFormat format = new DecimalFormat("0.##########");

    public ScientificCalculatorFrame() {
        setTitle("AI-Powered Calculator — Scientific Mode");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        buildUi();
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(new Color(246, 243, 255));
        root.setBorder(BorderFactory.createEmptyBorder(22, 28, 18, 28));

        JLabel title = new JLabel("Scientific Calculator", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(new Color(55, 48, 75));
        root.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);
        center.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 217, 240)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        input.setFont(new Font("SansSerif", Font.BOLD, 23));
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setToolTipText("Examples: 2 power 8, square root of 144, sin 30");
        center.add(input);
        center.add(Box.createVerticalStrut(12));
        result.setFont(new Font("SansSerif", Font.BOLD, 22));
        result.setForeground(new Color(132, 102, 190));
        center.add(result);
        center.add(Box.createVerticalStrut(18));

        JPanel grid = new JPanel(new GridLayout(3, 3, 10, 10));
        grid.setOpaque(false);
        add(grid, "√ Square Root", () -> run(() -> calculator.sqrt(Double.parseDouble(input.getText()))));
        add(grid, "xʸ Power", () -> {
            String[] n = input.getText().trim().split("\\s+");
            if (n.length != 2) throw new IllegalArgumentException("Enter: base exponent");
            run(() -> calculator.power(Double.parseDouble(n[0]), Double.parseDouble(n[1])));
        });
        add(grid, "% Percent", () -> run(() -> Double.parseDouble(input.getText()) / 100.0));
        add(grid, "sin", () -> run(() -> calculator.sin(Double.parseDouble(input.getText()))));
        add(grid, "cos", () -> run(() -> calculator.cos(Double.parseDouble(input.getText()))));
        add(grid, "tan", () -> run(() -> calculator.tan(Double.parseDouble(input.getText()))));
        add(grid, "π", () -> input.setText(String.valueOf(Math.PI)));
        add(grid, "e", () -> input.setText(String.valueOf(Math.E)));
        add(grid, "Clear", () -> { input.setText(""); result.setText("Result: 0"); });
        center.add(grid);
        center.add(Box.createVerticalStrut(16));

        JButton back = new JButton("← Back to Simple Calculator");
        back.setBackground(new Color(132, 102, 190));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        back.addActionListener(e -> dispose());
        center.add(back);
        root.add(center, BorderLayout.CENTER);

        JLabel footer = new JLabel("made with ❤️ by Raushan kumar", SwingConstants.CENTER);
        footer.setForeground(new Color(105, 94, 125));
        footer.setFont(new Font("SansSerif", Font.PLAIN, 13));
        root.add(footer, BorderLayout.SOUTH);
        setContentPane(root);
    }

    private void add(JPanel panel, String label, Runnable action) {
        JButton button = new JButton(label);
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setBackground(new Color(235, 228, 249));
        button.setForeground(new Color(55, 48, 75));
        button.setFocusPainted(false);
        button.addActionListener(e -> { try { action.run(); } catch (RuntimeException ex) { result.setText("Error: " + ex.getMessage()); } });
        panel.add(button);
    }

    private void run(java.util.function.DoubleSupplier operation) {
        double value = operation.getAsDouble();
        String formatted = format.format(value);
        result.setText("Result: " + formatted);
        input.setText(formatted);
        Toolkit.getDefaultToolkit().beep();
    }
}
