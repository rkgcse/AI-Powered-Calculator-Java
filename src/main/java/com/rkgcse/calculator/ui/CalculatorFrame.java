package com.rkgcse.calculator.ui;

import com.rkgcse.calculator.ai.NaturalLanguageEngine;
import com.rkgcse.calculator.history.CalculationHistory;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class CalculatorFrame extends JFrame {
    private final JTextField display = new JTextField();
    private final JLabel result = new JLabel("0", SwingConstants.CENTER);
    private final JLabel modeLabel = new JLabel("Simple Calculator", SwingConstants.CENTER);
    private final DefaultListModel<String> historyModel = new DefaultListModel<>();
    private final NaturalLanguageEngine ai = new NaturalLanguageEngine();
    private final CalculationHistory history = new CalculationHistory();
    private final DecimalFormat format = new DecimalFormat("0.##########");
    private final CelebrationPanel celebration = new CelebrationPanel();
    private boolean scientific;
    private double firstNumber;
    private String operator = "";
    private boolean freshResult = true;

    private static final Color BG = new Color(246, 243, 255);
    private static final Color CARD = new Color(255, 255, 255);
    private static final Color TEXT = new Color(55, 48, 75);
    private static final Color ACCENT = new Color(132, 102, 190);
    private static final Color SOFT = new Color(235, 228, 249);

    public CalculatorFrame() {
        setTitle("AI-Powered Calculator");
        setSize(700, 780);
        setMinimumSize(new Dimension(560, 680));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildUi();
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(22, 28, 14, 28));

        JLabel title = new JLabel("AI-Powered Calculator", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(TEXT);
        root.add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 217, 240)),
                new EmptyBorder(20, 24, 20, 24)));

        modeLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        modeLabel.setForeground(ACCENT);
        card.add(modeLabel);
        card.add(Box.createVerticalStrut(10));

        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("SansSerif", Font.BOLD, 25));
        display.setForeground(TEXT);
        display.setBackground(new Color(250, 248, 253));
        display.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 217, 240)),
                new EmptyBorder(12, 14, 12, 14)));
        card.add(display);
        card.add(Box.createVerticalStrut(8));

        result.setFont(new Font("SansSerif", Font.BOLD, 25));
        result.setForeground(TEXT);
        result.setBorder(new EmptyBorder(4, 0, 4, 0));
        card.add(result);

        JPanel buttons = new JPanel(new GridLayout(0, 4, 9, 9));
        buttons.setOpaque(false);
        addButton(buttons, "C", e -> clear());
        addButton(buttons, "⌫", e -> backspace());
        addButton(buttons, "%", e -> append("%"));
        addButton(buttons, "÷", e -> chooseOperator("/"));
        addButton(buttons, "7", e -> append("7"));
        addButton(buttons, "8", e -> append("8"));
        addButton(buttons, "9", e -> append("9"));
        addButton(buttons, "×", e -> chooseOperator("*"));
        addButton(buttons, "4", e -> append("4"));
        addButton(buttons, "5", e -> append("5"));
        addButton(buttons, "6", e -> append("6"));
        addButton(buttons, "−", e -> chooseOperator("-"));
        addButton(buttons, "1", e -> append("1"));
        addButton(buttons, "2", e -> append("2"));
        addButton(buttons, "3", e -> append("3"));
        addButton(buttons, "+", e -> chooseOperator("+"));
        addButton(buttons, "0", e -> append("0"));
        addButton(buttons, ".", e -> append("."));
        addButton(buttons, "AI", e -> aiCalculate());
        addButton(buttons, "=", e -> calculate());
        card.add(buttons);
        card.add(Box.createVerticalStrut(12));

        JButton mode = new JButton("Switch to Scientific");
        styleButton(mode, ACCENT, Color.WHITE);
        mode.addActionListener(e -> toggleScientific(card));
        card.add(mode);

        gbc.gridy = 0; gbc.weighty = 1; gbc.anchor = GridBagConstraints.CENTER;
        content.add(card, gbc);
        root.add(content, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(8, 5));
        bottom.setOpaque(false);
        JLabel footer = new JLabel("made with ❤️ by Raushan kumar", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 13));
        footer.setForeground(new Color(105, 94, 125));
        bottom.add(footer, BorderLayout.SOUTH);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
        setGlassPane(celebration);
        celebration.setVisible(true);
        display.addActionListener(e -> aiCalculate());
    }

    private void addButton(JPanel panel, String text, ActionListener action) {
        JButton b = new JButton(text);
        styleButton(b, SOFT, TEXT);
        b.addActionListener(action);
        panel.add(b);
    }

    private void styleButton(JButton b, Color bg, Color fg) {
        b.setBackground(bg); b.setForeground(fg);
        b.setFont(new Font("SansSerif", Font.BOLD, 17));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 211, 237)),
                new EmptyBorder(10, 8, 10, 8)));
    }

    private void append(String value) {
        if (freshResult) { display.setText(""); freshResult = false; }
        display.setText(display.getText() + value);
    }

    private void chooseOperator(String op) {
        try { firstNumber = Double.parseDouble(display.getText()); }
        catch (NumberFormatException e) { return; }
        operator = op; freshResult = true;
    }

    private void calculate() {
        try {
            double second = Double.parseDouble(display.getText());
            double value = switch (operator) {
                case "+" -> firstNumber + second;
                case "-" -> firstNumber - second;
                case "*" -> firstNumber * second;
                case "/" -> second == 0 ? Double.NaN : firstNumber / second;
                default -> second;
            };
            if (Double.isNaN(value) || Double.isInfinite(value)) throw new ArithmeticException("Invalid calculation");
            showResult(value, display.getText());
            operator = "";
        } catch (RuntimeException ex) { result.setText("Error: " + ex.getMessage()); }
    }

    private void aiCalculate() {
        try {
            String expression = display.getText().trim();
            double value = ai.evaluate(expression);
            showResult(value, expression);
        } catch (RuntimeException ex) { result.setText("Try: 25% of 840 or square root of 144"); }
    }

    private void showResult(double value, String expression) {
        String formatted = format.format(value);
        display.setText(formatted);
        result.setText("Result: " + formatted);
        history.add(expression, value);
        historyModel.add(0, expression + " = " + formatted);
        freshResult = true;
        celebrate();
    }

    private void celebrate() {
        Toolkit.getDefaultToolkit().beep();
        celebration.setVisible(true);
        celebration.celebrate();
    }

    private void clear() {
        display.setText(""); result.setText("0"); operator = ""; firstNumber = 0; freshResult = false;
    }

    private void backspace() {
        String s = display.getText();
        if (!s.isEmpty()) display.setText(s.substring(0, s.length() - 1));
    }

    private void toggleScientific(JPanel card) {
        scientific = !scientific;
        modeLabel.setText(scientific ? "Scientific Calculator" : "Simple Calculator");
        JOptionPane.showMessageDialog(this,
                scientific ? "Scientific mode enabled. Use AI input for sin, cos, tan, power and square root."
                           : "Simple mode enabled.",
                "Calculator Mode", JOptionPane.INFORMATION_MESSAGE);
    }
}
