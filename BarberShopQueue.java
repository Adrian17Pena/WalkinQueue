import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class BarberShopQueue extends JFrame {

    static class Customer {
        String firstName, lastName;
        long joinTime;

        Customer(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.joinTime = System.currentTimeMillis();
        }

        public String getWaitingTime() {
            long seconds = (System.currentTimeMillis() - joinTime) / 1000;
            return String.format("%02d:%02d", seconds / 60, seconds % 60);
        }

        public String toString() {
            return firstName + " " + lastName;
        }
    }

    private ArrayList<Customer> waitingList = new ArrayList<>();
    private JTextField firstNameField, lastNameField;
    private JTextArea queueArea;
    private JButton addButton, removeButton;
    private Timer timer;

    public BarberShopQueue() {
        setTitle("Barbershop Waiting List");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        panel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        panel.add(firstNameField);

        panel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        addButton = new JButton("Add Customer");
        removeButton = new JButton("Serve Next Customer");

        panel.add(addButton);
        panel.add(removeButton);

        queueArea = new JTextArea();
        queueArea.setEditable(false);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(queueArea), BorderLayout.CENTER);

        addButton.addActionListener(e -> addCustomer());
        removeButton.addActionListener(e -> removeCustomer());

        loadQueueFromFile();

        timer = new Timer(1000, e -> updateQueueDisplay());
        timer.start();

        updateQueueDisplay();
    }

    private void addCustomer() {
        String first = firstNameField.getText().trim();
        String last = lastNameField.getText().trim();

        if(first.equalsIgnoreCase("Done")) {
            saveQueueToFile();
            JOptionPane.showMessageDialog(this, "Queue saved successfully!");
            return;
        }

        if(first.isEmpty() || last.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both names.");
            return;
        }

        waitingList.add(new Customer(first, last));

        firstNameField.setText("");
        lastNameField.setText("");

        updateQueueDisplay();
    }

    private void removeCustomer() {
        if(waitingList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "The queue is empty.");
            return;
        }

        Customer served = waitingList.remove(0);

        JOptionPane.showMessageDialog(this, served + " is now being served.");

        saveQueueToFile();
        updateQueueDisplay();
    }

    private void updateQueueDisplay() {
        queueArea.setText("");

        for(int i = 0; i < waitingList.size(); i++) {
            Customer c = waitingList.get(i);

            queueArea.append(
                (i + 1) + ". " + c +
                " - Waiting: " + c.getWaitingTime() + "\n"
            );
        }
    }

    private void saveQueueToFile() {
        try {
            File folder = new File("BarberShopData");

            if(!folder.exists()) {
                folder.mkdir();
            }

            FileWriter writer = new FileWriter(
                "BarberShopData/barbershop_queue.txt"
            );

            for(Customer c : waitingList) {
                writer.write(
                    c.firstName + "," +
                    c.lastName + "," +
                    c.joinTime + "\n"
                );
            }

            writer.close();

        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving queue.");
        }
    }

    private void loadQueueFromFile() {
        File file = new File("BarberShopData/barbershop_queue.txt");

        if(!file.exists()) {
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if(data.length == 3) {
                    Customer c = new Customer(data[0], data[1]);
                    c.joinTime = Long.parseLong(data[2]);
                    waitingList.add(c);
                }
            }

            reader.close();

        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading queue.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BarberShopQueue().setVisible(true);
        });
    }
}