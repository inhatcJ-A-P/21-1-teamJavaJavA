package bookproject.borrow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bookproject.DB;

public class BookBorrowReturn extends JFrame implements ActionListener {

	private JPanel p1, p1_1, p1_2, p1_3, p2;
	private JLabel lbl_Bname, lbl_Bnum, lbl_title, lbl_author, lbl_publisher, lbl_price, lbl_borrow;
	private JTextField tf_Bname, tf_Bnum, tf_title, tf_author, tf_publisher, tf_price, tf_borrow;
	private JButton btn_search, btn_br, btn_reset, btn_refresh, btn_back;
	private Vector<String> con;
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollpane;
	private String bId;
	private String bTitle;
	private String bAuthor;
	private String bPublisher;
	private String bPrice;
	private String bBorrowInfo;
	private BorrowReturn br;

	public JTable getTable() {
		return table;
	}
	
	public DefaultTableModel getModel() {
		return model;
	}

	public BookBorrowReturn(String title, int width, int height) {
		this.setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		p1 = new JPanel();
		p1.setLayout(new BorderLayout());

		p1_1 = new JPanel();
		lbl_Bname = new JLabel("도서명");
		tf_Bname = new JTextField(13);
		tf_Bname.addActionListener(this);

		btn_search = new JButton("검색");
		btn_search.addActionListener(this);

		p1_1.add(lbl_Bname);
		p1_1.add(tf_Bname);
		p1_1.add(btn_search);

		p1.add(p1_1, BorderLayout.NORTH);

		p1_2 = new JPanel();
		p1_2.setLayout(null);
		p1_2.setPreferredSize(new Dimension(600, 110)); // 패널 크기 지정

		lbl_Bnum = new JLabel("도서번호");
		lbl_Bnum.setLocation(100, 20);
		lbl_Bnum.setSize(60, 20);
		p1_2.add(lbl_Bnum);

		tf_Bnum = new JTextField();
		tf_Bnum.setLocation(160, 20);
		tf_Bnum.setSize(90, 20);
		tf_Bnum.setEditable(false);
		tf_Bnum.setBackground(Color.white);
		p1_2.add(tf_Bnum);

		lbl_title = new JLabel("제      목");
		lbl_title.setLocation(330, 20);
		lbl_title.setSize(60, 20);
		p1_2.add(lbl_title);

		tf_title = new JTextField();
		tf_title.setLocation(390, 20);
		tf_title.setSize(90, 20);
		tf_title.setEditable(false);
		tf_title.setBackground(Color.white);
		p1_2.add(tf_title);

		lbl_author = new JLabel("저      자");
		lbl_author.setLocation(100, 50);
		lbl_author.setSize(60, 20);
		p1_2.add(lbl_author);

		tf_author = new JTextField();
		tf_author.setLocation(160, 50);
		tf_author.setSize(90, 20);
		tf_author.setEditable(false);
		tf_author.setBackground(Color.white);
		p1_2.add(tf_author);

		lbl_publisher = new JLabel("출 판 사");
		lbl_publisher.setLocation(330, 50);
		lbl_publisher.setSize(60, 20);
		p1_2.add(lbl_publisher);

		tf_publisher = new JTextField();
		tf_publisher.setLocation(390, 50);
		tf_publisher.setSize(90, 20);
		tf_publisher.setEditable(false);
		tf_publisher.setBackground(Color.white);
		p1_2.add(tf_publisher);

		lbl_price = new JLabel("가      격");
		lbl_price.setLocation(100, 80);
		lbl_price.setSize(60, 20);
		p1_2.add(lbl_price);

		tf_price = new JTextField();
		tf_price.setLocation(160, 80);
		tf_price.setSize(90, 20);
		tf_price.setEditable(false);
		tf_price.setBackground(Color.white);
		p1_2.add(tf_price);

		lbl_borrow = new JLabel("대출여부");
		lbl_borrow.setLocation(330, 80);
		lbl_borrow.setSize(60, 20);
		p1_2.add(lbl_borrow);

		tf_borrow = new JTextField();
		tf_borrow.setLocation(390, 80);
		tf_borrow.setSize(90, 20);
		tf_borrow.setEditable(false);
		tf_borrow.setBackground(Color.white);
		p1_2.add(tf_borrow);

		p1.add(p1_2);

		p1_3 = new JPanel();
		p1_3.setLayout(new FlowLayout(FlowLayout.LEFT));

		btn_br = new JButton("대여/반납");
		btn_reset = new JButton("초기화");
		btn_refresh = new JButton("새로고침");
		btn_back = new JButton("돌아가기");

		p1_3.add(btn_br);
		p1_3.add(btn_reset);
		p1_3.add(btn_refresh);
		p1_3.add(btn_back);

		btn_br.addActionListener(this);
		btn_reset.addActionListener(this);
		btn_refresh.addActionListener(this);
		btn_back.addActionListener(this);

		p1.add(p1_3, BorderLayout.SOUTH);

		Vector<String> column = new Vector<String>();
		column.addElement("도서번호");
		column.addElement("제목");
		column.addElement("저자");
		column.addElement("출판사");
		column.addElement("가격");
		column.addElement("대출여부");

		model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		makeTable();

		table = new JTable(model);
		scrollpane = new JScrollPane(table);

		table.getTableHeader().setReorderingAllowed(false);

		p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add(scrollpane);

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		this.setVisible(true);

	}

