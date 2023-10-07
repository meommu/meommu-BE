package com.meommu.meommuapi.image.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

@Configuration
public class S3Config {
	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;
	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey;
	@Value("${cloud.aws.endpoint}")
	private String endPoint;

	@Bean
	public AmazonS3 amazonS3() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		return AmazonS3ClientBuilder.standard()
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, null))
			.withCredentials(new AWSCredentialsProvider() {
				@Override
				public AWSCredentials getCredentials() {
					return awsCredentials;
				}

				@Override
				public void refresh() {

				}
			})
			.build();
	}

	@Bean
	public TransferManager transferManager() {
		TransferManager transferManager = TransferManagerBuilder.standard()
			.withS3Client(amazonS3())
			.build();
		return transferManager;
	}
}
