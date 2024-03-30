package as22;

import javax.swing.JFrame;

public class Window {

	public static void main(String[] args) {
		App app = new App();
		JFrame frame = new JFrame("da window lole");
		frame.add(app);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

}
