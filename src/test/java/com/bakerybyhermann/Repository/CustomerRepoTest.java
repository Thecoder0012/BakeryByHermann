package com.bakerybyhermann.Repository;

import com.bakerybyhermann.Model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepoTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRepoTest.class);

    @Autowired
    CustomerRepo underTest;


    @BeforeEach
    void setUp() {
        logger.info("Initiating test");
    }

    @AfterEach
    void tearDown() {
        logger.info("Test is successful");
    }

    @Test
    void fetchAll() {
        List<Customer> customerList = underTest.fetchAll();
        boolean actual = customerList.size() > 0;
        assertEquals(true,actual);
    }

    @Test
    void findById() {
        int id = 33;
        Customer customer = underTest.findById(id);
        int actual = customer.getCustomerId();
        assertEquals(id,actual);
    }

}