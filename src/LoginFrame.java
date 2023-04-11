import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.HashMap;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public LoginFrame(HashMap<String, User> userList) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Creating array of usernames
        String userArray[] = userList.keySet().toArray(new String[userList.size()]);

        // Creating frame components
        JComboBox comboBox = new JComboBox(userArray);
        JLabel lblNewLabel_1 = new JLabel("User login");
        JLabel lblNewLabel_2 = new JLabel(String.format("You have selected: %s (%s)", comboBox.getSelectedItem(), userList.get(comboBox.getSelectedItem()) instanceof Admin ? "Admin" : "Customer"));
        JLabel lblNewLabel = new JLabel("Select user..");
        JButton btnNewButton = new JButton("Login");

        // Event listener on combo box that will change the label to the current selected user
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String selectedUser = (String) cb.getSelectedItem();
                lblNewLabel_2.setText(String.format("You have selected: %s (%s)", selectedUser, userList.get(selectedUser) instanceof Admin ? "Admin" : "Customer"));
            }
        });

        // Event listener on log in button that will exit current frame and open a new one based on chosen user
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isAdmin = userList.get(comboBox.getSelectedItem()) instanceof Admin;
                if (isAdmin) {
                    // If user is admin, open admin frame
                    AdminFrame aFrame = new AdminFrame((Admin) userList.get(comboBox.getSelectedItem()));
                    aFrame.setVisible(true);
                    dispose();
                } else {
                    // If user is customer, open customer frame
                    CustomerFrame cFrame = new CustomerFrame((Customer) userList.get(comboBox.getSelectedItem()));
                    cFrame.setVisible(true);
                    dispose();
                }
            }
        });

        // Setting up locations of frame components
        comboBox.setBounds(124, 90, 162, 32);
        contentPane.add(comboBox);

        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel.setBounds(166, 57, 95, 22);
        contentPane.add(lblNewLabel);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel_1.setBounds(148, 11, 113, 32);
        contentPane.add(lblNewLabel_1);

        btnNewButton.setBounds(156, 194, 89, 23);
        contentPane.add(btnNewButton);

        lblNewLabel_2.setBounds(124, 157, 235, 26);
        contentPane.add(lblNewLabel_2);
    }
}
