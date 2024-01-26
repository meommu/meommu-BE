package com.meommu.meommuapi.core.kindergarten.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.meommu.meommuapi.core.kindergarten.domain.embedded.Email;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Name;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.OwnerName;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Phone;
import com.meommu.meommuapi.global.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Entity
@Table(indexes = {@Index(columnList = "email")})
@SQLDelete(sql = "UPDATE kindergarten SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Kindergarten extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Name name;

	@NotNull
	private OwnerName ownerName;

	@NotNull
	private Phone phone;

	@NotNull
	private Email email;

	@NotNull
	private Password password;

	@NotNull
	private boolean deleted = Boolean.FALSE;

	protected Kindergarten() {
	}

	@Builder
	private Kindergarten(String name, String ownerName, String phone, String email, Password password) {
		this.name = Name.from(name);
		this.ownerName = OwnerName.from(ownerName);
		this.phone = Phone.from(phone);
		this.email = Email.from(email);
		this.password = password;
	}

	public static Kindergarten of(String name, String ownerName, String phone, String email, Password password) {
		return Kindergarten.builder()
			.name(name)
			.ownerName(ownerName)
			.phone(phone)
			.email(email)
			.password(password)
			.build();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name.getValue();
	}

	public String getOwnerName() {
		return ownerName.getValue();
	}

	public String getPhone() {
		return phone.getValue();
	}

	public String getEmail() {
		return email.getValue();
	}

	public String getPassword() {
		return password.getValue();
	}

	public void updateName(String value) {
		this.name = Name.from(value);
	}

	public void updateOwnerName(String value) {
		this.ownerName = OwnerName.from(value);
	}

	public void updatePhone(String value) {
		this.phone = Phone.from(value);
	}

	public void updatePassword(Encryptor encryptor, String value) {
		this.password = Password.of(encryptor, value);
	}
}
