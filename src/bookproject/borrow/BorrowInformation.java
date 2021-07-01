package bookproject.borrow;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bookproject.DB;

public class BorrowInformation extends JFrame implements ActionListener{ 

   JPanel p1, p2;
   JButton btn_back;
   private Vector<String> con;
   private String bId, mName, mPhone, bName, bNum, date;
   private DefaultTableModel model;
   
   public BorrowInformation(String title, int width, int height) {
      this.setTitle(title);
      setSize(width, height);
      setLocationRelativeTo(this);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      p1 = new JPanel();
      p2 = new JPanel();
      
      p2.setLayout(new BorderLayout());
      
      btn_back = new JButton("돌아가기");
      btn_back.addActionListener(this);
      p1.add(btn_back);
      
      // 컬럼
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
      
      JTable table = new JTable(model);
      JScrollPane scrollpane = new JScrollPane(table);
      table.getTableHeader().setReorderingAllowed(false);
      p2.add(scrollpane);
      
      add(p1, BorderLayout.NORTH);
      add(p2, BorderLayout.CENTER);
      
      this.setVisible(true);
   }
   
   private void makeTable() {
      String sql = "SELECT r.RENT_NO, m.mb_name, m.mb_phone, l.lib_name, l.LIB_CODE, TO_CHAR(r.RENT_DATE, 'YYYY-MM-DD') " + 
            "FROM RENT r, LIB l, MEMBERS m " +
            "WHERE r.LIB_CODE = l.lib_code AND m.mb_phone = r.MEM_PHONE " +
            "ORDER BY r.RENT_NO";
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

   public static void main(String[] args) {
      DB.init();
      new BorrowInformation("대여정보", 500, 600);
      
   }

   @Override
   public void actionPerformed(ActionEvent e) {
       Object obj = e.getSource();
         if (obj == btn_back) {
            dispose();
         } 
   }
}