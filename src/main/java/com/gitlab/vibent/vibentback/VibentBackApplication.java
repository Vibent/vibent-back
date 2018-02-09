/*******************************************************
 * Copyright (C) 2018 Vibent
 *
 * This file is part of Vibent application
 *
 * Can not be copied and/or distributed without the express
 * permission of both fondators
 *******************************************************/

package com.gitlab.vibent.vibentback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class VibentBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(VibentBackApplication.class, args);
	}
}
