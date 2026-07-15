import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BarberShopQueue extends JFrame {

    // Customer class
    static class Customer {
        String firstName;
        String lastName;

        Customer(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return firstName + " " + lastName;
        }
    }

    // Waiting list
    private ArrayList<Customer> waitingList = new ArrayList<>();

    // GUI Components
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextArea queueArea;
    private JButton addButton;

    public BarberShopQueue() {
        setTitle("Barbershop Waiting List");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        inputPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        inputPanel.add(firstNameField);

        inputPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        inputPanel.add(lastNameField);

        addButton = new JButton("Add to Queue");
        inputPanel.add(addButton);

        // Empty label for spacing
        inputPanel.add(new JLabel(""));

        // Queue Display
        queueArea = new JTextArea();
        queueArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(queueArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button Action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
    }

    private void addCustomer() {
        String first = firstNameField.getText().trim();
        String last = lastNameField.getText().trim();

        if (first.isEmpty() || last.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both first and last name.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Customer customer = new Customer(first, last);
        waitingList.add(customer);

        updateQueueDisplay();

        firstNameField.setText("");
        lastNameField.setText("");
        firstNameField.requestFocus();
    }

    private void updateQueueDisplay() {
        queueArea.setText("");

        for (int i = 0; i < waitingList.size(); i++) {
            queueArea.append((i + 1) + ". " + waitingList.get(i) + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BarberShopQueue().setVisible(true);
        });
    }
}