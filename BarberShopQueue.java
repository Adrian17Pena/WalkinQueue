import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BarberShopQueue extends JFrame {

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

    private ArrayList<Customer> waitingList = new ArrayList<>();

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextArea queueArea;

    private JButton addButton;
    private JButton removeButton;


    public BarberShopQueue() {

        setTitle("Barbershop Waiting List");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));


        inputPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        inputPanel.add(firstNameField);


        inputPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        inputPanel.add(lastNameField);


        addButton = new JButton("Add to Queue");
        removeButton = new JButton("Serve Next Customer");

        inputPanel.add(addButton);
        inputPanel.add(removeButton);


        queueArea = new JTextArea();
        queueArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(queueArea);


        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);



        // Add customer button
        addButton.addActionListener(e -> {
            addCustomer();
        });


        // Remove first customer button
        removeButton.addActionListener(e -> {
            removeCustomer();
        });

    }

    private void addCustomer() {

        String first = firstNameField.getText().trim();
        String last = lastNameField.getText().trim();


        if(first.isEmpty() || last.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please enter both first and last name.");

            return;
        }


        waitingList.add(new Customer(first, last));

        updateQueueDisplay();


        firstNameField.setText("");
        lastNameField.setText("");

    }

    private void removeCustomer() {

        if(waitingList.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "The queue is empty.");

            return;
        }


        Customer finishedCustomer = waitingList.remove(0);


        JOptionPane.showMessageDialog(this,
                finishedCustomer + " has been served!");

        updateQueueDisplay();

    }

    private void updateQueueDisplay() {

        queueArea.setText("");


        for(int i = 0; i < waitingList.size(); i++) {

            queueArea.append(
                (i + 1) + ". " + waitingList.get(i) + "\n"
            );

        }

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new BarberShopQueue().setVisible(true);
        });

    }

}