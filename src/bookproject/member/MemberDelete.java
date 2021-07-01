package bookproject.member; 

import java.awt.print.Book.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class MemberDelete extends JFrame implements ActionListener {
	private JButton btnok, btnexit;
	private JTextField p2tf;
	private MemberList main;
	public MemberDelete(String Title, int width, int height) {
		this.setTitle(Title);
		setSize(width, height);
		//setLocation(900, 400);
		setLocationRelativeTo(this);
		
		JPanel p1 = new JPanel();
		p1.setBackground(Color.DARK_GRAY);
		JLabel lbl = new JLabel("회원삭제");
		Font ft = new Font("", Font.BOLD, 15);
		lbl.setFont(ft);
		lbl.setForeground(Color.WHITE);
		p1.add(lbl);
		
		JPanel p2 = new JPanel();
		JLabel p2lbl_1 = new JLabel("회원주민번호:");
		p2tf = new JTextField(10);
		p2.add(p2lbl_1); p2.add(p2tf);	
		
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		btnok= new JButton("확인");    btnok.addActionListener(this);
		btnexit = new JButton("취소"); btnexit.addActionListener(this);
		p3.add(btnok);
		p3.add(btnexit);
		
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		//new MemberDelete(	"ȸ������",300, 200);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob == btnexit) dispose();
		else if(ob == btnok) {
		
			String del = "DELETE FROM JAVAJO.MEMBERS "
					+ "WHERE NUM='" + p2tf.getText() + "'" ;
			DB.executeQuery(del);
			/*DBconnect db = new DBconnect();
			try {
				String del = "DELETE FROM JAVAJO.MEMBERS "
						+ "WHERE NUM='" + p2tf.getText() + "'" ;
				db.getStat().executeUpdate(del);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
		}
		
	} 

}
