package com.JazzDevStudio.LacunaExpress.Test;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;

/**
 * J unit testing
 */
public class FullTestSuite {

	public static Test suite(){
		return new TestSuiteBuilder(FullTestSuite.class).includeAllPackagesUnderHere().build();
	}

	public FullTestSuite() {
		super();
	}
}
