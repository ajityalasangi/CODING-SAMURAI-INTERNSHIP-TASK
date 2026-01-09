package ATM.ui;

import javax.swing.*;
import java.awt.*;
import ATM.service.AccountService;
import ATM.model.Account;

public class Signup {

    JFrame frame;
    JTextField nameField, accField;
    JPasswordField pinField, confirmPinField;
    JButton signupBtn, backBtn;

    public Signup() {
        frame = new JFrame("ATM - Create Account");
        frame.setSize(420, 380);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 40, 80));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("CREATE ACCOUNT");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField = new JTextField();
        accField = new JTextField();
        pinField = new JPasswordField();
        confirmPinField = new JPasswordField();

        styleInput(nameField);
        styleInput(accField);
        styleInput(pinField);
        styleInput(confirmPinField);

        signupBtn = new JButton("SIGN UP");
        backBtn = new JButton("BACK TO LOGIN");

        styleButton(signupBtn, new Color(0, 150, 0));
        styleButton(backBtn, new Color(100, 100, 100));

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));

        panel.add(createLabel("Name"));
        panel.add(nameField);

        panel.add(createLabel("Account Number"));
        panel.add(accField);

        panel.add(createLabel("PIN"));
        panel.add(pinField);

        panel.add(createLabel("Confirm PIN"));
        panel.add(confirmPinField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(signupBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(backBtn);

        frame.add(panel);
        signupLogic();
        frame.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    private void styleInput(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    }

    private void signupLogic() {
        signupBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                int accNum = Integer.parseInt(accField.getText());
                int pin = Integer.parseInt(new String(pinField.getPassword()));
                int confirmPin = Integer.parseInt(new String(confirmPinField.getPassword()));

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Name cannot be empty");
                    return;
                }

                if (pin != confirmPin) {
                    JOptionPane.showMessageDialog(frame, "PINs do not match");
                    return;
                }

                Account acc = new Account(accNum, name, pin);
                if (AccountService.createAccount(acc)) {
                    JOptionPane.showMessageDialog(frame, "Account Created Successfully");
                    frame.dispose();
                    new Login();
                } else {
                    JOptionPane.showMessageDialog(frame, "Account already exists");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid numeric values");
            }
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            new Login();
        });
    }
}
