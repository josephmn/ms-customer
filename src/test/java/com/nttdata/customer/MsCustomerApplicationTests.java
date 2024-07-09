package com.nttdata.customer;

import com.banking.openapi.model.CustomerRequest;
import com.banking.openapi.model.CustomerResponse;
import com.nttdata.customer.controller.CustomerController;
import com.nttdata.customer.exception.types.NotFoundException;
import com.nttdata.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebFluxTest(CustomerController.class)
class MsCustomerApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private CustomerService service;

	private CustomerResponse customer1;
	private CustomerResponse customer2;
	private Flux<CustomerResponse> customerResponseFlux;

	@BeforeEach
	public void Setup() {

		customer1 = new CustomerResponse();
		customer1.setId("123456788");
		customer1.setName("Juan Pepe");
		customer1.setLastName("Vasquez Perez");
		customer1.setReason("");
		customer1.setDocumentType(CustomerResponse.DocumentTypeEnum.DNI);
		customer1.setDocumentNumber("12345678");
		customer1.setClientType(CustomerResponse.ClientTypeEnum.STAFF);

		customer2 = new CustomerResponse();
		customer2.setId("123456789");
		customer2.setName("");
		customer2.setLastName("");
		customer2.setReason("EMPRESA DE TRANSPORTE SA");
		customer2.setDocumentType(CustomerResponse.DocumentTypeEnum.RUC);
		customer2.setDocumentNumber("203145698745");
		customer2.setClientType(CustomerResponse.ClientTypeEnum.BUSINESS);

		customerResponseFlux = Flux.just(customer1, customer2);
	}

	@Test
	@DisplayName("method get all customer test")
	public void getCustomerTest() {
		when(service.getCustomer()).thenReturn(Mono.just(ResponseEntity.ok(customerResponseFlux)));

		webTestClient.get().uri("/api/v1/customer")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(CustomerResponse.class)
				.hasSize(2)
				.contains(customer1, customer2);
	}

	@Test
	@DisplayName("method get all customer empty test")
	public void getCustomerEmptyTest() {
		when(service.getCustomer()).thenReturn(Mono.just(ResponseEntity.ok(Flux.empty())));

		webTestClient.get().uri("/api/v1/customer")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(CustomerResponse.class);
	}

	@Test
	@DisplayName("method get customer by id test")
	public void getCustomerByIdTest() {
		CustomerResponse expectedCustomer = customer1;
		when(service.getCustomerById(expectedCustomer.getId())).thenReturn(Mono.just(ResponseEntity.ok(expectedCustomer)));

		webTestClient.get().uri("/api/v1/customer/{id}", expectedCustomer.getId())
				.exchange()
				.expectStatus().isOk()
				.expectBody(CustomerResponse.class)
				.isEqualTo(expectedCustomer);
	}

	@Test
	@DisplayName("method get customer by id not found test and response message")
	public void getCustomerByIdNotFoundTest() {
		String nonExistentCustomerId = "123456799";
		String expectedMessage = String.format("Customer with ID %s does not exist", nonExistentCustomerId);

		when(service.getCustomerById(nonExistentCustomerId)).thenReturn(Mono.error(new NotFoundException(expectedMessage)));

		webTestClient.get().uri("/api/v1/customer/{id}", nonExistentCustomerId)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$.message").isEqualTo(expectedMessage);
	}

	@Test
	@DisplayName("method get customer by id not found test and response status")
	public void getCustomerByIdNotFoundStatusTest() {
		String nonExistentCustomerId = "123456799";
		String expectedMessage = String.format("Customer with ID %s does not exist", nonExistentCustomerId);

		when(service.getCustomerById(nonExistentCustomerId)).thenReturn(Mono.error(new NotFoundException(expectedMessage)));

		webTestClient.get().uri("/api/v1/customer/{id}", nonExistentCustomerId)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$.status").isEqualTo(404);
	}

	@Test
	@DisplayName("method get customer by id not found test and response message")
	public void getCustomerByIdNotFoundStatusMessageTest() {
		String nonExistentCustomerId = "123456799";
		String expectedMessage = String.format("Customer with ID %s does not exist", nonExistentCustomerId);

		when(service.getCustomerById(nonExistentCustomerId)).thenReturn(Mono.error(new NotFoundException(expectedMessage)));

		webTestClient.get().uri("/api/v1/customer/{id}", nonExistentCustomerId)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$.status").isEqualTo(404)
				.jsonPath("$.message").isEqualTo(expectedMessage);
	}

	@Test
	@DisplayName("method create customer test")
	public void createCustomerTest() {
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setName("");
		customerRequest.setLastName("");
		customerRequest.setReason("EMPRESA DE INMUEBLES SAC");
		customerRequest.setDocumentType(CustomerRequest.DocumentTypeEnum.RUC);
		customerRequest.setDocumentNumber("20145879453");
		customerRequest.setClientType(CustomerRequest.ClientTypeEnum.STAFF);

		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setId("123456788");
		customerResponse.setName("");
		customerResponse.setLastName("");
		customerResponse.setReason("EMPRESA DE INMUEBLES SAC");
		customerResponse.setDocumentType(CustomerResponse.DocumentTypeEnum.RUC);
		customerResponse.setDocumentNumber("20145879453");
		customerResponse.setClientType(CustomerResponse.ClientTypeEnum.STAFF);

		when(service.createCustomer(customerRequest)).thenReturn(Mono.just(ResponseEntity.ok(customerResponse)));

		webTestClient.post().uri("/api/v1/customer")
				.bodyValue(customerRequest)
				.exchange()
				.expectStatus().isOk()
				.expectBody(CustomerResponse.class)
				.isEqualTo(customerResponse);
	}

}
