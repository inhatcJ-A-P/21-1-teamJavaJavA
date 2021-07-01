package bookproject.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bookproject.DB;
import bookproject.book.BookManagement;
import bookproject.borrow.BookBorrowReturn;
import bookproject.borrow.BorrowInformation;
import bookproject.member.MemberList;

public class BookProgram extends JFrame implements ActionListener {

	private JPanel p1, p2;
	private JButton btn_member, btn_bookEm, btn_bookBr, btn_allBr, btn_exit;
	private ImageIcon icon;
	private JLabel lblImg;
	
	public BookProgram(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);	//현재 클래스에 대해서 상대적인 위치
		
		p1 = new JPanel();
		
		btn_member = new JButton("회원 등록/삭제");
		btn_member.addActionListener(this);
		p1.add(btn_member);
		
		btn_bookEm = new JButton("도서 등록/삭제");
		btn_bookEm.addActionListener(this);
		p1.add(btn_bookEm);
		
		btn_bookBr = new JButton("도서 대여/반납");
		btn_bookBr.addActionListener(this);
		p1.add(btn_bookBr);
		
		btn_allBr = new JButton("모든 대여 정보");
		btn_allBr.addActionListener(this);
		p1.add(btn_allBr);
		
		btn_exit = new JButton("종료");
		btn_exit.addActionListener(this);
		p1.add(btn_exit);
		
		p2 = new JPanel();
		icon = new ImageIcon("bookImage\\Welcome.jpg");
		lblImg = new JLabel(icon);
		p2.add(lblImg);
		
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		setVisible(true);
	}

	public static void main(String[] args) {
		DB.init();
		new BookProgram("도서 관리 프로그램", 1000, 700);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btn_exit) {
			System.exit(0);
		}
		else if(obj == btn_member) {
			new MemberList("회원목록",480, 600);
		}
		else if(obj == btn_bookEm) {
			new BookManagement("도서관리", 500, 600);
		}
		else if(obj == btn_bookBr) {
			new BookBorrowReturn("도서대여/반납", 600, 700);
		}
		else if(obj==btn_allBr) {
			new BorrowInformation("대여정보", 500, 600);
		}
	}
}
