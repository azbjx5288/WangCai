/*******************************************************
 * Copyright 2010 - 2012 OPPO Mobile Comm Corp., Ltd.
 * All rights reserved.
 *
 * Description	:
 * History  	:
 * (ID, Date, Author, Description)
 *
 *******************************************************/
package com.wangcai.lottery.base.thread;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * @author LiuJunChao
 *
 * 2013-4-9
 */
public class CoresUtils {
	
	public static int getNumCores()
	{
		// Private Class to display only CPU devices in the directory listing
		class CpuFilter implements FileFilter
		{
			@Override
			public boolean accept(File pathname)
			{
				// Check if filename is "cpu", followed by a single digit number
				return Pattern.matches("cpu[0-9]", pathname.getName());
			}
		}
		
		try
		{
			// Get directory containing CPU info
			File dir = new File("/sys/devices/system/cpu/");
			// Filter to only list the devices we care about
			File[] files = dir.listFiles(new CpuFilter());
			// Return the number of cores (virtual CPU devices)
			return files.length;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// Default to return 1 core
			return 2;
		}
	}
}
