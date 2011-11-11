package com.cs421.rccar.test;

import android.os.Handler;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.cs421.rccar.Master.Master;

public class MasterTest extends AndroidTestCase
{

	private Master mMaster;
	
	public MasterTest()
	{
		mMaster = new Master(new Handler());
	}
	
	@SmallTest
	protected void testNotNull() throws Exception
	{
		assert(mMaster != null);
	}

}
