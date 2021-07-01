package bookproject.member; 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bookproject.DB;

public class MemberDelete extends JFrame implements ActionListener, KeyListener {
	private JButton btnok, btnexit;
	private JTextField p2tf;
	private MemberList main;
	public MemberDelete(String Title, int width, int height, MemberList main) {
		this.main = main;
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
		p2tf.addKeyListener(this);
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
					+ "WHERE mb_NUM='" + p2tf.getText() + "'" ;
			DB.executeQuery(del);
			JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			main.dbroad();
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int ob = e.getKeyCode();
		if(ob == 10) {
			String del = "DELETE FROM JAVAJO.MEMBERS "
					+ "WHERE mb_NUM='" + p2tf.getText() + "'" ;
			DB.executeQuery(del);
			JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			main.dbroad();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	} 

}
