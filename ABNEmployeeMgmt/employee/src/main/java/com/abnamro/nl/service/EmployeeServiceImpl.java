package com.abnamro.nl.service;

import com.abnamro.nl.dtos.CreateResponseDto;
import com.abnamro.nl.dtos.EmployeeRequestDto;
import com.abnamro.nl.dtos.EmployeeResponseDto;
import com.abnamro.nl.exceptions.TechnicalServerException;
import com.abnamro.nl.mappers.EmployeeMappers;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.retry.event.RetryOnRetryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;


/**
 * Implementation of the interface defined
 */
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Value("${operations.api.name}")
    private String apiName;
    @Value("${api.user.name}")
    private String apiUser;
    @Value("${api.user.password}")
    private String apiPass;
    private final RestClient restClient;
    private static final String OPERATION = "operation";
    private final io.github.resilience4j.retry.Retry retry;
    private final RestartEndpoint restart;
    private ResponseEntity<EmployeeResponseDto> response;
    private ResponseEntity<Map> deleteResponse;
    private final RestartEndpoint restartEndpoint;
    private final EmployeeMappers mappers;

    public EmployeeServiceImpl( RestClient restClient, io.github.resilience4j.retry.Retry retry, RestartEndpoint restart, RestartEndpoint restartEndpoint, EmployeeMappers mappers) {
        this.restClient = restClient;
        this.retry = retry;
        this.restart = restart;
        this.restartEndpoint = restartEndpoint;
        this.mappers = mappers;
    }

    /**
     * APi call to create an employee with retry options
     * @param employeeDto
     * @param role
     * @return
     */

    @Override
    @Retry(name = OPERATION )
    public ResponseEntity<EmployeeResponseDto> createEmployee(EmployeeRequestDto employeeDto, String role) {
        String encodedCred= Base64.getEncoder().
                encodeToString((apiUser+":"+apiPass).getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", encodedCred);
        headers.setContentType(MediaType.APPLICATION_JSON);
        retry.getEventPublisher().onRetry(this::handleRetry);
        retry.executeRunnable(()->{
            log.info("Initiating the api call to create employee {}",employeeDto.firstName());
            ResponseEntity<CreateResponseDto> createResponseDtoResponseEntity= restClient.post()
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(encodedCred))
                    .body(mappers.mapNameRole(employeeDto,role))
                    .retrieve()
                    .onStatus(status -> status.value() == 500, (request, response) -> {
                        throw new TechnicalServerException("Technical error occured while processing request");
                    })
                    .toEntity(CreateResponseDto.class);
            response= new ResponseEntity<>(mappers.mapToResponse(createResponseDtoResponseEntity.getBody()), HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
        });
        return response;

    }
    /**
     * APi call to get the details of the employee
     * @param Id
     * @return
     */
    @Override
    @Retry(name = OPERATION )

    public ResponseEntity<EmployeeResponseDto> fetchEmployee(Long Id) {
        String encodedCred= Base64.getEncoder().
                encodeToString((apiUser+":"+apiPass).getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", encodedCred);
        headers.setContentType(MediaType.APPLICATION_JSON);
        retry.getEventPublisher().onRetry(this::handleRetry);
        retry.executeRunnable(()->{
            log.info("Initiating the api call to fetch employee {}",Id);
            ResponseEntity<CreateResponseDto> createResponseDtoResponseEntity= restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + Id).build())
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(encodedCred))
                    .retrieve()
                    .onStatus(status -> status.value() == 500, (request, response) -> {
                        throw new TechnicalServerException("Technical error occured while processing request");
                    })
                    .toEntity(CreateResponseDto.class);
            response= ResponseEntity.ok( mappers.mapToResponse(createResponseDtoResponseEntity.getBody()));

        });
        return  response;
    }
    /**
     * API call to update the details of the employee
     * @param Id
     * @param employeeDto
     * @param role
     * @return
     */

    @Override
    @Retry(name = OPERATION )
    public ResponseEntity<EmployeeResponseDto> updateEmployee(Long Id, EmployeeRequestDto employeeDto,String role) {
        String encodedCred= Base64.getEncoder().
                encodeToString((apiUser+":"+apiPass).getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", encodedCred);
        headers.setContentType(MediaType.APPLICATION_JSON);
        retry.getEventPublisher().onRetry(this::handleRetry);
        retry.executeRunnable(()-> {
            log.info("Initiating the api call to fetch employee {}", Id);
            ResponseEntity<CreateResponseDto> createResponseDtoResponseEntity = restClient.put()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + Id).build())
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(encodedCred))
                    .body(mappers.mapNameRole(employeeDto, role))
                    .retrieve()
                    .onStatus(status -> status.value() == 500, (request, response) -> {
                        throw new TechnicalServerException("Technical error occured while processing request");
                    })
                    .toEntity(CreateResponseDto.class);
            response=ResponseEntity.ok( mappers.mapToResponse(createResponseDtoResponseEntity.getBody()));
        });
        return response;
    }


    /**
     * API call to delete the employee
     * @param Id
     * @return
     */

    @Override
    public ResponseEntity<Map> deleteEmployee(Long Id) {
        String encodedCred= Base64.getEncoder().
                encodeToString((apiUser+":"+apiPass).getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", encodedCred);
        headers.setContentType(MediaType.APPLICATION_JSON);
        retry.getEventPublisher().onRetry(this::handleRetry);
        retry.executeRunnable(()-> {
            deleteResponse = restClient.delete()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + Id).build())
                    .headers(httpHeaders -> httpHeaders.setBasicAuth(encodedCred))
                    .retrieve()
                    .onStatus(status -> status.value() == 500, (request, response) -> {
                        throw new TechnicalServerException("Technical error occured while processing request");
                    })
                    .toEntity(Map.class);
        });
        return deleteResponse;
    }

    /**
     * Restart Application after 2 attempts and then try 3rd time
     * @param retryOnRetryEvent
     */
    private synchronized void handleRetry(RetryOnRetryEvent retryOnRetryEvent) {
        if (retryOnRetryEvent.getNumberOfRetryAttempts() == 2 ) {
            log.info("Number of attempts reached an hence restarting");
        }
        restart.restart();

    }
}