package com.cos.hello.model;

import lombok.Data;

@Data
public class Board {
	private int id;
	private String title;
	private String content;
	private int readCount;
	private int userId;
}
