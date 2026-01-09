package ATM.ui;

import javax.swing.*;
import java.awt.*;
import ATM.service.AccountService;

public class Login {

    JFrame frame;
    JTextField accField;
    JPasswordField pinField;
    JButton loginBtn, createAccBtn;

    public Login() {
        frame = new JFrame("ATM");
        frame.setSize(420, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center screen

        // Main panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 40, 80)); // ATM blue
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title
        JLabel title = new JLabel("ATM LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Labels
        JLabel accLabel = new JLabel("Account Number");
        accLabel.setForeground(Color.WHITE);

        JLabel pinLabel = new JLabel("PIN");
        pinLabel.setForeground(Color.WHITE);

        // Inputs
        accField = new JTextField();
        pinField = new JPasswordField();

        styleInput(accField);
        styleInput(pinField);

        // Buttons
        loginBtn = new JButton("LOGIN");
        createAccBtn = new JButton("CREATE ACCOUNT");

        styleButton(loginBtn, new Color(0, 150, 0));     // green
        styleButton(createAccBtn, new Color(70, 130, 180)); // blue

        // Add components
        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(accLabel);
        panel.add(accField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(createAccBtn);

        frame.add(panel);
        loginLogic();
        frame.setVisible(true);
    }

    private void styleInput(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    }

    private void loginLogic() {
        loginBtn.addActionListener(e -> {
            try {
                int accNum = Integer.parseInt(accField.getText());
                int pin = Integer.parseInt(new String(pinField.getPassword()));

                if (AccountService.validateLogin(accNum, pin)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful");
                    frame.dispose();
                    // new Menu();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Credentials");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid numbers");
            }
        });

        createAccBtn.addActionListener(e -> {
            frame.dispose();
            new Signup();
        });
    }
}
