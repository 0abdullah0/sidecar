package com.ropulva.sidecars.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RopulvaAppDto {

	private String id;

	@NotNull
	private String name;

	private String senderId;

}
