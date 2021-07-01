package bookproject.borrow;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bookproject.DB;

public class BorrowReturn extends JFrame implements ActionListener {

	private JPanel p1, p1_1, p1_2, p2;
	private JLabel lbl_rrn, lbl_Bnum, lbl_title;
	private JTextField tf_rrn, tf_Bnum, tf_title;
	private JButton btn_borrow, btn_return, btn_cancel;
	private Vector<String> con;
	private DefaultTableModel model;
	private String bId;
	private String mName;
	private String mPhone;
	private String bName;
	private String bNum;
	private String date;
	private BookBorrowReturn bbr;
	private String bookNum;

	public BorrowReturn(String title, int width, int height, BookBorrowReturn bbr) {
		this.bbr = bbr;
		this.setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		p1 = new JPanel();
		p1.setLayout(new BorderLayout());

		p1_1 = new JPanel();

		lbl_rrn = new JLabel("회원주민번호");
		tf_rrn = new JTextField(10);
		btn_borrow = new JButton("대여");
		btn_borrow.addActionListener(this);
		btn_return = new JButton("반납");
		btn_return.addActionListener(this);
		btn_cancel = new JButton("취소");
		btn_cancel.addActionListener(this);

		p1_1.add(lbl_rrn);
		p1_1.add(tf_rrn);
		p1_1.add(btn_borrow);
		p1_1.add(btn_return);
		p1_1.add(btn_cancel);
		p1.add(p1_1, BorderLayout.NORTH);

		p1_2 = new JPanel();

		lbl_Bnum = new JLabel("도서번호");
		tf_Bnum = new JTextField(10);
		//tf_Bnum.addActionListener(this);
		lbl_title = new JLabel("제    목");
		tf_title = new JTextField(10);
		tf_title.addActionListener(this);

		Vector<String> column = new Vector<String>();
		column.addElement("대여번호");
		column.addElement("회원이름");
		column.addElement("회원전화");
		column.addElement("도서이름");
		column.addElement("도서번호");
		column.addElement("날짜");

		model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		makeTable();

		p2 = new JPanel();
		p2.setLayout(new BorderLayout());

		JTable table = new JTable(model);
		JScrollPane scrollpane = new JScrollPane(table);
		p2.add(scrollpane);

		p1_2.add(lbl_Bnum);
		p1_2.add(tf_Bnum);
		p1_2.add(lbl_title);
		p1_2.add(tf_title);
		p1.add(p1_2);

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);

		this.setVisible(true);
	}

	private void makeTable() {
		String sql = "SELECT RENT_NO, MEM_NAME, MEM_PHONE, LIB_NAME, LIB_CODE, TO_CHAR(RENT_DATE, 'YYYY-MM-DD') FROM RENT ORDER BY RENT_NO";
		ResultSet rs = DB.getResultSet(sql);

		try {
			while (rs.next()) {
				con = new Vector<String>();
				bId = rs.getString(1);
				mName = rs.getString(2);
				mPhone = rs.getString(3);
				bName = rs.getString(4);
				bNum = rs.getString(5);
				date = rs.getString(6);

				con.add(bId);
				con.add(mName);
				con.add(mPhone);
				con.add(bName);
				con.add(bNum);
				con.add(date);
				model.addRow(con);
			}
		} catch (SQLException e) {
			System.out.println("해당 드라이버가 없습니다.");
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean borrowState = false;

		if (e.getSource() == btn_borrow || e.getSource() == tf_title) {

			String mbNum = tf_rrn.getText();
			String libCode = tf_Bnum.getText();
			String libName = tf_title.getText();

			libCode = libCode.toUpperCase();

			String rsBName = "SELECT LIB_NAME " + "FROM LIB " + "WHERE LIB_CODE = '" + libCode + "'";
			ResultSet rsName = DB.getResultSet(rsBName);

			String bookName = "";

			try {
				if (rsName.next()) {
					bookName = rsName.getString(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (int i = 0; i < bbr.getTable().getRowCount(); i++) {
				if (libCode.equals(bbr.getTable().getValueAt(i, 0))) {
					if (bbr.getTable().getValueAt(i, 5).equals("N")) {
						borrowState = true;

						JOptionPane.showMessageDialog( // 메시지창 출력
								this, "대여중인 도서입니다.", "ErrorMessage", JOptionPane.ERROR_MESSAGE);
						tf_Bnum.setText("");
						tf_title.setText("");
					}
				}
			}

			if ((borrowState == false && bookName.equals(libName)) || libCode.equals("") || libName.equals("")) {
				if (mbNum.equals("")) {
					JOptionPane.showMessageDialog( // 메시지창 출력
							this, "회원주민번호를 입력해주세요.", "ErrorMessage", JOptionPane.ERROR_MESSAGE);
				} else if (libCode.equals("") || libName.equals("")) {
					JOptionPane.showMessageDialog( // 메시지창 출력
							this, "도서정보를 전부 입력해주세요.", "ErrorMessage", JOptionPane.ERROR_MESSAGE);
				} else {
					String sql = "INSERT INTO JAVAJO.RENT VALUES((SELECT NVL(MAX(RENT_NO)+1,1) FROM JAVAJO.RENT), "
							+ "(SELECT MB_NAME FROM MEMBERS WHERE MB_NUM = '" + mbNum + "'), "
							+ "(SELECT MB_PHONE FROM MEMBERS WHERE MB_NUM = '" + mbNum + "'),"
							+ "(SELECT LIB_CODE FROM LIB WHERE LIB_CODE = '" + libCode + "'),"
							+ "(SELECT LIB_NAME FROM LIB WHERE LIB_CODE = '" + libCode + "'), TO_DATE(SYSDATE))";

					String sqlState = "UPDATE LIB SET LIB_STATE='N' " + "WHERE LIB_CODE='" + libCode + "'";

					DB.executeQuery(sql); // DB 내용 추가
					DB.executeQuery(sqlState); // 대출여부 수정

					tf_Bnum.setText("");
					tf_title.setText("");

					model.setNumRows(0);
					makeTable();
					
					bbr.getModel().setNumRows(0);
					bbr.makeTable();
				}
			} else {
				System.out.println(123);
			}
		}

		if (e.getSource() == btn_return) {

			bookNum = "";
			String mbNum = tf_rrn.getText();

			String rsMemNum = "SELECT MB_NAME " + "FROM MEMBERS " + "WHERE MB_NUM = '" + mbNum + "'";
			ResultSet rsNum = DB.getResultSet(rsMemNum);

			String memName = "";

			try {
				if (rsNum.next()) {
					memName = rsNum.getString(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (int i = 0; i < model.getRowCount(); i++) {
				if (memName.equals(model.getValueAt(i, 1))) {
					bookNum = (String) model.getValueAt(i, 4);
				}
			}

			String sql = "DELETE FROM RENT WHERE MEM_NAME = (SELECT MB_NAME FROM MEMBERS WHERE MB_NUM = '" + mbNum
					+ "')";

			String sqlState = "UPDATE LIB SET LIB_STATE='Y' WHERE LIB_CODE='" + bookNum + "'";

			DB.executeQuery(sql); // DB 내용 삭제
			DB.executeQuery(sqlState); // 대출여부 수정

			mbNum = "";

			model.setNumRows(0);
			makeTable();
			
			bbr.getModel().setNumRows(0);
			bbr.makeTable();
		}

		if (e.getSource() == btn_cancel) {
			this.dispose();
		}
	}

}