	public void makeTable() {
		String sql = "SELECT * FROM LIB ORDER BY LIB_CODE";
		ResultSet rs = DB.getResultSet(sql);

		try {
			while (rs.next()) {
				con = new Vector<String>();
				bId = rs.getString(1);
				bTitle = rs.getString(2);
				bAuthor = rs.getString(3);
				bPublisher = rs.getString(4);
				bPrice = rs.getString(5);
				bBorrowInfo = rs.getString(6);

				con.add(bId);
				con.add(bTitle);
				con.add(bAuthor);
				con.add(bPublisher);
				con.add(bPrice);
				con.add(bBorrowInfo);
				model.addRow(con);
			}
		} catch (SQLException e) {
			System.out.println("해당 드라이버가 없습니다.");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DB.init();
		new BookBorrowReturn("도서대여/반납", 600, 700);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_br) {
			
			String libCode = tf_Bnum.getText();
			String libName = tf_Bname.getText();
			
			br = new BorrowReturn("대여/반납", 500, 600, this);
			
			br.getTfBnum().setText(libCode);
			br.getTfTitle().setText(libName);
		}

		if (e.getSource() == btn_search | e.getSource() == tf_Bname) {
			String strName = tf_Bname.getText();
			String sql = "SELECT * FROM LIB WHERE LIB_NAME = '" + strName + "'";
			ResultSet rs = DB.getResultSet(sql);

			if (strName.equals(tf_Bname.getText())) {
				try {
					while (rs.next()) {
						con = new Vector<String>();
						bId = rs.getString(1);
						bTitle = rs.getString(2);
						bAuthor = rs.getString(3);
						bPublisher = rs.getString(4);
						bPrice = rs.getString(5);
						bBorrowInfo = rs.getString(6);

						tf_Bnum.setText(bId);
						tf_title.setText(bTitle);
						tf_author.setText(bAuthor);
						tf_publisher.setText(bPublisher);
						tf_price.setText(bPrice);
						tf_borrow.setText(bBorrowInfo);
					}
				} catch (SQLException e1) {
					System.out.println("해당 드라이버가 없습니다.");
					e1.printStackTrace();
				}
			}
		}

		if (e.getSource() == btn_reset) {
			tf_Bname.setText("");
			tf_Bnum.setText("");
			tf_title.setText("");
			tf_author.setText("");
			tf_publisher.setText("");
			tf_price.setText("");
			tf_borrow.setText("");
		}

		if (e.getSource() == btn_refresh) {
			model.setNumRows(0);
			makeTable();
		}

		if (e.getSource() == btn_back) {
			this.dispose();
		}
	}
}