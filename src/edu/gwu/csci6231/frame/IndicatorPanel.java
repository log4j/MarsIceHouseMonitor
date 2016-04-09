package edu.gwu.csci6231.frame;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import edu.gwu.csci6231.device.model.DataModel;

public class IndicatorPanel extends Composite implements Observer{
	
	protected DataModel model = null;

	protected Label nameLabel,valueLabel,alarmLabel;
	protected Button fixButton;
	
	protected IndicatorPanel(Composite parent, int mode) {
		super(parent, mode);
		
		this.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		nameLabel = new Label(this,SWT.NONE);
		valueLabel = new Label(this,SWT.NONE);
		fixButton = new Button(this,SWT.NONE);
		alarmLabel = new Label(this,SWT.NONE);
		alarmLabel.setForeground(new Color(null,255,0,0));
		fixButton.setText("Send Robot");
		fixButton.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				System.out.println("Fix clicked");
			}
			
		});
	}
	
	public IndicatorPanel(Composite parent, int mode, DataModel model){
		this(parent,mode);
		this.model = model;
		this.update();
	}
	
	public void update(){
		if(model!=null){
			nameLabel.setText(model.getModelName());
			valueLabel.setText(model.getDataString());
			alarmLabel.setText(model.isAlarming()?"Alarm!!!":"");
		}else{
			nameLabel.setText("NA");
		}
//		System.out.println(model.getData());
	}

	@Override
	public void update(Observable o, Object arg) {
		if(this.getDisplay().isDisposed())
			return;
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				update();
			}
		});
	}

}
