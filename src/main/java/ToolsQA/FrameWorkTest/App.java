package ToolsQA.FrameWorkTest;

import core.framework.DriverScript;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		try {
			@SuppressWarnings("unused")
			DriverScript driver = new DriverScript();
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
    }
}
