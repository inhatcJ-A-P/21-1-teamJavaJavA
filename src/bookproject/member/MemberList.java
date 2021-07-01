package bookproject.member;
	
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import bookproject.DB;

//import org.graalvm.compiler.nodes.NodeView.Default;
	
public class MemberList extends JFrame implements ActionListener {
	
	private JPanel p1, p2;
	private JButton p1_reset, p1_enrollment, p1_lookup, p1_retouch, p1_del, p1_back;
	private static DefaultTableModel model;
	private Vector<String> con;
	public MemberList(String Title, int width, int height) {
		this.setTitle(Title);
		setSize(width, height);
		setLocation(900, 400);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		setLayout(new BorderLayout());
			
		p1 = new JPanel(); 
		p1_reset = new JButton("새로고침");   p1_reset.addActionListener(this);
		p1_enrollment = new JButton("등록"); p1_enrollment.addActionListener(this);
		p1_lookup = new JButton("조회");	    p1_lookup.addActionListener(this);
		p1_retouch = new JButton("수정");		p1_retouch.addActionListener(this);
		p1_del = new JButton("삭제");			p1_del.addActionListener(this);
		p1_back = new JButton("돌아가기");

		p1.add(p1_reset);
		p1.add(p1_enrollment);
		p1.add(p1_lookup);
		p1.add(p1_retouch);
		p1.add(p1_del);
		p1.add(p1_back);

		p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.setBorder(new TitledBorder(new LineBorder(Color.black,1),"회원목록"));

		JPanel p2_1 = new JPanel();
		p2_1.setLayout(new BorderLayout());
		
		Vector<String> header = new Vector<String>();
		header.add("이름");
		header.add("주민번호");
		header.add("전화번호");
		header.add("주소");

		model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		Vector<String> con;
		JTable table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scroll = new JScrollPane(table);
		p2_1.add(scroll);
		
		dbroad();

		p2.add(p2_1, BorderLayout.CENTER);
		
		p1.setPreferredSize(new Dimension(480, 40)); 		
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
	
		setVisible(true);
	}
	public static void dbroad() /*throws SQLException*/ {
		ResultSet re = DB.getResultSet("select * from members order by mb_name asc");
		try {
			while(re.next()) {
				String name = re.getString("mb_name");
				String num = re.getString("mb_num");
				String phone = re.getString("mb_phone");
				String address = re.getString("mb_addr");
				String [] tmp = {name, num, phone, address};
				model.addRow(tmp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*DBconnect db = new DBconnect();
		//ResultSet re = db.getStat().executeQuery("SELECT * FROM members");
		ResultSet re = db.getStat().executeQuery("select * from members order by name asc");
		while(re.next()) {
			String name = re.getString("name");
			String num = re.getString("num");
			String phone = re.getString("phone");
			String address = re.getString("address");
			String [] tmp = {name, num, phone, address};
			model.addRow(tmp);
		}
		*/
	}
	public static void main(String[] args) {
		DB.init();
		new MemberList("회원목록",480, 600);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob == p1_enrollment) new MemberEnrollment("회원등록", 300, 200, this);
		else if(ob == p1_lookup) new MemberSearch("회원검색", 300, 200, this);
		else if(ob == p1_retouch) new MemberRetouch("회원정보 수정", 300, 200);
		else if(ob == p1_del) new MemberDelete("회원 삭제", 300, 200);
		else if(ob == p1_reset) {
			model.setNumRows(0);
			dbroad();
		}
	}
	public DefaultTableModel getModel() {
		return model;
	}
	public void setModel(DefaultTableModel model) {
		this.model = model;
	}
}
