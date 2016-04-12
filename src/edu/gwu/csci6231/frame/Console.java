package edu.gwu.csci6231.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import edu.gwu.csci6231.device.model.DataModel;

public class Console extends Composite implements Observer{

	
	private List<IndicatorPanel> indicators;
	
	
	public Console(Composite parent, int mode) {
		super(parent, mode);
		
		this.setLayout(null);
	
		
		indicators = new ArrayList<IndicatorPanel>();
		
	}
	
	public void addDataModel(DataModel model){
		IndicatorPanel indicator = new IndicatorPanel(this,SWT.NONE,model);
		indicators.add(indicator);
		indicator.setBgColor(FrameUtil.INDICATOR_BGS[indicators.size()%FrameUtil.INDICATOR_BGS.length]);
		indicator.setFgColor(FrameUtil.INDICATOR_FGS[indicators.size()%FrameUtil.INDICATOR_FGS.length]);
		indicator.setSubTitleColor(FrameUtil.INDICATOR_TITLECOLORS[indicators.size()%FrameUtil.INDICATOR_TITLECOLORS.length]);
		indicator.setBounds(10, 10 + (5+IndicatorPanel.HEIGHT) * (indicators.size()-1), IndicatorPanel.WIDTH, IndicatorPanel.HEIGHT);
		model.addObserver(indicator);
		model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(this.isDisposed()||this.getDisplay().isDisposed())
			return;
		this.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
//				pack();
			}
		});
		
	}

}
