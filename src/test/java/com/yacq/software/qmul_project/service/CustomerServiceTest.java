package com.yacq.software.qmul_project.service;

import com.yacq.software.qmul_project.model.Customer;
import com.yacq.software.qmul_project.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer sampleCustomer;

    @BeforeEach
    void setUp() {
        sampleCustomer = new Customer();
        sampleCustomer.setCustomerId(UUID.randomUUID().toString());
        sampleCustomer.setName("John Doe");
        sampleCustomer.setEmail("johndoe@example.com");
        sampleCustomer.setPhone("1234567890");
        sampleCustomer.setPreferences("Vegetarian");
    }

    @Test
    void testSaveCustomer_NewCustomer_GeneratesIdAndSaves() {
        Customer newCustomer = new Customer();
        newCustomer.setName("Jane Doe");
        newCustomer.setEmail("janedoe@example.com");
        newCustomer.setPhone("0987654321");


        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer savedCustomer = customerService.saveCustomer(newCustomer);

        assertNotNull(savedCustomer.getCustomerId());
        assertEquals("Jane Doe", savedCustomer.getName());
        verify(customerRepository, times(1)).save(newCustomer);
    }

    @Test
    void testSaveCustomer_ExistingEmail_UpdatesInsteadOfDuplicate() {
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(UUID.randomUUID().toString());
        existingCustomer.setName("Existing User");
        existingCustomer.setEmail(sampleCustomer.getEmail());
        existingCustomer.setPhone("0000000000");

        when(customerRepository.existsByEmail(sampleCustomer.getEmail())).thenReturn(true);
        when(customerRepository.findByEmail(sampleCustomer.getEmail())).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer savedCustomer = customerService.saveCustomer(sampleCustomer);

        assertEquals(existingCustomer.getCustomerId(), savedCustomer.getCustomerId());
        verify(customerRepository, times(1)).findByEmail(sampleCustomer.getEmail());
        verify(customerRepository, times(1)).save(sampleCustomer);
    }

    @Test
    void testGetCustomerById_CustomerExists() {
        when(customerRepository.findByCustomerId(sampleCustomer.getCustomerId())).thenReturn(Optional.of(sampleCustomer));

        Optional<Customer> result = customerService.getCustomerById(sampleCustomer.getCustomerId());

        assertTrue(result.isPresent());
        assertEquals(sampleCustomer, result.get());
        verify(customerRepository, times(1)).findByCustomerId(sampleCustomer.getCustomerId());
    }

    @Test
    void testGetCustomerById_CustomerDoesNotExist() {
        String customerId = UUID.randomUUID().toString();
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        Optional<Customer> result = customerService.getCustomerById(customerId);

        assertFalse(result.isPresent());
        verify(customerRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void testGetCustomerByEmail_CustomerExists() {
        when(customerRepository.findByEmail(sampleCustomer.getEmail())).thenReturn(Optional.of(sampleCustomer));

        Optional<Customer> result = customerService.getCustomerByEmail(sampleCustomer.getEmail());

        assertTrue(result.isPresent());
        assertEquals(sampleCustomer, result.get());
        verify(customerRepository, times(1)).findByEmail(sampleCustomer.getEmail());
    }

    @Test
    void testGetCustomerByPhone_CustomerExists() {
        when(customerRepository.findByPhone(sampleCustomer.getPhone())).thenReturn(Optional.of(sampleCustomer));

        Optional<Customer> result = customerService.getCustomerByPhone(sampleCustomer.getPhone());

        assertTrue(result.isPresent());
        assertEquals(sampleCustomer, result.get());
        verify(customerRepository, times(1)).findByPhone(sampleCustomer.getPhone());
    }

    @Test
    void testCustomerExists_CustomerExists() {
        when(customerRepository.existsByCustomerId(sampleCustomer.getCustomerId())).thenReturn(true);

        boolean exists = customerService.customerExists(sampleCustomer.getCustomerId());

        assertTrue(exists);
        verify(customerRepository, times(1)).existsByCustomerId(sampleCustomer.getCustomerId());
    }

    @Test
    void testCustomerExists_CustomerDoesNotExist() {
        when(customerRepository.existsByCustomerId(sampleCustomer.getCustomerId())).thenReturn(false);

        boolean exists = customerService.customerExists(sampleCustomer.getCustomerId());

        assertFalse(exists);
        verify(customerRepository, times(1)).existsByCustomerId(sampleCustomer.getCustomerId());
    }
}