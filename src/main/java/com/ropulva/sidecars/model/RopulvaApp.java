package com.ropulva.sidecars.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ropulva_app")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RopulvaApp extends BaseEntity {
	@Id
	private String id;

	@Column(name = "name")
	@NotNull
	private String name;

}
