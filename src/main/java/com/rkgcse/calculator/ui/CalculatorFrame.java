package com.rkgcse.calculator.ui;

import com.rkgcse.calculator.ai.NaturalLanguageEngine;
import com.rkgcse.calculator.history.CalculationHistory;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class CalculatorFrame extends JFrame {
    private final JTextField input = new JTextField();
    private final JLabel result = new JLabel("Result: —");
    private final DefaultListModel<String> historyModel = new DefaultListModel<>();
    private final NaturalLanguageEngine engine = new NaturalLanguageEngine();
    private final CalculationHistory history = new CalculationHistory();
    private final DecimalFormat format = new DecimalFormat("0.##########");

    public CalculatorFrame() {
        setTitle("AI-Powered Calculator");
        setSize(620, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildUi();
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("AI-Powered Calculator");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        root.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(8, 8));
        input.setFont(new Font("SansSerif", Font.PLAIN, 18));
        input.setToolTipText("Try: 25% of 840, square root of 144, or 12 multiplied by 8");
        center.add(input, BorderLayout.NORTH);

        JButton calculate = new JButton("Calculate");
        calculate.addActionListener(e -> calculate());
        input.addActionListener(e -> calculate());

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(calculate);
        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> input.setText(""));
        controls.add(clear);
        center.add(controls, BorderLayout.CENTER);

        result.setFont(new Font("SansSerif", Font.BOLD, 20));
        center.add(result, BorderLayout.SOUTH);
        root.add(center, BorderLayout.CENTER);

        JList<String> historyList = new JList<>(historyModel);
        historyList.setBorder(BorderFactory.createTitledBorder("Calculation History"));
        JScrollPane scroll = new JScrollPane(historyList);
        scroll.setPreferredSize(new Dimension(280, 0));
        root.add(scroll, BorderLayout.EAST);

        setContentPane(root);
    }

    private void calculate() {
        String expression = input.getText().trim();
        try {
            double value = engine.evaluate(expression);
            String formatted = format.format(value);
            result.setText("Result: " + formatted);
            history.add(expression, value);
            historyModel.add(0, expression + " = " + formatted);
        } catch (RuntimeException ex) {
            result.setText("Error: " + ex.getMessage());
        }
    }
}
