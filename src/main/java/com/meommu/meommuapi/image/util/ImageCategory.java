package com.meommu.meommuapi.image.util;

public enum ImageCategory {
	PARENT_PROFILE("profile-images/parents", 1),
	KINDERGARTEN_PROFILE("profile-images/kindergartens", 1),
	DOG_PROFILE("profile-images/dogs", 1),
	DIARY_IMAGE("diary-images", 5),
	NOTICE_IMAGE("notice-images", 1),
	;

	private final String directory;
	private final int maxFiles;

	ImageCategory(String directory, int maxFiles) {
		this.directory = directory;
		this.maxFiles = maxFiles;
	}

	public String getDirectory() {
		return directory;
	}

	public int getMaxFiles() {
		return maxFiles;
	}
}
