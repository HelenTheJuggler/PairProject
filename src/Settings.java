
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Settings extends JPanel{
	String[] names = {"Normal","Other Cat"};
	Window win;
	JPanel panel;
	JComboBox comboBox;
	JCheckBox checkBox;
	boolean friction;
	public Settings(Window w){
		win = w;
		friction = false;
		panel = new JPanel(new GridLayout(0, 2, 10, 10));
		panel.add(new JLabel("Type of Cat: "));
		comboBox = new JComboBox(names);
		comboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				String selected = (String) ((JComboBox)event.getSource()).getSelectedItem();
				if(selected.equals(names[0])){
					win.getGame().setCat(new Cat(false));
				}
			}
		});
		panel.add(comboBox);
		panel.add(new JLabel("Friction"));
		checkBox = new JCheckBox();
		checkBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(checkBox.isSelected()){
					checkBox.setSelected(false);
				}
				else{
					checkBox.setSelected(true);
				}
				friction = !friction;
				win.getGame().getCat().setFriction(friction);
			}
		});
	}
}
	
