package com.spike.locationSpike;

import com.spike.locationSpike.utils.HaversineValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.spike")
@RequiredArgsConstructor
public class LocationSpikeApplication {

	private final HaversineValidator validator;

	public static void main(String[] args) {
		SpringApplication.run(LocationSpikeApplication.class, args);
	}

	@Bean
	CommandLineRunner runTest() {
		return args -> {
			test();
		};
	}

	public void test() {
		double userLatitude = -24.252382903989812;
		double userLongitude = -41.591601372308155;
		double radiusKm = 2.0;

		long startTime = System.nanoTime();

		boolean isWithinRadius = validator.isAnyRiskAreaWithinRadius(userLatitude, userLongitude, radiusKm);

		long endTime = System.nanoTime();

		long durationInMilliseconds = (endTime - startTime) / 1_000_000;

		System.out.println("Tempo de execução: " + durationInMilliseconds + " ms");

		String resultMessage = isWithinRadius
				? "Há pelo menos um presídio dentro do raio de 2 km."
				: "Não há presídios dentro do raio de 2 km.";

		System.out.println(resultMessage);
	}
}
