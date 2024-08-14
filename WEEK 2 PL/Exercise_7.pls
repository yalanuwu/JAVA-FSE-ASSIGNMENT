CREATE OR REPLACE PACKAGE CustomerManagement AS
    
    PROCEDURE AddCustomer (
        p_customer_id     IN NUMBER,
        p_name            IN VARCHAR2,
        p_date_of_birth   IN DATE,
        p_address         IN VARCHAR2,
        p_phone_number    IN VARCHAR2
    );
    
    
    PROCEDURE UpdateCustomerDetails (
        p_customer_id     IN NUMBER,
        p_name            IN VARCHAR2,
        p_date_of_birth   IN DATE,
        p_address         IN VARCHAR2,
        p_phone_number    IN VARCHAR2
    );
    
    
    FUNCTION GetCustomerBalance (
        p_customer_id     IN NUMBER
    ) RETURN NUMBER;
    
END CustomerManagement;

CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

    -- Procedure to add a new customer
    PROCEDURE AddCustomer (
        p_customer_id     IN NUMBER,
        p_name            IN VARCHAR2,
        p_date_of_birth   IN DATE,
        p_address         IN VARCHAR2,
        p_phone_number    IN VARCHAR2
    ) IS
    BEGIN
        -- Insert a new record into the Customers table
        INSERT INTO customers (
            customer_id, name, date_of_birth, address, phone_number, balance, lastmodified
        ) VALUES (
            p_customer_id, p_name, p_date_of_birth, p_address, p_phone_number, 0, SYSDATE
        );
        
        -- Commit the transaction
        COMMIT;
        
        DBMS_OUTPUT.PUT_LINE('Customer added successfully.');
    EXCEPTION
        WHEN OTHERS THEN
            -- Handle unexpected errors
            DBMS_OUTPUT.PUT_LINE('An error occurred while adding the customer: ' || SQLERRM);
            ROLLBACK;
    END AddCustomer;
    
    -- Procedure to update customer details
    PROCEDURE UpdateCustomerDetails (
        p_customer_id     IN NUMBER,
        p_name            IN VARCHAR2,
        p_date_of_birth   IN DATE,
        p_address         IN VARCHAR2,
        p_phone_number    IN VARCHAR2
    ) IS
    BEGIN
        -- Update the customer details in the Customers table
        UPDATE customers
        SET name = p_name,
            date_of_birth = p_date_of_birth,
            address = p_address,
            phone_number = p_phone_number,
            lastmodified = SYSDATE
        WHERE customer_id = p_customer_id;
        
        -- Commit the transaction
        COMMIT;
        
        DBMS_OUTPUT.PUT_LINE('Customer details updated successfully.');
    EXCEPTION
        WHEN OTHERS THEN
            -- Handle unexpected errors
            DBMS_OUTPUT.PUT_LINE('An error occurred while updating the customer details: ' || SQLERRM);
            ROLLBACK;
    END UpdateCustomerDetails;
    
    -- Function to get customer balance
    FUNCTION GetCustomerBalance (
        p_customer_id     IN NUMBER
    ) RETURN NUMBER IS
        v_balance NUMBER;
    BEGIN
        -- Retrieve the balance for the specified customer
        SELECT balance
        INTO v_balance
        FROM customers
        WHERE customer_id = p_customer_id;
        
        RETURN v_balance;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- Handle case where customer is not found
            DBMS_OUTPUT.PUT_LINE('Customer not found.');
            RETURN NULL;
        WHEN OTHERS THEN
            -- Handle unexpected errors
            DBMS_OUTPUT.PUT_LINE('An error occurred while retrieving the customer balance: ' || SQLERRM);
            RETURN NULL;
    END GetCustomerBalance;
    
END CustomerManagement;

