package com.meommu.meommuapi.core.image.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.meommu.meommuapi.core.image.exception.S3DeleteException;
import com.meommu.meommuapi.core.image.exception.S3UploadException;
import com.meommu.meommuapi.core.image.repository.ImageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileManager {
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	private final AmazonS3 amazonS3;

	private final TransferManager transferManager;

	private final ImageRepository imageRepository;

	public FileManager(AmazonS3 amazonS3, TransferManager transferManager, ImageRepository imageRepository) {
		this.amazonS3 = amazonS3;
		this.transferManager = transferManager;
		this.imageRepository = imageRepository;
	}

	/**
	 * @param image     업로드할 이미지
	 * @param directory 이미지가 저장될 디렉토리주소
	 * @return 이미지가 저장된 URL
	 */
	public String upload(MultipartFile image, String directory) {
		String imageUrl;

		try {
			imageUrl = uploadImageToImageServer(image, directory);
		} catch (AmazonClientException | InterruptedException | IOException e) {
			throw new S3UploadException();
		}

		return imageUrl;
	}

	/**
	 * @param images    업로드할 이미지들
	 * @param directory 이미지가 저장될 디렉토리 주소
	 * @return 이미지가 저장된 URL들
	 */
	public List<String> upload(List<MultipartFile> images, String directory) {
		List<String> imageUrls = new ArrayList<>();
		try {
			for (MultipartFile image : images) {
				imageUrls.add(uploadImageToImageServer(image, directory));
			}
		} catch (AmazonClientException | InterruptedException | IOException e) {
			for (String imageUrl : imageUrls) {
				delete(imageUrl);
			}

			throw new S3UploadException();
		}

		return imageUrls;
	}

	public void delete(String objectUrl) {
		try {
			URL url = new URL(objectUrl);
			String key = url.getPath().substring(1);
			amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
		} catch (Exception e) {
			throw new S3DeleteException();
		}
	}

	private String uploadImageToImageServer(MultipartFile image, String directory) throws
		AmazonClientException,
		InterruptedException,
		IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("image/png");
		metadata.setContentLength(image.getSize());

		String uuid = UUID.randomUUID().toString();
		String path = directory + "/" + uuid;

		Upload upload = transferManager.upload(bucketName, path, image.getInputStream(), metadata);
		upload.waitForCompletion();

		amazonS3.setObjectAcl(bucketName, path, CannedAccessControlList.PublicRead);

		return getKeyUrl(path);
	}

	private String getKeyUrl(String key) {
		return amazonS3.getUrl(bucketName, key).toString();
	}
}
