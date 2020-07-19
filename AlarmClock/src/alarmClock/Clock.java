package alarmClock;

import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Clock {

	public static void main(String[] args) {
		String loadFile = null;
		if (showConfirmDialog("Load alarms?")) {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				loadFile = selectedFile.getAbsolutePath();
			}
		}
		Model model = new Model();
		AlarmClock alarmClock = new AlarmClock(model, loadFile);
		alarmClock.setVisible(true);
		model.addObserver(alarmClock);
		Controller controller = new Controller(model, alarmClock);
		alarmClock.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.out.println("HEEEEEEEE");
				if (alarmClock.getQueue().size() > 0) {
					if (showConfirmDialog("Save alarms?")) {
						saveFile(alarmClock);
					}
				}
			}
		});
	}

	private static boolean showConfirmDialog(String message) {
		return JOptionPane.showConfirmDialog(null, message, message, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	private static void saveFile(AlarmClock alarmClock) {
		JFileChooser fileChooser = new JFileChooser();
		int retval = fileChooser.showSaveDialog(alarmClock);
		String fileName = null;
		if (retval == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file != null) {
				if (!file.getName().toLowerCase().endsWith(".txt")) {
					file = new File(file.getParentFile(), file.getName() + ".txt");
				}
			}
			fileName = file.getAbsolutePath();
		}
		if (fileName != null) {
			System.out.println("HERE");
			File file = new File(fileName);
			PrintWriter printWriter = null;
			if (file.exists()) {
				file.delete();
			}
			try {
				if (!file.exists()) {
					System.out.println("MAKING NEW FILE");
					if (file.createNewFile()) {
						System.out.println("NEW");
					}
				}
				printWriter = new PrintWriter(new FileOutputStream(file, true));
				for (int i = 0; i < alarmClock.getQueue().size(); i++) {
					System.out.println(alarmClock.getQueue().get(i));
					printWriter.println(alarmClock.getQueue().get(i));
				}
			} catch (IOException ioex) {
				System.out.println(ioex);
			} finally {
				if (printWriter != null) {
					printWriter.flush();
					printWriter.close();
				}
			}
		}
	}
}
