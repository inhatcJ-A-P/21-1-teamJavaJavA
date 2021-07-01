package bookproject.main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bookproject.DB;

public class ManagerLogin extends JFrame implements ActionListener {

	private JPanel p1;
	private JLabel lblIcon, lblId, lblPw;
	private JTextField tfId, tfPw;
	private JButton btnLogin, btnExit;
	private boolean check;

	public ManagerLogin(String title, int width, int height) {
		this.setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
		setResizable(false);

		p1 = new JPanel();
		p1.setLayout(new FlowLayout());

		ImageIcon icon = new ImageIcon("bookImage\\Intro.jpg");
		lblIcon = new JLabel(icon);
		p1.add(lblIcon);

		lblId = new JLabel("ID : ");
		tfId = new JTextField(10);
		tfId.addActionListener(this);
		lblPw = new JLabel("PW : ");
		tfPw = new JTextField(10);
		tfPw.addActionListener(this);
		btnLogin = new JButton("로그인");
		btnExit = new JButton("종료");

		btnLogin.addActionListener(this);
		btnExit.addActionListener(this);

		p1.add(lblId);
		p1.add(tfId);
		p1.add(lblPw);
		p1.add(tfPw);
		p1.add(btnLogin);
		p1.add(btnExit);

		add(p1);
		tfId.requestFocus();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		DB.init(); // D
		new ManagerLogin("관리자 로그인", 930, 430);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		check = false;

		Object obj = e.getSource();
		if (obj == btnLogin || obj == tfId || obj == tfPw) {
			admin();
			if (check == true) {
				new BookProgram("도서 관리 프로그램", 1000, 700);
			}
			else if (tfId.getText().equals("")|| tfPw.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력하세요.", "메시지", JOptionPane.INFORMATION_MESSAGE, null);
				tfId.setText("");
				tfPw.setText("");
				tfId.requestFocus();
			}
			else {
				JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인하세요.", "메시지", JOptionPane.INFORMATION_MESSAGE, null);
				tfId.setText("");
				tfPw.setText("");
				tfId.requestFocus();
			}

		}

		if (e.getSource() == btnExit) {
			System.exit(0);
		}
	}

	private void admin() {

		String strAdmin = "SELECT ID, PW FROM ADMIN";
		ResultSet rs = DB.getResultSet(strAdmin);

		try {

			while (rs.next()) {
				if (tfId.getText().equals(rs.getString(1)) && tfPw.getText().equals(rs.getString(2))) {
					check = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}