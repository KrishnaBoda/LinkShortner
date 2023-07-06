package com.pro.wrapper;

import java.io.Serializable;
import com.pro.entity.Response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GeneratedShortURLWrapper extends Response implements Serializable {


	private static final long serialVersionUID = 1L;
	private String generatedShortURL;

}
