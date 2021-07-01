package bookproject.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bookproject.DB;

public class MemberSearch extends JFrame implements ActionListener {
	private JButton btnok, btnexit;
	private MemberList main;
	private Vector<String> con;
	private JTextField p2tf; 
	public MemberSearch(String Title, int width, int height, MemberList main) {
		this.main = main;
		this.setTitle(Title);
		setSize(width, height);
		//setLocation(900, 400);
		setLocationRelativeTo(this);
		
		JPanel p1 = new JPanel();
		p1.setBackground(Color.DARK_GRAY);
		JLabel lbl = new JLabel("회원검색");
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
		//new MemberSearch("ȸ���˻�",300, 200);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob == btnexit) dispose();
		if(ob == btnok) {
			main.getModel().setNumRows(0);
			ResultSet re = DB.getResultSet("SELECT * FROM MEMBERS WHERE NUM = '" + p2tf.getText() + "' ");
			try {
				while(re.next()) {
					String name = re.getString("name");
					String num = re.getString("num");
					String phone = re.getString("phone");
					String address = re.getString("address");
					String [] tmp = {name, num, phone, address};
					main.getModel().addRow(tmp);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
