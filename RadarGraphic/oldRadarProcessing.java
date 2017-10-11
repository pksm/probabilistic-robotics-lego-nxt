import processing.core.PApplet;

	public class RadarProcessing extends PApplet implements Runnable
		{

			String angle="";
			String distance="";
			String data="";
			String noObject;
			float pixsDistance;
			int iAngle, iDistance;
			int index1=0;
			int index2=0;

			ProcessingSource[] ps = new ProcessingSource[90];
			//@Override
			private CubbyHole cubbyhole;

			public RadarProcessing(CubbyHole c)
			{
				cubbyhole = c;
			}

			public void run(){
				PApplet.main(new String[] {"RadarProcessing"});
			}

			// public void main(String[] args) {
			// 	PApplet.main(new String[] {"RadarProcessing"});
			// }
			
			public void settings()
			{
				size(800,600);
			}
			
			public void setup()
			{
				size (800, 600);
				smooth();
				for (int i = 0; i < ps.length; i++)
				{
					ps[i] = new ProcessingSource(this);
				}	
			}
			
			public void drawRadar()
			{
				pushMatrix();
				translate((float)width/2,(float)(height-height*0.074));
				noFill();
				strokeWeight(2);
				stroke(98,245,31);
				// draws the arc lines
				arc((float)0.0,(float)0.0,(float)(width-width*0.0625),(float)(width-width*0.0625),PI,TWO_PI);
				arc((float)0.0,(float)0.0,(float)(width-width*0.27),(float)(width-width*0.27),PI,TWO_PI);
				arc((float)0.0,(float)0.0,(float)(width-width*0.479),(float)(width-width*0.479),PI,TWO_PI);
				arc((float)0.0,(float)0.0,(float)(width-width*0.687),(float)(width-width*0.687),PI,TWO_PI);
				// draws the angle lines
				line(-width/2,0,width/2,0);
				line(0,0,(-width/2)*cos(radians(30)),(-width/2)*sin(radians(30)));
				line(0,0,(-width/2)*cos(radians(60)),(-width/2)*sin(radians(60)));
				line(0,0,(-width/2)*cos(radians(90)),(-width/2)*sin(radians(90)));
				line(0,0,(-width/2)*cos(radians(120)),(-width/2)*sin(radians(120)));
				line(0,0,(-width/2)*cos(radians(150)),(-width/2)*sin(radians(150)));
				line((-width/2)*cos(radians(30)),0,width/2,0);
				popMatrix();
			}

			public void drawObject()
			{
				pushMatrix();
				translate((float)(width/2),(float)(height-height*0.074));
				strokeWeight(9);
				stroke(255,10,10);
				pixsDistance = iDistance*((float)((height-height*0.1666)*0.025)); // covers the distance from the sensor from cm to pixels
				if(iDistance<200) // distance in cm
				{
					line(pixsDistance*cos(radians(iAngle)),-pixsDistance*sin(radians(iAngle)),(float)(width-width*0.505)*cos(radians(iAngle)),(float)-(width-width*0.505)*sin(radians(iAngle)));
				}
				popMatrix();
			}

			public void drawLine()
			{
				pushMatrix();
				strokeWeight(9);
				stroke(30,250,60);
				translate((float)(width/2),(float)(height-height*0.074)); // moves the starting coordinats to new location
				line((float)0.0,(float)0.0,(float)((height-height*0.12)*cos(radians(iAngle))),(float)-(height-height*0.12)*sin(radians(iAngle))); // draws the line according to the angle
				popMatrix();
			}

			public void drawText()
			{
				pushMatrix();
				if(iDistance>200)
				{
					noObject = "Out of Range";
				}
				else
				{
					noObject = "In Range";
				}
				fill(0,0,0);
				noStroke();
				rect((float)0.0, (float)(height-height*0.0648), (float)width, (float)height);
				fill(98,245,31);
				textSize(25);

				text("10cm",(float)(width-width*0.3854),(float)(height-height*0.0833));
				text("20cm",(float)(width-width*0.281),(float)(height-height*0.0833));
				text("30cm",(float)(width-width*0.177),(float)(height-height*0.0833));
				text("40cm",(float)(width-width*0.0729),(float)(height-height*0.0833));
				textSize(40);
				text("Object: " + noObject, (float)(width-width*0.875), (float)(height-height*0.0277));
				text("Angle: " + iAngle +" °", (float)(width-width*0.48), (float)(height-height*0.0277));
				text("Distance: ", (float)(width-width*0.26), (float)(height-height*0.0277));
				if(iDistance<200)
				{
					text("        " + iDistance +" cm", (float)(width-width*0.225), (float)(height-height*0.0277));
				}
				textSize(25);
				fill(98,245,60);
				translate((float)((width-width*0.4994)+width/2*cos(radians(30))),(float)((height-height*0.0907)-width/2*sin(radians(30))));
				rotate(-radians(-60));
				text("30°",0,0);
				resetMatrix();
				translate((float)((width-width*0.503)+width/2*cos(radians(60))),(float)((height-height*0.0888)-width/2*sin(radians(60))));
				rotate(-radians(-30));
				text("60°",0,0);
				resetMatrix();
				translate((float)((width-width*0.507)+width/2*cos(radians(90))),(float)((height-height*0.0833)-width/2*sin(radians(90))));
				rotate(radians(0));
				text("90°",0,0);
				resetMatrix();
				translate((float)(width-width*0.513+width/2*cos(radians(120))),(float)((height-height*0.07129)-width/2*sin(radians(120))));
				rotate(radians(-30));
				text("120°",0,0);
				resetMatrix();
				translate((float)((width-width*0.5104)+width/2*cos(radians(150))),(float)((height-height*0.0574)-width/2*sin(radians(150))));
				rotate(radians(-60));
				text("150°",0,0);
				popMatrix(); 
			}	

			public void draw()
			{
				fill(98,245,31);
				for (int i = 0; i < ps.length; i++) {
					ps[i].rrun();
					ps[i].display();
				}
				//noStroke();
				//fill(0,4); 
				//rect((float)0.0, (float)0.0, (float)width, (float)(height-height*0.065));

				//fill(98,245,31); // green color
				//// calls the functions for drawing the radar
				//drawRadar(); 
				//drawLine();
				//drawObject();
				//drawText();	
			}
			
		}
