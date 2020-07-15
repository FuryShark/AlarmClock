package alarmClock;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.border.*;

public class AlarmClock extends JFrame {
	public AlarmClock() {
		initComponents();
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
		spinnerHour.setModel(new SpinnerNumberModel(0, 0, 24, 1));

		//---- labelHour ----
		labelHour.setText("HOUR");
		labelHour.setHorizontalAlignment(SwingConstants.CENTER);

		//---- labelMinute ----
		labelMinute.setText("MINUTE");
		labelMinute.setHorizontalAlignment(SwingConstants.CENTER);

		//---- spinnerMinute ----
		spinnerMinute.setModel(new SpinnerNumberModel(0, 0, 60, 1));

		//---- labelSecond ----
		labelSecond.setText("SECOND");
		labelSecond.setHorizontalAlignment(SwingConstants.CENTER);

		//---- spinnerSecond ----
		spinnerSecond.setModel(new SpinnerNumberModel(0, 0, 60, 1));

		//---- buttonAdd ----
		buttonAdd.setText("ADD ALARM");

		//---- buttonRemove ----
		buttonRemove.setText("REMOVE ALARM");

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
