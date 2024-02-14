package com.meommu.meommuapi.global.cache;

public enum CacheType {

	NOTICE("notice", 60 * 60, 10000),

	GPT_GUIDE("gpt_guide", 60 * 60, 10000),

	GPT_GUIDE_DETAIL("gpt_guide_detail", 60 * 60, 10000);

	private final String cacheName;
	private final int expiredAfterWrite;
	private final int maximumSize;

	CacheType(String cacheName, int expiredAfterWrite, int maximumSize) {
		this.cacheName = cacheName;
		this.expiredAfterWrite = expiredAfterWrite;
		this.maximumSize = maximumSize;
	}

	public String getCacheName() {
		return cacheName;
	}

	public int getExpiredAfterWrite() {
		return expiredAfterWrite;
	}

	public int getMaximumSize() {
		return maximumSize;
	}
}