package org.microbridge.servocontrol;

import java.io.IOException;

import org.microbridge.server.AbstractServerListener;
import org.microbridge.server.Server;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 
 * A quick example of how to do two-way communication between an Android device and an Arduino using TCP forwarding over ADB.
 * 
 * @author Niels Brouwers
 *
 */
public class JoystickView extends View implements OnTouchListener
{
	
	private Paint borderPaint = new Paint();
	private Paint sensorPaint = new Paint();
	
	private Server server;
	private float sx, sy;
	private int sensorValue;
	
	public JoystickView(Context context, Server server)
	{
		super(context);
		
		this.server = server;
		this.server.addListener(new AbstractServerListener() {
			
			@Override
			public void onReceive(org.microbridge.server.Client client, byte[] data)
			{
				
				if (data.length<2) return;
				
				sensorValue = (data[0] & 0xff) | ((data[1] & 0xff) << 8);
				
				postInvalidate();
			};
			
		});
		
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		borderPaint.setColor(Color.WHITE);
		borderPaint.setAntiAlias(true);
		borderPaint.setStyle(Style.FILL);

		sensorPaint.setColor(Color.RED);
		sensorPaint.setStyle(Style.FILL);

		this.setOnTouchListener(this);
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		// Draw white background
		canvas.drawRect(this.getLeft(), this.getTop(), this.getRight(), this.getBottom(), borderPaint);
		
		canvas.drawRect(this.getLeft(), 4, (sensorValue / 1023.0f) * this.getRight(), 28, sensorPaint);
		
		canvas.drawText("" + sensorValue, 4, 50, sensorPaint);
		
		canvas.drawRect(sx - 20, sy - 20, sx + 20, sy + 20, sensorPaint);

	}
	
	public boolean onTouch(View v, MotionEvent event)
	{
		sx = event.getX();
		sy = event.getY();
		
		int x = Math.round((sx / this.getWidth()) * 180);
		int y = Math.round((sy / this.getHeight()) * 180);
		
		try
		{
		server.send(new byte[] { (byte)x, (byte)y });
			//server.send(new byte[] { (byte)1, (byte)2 }); //dummy values 
		} catch (IOException e)
		{
			Log.e("microbridge", "problem sending TCP message", e);
		}		
		
		invalidate();
		return true;
	}

}
