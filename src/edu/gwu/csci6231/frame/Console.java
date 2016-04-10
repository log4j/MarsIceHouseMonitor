package edu.gwu.csci6231.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import edu.gwu.csci6231.device.model.DataModel;

public class Console extends Composite implements Observer{

	private List<IndicatorPanel> indicators;
	
	
	public Console(Composite parent, int mode) {
		super(parent, mode);
		
		this.setLayout(new RowLayout(SWT.VERTICAL | SWT.BORDER));
		
		indicators = new ArrayList<IndicatorPanel>();
		
	}
	
	public void addDataModel(DataModel model){
		IndicatorPanel indicator = new IndicatorPanel(this,SWT.None,model);
		indicators.add(indicator);
		model.addObserver(indicator);
		model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(this.getDisplay().isDisposed())
			return;
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				pack();
			}
		});
		
	}

}
