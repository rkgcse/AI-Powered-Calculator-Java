package com.rkgcse.calculator.ui;

import com.rkgcse.calculator.ai.NaturalLanguageEngine;
import com.rkgcse.calculator.core.CalculatorEngine;
import com.rkgcse.calculator.history.CalculationHistory;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class CalculatorFrame extends JFrame {
    private final JTextField display = new JTextField();
    private final JLabel result = new JLabel("Ready", SwingConstants.CENTER);
    private final JLabel modeLabel = new JLabel("Simple Calculator", SwingConstants.CENTER);
    private final JLabel historyLabel = new JLabel("", SwingConstants.CENTER);
    private final JPanel scientificPanel = new JPanel(new GridLayout(2, 6, 8, 8));
    private final JButton modeButton = new AnimatedButton("⚗ Scientific Mode", COLORS.ACCENT, COLORS.ACCENT_HOVER, Color.WHITE);
    private final CalculatorEngine calculator = new CalculatorEngine();
    private final NaturalLanguageEngine ai = new NaturalLanguageEngine();
    private final CalculationHistory history = new CalculationHistory();
    private final DecimalFormat format = new DecimalFormat("0.##########");
    private final CelebrationPanel celebration = new CelebrationPanel();
    private boolean scientific;
    private boolean freshResult;

    private static final class COLORS {
        static final Color BG = new Color(247, 249, 255);
        static final Color CARD = new Color(255, 255, 255);
        static final Color DISPLAY = new Color(250, 248, 255);
        static final Color TEXT = new Color(55, 52, 75);
        static final Color MUTED = new Color(112, 105, 130);
        static final Color SOFT = new Color(236, 232, 249);
        static final Color SOFT_HOVER = new Color(224, 216, 244);
        static final Color ACCENT = new Color(133, 105, 193);
        static final Color ACCENT_HOVER = new Color(116, 88, 177);
        static final Color OP = new Color(235, 243, 255);
        static final Color OP_HOVER = new Color(217, 231, 250);
        static final Color EQUAL = new Color(117, 177, 150);
        static final Color EQUAL_HOVER = new Color(96, 157, 130);
        static final Color DANGER = new Color(247, 224, 232);
    }

    public CalculatorFrame() {
        setTitle("AI-Powered Calculator");
        setSize(720, 850);
        setMinimumSize(new Dimension(600, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildUi();
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(COLORS.BG);
        root.setBorder(new EmptyBorder(20, 28, 12, 28));

        JLabel title = new JLabel("AI-Powered Calculator", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 29));
        title.setForeground(COLORS.TEXT);
        JLabel subtitle = new JLabel("Fast everyday maths • scientific tools • natural-language commands", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(COLORS.MUTED);
        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.add(title);
        heading.add(Box.createVerticalStrut(4));
        heading.add(subtitle);
        root.add(heading, BorderLayout.NORTH);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COLORS.CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(224, 221, 237)),
                new EmptyBorder(18, 22, 18, 22)));

        modeLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        modeLabel.setForeground(COLORS.ACCENT);
        modeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(modeLabel);
        card.add(Box.createVerticalStrut(10));

        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("SansSerif", Font.BOLD, 27));
        display.setForeground(COLORS.TEXT);
        display.setBackground(COLORS.DISPLAY);
        display.setCaretColor(COLORS.ACCENT);
        display.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 216, 237)),
                new EmptyBorder(12, 14, 12, 14)));
        display.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        card.add(display);
        card.add(Box.createVerticalStrut(6));

        result.setFont(new Font("SansSerif", Font.BOLD, 22));
        result.setForeground(COLORS.TEXT);
        result.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(result);
        historyLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        historyLabel.setForeground(COLORS.MUTED);
        historyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(historyLabel);
        card.add(Box.createVerticalStrut(10));

        JPanel simple = new JPanel(new GridLayout(5, 4, 8, 8));
        simple.setOpaque(false);
        addButton(simple, "C", COLORS.DANGER, COLORS.SOFT_HOVER, COLORS.TEXT, e -> clear());
        addButton(simple, "⌫", COLORS.SOFT, COLORS.SOFT_HOVER, COLORS.TEXT, e -> backspace());
        addButton(simple, "(", COLORS.OP, COLORS.OP_HOVER, COLORS.TEXT, e -> append("("));
        addButton(simple, ")", COLORS.OP, COLORS.OP_HOVER, COLORS.TEXT, e -> append(")"));
        addButton(simple, "7", COLORS.SOFT, COLORS.SOFT_HOVER, COLORS.TEXT, e -> append("7"));
        addButton(simple, "8", COLORS.SOFT, COLORS.SOFT_HOVER, COLORS.TEXT, e -> append("8"));
        addButton(simple, "9", COLORS.SOFT, COLORS.SOFT_HOVER, COLORS.TEXT, e -> append("9"));
        addButton(simple, "÷", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("/"));
        addButton(simple, "4", COLORS.SOFT, COLORS.SOFT_HOVER, COLORS.TEXT, e -> append("4"));
        addButton(simple, "5", COLORS.SOFT, COLORS.SOFT_HOVER, COLORS.TEXT, e -> append("5"));
        addButton(simple, "6", COLORS.SOFT, COLORS.SOFT_HOVER, COLORS.TEXT, e -> append("6"));
        addButton(simple, "×", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("*"));
        addButton(simple, "1", COLORS.SOFT, COLORS.SOFT_HOVER, COLORS.TEXT, e -> append("1"));
        addButton(simple, "2", COLORS.SOFT, COLORS.SOFT_HOVER, COLORS.TEXT, e -> append("2"));
        addButton(simple, "3", COLORS.SOFT, COLORS.SOFT_HOVER, COLORS.TEXT, e -> append("3"));
        addButton(simple, "−", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("-"));
        addButton(simple, "0", COLORS.SOFT, COLORS.SOFT_HOVER, COLORS.TEXT, e -> append("0"));
        addButton(simple, ".", COLORS.SOFT, COLORS.SOFT_HOVER, COLORS.TEXT, e -> append("."));
        addButton(simple, "%", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("%"));
        addButton(simple, "+", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("+"));
        addButton(simple, "=", COLORS.EQUAL, COLORS.EQUAL_HOVER, Color.WHITE, e -> calculate());
        card.add(simple);
        card.add(Box.createVerticalStrut(10));

        buildScientificPanel();
        scientificPanel.setVisible(false);
        card.add(scientificPanel);
        card.add(Box.createVerticalStrut(10));

        JPanel controls = new JPanel(new GridLayout(1, 3, 8, 8));
        controls.setOpaque(false);
        addButton(controls, "🤖 AI", COLORS.ACCENT, COLORS.ACCENT_HOVER, Color.WHITE, e -> aiCalculate());
        addButton(controls, "🧹 Clear", COLORS.DANGER, COLORS.SOFT_HOVER, COLORS.TEXT, e -> clear());
        modeButton.addActionListener(e -> toggleScientific());
        controls.add(modeButton);
        card.add(controls);
        card.add(Box.createVerticalStrut(12));

        JLabel help = new JLabel("Try: 25% of 840  •  sqrt(144)  •  2^8  •  sin(30)  •  5!", SwingConstants.CENTER);
        help.setFont(new Font("SansSerif", Font.PLAIN, 12));
        help.setForeground(COLORS.MUTED);
        help.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(help);

        JPanel centered = new JPanel(new GridBagLayout());
        centered.setOpaque(false);
        centered.add(card);
        root.add(centered, BorderLayout.CENTER);

        JLabel footer = new JLabel("made with ❤️ by Raushan kumar", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 13));
        footer.setForeground(COLORS.MUTED);
        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);
        setGlassPane(celebration);
        celebration.setVisible(true);
        display.addActionListener(e -> calculate());
        display.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) calculate();
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) clear();
            }
        });
    }

    private void buildScientificPanel() {
        scientificPanel.setOpaque(false);
        addButton(scientificPanel, "√", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("sqrt("));
        addButton(scientificPanel, "sin", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("sin("));
        addButton(scientificPanel, "cos", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("cos("));
        addButton(scientificPanel, "tan", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("tan("));
        addButton(scientificPanel, "log", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("log("));
        addButton(scientificPanel, "ln", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("ln("));
        addButton(scientificPanel, "xʸ", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("^"));
        addButton(scientificPanel, "x!", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("!"));
        addButton(scientificPanel, "π", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("pi"));
        addButton(scientificPanel, "e", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("e"));
        addButton(scientificPanel, "abs", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("abs("));
        addButton(scientificPanel, "exp", COLORS.OP, COLORS.OP_HOVER, COLORS.ACCENT, e -> append("exp("));
    }

    private void addButton(JPanel panel, String text, Color bg, Color hover, Color fg, ActionListener action) {
        AnimatedButton button = new AnimatedButton(text, bg, hover, fg);
        button.addActionListener(action);
        panel.add(button);
    }

    private void append(String value) {
        if (freshResult) {
            display.setText("");
            freshResult = false;
        }
        display.setText(display.getText() + value);
        display.requestFocusInWindow();
    }

    private void calculate() {
        String expression = display.getText().trim();
        if (expression.isEmpty()) return;
        try {
            double value;
            try {
                value = calculator.evaluateExpression(expression);
            } catch (RuntimeException basicParserError) {
                value = ai.evaluate(expression);
            }
            showResult(value, expression);
        } catch (RuntimeException ex) {
            result.setText("⚠ " + ex.getMessage());
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private void aiCalculate() {
        String expression = display.getText().trim();
        if (expression.isEmpty()) return;
        try {
            double value = ai.evaluate(expression);
            showResult(value, expression);
        } catch (RuntimeException ex) {
            result.setText("Try a command like: 25% of 840");
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private void showResult(double value, String expression) {
        String formatted = format.format(value);
        display.setText(formatted);
        result.setText("✓ Result: " + formatted);
        history.add(expression, value);
        historyLabel.setText("Last: " + expression + " = " + formatted);
        freshResult = true;
        celebrate();
    }

    private void celebrate() {
        celebration.setVisible(true);
        celebration.celebrate();
        CelebrationSound.play();
    }

    private void clear() {
        display.setText("");
        result.setText("Ready");
        historyLabel.setText("");
        freshResult = false;
        display.requestFocusInWindow();
    }

    private void backspace() {
        String text = display.getText();
        if (!text.isEmpty()) display.setText(text.substring(0, text.length() - 1));
        display.requestFocusInWindow();
    }

    private void toggleScientific() {
        scientific = !scientific;
        scientificPanel.setVisible(scientific);
        modeLabel.setText(scientific ? "Scientific Calculator" : "Simple Calculator");
        modeButton.setText(scientific ? "↩ Simple Mode" : "⚗ Scientific Mode");
        pack();
        setSize(Math.max(getWidth(), 720), scientific ? 850 : 780);
        setLocationRelativeTo(null);
        display.requestFocusInWindow();
    }
}
