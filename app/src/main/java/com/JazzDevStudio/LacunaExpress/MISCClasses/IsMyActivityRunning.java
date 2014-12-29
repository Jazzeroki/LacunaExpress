package com.JazzDevStudio.LacunaExpress.MISCClasses;

/**
 * Created by PatrickSSD2 on 12/15/2014.
 */
public class IsMyActivityRunning {

	public static boolean isActivityVisible() {
		return activityVisible;
	}

	public static void activityResumed() {
		activityVisible = true;
	}

	public static void activityPaused() {
		activityVisible = false;
	}

	private static boolean activityVisible;
}