package bookproject.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bookproject.DB;



public class MemberEnrollment extends JFrame  implements ActionListener, KeyListener {

	private JButton btnok, btnexit;
	private JTextField p2tf[] = new JTextField[4]; //p2tf_1, p2tf_2, p2tf_3, p2tf_4;
	Vector<String> con;
	private MemberList main;
	private BufferedWriter bufwriter;
	public MemberEnrollment(String Title, int width, int height, MemberList main) {
		this.main = main;
		this.setTitle(Title);
		setSize(width, height);
		//setLocation(900, 400);
		setLocationRelativeTo(this);
		
		JPanel p1 = new JPanel();
		p1.setBackground(Color.DARK_GRAY);
		JLabel lbl = new JLabel("회원등록");
		Font ft = new Font("", Font.BOLD, 15);
		lbl.setFont(ft);
		lbl.setForeground(Color.WHITE);
		p1.add(lbl);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(4, 2));
		JLabel p2lbl_1 = new JLabel("이름:");
		p2tf[0] = new JTextField();
		
		JLabel p2lbl_2 = new JLabel("주민번호:");
		p2tf[1] = new JTextField();

		JLabel p2lbl_3 = new JLabel("연락처:");
		p2tf[2] = new JTextField();
		
		JLabel p2lbl_4 = new JLabel("주소:");
		p2tf[3] = new JTextField();
		p2tf[3].addKeyListener(this);
		
		p2.add(p2lbl_1); p2.add(p2tf[0]);	
		p2.add(p2lbl_2); p2.add(p2tf[1]);	
		p2.add(p2lbl_3); p2.add(p2tf[2]);	
		p2.add(p2lbl_4); p2.add(p2tf[3]);
		
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		btnok= new JButton("확인");	  btnok.addActionListener(this);
		btnexit = new JButton("취소"); btnexit.addActionListener(this);
		p3.add(btnok);
		p3.add(btnexit);
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		//new MemberEnrollment("1",300, 200, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		boolean bool = true;
		//if(ob == btnexit) setVisible(false); ���1
		if(ob == btnexit) dispose(); // ���2
		if(ob == btnok) {
			for(int i = 0; i < main.getModel().getRowCount(); i++) {
				if(p2tf[1].getText().equals(main.getModel().getValueAt(i, 1))) {
					JOptionPane.showMessageDialog(null, "중복된 아이디가 있습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
					bool = false;
					for(int j = 0; j < p2tf.length; j++) 
						p2tf[j].setText("");
				}
			}
			if(bool && !p2tf[1].equals("")) {
				String insert = "INSERT INTO JAVAJO.MEMBERS "
						+ "(mb_NAME, mb_NUM, mb_PHONE, mb_ADDR) "
						+ "VALUES('" + 
						p2tf[0].getText() + "', '" + 
						p2tf[1].getText() + "', '" + 
						p2tf[2].getText() + "', '" + 
						p2tf[3].getText() + "') ";
				System.out.println(insert);
				DB.executeQuery(insert);
				
		     JOptionPane.showMessageDialog(null, "처리가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
		     dispose();
		     main.dbroad();

			}
		     
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int ob = e.getKeyCode();
		boolean bool = true;
		//System.out.println(p2tf[1].getText());
		if(ob == 10 && !p2tf[1].getText().equals("")) {
			for(int i = 0; i < main.getModel().getRowCount(); i++) {
				if(p2tf[1].getText().equals(main.getModel().getValueAt(i, 1))) {
					JOptionPane.showMessageDialog(null, "중복된 아이디가 있습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
					bool = false;
					for(int j = 0; j < p2tf.length; j++) 
						p2tf[j].setText("");
				}
				
			}
			if(bool) {
				System.out.println("!");
				String insert = "INSERT INTO JAVAJO.MEMBERS "
						+ "(mb_NAME, mb_NUM, mb_PHONE, mb_ADDR) "
						+ "VALUES('" + 
						p2tf[0].getText() + "', '" + 
						p2tf[1].getText() + "', '" + 
						p2tf[2].getText() + "', '" + 
						p2tf[3].getText() + "') ";
				System.out.println(insert);
				DB.executeQuery(insert);
		     JOptionPane.showMessageDialog(null, "처리가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
		     
		     dispose();
		     main.dbroad();
			}
		     
		}
		
	}

}
