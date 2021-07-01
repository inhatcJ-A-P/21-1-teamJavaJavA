package bookproject.book;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bookproject.DB;

public class BookRetouch extends JFrame implements ActionListener {

	private JPanel p1, p2, p3;
	private JLabel lblSearchLibCode, lblLibCode, lblLibName, lblLibAuthor, lblLibPublisher, lblLibPrice, lblLibState;
	private JTextField tfSearchLibCode, tfLibCode, tfLibName, tfLibAuthor, tfLibPublisher, tfLibPrice, tfLibState;
	private JButton btn_search, btn_retouch, btn_cancel;
	private String strSelect;
	private BookManagement bookManagement;
	private boolean SearchCheck;
	

	public BookRetouch(String title, int width, int height, BookManagement bookManagement) {
		this.bookManagement = bookManagement;
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);

		// 패널1
		AddBookNum();

		// 패널2
		AddCompo();

		// 패널3
		AddBtn();

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);
		setVisible(true);
	}

	// 패널 1
	private void AddBookNum() {
		p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.CENTER));

		lblSearchLibCode = new JLabel("도서번호 :");
		
		tfSearchLibCode = new JTextField(11);
		tfSearchLibCode.addActionListener(this);
		
		btn_search = new JButton("검색");
		btn_search.addActionListener(this);

		p1.add(lblSearchLibCode);
		p1.add(tfSearchLibCode);
		p1.add(btn_search);

	}

	// 패널2
	private void AddCompo() {
		p2 = new JPanel();
		p2.setLayout(new GridLayout(6, 2));

		lblLibCode = new JLabel("도서번호");
		tfLibCode = new JTextField();
		tfLibCode.setEditable(false);
		p2.add(lblLibCode);
		p2.add(tfLibCode);

		lblLibName = new JLabel("제목");
		tfLibName = new JTextField();
		tfLibName.addActionListener(this);
		p2.add(lblLibName);
		p2.add(tfLibName);

		lblLibAuthor = new JLabel("저자");
		tfLibAuthor = new JTextField();
		tfLibAuthor.addActionListener(this);
		p2.add(lblLibAuthor);
		p2.add(tfLibAuthor);

		lblLibPublisher = new JLabel("출판사");
		tfLibPublisher = new JTextField();
		tfLibPublisher.addActionListener(this);
		p2.add(lblLibPublisher);
		p2.add(tfLibPublisher);

		lblLibPrice = new JLabel("가격");
		tfLibPrice = new JTextField();
		tfLibPrice.addActionListener(this);
		p2.add(lblLibPrice);
		p2.add(tfLibPrice);

		lblLibState = new JLabel("대여여부");
		tfLibState = new JTextField();
		tfLibState.addActionListener(this);
		p2.add(lblLibState);
		p2.add(tfLibState);

	}

	// 패널3
	private void AddBtn() {
		p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.CENTER));

		btn_retouch = new JButton("수정");
		btn_retouch.addActionListener(this);
		p3.add(btn_retouch);

		btn_cancel = new JButton("돌아가기");
		btn_cancel.addActionListener(this);
		p3.add(btn_cancel);

	}

	//메인
	public static void main(String[] args) {
		DB.init();
		//new BookRetouch("도서정보 수정", 300, 280);
	}

	//액션 폼
	@Override
	public void actionPerformed(ActionEvent e) {
		SearchCheck = false;
		Object obj = e.getSource();

		if (obj == btn_cancel) {

			dispose();

		} else if (obj == btn_search || obj == tfSearchLibCode) {
			
			if(tfSearchLibCode.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "도서번호를 입력해주세요.", "메시지", JOptionPane.INFORMATION_MESSAGE, null);
			}
			else {
				Search();
				if(SearchCheck == true) {
					JOptionPane.showMessageDialog(null, "검색이 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE, null);
				}
				else {
					JOptionPane.showMessageDialog(null, "도서가 존재하지 않습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE, null);
					tfSearchLibCode.setText("");
					tfLibCode.setText("");
					tfLibName.setText("");
					tfLibAuthor.setText("");
					tfLibPublisher.setText("");
					tfLibPrice.setText("");
					tfLibState.setText("");
				}
			}

		} else if (obj == btn_retouch || obj == tfLibName || obj == tfLibAuthor || obj == tfLibPublisher || obj == tfLibPrice || obj == tfLibState) {
			
			Retouch();
			
		}
	}
	
	//검색
	private void Search() {
		strSelect = "SELECT * FROM LIB WHERE LIB_CODE = (SELECT UPPER('" + tfSearchLibCode.getText() + "') FROM dual)";
		ResultSet rs = DB.getResultSet(strSelect);
		
		
		try {
			while(rs.next()) {
				tfLibCode.setText(rs.getString(1));
				tfLibName.setText(rs.getString(2));
				tfLibAuthor.setText(rs.getString(3));
				tfLibPublisher.setText(rs.getString(4));
				tfLibPrice.setText(rs.getString(5));
				tfLibState.setText(rs.getString(6));
				SearchCheck = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	// 수정
	private void Retouch() {
		if (tfSearchLibCode.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "도서를 검색해주세요.", "메시지", JOptionPane.WARNING_MESSAGE, null);
		}
		else {
			
			String strRetouch = "UPDATE LIB SET LIB_NAME='" + tfLibName.getText() + "'," + "LIB_AUTHOR='" + tfLibAuthor.getText() + "', LIB_PUBLISHER= '" + tfLibPublisher.getText() + "', LIB_PRICE=" + Integer.parseInt(tfLibPrice.getText()) +", LIB_STATE= (SELECT UPPER('" + tfLibState.getText() + "') FROM dual)" + " WHERE LIB_CODE= (SELECT UPPER('" + tfSearchLibCode.getText() + "') FROM dual)";
			DB.executeQuery(strRetouch);
			
			
			JOptionPane.showMessageDialog(null, "수정되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE, null);
			bookManagement.TableRead();
			dispose();
		}
	}

}
