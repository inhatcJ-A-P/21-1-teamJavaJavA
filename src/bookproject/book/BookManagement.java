package bookproject.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import bookproject.DB;

//도서관리
public class BookManagement extends JFrame implements ActionListener {

	private JPanel p1, p2;
	private JButton btn_reset, btn_enrollment, btn_search, btn_retouch, btn_del, btn_back;

	private JTable table;
	private DefaultTableModel model;

	private Vector<String> bookColumn;

	public BookManagement(String title, int width, int height) {
		
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);

		setLayout(new BorderLayout());

		// 패널1
		AddBtn();

		// 패널 2

		AddTable();

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		setVisible(true);
	}

	private void AddBtn() { // 패널 1 버튼 생성

		p1 = new JPanel();

		btn_reset = new JButton("새로고침");
		btn_reset.addActionListener(this);
		p1.add(btn_reset);

		btn_enrollment = new JButton("등록");
		btn_enrollment.addActionListener(this);
		p1.add(btn_enrollment);

		btn_search = new JButton("조회");
		btn_search.addActionListener(this);
		p1.add(btn_search);

		btn_retouch = new JButton("수정");
		btn_retouch.addActionListener(this);
		p1.add(btn_retouch);

		btn_del = new JButton("삭제");
		btn_del.addActionListener(this);
		p1.add(btn_del);

		btn_back = new JButton("돌아가기");
		btn_back.addActionListener(this);
		p1.add(btn_back);

	}

	private void AddTable() { // 패널 2 테이블 구성

		p2 = new JPanel();
		p2.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "도서목록"));
		p2.setLayout(new BorderLayout());

		bookColumn = new Vector<String>();
		bookColumn.add("도서번호");
		bookColumn.add("제목");
		bookColumn.add("저자");
		bookColumn.add("출판사");
		bookColumn.add("가격");
		bookColumn.add("대출여부");

		model = new DefaultTableModel(bookColumn, 0) {

			public boolean isCellEditable(int r, int c) {
				return false;
			}

		};

		TableRead(); // 파일 읽어오기

	}

	public void TableRead() { // 테이블 안의 파일 출력
		model.setNumRows(0);
		String strSelect = "select * from LIB order by LIB_CODE asc";
		ResultSet rs = DB.getResultSet(strSelect);

		String[] ArrRs = new String[6];
		try {

			while (rs.next()) {
				
				for (int i = 0; i < ArrRs.length; i++) {
					ArrRs[i] = rs.getString(i + 1); // 값 저장
				}
				
				model.addRow(ArrRs);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false); // 테이블 편집X

		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 스크롤 고정

		p2.add(sc);

	}

	public static void main(String[] args) {
		DB.init();
		//new BookManagement("도서관리", 500, 600);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();

		if (obj == btn_back) {

			dispose();

		} else if (obj == btn_reset) {
			TableRead();

		} else if (obj == btn_enrollment) {

			new BookEnrollment("도서 등록", 300, 280, this);
			

		} else if (obj == btn_search) {

			new BookSearch("도서검색", 300, 200, this);

		} else if (obj == btn_retouch) {

			new BookRetouch("도서정보 수정", 300, 280, this);
			

		} else if (obj == btn_del) {

			new BookDelete("도서 삭제", 300, 200, this);
		}
		
	}

	public DefaultTableModel getModel() {
		return model;
	}

}