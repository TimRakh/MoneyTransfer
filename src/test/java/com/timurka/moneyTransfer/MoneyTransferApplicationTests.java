package com.timurka.moneyTransfer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import com.timurka.moneyTransfer.model.response.TransferResponse;

import static com.timurka.moneyTransfer.data.TestData.TRANSFER_REQUEST_1;


@SpringBootTest(webEnvironment =
		SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferApplicationTests {

	private static final String HOST = "http://localhost:";
	private static final int PORT = 5500;
	private static final String TRANSFER_ENDPOINT = "/transfer";
	private static final String CONFIRM_ENDPOINT = "/confirmOperation";
	@Autowired
	private TestRestTemplate restTemplate;


	private static final GenericContainer<?> mtransfer = new GenericContainer<>
			("mtransfer:latest").withExposedPorts(PORT);

	@BeforeAll
	static void startUp() {
		mtransfer.start();
	}

	@Test
	void contextLoads() {
		ResponseEntity<TransferResponse> transferEntity = restTemplate.postForEntity(
				HOST + mtransfer.getMappedPort(PORT) + TRANSFER_ENDPOINT, TRANSFER_REQUEST_1, TransferResponse.class);
		Assertions.assertEquals(transferEntity.getBody().getOperationId(), "1");
	}


}
