package bookproject.main;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ManagerLogin extends JFrame implements ActionListener{ 

   private JPanel p1;
   private JLabel lblIcon, lblId, lblPw;
   private JTextField tfId, tfPw;
   private JButton btnLogin, btnExit;
   
   public ManagerLogin(String title, int width, int height) {
      this.setTitle(title);
      setSize(width, height);
      setLocationRelativeTo(this);
      setResizable(false);
      
      p1 = new JPanel();
      p1.setLayout(new FlowLayout());
      

      ImageIcon icon = new ImageIcon("bookImage\\Intro.jpg");
      lblIcon = new JLabel(icon);
      p1.add(lblIcon);
      
      lblId = new JLabel("ID : ");
      tfId = new JTextField(10);
      lblPw = new JLabel("PW : ");
      tfPw = new JTextField(10);
      btnLogin = new JButton("로그인");
      btnExit = new JButton("종료");
      
      btnLogin.addActionListener(this);
      btnExit.addActionListener(this);
      
      p1.add(lblId);
      p1.add(tfId);
      p1.add(lblPw);
      p1.add(tfPw);
      p1.add(btnLogin);
      p1.add(btnExit);
      
      add(p1);
      
      this.setVisible(true);
   }
   
   public static void main(String[] args) {
      new ManagerLogin("관리자 로그인", 930, 430);
      
      
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == btnLogin) {
         if(tfId.getText().equals("admin") && tfPw.getText().equals("1234")) {
            new BookProgram("도서 관리 프로그램", 1000, 700);
         }
      }   
      
         if (e.getSource() == btnExit) {
            System.exit(0);
      }
   }
}