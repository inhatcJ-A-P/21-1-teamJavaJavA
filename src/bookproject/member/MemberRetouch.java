package bookproject.member;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bookproject.DB;

public class MemberRetouch extends JFrame implements ActionListener, KeyListener {

	private JButton btnok, btnback, btnsearch;
	private JTextField[] p2tf = new JTextField[4];
	private JTextField retouch;
	private MemberList main;

	public MemberRetouch(String Title, int width, int height, MemberList main) {
		this.main = main;
		this.setTitle(Title);
		setSize(width, height);
		// setLocation(900, 400);
		setLocationRelativeTo(this);

		JPanel p1 = new JPanel();
		JLabel p1lbl_1 = new JLabel("회원 주민번호:");
		retouch = new JTextField(12);
		retouch.addKeyListener(this);
		btnsearch = new JButton("검색");
		btnsearch.addActionListener(this);
		p1.add(p1lbl_1);
		p1.add(retouch);
		p1.add(btnsearch);

		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(4, 2));
		JLabel p2lbl_1 = new JLabel("이름");
		p2tf[0] = new JTextField();
		p2tf[0].addKeyListener(this);

		JLabel p2lbl_2 = new JLabel("주민번호");
		p2tf[1] = new JTextField();
		p2tf[1].setEditable(false);

		JLabel p2lbl_3 = new JLabel("연락처");
		p2tf[2] = new JTextField();
		p2tf[2].addKeyListener(this);

		JLabel p2lbl_4 = new JLabel("주소");
		p2tf[3] = new JTextField();
		p2tf[3].addKeyListener(this);

		p2.add(p2lbl_1);
		p2.add(p2tf[0]);
		p2.add(p2lbl_2);
		p2.add(p2tf[1]);
		p2.add(p2lbl_3);
		p2.add(p2tf[2]);
		p2.add(p2lbl_4);
		p2.add(p2tf[3]);

		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.CENTER));

		btnok = new JButton("수정");
		btnok.addActionListener(this);
		btnback = new JButton("돌아가기");
		btnback.addActionListener(this);
		p3.add(btnok);
		p3.add(btnback);

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);

		setVisible(true);
	}

	public static void main(String[] args) {
		// new MemberRetouch("ȸ������ ����",300, 200);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if (ob == btnback)
			dispose();
		else if (ob == btnsearch) {
			ResultSet re = DB.getResultSet("SELECT * FROM MEMBERS WHERE mb_NUM = '" + retouch.getText() + "' ");
			try {
				while (re.next()) {
					String name = re.getString("mb_name");
					String num = re.getString("mb_num");
					String phone = re.getString("mb_PHONE");
					String address = re.getString("mb_addr");

					p2tf[0].setText(name);
					p2tf[1].setText(num);
					p2tf[2].setText(phone);
					p2tf[3].setText(address);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (ob == btnok) {
			String update = "UPDATE JAVAJO.MEMBERS " + "SET mb_NAME='" + p2tf[0].getText() + "', " + "mb_PHONE='"
					+ p2tf[2].getText() + "', " + "mb_ADDR='" + p2tf[3].getText() + "' " + "WHERE mb_NUM='"
					+ p2tf[1].getText() + "' ";
			System.out.println(update);
			DB.executeQuery(update);
			JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			main.dbroad();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object obj = e.getSource();
		int ob = e.getKeyCode();
		if (obj == retouch) {
			if (ob == 10) {
				ResultSet re = DB.getResultSet("SELECT * FROM MEMBERS WHERE mb_NUM = '" + retouch.getText() + "' ");
				try {
					while (re.next()) {
						String name = re.getString("mb_name");
						String num = re.getString("mb_num");
						String phone = re.getString("mb_PHONE");
						String address = re.getString("mb_addr");

						p2tf[0].setText(name);
						p2tf[1].setText(num);
						p2tf[2].setText(phone);
						p2tf[3].setText(address);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} else {

			if (ob == 10) {
				String update = "UPDATE JAVAJO.MEMBERS " + "SET mb_NAME='" + p2tf[0].getText() + "', " + "mb_PHONE='"
						+ p2tf[2].getText() + "', " + "mb_ADDR='" + p2tf[3].getText() + "' " + "WHERE mb_NUM='"
						+ p2tf[1].getText() + "' ";
				System.out.println(update);
				DB.executeQuery(update);
				JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				dispose();
				main.dbroad();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
