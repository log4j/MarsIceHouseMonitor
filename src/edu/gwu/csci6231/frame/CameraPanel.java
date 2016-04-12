package edu.gwu.csci6231.frame;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class CameraPanel  extends Composite implements Observer {

	protected Canvas videoCanvas;
	
	public CameraPanel(Composite parent, int mode) {
		super(parent, mode);
		
		videoCanvas = new Canvas(this, SWT.NONE);
		videoCanvas.setBounds(5, 5, 200, 200);
		videoCanvas.addPaintListener(new PaintListener(){

			@Override
			public void paintControl(PaintEvent e) {
				
				e.gc.setBackground(FrameUtil.INDICATOR_FGS[0]);
				
				e.gc.fillRectangle(0, 0, 200, 200);
				
			}
			
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}

}
