package com.cs421.rccar.test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.cs421.rccar.UI.SlaveActivity;
import com.cs421.rccar.Util.Command;

public class TestSlaveActivity extends AndroidTestCase 
{
	private SlaveActivity mSlaveActivity;
	
	public TestSlaveActivity()
	{
		mSlaveActivity = new SlaveActivity();
	}
	
	@SmallTest
	protected void testNotNull()
	{
		assert(mSlaveActivity != null);
	}
	
	@SmallTest
	protected void testSendMessage()
	{
		mSlaveActivity.sendMessage(Command.FORWARD);
		assert(mSlaveActivity.getState() == Command.FORWARD);
		
		mSlaveActivity.sendMessage(Command.BACKWARD);
		assert(mSlaveActivity.getState() == Command.BACKWARD);
		
		mSlaveActivity.sendMessage(Command.STOP);
		assert(mSlaveActivity.getState() == Command.STOP);
		
		mSlaveActivity.sendMessage(Command.LEFT);
		assert(mSlaveActivity.getState() == Command.LEFT);
		
		mSlaveActivity.sendMessage(Command.RIGHT);
		assert(mSlaveActivity.getState() == Command.RIGHT);
		
		mSlaveActivity.sendMessage(Command.FORWARDRIGHT);
		assert(mSlaveActivity.getState() == Command.FORWARDRIGHT);
		
		mSlaveActivity.sendMessage(Command.FORWARDLEFT);
		assert(mSlaveActivity.getState() == Command.FORWARDLEFT);
		
		mSlaveActivity.sendMessage(Command.BACKWARDLEFT);
		assert(mSlaveActivity.getState() == Command.BACKWARDLEFT);
		
		mSlaveActivity.sendMessage(Command.BACKWARDRIGHT);
		assert(mSlaveActivity.getState() == Command.BACKWARDRIGHT);
	}
	
	
}
