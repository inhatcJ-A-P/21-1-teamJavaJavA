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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bookproject.DB;

@SuppressWarnings("unused")
public class BookSearch extends JFrame implements ActionListener {

	private JPanel p1, p2, p3;
	private JLabel lbl_search, lblLibName;
	private JTextField tfLibName;
	private JButton btn_search, btn_cancel;
	private Vector<String> str = null;
	private File file;
	private FileReader filereader;
	private BufferedReader bufReader;
	private Vector<String> con;
	private String[] tmp;
	private BookManagement bookManagement;
	private String strSelect;
	private boolean SearchCheck;

	public BookSearch(String title, int width, int height, BookManagement bookManagement) {
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
		tfLibName.requestFocus();
	}

	private void TopTitle() {
		p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 1));
		p1.setPreferredSize(new Dimension(300, 35));
		p1.setBackground(Color.darkGray);

		lbl_search = new JLabel("도서검색");
		lbl_search.setHorizontalAlignment(JLabel.CENTER); // 라벨 가운데 정렬
		lbl_search.setForeground(Color.white); // 라벨 색상
		p1.add(lbl_search);

	}

	private void Input() {
		p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.CENTER));
		lblLibName = new JLabel("도서제목:");
		tfLibName = new JTextField(11);
		tfLibName.addActionListener(this);

		p2.add(lblLibName);
		p2.add(tfLibName);

	}

	private void Button() {
		p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.CENTER));

		btn_search = new JButton("검색");
		btn_search.addActionListener(this);
		p3.add(btn_search);

		btn_cancel = new JButton("취소");
		btn_cancel.addActionListener(this);
		p3.add(btn_cancel);

	}

	public static void main(String[] args) {
		// new BookSearch("도서검색", 300, 200);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SearchCheck = false;
		
		Object obj = e.getSource();
		if (obj == btn_search || obj == tfLibName) {
			Search(tfLibName.getText());
			if (tfLibName.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "도서번호를 입력해주세요.", "메시지", JOptionPane.INFORMATION_MESSAGE, null);
			}

			else if(SearchCheck == true){
				JOptionPane.showMessageDialog(null, "검색이 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE, null);
			}
			else {
				JOptionPane.showMessageDialog(null, "도서가 존재하지 않습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE, null);
			}
			
			tfLibName.requestFocus();
			dispose();
		} else if (obj == btn_cancel) {
			dispose();
		}
	}

	private void Search(String strTitle) { // 테이블 안의 파일 출력

		bookManagement.getModel().setNumRows(0);
		
		String strSelect = "select * from LIB where LIB_NAME like '%" + tfLibName.getText() + "%'" ;
		ResultSet rs = DB.getResultSet(strSelect);
		
		String[] ArrRs = new String[6];
		try {

			while (rs.next()) {

				for (int i = 0; i < ArrRs.length; i++) {
					ArrRs[i]=rs.getString(i+1);
					
				}
				
				SearchCheck = true;
				bookManagement.getModel().addRow(ArrRs);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}