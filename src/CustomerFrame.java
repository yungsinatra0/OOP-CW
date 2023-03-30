

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class CustomerFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public CustomerFrame(User currentUser) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(String.format("Welcome, %s!", currentUser.getSurname()));
		lblNewLabel.setBounds(10, 11, 158, 26);
		contentPane.add(lblNewLabel);
	}

}
