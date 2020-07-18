package alarmClock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.*;

public class AlarmClock extends JFrame {

	private LinkedList<Long> queue = new LinkedList<Long>();
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	
	public LinkedList<Long> getQueue(){
		return queue;
	}

	public AlarmClock() {
		initComponents();
		dlm.addElement("No alarms set");
	}

	public static String timeFormatHMS(long time) {
		StringBuilder t = new StringBuilder();
		long total_secs = time / 1000L;
		long total_mins = total_secs / 60L;
		long total_hrs = total_mins / 60L;
		int secs = (int) total_secs % 60;
		int mins = (int) total_mins % 60;
		int hrs = (int) total_hrs % 24;
		if (hrs < 10) {
			t.append("0");
		}
		t.append(hrs).append(":");
		if (mins < 10) {
			t.append("0");
		}
		t.append(mins).append(":");
		if (secs < 10) {
			t.append("0");
		}
		t.append(secs);
		return t.toString();
	}

	private void buttonAddActionPerformed(ActionEvent e) {
		int hour = (int) spinnerHour.getValue();
		int minute = (int) spinnerMinute.getValue();
		int second = (int) spinnerSecond.getValue();
		Long alarmTimeMs = (long) ((hour * 3600000) + (minute * 60000) + (second * 1000));
		addToQueue(alarmTimeMs);
		dlm.clear();
		for (int i = 0; i < queue.size(); i++) {
			dlm.addElement("Alarm set: " + timeFormatHMS(queue.get(i)));
		}
		listAlarms.setModel(dlm);
	}


	private void addToQueue(Long alarmTimeMs) {
		if (queue.isEmpty()) {
			queue.add(alarmTimeMs);
		} else if (queue.get(0) > alarmTimeMs) {
			queue.add(0, alarmTimeMs);
		} else if (queue.get(queue.size() - 1) < alarmTimeMs) {
			queue.add(queue.size(), alarmTimeMs);
		} else {
			int i = 0;
			while (queue.get(i) < alarmTimeMs) {
				i++;
			}
			queue.add(i, alarmTimeMs);
		}
	}

	private void buttonRemoveActionPerformed(ActionEvent e) {
		if (dlm.size() != -1 ) {
			if (listAlarms.getSelectedIndex() != -1) {
				String selection = listAlarms.getSelectedValue().split("set: ")[1];
				if (selection.equals("No alarms set")) {
					return;
				} else {
					int hour = Integer.parseInt(selection.split(":")[0]);
					int minute = Integer.parseInt(selection.split(":")[1]);
					int second = Integer.parseInt(selection.split(":")[2]);
					Long alarmTimeMs = (long) ((hour * 3600000) + (minute * 60000) + (second * 1000));
					queue.remove(alarmTimeMs);
					dlm.remove(listAlarms.getSelectedIndex());
				}
			} else {
				String selection = dlm.get(dlm.getSize() - 1);
				if (selection.equals("No alarms set")) {
					return;
				} else {
					
					dlm.remove(dlm.getSize() - 1);
					
				}
			}
			if (dlm.isEmpty()) {
				dlm.addElement("No alarms set");
			}
		}
		return;
	}

	private void initComponents() {
		panelClock = new JPanel();
		scrollPaneAlarms = new JScrollPane();
		listAlarms = new JList<>();
		spinnerHour = new JSpinner();
		labelHour = new JLabel();
		labelMinute = new JLabel();
		spinnerMinute = new JSpinner();
		labelSecond = new JLabel();
		spinnerSecond = new JSpinner();
		buttonAdd = new JButton();
		buttonRemove = new JButton();

		//======== this ========
		setTitle("AlarmClock");
		Container contentPane = getContentPane();

		//======== panelClock ========
		{
			panelClock.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));

			GroupLayout panelClockLayout = new GroupLayout(panelClock);
			panelClock.setLayout(panelClockLayout);
			panelClockLayout.setHorizontalGroup(
					panelClockLayout.createParallelGroup()
					.addGap(0, 198, Short.MAX_VALUE)
					);
			panelClockLayout.setVerticalGroup(
					panelClockLayout.createParallelGroup()
					.addGap(0, 200, Short.MAX_VALUE)
					);
		}

		//======== scrollPaneAlarms ========
		{

			//---- listAlarms ----
			listAlarms.setModel(new AbstractListModel<String>() {
				String[] values = {
						"No alarms set"
				};
				@Override
				public int getSize() { return values.length; }
				@Override
				public String getElementAt(int i) { return values[i]; }
			});
			scrollPaneAlarms.setViewportView(listAlarms);
		}

		//---- spinnerHour ----
		spinnerHour.setModel(new SpinnerNumberModel(0, 0, 23, 1));

		//---- labelHour ----
		labelHour.setText("HOUR");
		labelHour.setHorizontalAlignment(SwingConstants.CENTER);

		//---- labelMinute ----
		labelMinute.setText("MINUTE");
		labelMinute.setHorizontalAlignment(SwingConstants.CENTER);

		//---- spinnerMinute ----
		spinnerMinute.setModel(new SpinnerNumberModel(0, 0, 59, 1));

		//---- labelSecond ----
		labelSecond.setText("SECOND");
		labelSecond.setHorizontalAlignment(SwingConstants.CENTER);

		//---- spinnerSecond ----
		spinnerSecond.setModel(new SpinnerNumberModel(0, 0, 59, 1));

		//---- buttonAdd ----
		buttonAdd.setText("ADD ALARM");
		buttonAdd.addActionListener(e -> buttonAddActionPerformed(e));

		//---- buttonRemove ----
		buttonRemove.setText("REMOVE ALARM");
		buttonRemove.addActionListener(e -> buttonRemoveActionPerformed(e));

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
				contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(buttonAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(scrollPaneAlarms, GroupLayout.Alignment.TRAILING)
								.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addComponent(panelClock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(contentPaneLayout.createSequentialGroup()
										.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(spinnerHour, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
												.addComponent(labelHour, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
										.addGap(18, 18, 18)
										.addGroup(contentPaneLayout.createParallelGroup()
												.addComponent(spinnerMinute)
												.addComponent(labelMinute, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGap(18, 18, 18)
										.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(spinnerSecond, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
												.addComponent(labelSecond, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)))
								.addComponent(buttonRemove, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap())
				);
		contentPaneLayout.setVerticalGroup(
				contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelClock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(scrollPaneAlarms, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(labelHour)
								.addComponent(labelSecond)
								.addComponent(labelMinute))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(spinnerSecond, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinnerHour, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinnerMinute, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addComponent(buttonAdd)
						.addGap(18, 18, 18)
						.addComponent(buttonRemove)
						.addContainerGap(13, Short.MAX_VALUE))
				);
		pack();
		setLocationRelativeTo(getOwner());
	}

	private JPanel panelClock;
	private JScrollPane scrollPaneAlarms;
	private JList<String> listAlarms;
	private JSpinner spinnerHour;
	private JLabel labelHour;
	private JLabel labelMinute;
	private JSpinner spinnerMinute;
	private JLabel labelSecond;
	private JSpinner spinnerSecond;
	private JButton buttonAdd;
	private JButton buttonRemove;
}
