package chart.core;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.Chart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class SemiCircleChart extends Chart {
	private ObservableList<Data> dataList; //TODO: react to changes in dataList
	private double centerX;
	private double centerY;
	private double radius;
	private double innerHoleRadius;
	private double separatorLength;
	private Color holeColor = Color.web("#f4f4f4");
	private Color separatorColor = holeColor;
	
	/**
	 * Construct a new SemiCircleChart with the data given.
	 * @param dataList the data to use
	 */
	public SemiCircleChart(ObservableList<Data> dataList){ 
		this(dataList, 0, 0,100);
	}
	
	/**
	 * Construct a new SemiCircleChart with the data given, at the given location, with the specified radius.
	 * @param dataList the data to use
	 * @param centerX X coordinate
	 * @param centerY Y coordinate
	 * @param radius the radius of the chart
	 */
	public SemiCircleChart(ObservableList<Data> dataList, double centerX, double centerY, double radius){
		this(dataList,centerX,centerY,radius,0,0);
	}
	
	/**
	 * Construct a new SemiCircleChart with the data given, at the given location, with the specified radius, and adds an inner hole with the specified radius, and separators between slices with the specified length.
	 * @param dataList the data to use
	 * @param centerX X coordinate
	 * @param centerY Y coordinate
	 * @param radius the radius of the chart, must be &gt;0
	 * @param innerHoleRadius the radius of the inner hole, must be &gt;=0. If no hole is desired, enter 0 or use one of the other constructors.
	 * @param separatorLength the length of the separators between slices, must be &gt;= 0. If no separators are desired, enter 0 or use one of the other constructors.
	 * @throws IllegalArgumentException if radius&lt;=0, innerHoleRadius&lt;0 or separatorLength&lt;0
	 */
	public SemiCircleChart(ObservableList<Data> dataList, double centerX, double centerY, double radius, double innerHoleRadius, double separatorLength){
		if(radius<=0) { throw new IllegalArgumentException("radius must be >0"); }
		if(separatorLength<0){ throw new IllegalArgumentException("separatorLength can't be negative"); }
		if(innerHoleRadius<0){ throw new IllegalArgumentException("innerHoleRadius can't be negative");	}
		
		this.dataList = dataList;
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.innerHoleRadius = innerHoleRadius;
		this.separatorLength = separatorLength;
		makeChart();
	}
	
	/**
	 * @return The data represented by the chart.
	 */
	public ObservableList<Data> getData(){
		return dataList;
	}
	
	/**
	 * @param data The data the chart will represent.
	 */
	public void setData(ObservableList<Data> data) {
		dataList = data;
	}
	
	/**
	 * Sets the color of the inner hole, can be used to match the background color.
	 * @param holeColor the Color for the inner hole.
	 */
	public void setHoleColor(Color holeColor){
		this.holeColor = holeColor;
		
		//reset the chart
		this.getChildren().clear();
		makeChart();
	}
	
	/**
	 * Sets the color of the separator, can be used to match the background color.
	 * @param separatorColor the Color for the separator.
	 */
	public void setSeparatorColor(Color separatorColor){
		this.separatorColor = separatorColor;
		
		//reset the chart
		this.getChildren().clear();
		makeChart();
	}
	
	private void makeChart(){
		double totalValues=0;
		
		for(Data data:dataList){
			totalValues += data.getValue();
		}
		
		double ratio = totalValues/180;
		double totalAngle=180;
		
		for(int i=0;i<dataList.size();i++){
			Data data = dataList.get(i);
			Arc slice = new Arc();
			slice.setCenterX(centerX);
			slice.setCenterY(centerY);
			slice.setRadiusX(radius);
			slice.setRadiusY(radius);
			slice.setType(ArcType.ROUND);
			slice.setFill(data.getColor());
			slice.setStartAngle(totalAngle);
			
			double length = data.getValue()/ratio;
			slice.setLength(-length);
			totalAngle -= length;
			
			//adding a tooltip
			 Tooltip tooltip = new Tooltip(data.getName());
			 

				slice.setOnMouseEntered(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						slice.setFill(data.getColor().brighter());
						tooltip.show(slice, event.getScreenX()+10, event.getScreenY()+10);
					}
				});

				slice.setOnMouseExited(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						slice.setFill(data.getColor());
						tooltip.hide();
					}
				});
				
				getChartChildren().add(slice);
			
			
			if(separatorLength>0&&i<dataList.size()-1){ //checking it's not the last slice because there can't be a separator after it.
				Arc sep = new Arc();
				sep.setCenterX(centerX);
				sep.setCenterY(centerY);
				sep.setRadiusX(radius+1);//enough to cover the border of the slice
				sep.setRadiusY(radius+1);
				sep.setType(ArcType.ROUND);
				sep.setFill(separatorColor);
				sep.setLength(-separatorLength);
				
				//we calculate the start angle so half of the separator overlaps one slice and the other half the other.
				double halfLength = separatorLength/2;
				sep.setStartAngle(totalAngle+halfLength);
				getChartChildren().add(sep);
			}
		} //closing for loop
		
		if(innerHoleRadius!=0){
			Arc hole = new Arc();
			hole.setCenterX(centerX);
			hole.setCenterY(centerY);
			hole.setRadiusX(innerHoleRadius);
			hole.setRadiusY(innerHoleRadius);
			hole.setType(ArcType.ROUND);
			hole.setFill(holeColor);
			hole.setStartAngle(0);
			hole.setLength(180);
			getChartChildren().add(hole);
		}
	}
	
	@Override
	protected void layoutChartChildren(double top, double left, double width, double height) {
		// TODO implement this
		
	}

	/**
	 * Class for holding the data for SemiCircleChart, it stores the name of the data element, its value and the color of the slice that will represent it.
	 *
	 */
	public static final class Data{
		private String name;
		private double value;
		private Color color;
		
		/**
		 * Creates a new Data object. The color will be chosen at random, since it's not specified.
		 * @param name the name of the data element.
		 * @param value the value of the data element.
		 */
		public Data(String name, double value) {
			this(name,value,Color.color(Math.random(), Math.random(), Math.random()));
		}
		
		/**
		 * Creates a new Data object with the specified color.
		 * @param name the name of the data element.
		 * @param value the value of the data element.
		 * @param color the color for the slice that will represent the data element.
		 */
		public Data(String name, double value, Color color) {
			this.name = name;
			this.value = value;
			this.color = color;
		}
		
		/**
		 * @return The name of the Data element.
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Sets the name of the Data element.
		 * @param name the new name for the element.
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * @return The value of the data element.
		 */
		public double getValue() {
			return value;
		}
		
		/**
		 * Sets a new value for the Data element.
		 * @param value the new value for the element.
		 */
		public void setValue(double value) {
			this.value = value;
		}
		
		/**
		 * @return The color assigned to the Data element.
		 */
		public Color getColor() {
			return color;
		}
		
		/**
		 * Assign a new color to the Data element.
		 * @param color the new color for representing the element.
		 */
		public void setColor(Color color) {
			this.color = color;
		}
	}
}
