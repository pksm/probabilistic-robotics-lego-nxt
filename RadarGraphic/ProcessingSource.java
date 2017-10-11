import java.awt.event.KeyEvent; // imports library for reading the data from the serial port
import java.io.IOException;

import processing.core.PApplet;

public class ProcessingSource
{
	int iAngle, iDistance;
	String angle="";
	String distance="";
	String data="";
	String noObject;
	float pixsDistance;
	PApplet parent;
	Reading r;
	CubbyHole c;
	
	ProcessingSource(PApplet p)
	{
		parent = p;
		iAngle = 0;
		iDistance = (int) parent.random(10.0f, 70.0f);
		//this.c = c;
	}

	// Draw stripe
	void display()
	{
		parent.fill(98,245,31);
		parent.noStroke();
		parent.fill(0,4); 
		parent.rect((float)0.0, (float)0.0, (float)parent.width, (float)(parent.height-parent.height*0.065));

		parent.fill(98,245,31); // green color
		// calls the functions for drawing the radar
		drawRadar();
		drawLine();
		drawObject();
		drawText();
	}
	
	void drawRadar()
	{
		parent.pushMatrix();
		parent.translate((float)parent.width/2,(float)(parent.height-parent.height*0.074));
		parent.noFill();
		parent.strokeWeight(2);
		parent.stroke(98,245,31);
		// draws the arc lines
		parent.arc((float)0.0,(float)0.0,(float)(parent.width-parent.width*0.0625),(float)(parent.width-parent.width*0.0625),parent.PI,parent.TWO_PI);
		parent.arc((float)0.0,(float)0.0,(float)(parent.width-parent.width*0.27),(float)(parent.width-parent.width*0.27),parent.PI,parent.TWO_PI);
		parent.arc((float)0.0,(float)0.0,(float)(parent.width-parent.width*0.479),(float)(parent.width-parent.width*0.479),parent.PI,parent.TWO_PI);
		parent.arc((float)0.0,(float)0.0,(float)(parent.width-parent.width*0.687),(float)(parent.width-parent.width*0.687),parent.PI,parent.TWO_PI);
		// draws the angle lines
		parent.line(-parent.width/2,0,parent.width/2,0);
		parent.line(0,0,(-parent.width/2)*parent.cos(parent.radians(30)),(-parent.width/2)*parent.sin(parent.radians(30)));
		parent.line(0,0,(-parent.width/2)*parent.cos(parent.radians(60)),(-parent.width/2)*parent.sin(parent.radians(60)));
		parent.line(0,0,(-parent.width/2)*parent.cos(parent.radians(90)),(-parent.width/2)*parent.sin(parent.radians(90)));
		parent.line(0,0,(-parent.width/2)*parent.cos(parent.radians(120)),(-parent.width/2)*parent.sin(parent.radians(120)));
		parent.line(0,0,(-parent.width/2)*parent.cos(parent.radians(150)),(-parent.width/2)*parent.sin(parent.radians(150)));
		parent.line((-parent.width/2)*parent.cos(parent.radians(30)),0,parent.width/2,0);
		parent.popMatrix();
	}

	void drawObject()
	{
		parent.pushMatrix();
		parent.translate((float)(parent.width/2),(float)(parent.height-parent.height*0.074));
		parent.strokeWeight(9);
		parent.stroke(255,10,10);
		pixsDistance = iDistance*((float)((parent.height-parent.height*0.1666)*0.025)); // covers the distance from the sensor from cm to pixels
		if(iDistance<200) // distance in cm
		{
			parent.line(pixsDistance*parent.cos(parent.radians(iAngle)),-pixsDistance*parent.sin(parent.radians(iAngle)),(float)(parent.width-parent.width*0.505)*parent.cos(parent.radians(iAngle)),(float)-(parent.width-parent.width*0.505)*parent.sin(parent.radians(iAngle)));
		}
		parent.popMatrix();
	}

