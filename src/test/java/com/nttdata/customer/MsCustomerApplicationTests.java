package com.nttdata.customer;

import com.nttdata.customer.controller.CustomerController;
import com.nttdata.customer.exception.types.NotFoundException;
import com.nttdata.customer.model.CustomerRequest;
import com.nttdata.customer.model.CustomerResponse;
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

	private static final String CUSTOMER_API = "/api/v1/customer";
	private static final String CUSTOMER_ID = "123456788";
	private static final String NON_EXISTENT_CUSTOMER_ID = "123456799";
	private static final String NOT_FOUND_MESSAGE = String.format("Customer with ID %s does not exist", NON_EXISTENT_CUSTOMER_ID);

	@BeforeEach
	public void setup() {
		customer1 = createCustomerResponse("123456788", "Juan Pepe", "Vasquez Perez", "",
				CustomerResponse.DocumentTypeEnum.DNI, "12345678", CustomerResponse.ClientTypeEnum.STAFF);
		customer2 = createCustomerResponse("123456789", "", "", "EMPRESA DE TRANSPORTE SA",
				CustomerResponse.DocumentTypeEnum.RUC, "203145698745", CustomerResponse.ClientTypeEnum.BUSINESS);
		customerResponseFlux = Flux.just(customer1, customer2);
	}

	@Test
	@DisplayName("method get all customer test")
	public void getCustomerTest() {
		when(service.getCustomer()).thenReturn(Mono.just(ResponseEntity.ok(customerResponseFlux)));

		webTestClient.get().uri(CUSTOMER_API)
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

		webTestClient.get().uri(CUSTOMER_API)
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(CustomerResponse.class);
	}

	@Test
	@DisplayName("method get customer by id test")
	public void getCustomerByIdTest() {
		when(service.getCustomerById(CUSTOMER_ID)).thenReturn(Mono.just(ResponseEntity.ok(customer1)));

		webTestClient.get().uri(CUSTOMER_API + "/{id}", CUSTOMER_ID)
				.exchange()
				.expectStatus().isOk()
				.expectBody(CustomerResponse.class)
				.isEqualTo(customer1);
	}

	@Test
	@DisplayName("method get customer by id not found test")
	public void getCustomerByIdNotFoundTest() {
		when(service.getCustomerById(NON_EXISTENT_CUSTOMER_ID)).thenReturn(Mono.error(new NotFoundException(NOT_FOUND_MESSAGE)));

		webTestClient.get().uri(CUSTOMER_API + "/{id}", NON_EXISTENT_CUSTOMER_ID)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$.status").isEqualTo(404)
				.jsonPath("$.message").isEqualTo(NOT_FOUND_MESSAGE);
	}

	@Test
	@DisplayName("method create customer test")
	public void createCustomerTest() {
		CustomerRequest customerRequest = createCustomerRequest("", "", "EMPRESA DE INMUEBLES SAC",
				CustomerRequest.DocumentTypeEnum.RUC, "20145879453", CustomerRequest.ClientTypeEnum.STAFF);
		CustomerResponse customerResponse = createCustomerResponse("123456788", "", "", "EMPRESA DE INMUEBLES SAC",
				CustomerResponse.DocumentTypeEnum.RUC, "20145879453", CustomerResponse.ClientTypeEnum.STAFF);

		when(service.createCustomer(any(CustomerRequest.class))).thenReturn(Mono.just(ResponseEntity.ok(customerResponse)));

		webTestClient.post().uri(CUSTOMER_API)
				.bodyValue(customerRequest)
				.exchange()
				.expectStatus().isOk()
				.expectBody(CustomerResponse.class)
				.isEqualTo(customerResponse);
	}

	private CustomerResponse createCustomerResponse(String id, String name, String lastName, String reason,
													CustomerResponse.DocumentTypeEnum documentType, String documentNumber,
													CustomerResponse.ClientTypeEnum clientType) {
		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setId(id);
		customerResponse.setName(name);
		customerResponse.setLastName(lastName);
		customerResponse.setReason(reason);
		customerResponse.setDocumentType(documentType);
		customerResponse.setDocumentNumber(documentNumber);
		customerResponse.setClientType(clientType);
		return customerResponse;
	}

	private CustomerRequest createCustomerRequest(String name, String lastName, String reason,
												  CustomerRequest.DocumentTypeEnum documentType, String documentNumber,
												  CustomerRequest.ClientTypeEnum clientType) {
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setName(name);
		customerRequest.setLastName(lastName);
		customerRequest.setReason(reason);
		customerRequest.setDocumentType(documentType);
		customerRequest.setDocumentNumber(documentNumber);
		customerRequest.setClientType(clientType);
		return customerRequest;
	}

}
