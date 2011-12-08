package com.cs421.rccar.UI;
import android.content.Context;
import android.hardware.Camera;


public class CameraView {
	Camera camera; 
	public CameraView()	{
		camera = Camera.open(); 
	}
	
	public void takePicture(Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback jpeg) {
		camera.takePicture(shutter, raw, jpeg); 
	}
	
	
}