	void drawLine()
	{
		parent.pushMatrix();
		parent.strokeWeight(9);
		parent.stroke(30,250,60);
		parent.translate((float)(parent.width/2),(float)(parent.height-parent.height*0.074)); // moves the starting coordinats to new location
		parent.line((float)0.0,(float)0.0,(float)((parent.height-parent.height*0.12)*parent.cos(parent.radians(iAngle))),(float)-(parent.height-parent.height*0.12)*parent.sin(parent.radians(iAngle))); // draws the line according to the angle
		parent.popMatrix();
	}

	void drawText()
	{
		parent.pushMatrix();
		if(iDistance>200)
		{
			noObject = "Out of Range";
		}
		else
		{
			noObject = "In Range";
		}
		parent.fill(0,0,0);
		parent.noStroke();
		parent.rect((float)0.0, (float)(parent.height-parent.height*0.0648), (float)parent.width, (float)parent.height);
		parent.fill(98,245,31);
		parent.textSize(25);

		parent.text("10cm",(float)(parent.width-parent.width*0.3854),(float)(parent.height-parent.height*0.0833));
		parent.text("20cm",(float)(parent.width-parent.width*0.281),(float)(parent.height-parent.height*0.0833));
		parent.text("30cm",(float)(parent.width-parent.width*0.177),(float)(parent.height-parent.height*0.0833));
		parent.text("40cm",(float)(parent.width-parent.width*0.0729),(float)(parent.height-parent.height*0.0833));
		parent.textSize(40);
		parent.text("Object: " + noObject, (float)(parent.width-parent.width*0.875), (float)(parent.height-parent.height*0.0277));
		parent.text("Angle: " + iAngle +" °", (float)(parent.width-parent.width*0.48), (float)(parent.height-parent.height*0.0277));
		parent.text("Distance: ", (float)(parent.width-parent.width*0.26), (float)(parent.height-parent.height*0.0277));
		if(iDistance<200)
		{
			parent.text("        " + iDistance +" cm", (float)(parent.width-parent.width*0.225), (float)(parent.height-parent.height*0.0277));
		}
		parent.textSize(25);
		parent.fill(98,245,60);
		parent.translate((float)((parent.width-parent.width*0.4994)+parent.width/2*parent.cos(parent.radians(30))),(float)((parent.height-parent.height*0.0907)-parent.width/2*parent.sin(parent.radians(30))));
		parent.rotate(-parent.radians(-60));
		parent.text("30°",0,0);
		parent.resetMatrix();
		parent.translate((float)((parent.width-parent.width*0.503)+parent.width/2*parent.cos(parent.radians(60))),(float)((parent.height-parent.height*0.0888)-parent.width/2*parent.sin(parent.radians(60))));
		parent.rotate(-parent.radians(-30));
		parent.text("60°",0,0);
		parent.resetMatrix();
		parent.translate((float)((parent.width-parent.width*0.507)+parent.width/2*parent.cos(parent.radians(90))),(float)((parent.height-parent.height*0.0833)-parent.width/2*parent.sin(parent.radians(90))));
		parent.rotate(parent.radians(0));
		parent.text("90°",0,0);
		parent.resetMatrix();
		parent.translate((float)(parent.width-parent.width*0.513+parent.width/2*parent.cos(parent.radians(120))),(float)((parent.height-parent.height*0.07129)-parent.width/2*parent.sin(parent.radians(120))));
		parent.rotate(parent.radians(-30));
		parent.text("120°",0,0);
		parent.resetMatrix();
		parent.translate((float)((parent.width-parent.width*0.5104)+parent.width/2*parent.cos(parent.radians(150))),(float)((parent.height-parent.height*0.0574)-parent.width/2*parent.sin(parent.radians(150))));
		parent.rotate(parent.radians(-60));
		parent.text("150°",0,0);
		parent.popMatrix(); 
	}
	
	void rrun()
	{
		c = Consumer.getCubby();
		r = c.get();
		iAngle = r.getAngle();
		iDistance = r.getDistance();
	}

}
