package com.rkgcse.calculator.ui;

import javax.sound.sampled.*;

/** Generates a short three-note success chime without external audio files. */
public final class CelebrationSound {
    private CelebrationSound() { }

    public static void play() {
        Thread soundThread = new Thread(() -> {
            try {
                playTone(523.25, 100);
                playTone(659.25, 100);
                playTone(783.99, 170);
            } catch (Exception ignored) {
                ToolkitFallback.beep();
            }
        }, "calculator-success-sound");
        soundThread.setDaemon(true);
        soundThread.start();
    }

    private static void playTone(double frequency, int milliseconds) throws LineUnavailableException {
        float sampleRate = 44100f;
        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
        try (SourceDataLine line = AudioSystem.getSourceDataLine(format)) {
            line.open(format);
            line.start();
            byte[] buffer = new byte[(int) (sampleRate * milliseconds / 1000)];
            for (int i = 0; i < buffer.length; i++) {
                double envelope = Math.min(1.0, i / 500.0) * Math.min(1.0, (buffer.length - i) / 1800.0);
                buffer[i] = (byte) (Math.sin(2 * Math.PI * frequency * i / sampleRate) * 95 * envelope);
            }
            line.write(buffer, 0, buffer.length);
            line.drain();
        }
    }

    private static final class ToolkitFallback {
        static void beep() { java.awt.Toolkit.getDefaultToolkit().beep(); }
    }
}
