package eu.smart.cbdm.charts;

import javax.annotation.PostConstruct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.BubbleChartSeries;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.primefaces.model.chart.OhlcChartModel;
import org.primefaces.model.chart.OhlcChartSeries;

import eu.smart.cdbm.dao.DBException;
import eu.smart.cdbm.dao.TrendDAO;
import eu.smart.cdbm.dao.WeeklyTrend;
import eu.smart.cdbm.dao.WeeklyTrendByHour;
 
@ManagedBean
public class ChartView implements Serializable {
 

    private BarChartModel barModel;
    private BarChartModel barModel2;
    private String location_id="smart_0001";

 
    @PostConstruct
    public void init() {
        createBarModel();

    }
    


    
    private BarChartModel initBarModelUsingWeekLyTrend() {
        BarChartModel model = new BarChartModel();
        TrendDAO _dao = new TrendDAO();
        try {
			List <WeeklyTrend> trends = _dao.weeklyTrends(location_id);
			Iterator<WeeklyTrend> _iter = trends.iterator();
			while(_iter.hasNext()){
				WeeklyTrend trend = _iter.next();
				ChartSeries chart_serie = new ChartSeries();
				chart_serie.setLabel(trend.getAlarm());
				chart_serie.set("MON", trend.getMonday());
				chart_serie.set("TUE", trend.getTuesday());
				chart_serie.set("WED", trend.getWednesday());
				chart_serie.set("THU", trend.getThursday());
				chart_serie.set("FRY", trend.getFriday());
				chart_serie.set("SAT", trend.getSaturday());
				chart_serie.set("SUN", trend.getSunday());
		        model.addSeries(chart_serie);
				
			}
			
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

         
        return model;
    }
    
    
    private BarChartModel initBarModelUsingWeekLyTrendByHour() {
        BarChartModel model = new BarChartModel();
        TrendDAO _dao = new TrendDAO();
        try {
	
        	
        	
        	
        	ChartSeries chart_serie = new ChartSeries();
			chart_serie.setLabel("MONDAY");
			ChartSeries chart_serie2 = new ChartSeries();
			chart_serie2.setLabel("TUESDAY");
			ChartSeries chart_serie3 = new ChartSeries();
			chart_serie3.setLabel("WEDNESDAY");
			ChartSeries chart_serie4 = new ChartSeries();
			chart_serie4.setLabel("THURSDAY");
			ChartSeries chart_serie5 = new ChartSeries();
			chart_serie5.setLabel("FRIDAY");
			ChartSeries chart_serie6 = new ChartSeries();
			chart_serie6.setLabel("SATURDAY");
			ChartSeries chart_serie7 = new ChartSeries();
			chart_serie7.setLabel("SUNDAY");

        	List <WeeklyTrendByHour> trends = _dao.weeklyTrendsByHour("smart_0001");
			Iterator<WeeklyTrendByHour> _iter = trends.iterator();
			while(_iter.hasNext()){
				WeeklyTrendByHour trend = _iter.next();
				chart_serie.set(""+trend.getHour(), trend.getMonday());
				chart_serie2.set(""+trend.getHour(), trend.getTuesday());
				chart_serie3.set(""+trend.getHour(), trend.getWednesday());
				chart_serie4.set(""+trend.getHour(), trend.getThursday());
				chart_serie5.set(""+trend.getHour(), trend.getFriday());
				chart_serie6.set(""+trend.getHour(), trend.getSaturday());
				chart_serie7.set(""+trend.getHour(), trend.getSunday());
				
			}
			model.addSeries(chart_serie);
			model.addSeries(chart_serie2);
			model.addSeries(chart_serie3);
			model.addSeries(chart_serie4);
			model.addSeries(chart_serie5);
			model.addSeries(chart_serie6);
			model.addSeries(chart_serie7);
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

         
        return model;
    }
    
    private void createBarModel() {
        barModel = initBarModelUsingWeekLyTrend();
         
        barModel.setTitle("Bar Chart");
        barModel.setLegendPosition("ne");
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Day of week");
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Alarm occurrences");
        yAxis.setMin(0);
        yAxis.setMax(40);
        
        barModel2 = initBarModelUsingWeekLyTrendByHour();
        
        barModel2.setTitle("Bar Chart");
        barModel2.setLegendPosition("ne");
         
        Axis xAxis2 = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Day/Hour of week");
         
        Axis yAxis2 = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Traffic alarms occurrences");
        yAxis.setMin(0);
        yAxis.setMax(40);
        
        
    }

 
   
	public BarChartModel getBarModel() {
		return barModel;
	}


	public BarChartModel getBarModel2() {
		return barModel2;
	}




	public String getLocation_id() {
		return location_id;
	}




	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}
	
	public void aggiornaLocation_id(String location_id) {
		this.location_id = location_id;
	}

	
 
}