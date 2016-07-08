package com.atguigu.crm.orm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

@Component("success")
public class JfreeChartView extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> arg0,
			HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		PieDataset paramPieDataset = createDataset(arg0);
		JFreeChart chart = createChart(paramPieDataset);
		
		ChartUtilities.writeChartAsPNG(arg2.getOutputStream(), chart, 500, 270);
	}
	
	private static PieDataset createDataset(Map<String, Object> map) {
		DefaultPieDataset localDefaultPieDataset = new DefaultPieDataset();
		
		for(Map.Entry<String, Object> entry: map.entrySet()){
			localDefaultPieDataset.setValue(entry.getKey(), (Number)entry.getValue());
		}
		
		return localDefaultPieDataset;
	}

	private static JFreeChart createChart(PieDataset paramPieDataset) {
		JFreeChart localJFreeChart = ChartFactory.createPieChart3D(
				"Pie Chart 3D Demo 1", paramPieDataset, true, true, false);
		PiePlot3D localPiePlot3D = (PiePlot3D) localJFreeChart.getPlot();
		localPiePlot3D.setDarkerSides(true);
		localPiePlot3D.setStartAngle(290.0D);
		localPiePlot3D.setDirection(Rotation.CLOCKWISE);
		localPiePlot3D.setForegroundAlpha(0.5F);
		localPiePlot3D.setNoDataMessage("No data to display");
		return localJFreeChart;
	}


}
