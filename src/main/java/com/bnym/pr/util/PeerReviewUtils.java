package com.bnym.pr.util;

import org.apache.commons.lang.RandomStringUtils;

public class PeerReviewUtils {


	public static String getRandomToken() {
		return RandomStringUtils.randomAlphabetic(50); 
	}

}
