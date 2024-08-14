CREATE OR REPLACE PROCEDURE SafeTransferFunds (
    p_source_account_id   IN NUMBER,
    p_target_account_id   IN NUMBER,
    p_amount              IN NUMBER
)
IS
    v_source_balance  NUMBER;
    v_target_balance  NUMBER;
    e_insufficient_funds EXCEPTION;
    e_invalid_account EXCEPTION;
BEGIN
    -- Check if source account exists and get balance
    SELECT balance
    INTO v_source_balance
    FROM accounts
    WHERE account_id = p_source_account_id
    FOR UPDATE OF balance;
    
    -- Check if target account exists and get balance
    SELECT balance
    INTO v_target_balance
    FROM accounts
    WHERE account_id = p_target_account_id
    FOR UPDATE OF balance;
    
    -- Check if there are sufficient funds in the source account
    IF v_source_balance < p_amount THEN
        RAISE e_insufficient_funds;
    END IF;
    
    -- Subtract the amount from the source account
    UPDATE accounts
    SET balance = balance - p_amount
    WHERE account_id = p_source_account_id;
    
    -- Add the amount to the target account
    UPDATE accounts
    SET balance = balance + p_amount
    WHERE account_id = p_target_account_id;
    
    -- Commit the transaction
    COMMIT;
    
    DBMS_OUTPUT.PUT_LINE('Funds transferred successfully.');
    
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Handle case where one of the accounts does not exist
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: One or both account IDs are invalid.');
        
    WHEN e_insufficient_funds THEN
        -- Handle case where there are insufficient funds
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds in source account.');
        
    WHEN OTHERS THEN
        -- Handle any other errors
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('An unexpected error occurred: ' || SQLERRM);
        
        -- Optionally, log the error to an error log table
        INSERT INTO error_log (error_message, error_date)
        VALUES (SQLERRM, SYSDATE);
        
END SafeTransferFunds;
