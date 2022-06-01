package bookproject.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bookproject.DB;

public class BookEnrollment extends JFrame implements ActionListener {

	private JPanel p1, p2, p3;
	private JLabel lbl_enrollment, lbl_num, lbl_title, lbl_author, lbl_publisher, lbl_price, lbl_state;
	private JTextField tfLibCode, tfLibName, tfLibAuthor, tfLibPublisher, tfLibPrice, tfLibState;
	private JButton btn_ok, btn_cancel;
	private boolean checkLibCode;
	private String strLibCode;
	private String strLibName;
	private String strLibAuthor;
	private String strLibPublisher;
	private String strLibPrice;
	private String strLibState;
	private String strInsert;
	private BookManagement bookManagement;

	public BookEnrollment(String title, int width, int height, BookManagement bookManagement) {
		this.bookManagement = bookManagement;
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);

		setLayout(new BorderLayout());

		// 패널 1
		AddTitle();

		// 패널 2
		AddInput();

		// 패널3
		AddBtn();

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void AddTitle() { // 패널1 타이틀 출력
		p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 1));
		p1.setPreferredSize(new Dimension(300, 35)); // 패널 크기
		p1.setBackground(Color.DARK_GRAY); // 패널 색상

		lbl_enrollment = new JLabel("도서등록");
		lbl_enrollment.setHorizontalAlignment(JLabel.CENTER); // 라벨 가운데 정렬
		lbl_enrollment.setForeground(Color.white); // 라벨 색상

		p1.add(lbl_enrollment);

	}

	private void AddInput() { // 패널2 입력 요소
		p2 = new JPanel();
		p2.setLayout(new GridLayout(6, 2));

		lbl_num = new JLabel("도서 번호");
		p2.add(lbl_num);

		tfLibCode = new JTextField();
		tfLibCode.addActionListener(this);
		p2.add(tfLibCode);

		lbl_title = new JLabel("제목");
		p2.add(lbl_title);

		tfLibName = new JTextField();
		tfLibName.addActionListener(this);
		p2.add(tfLibName);

		lbl_author = new JLabel("저자");
		p2.add(lbl_author);

		tfLibAuthor = new JTextField();
		tfLibAuthor.addActionListener(this);
		p2.add(tfLibAuthor);

		lbl_publisher = new JLabel("출판사");
		p2.add(lbl_publisher);

		tfLibPublisher = new JTextField();
		tfLibPublisher.addActionListener(this);
		p2.add(tfLibPublisher);

		lbl_price = new JLabel("가격");
		p2.add(lbl_price);

		tfLibPrice = new JTextField();
		tfLibPrice.addActionListener(this);
		p2.add(tfLibPrice);

		lbl_state = new JLabel("대여정보");
		p2.add(lbl_state);

		tfLibState = new JTextField();
		tfLibState.addActionListener(this);
		p2.add(tfLibState);

	}

	private void AddBtn() { // 패널3 버튼
		p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.CENTER));

		btn_ok = new JButton("확인");
		btn_ok.addActionListener(this);

		btn_cancel = new JButton("취소");
		btn_cancel.addActionListener(this);

		p3.add(btn_ok);
		p3.add(btn_cancel);

	}

	public static void main(String[] args) {
		DB.init();
		//new BookEnrollment("도서등록", 300, 280);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();

		if (obj == btn_ok || obj == tfLibCode || obj == tfLibName || obj == tfLibAuthor || obj == tfLibPublisher || obj == tfLibPrice || obj == tfLibState) {

			ActionBtnOk(); // 버튼에 액션 이벤트 추가

		}

		else if (obj == btn_cancel) {
			dispose();
		}

	}

	private void ActionBtnOk() { // 버튼OK 액션 이벤트
		
		SelectId(tfLibCode.getText()); // 함수 불러오기

		if (tfLibAuthor.getText().equals("") || tfLibCode.getText().equals("") || tfLibPrice.getText().equals("")
				|| tfLibPublisher.getText().equals("") || tfLibState.getText().equals("")
				|| tfLibName.getText().equals("")) {

			JOptionPane.showMessageDialog(null, "데이터를 다 입력하지 않았습니다.", "메시지", JOptionPane.WARNING_MESSAGE, null);

		}

		else if (checkLibCode == true) {
			JOptionPane.showMessageDialog(null, "중복된 도서번호가 있습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE, null);
			//tfReset();	//전체 지우기
			tfLibCode.setText("");
			tfLibCode.requestFocus();
		}

		else {
			JOptionPane.showMessageDialog(null, "처리가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE, null);
			strLibCode = tfLibCode.getText();
			strLibName = tfLibName.getText();
			strLibAuthor = tfLibAuthor.getText();
			strLibPublisher = tfLibPublisher.getText();
			strLibPrice = tfLibPrice.getText();
			strLibState = tfLibState.getText();

			strInsert = "INSERT INTO LIB (LIB_CODE, LIB_NAME, LIB_AUTHOR, LIB_PUBLISHER, LIB_PRICE, LIB_STATE) VALUES((SELECT UPPER('" + strLibCode + "') FROM dual)"
			+ " ,'" + strLibName + "','" + strLibAuthor + "','" + strLibPublisher + "'," + Integer.parseInt(strLibPrice)+ ", (SELECT UPPER('" + strLibState + "') FROM dual))";
			
			
			DB.executeQuery(strInsert);
			bookManagement.TableRead();
			dispose();
		}
	}

//	private void tfReset() {
//		tfLibCode.setText("");
//		tfLibName.setText("");
//		tfLibAuthor.setText("");
//		tfLibPublisher.setText("");
//		tfLibPrice.setText("");
//		tfLibState.setText("");
//
//	}

	private void SelectId(String selectLibCode) { // 테이블 안의 파일 출력
		checkLibCode = false;
		String strSelect = "SELECT * FROM LIB WHERE LIB_CODE = (SELECT UPPER('" + selectLibCode + "') FROM dual)";
		ResultSet rs = DB.getResultSet(strSelect);

		try {
			if (rs.next()) {
				checkLibCode = true;

			} else {
				checkLibCode = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
