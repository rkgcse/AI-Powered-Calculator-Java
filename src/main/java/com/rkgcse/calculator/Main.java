package com.rkgcse.calculator;

import com.rkgcse.calculator.ui.CalculatorFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorFrame().setVisible(true));
    }
}
