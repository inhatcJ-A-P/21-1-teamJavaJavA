package bookproject.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bookproject.DB;

public class BookDelete extends JFrame implements ActionListener {

	private JPanel p1, p2, p3;
	private JLabel lbl_del, lbl_num;
	private JTextField tf_num;
	private JButton btn_del, btn_cancel;

	private boolean check;
	private String strSelect;
	private BookManagement bookManagement;

	public BookDelete(String title, int width, int height, BookManagement bookManagement) {
		this.bookManagement = bookManagement;
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);

		// 패널1
		TopTitle();

		// 패널2
		Input();

		// 패널3
		Button();

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void TopTitle() {
		p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 1));
		p1.setPreferredSize(new Dimension(300, 35));
		p1.setBackground(Color.darkGray);

		lbl_del = new JLabel("도서삭제");
		lbl_del.setHorizontalAlignment(JLabel.CENTER); // 라벨 가운데 정렬
		lbl_del.setForeground(Color.white); // 라벨 색상
		p1.add(lbl_del);

	}

	private void Input() {
		p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.CENTER));

		lbl_num = new JLabel("도서번호");
		tf_num = new JTextField(11);
		tf_num.addActionListener(this);

		p2.add(lbl_num);
		p2.add(tf_num);

	}

	private void Button() {
		p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.CENTER));

		btn_del = new JButton("삭제");
		btn_del.addActionListener(this);
		p3.add(btn_del);

		btn_cancel = new JButton("취소");
		btn_cancel.addActionListener(this);
		p3.add(btn_cancel);

	}

	public static void main(String[] args) {
		DB.init();
		// new BookDelete("도서삭제", 300, 200);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		check = false;
		Object obj = e.getSource();
		if (obj == btn_del || obj == tf_num) {

			if (tf_num.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "도서번호를 입력해주세요.", "메시지", JOptionPane.WARNING_MESSAGE, null);
			}

			else {
				Del(tf_num.getText());
				if (check == true) {

					String strDelete = "DELETE FROM LIB WHERE LIB_CODE = (SELECT UPPER('" + tf_num.getText() + "') FROM dual)";
					DB.executeQuery(strDelete);

					JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE, null);
					bookManagement.TableRead();
					dispose();

				} else {
					JOptionPane.showMessageDialog(null, "도서가 존재하지 않습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE,
							null);
				}

			}

		} else if (obj == btn_cancel) {
			dispose();
		}
	}

	private void Del(String delLibCode) {
		strSelect = "SELECT * FROM LIB WHERE LIB_CODE = (SELECT UPPER('" + delLibCode + "') FROM dual )";
		ResultSet rs = DB.getResultSet(strSelect);

		try {
			if (rs.next()) {
				check = true;

			} else {
				check = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
