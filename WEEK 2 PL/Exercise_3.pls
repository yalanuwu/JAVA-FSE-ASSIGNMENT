CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest
IS
    -- Define variables to store account details
    CURSOR c_savings_accounts IS
        SELECT account_id, balance
        FROM accounts
        WHERE account_type = 'SAVINGS'
        FOR UPDATE OF balance;
    
    v_account_id accounts.account_id%TYPE;
    v_balance    accounts.balance%TYPE;
    
BEGIN
    -- Open the cursor to iterate through savings accounts
    OPEN c_savings_accounts;
    
    LOOP
        FETCH c_savings_accounts INTO v_account_id, v_balance;
        EXIT WHEN c_savings_accounts%NOTFOUND;
        
        -- Calculate the interest (1% of the current balance)
        v_balance := v_balance + (v_balance * 0.01);
        
        -- Update the account balance with the new balance
        UPDATE accounts
        SET balance = v_balance
        WHERE account_id = v_account_id;
    END LOOP;
    
    -- Close the cursor
    CLOSE c_savings_accounts;
    
    -- Commit the transaction to save all updates
    COMMIT;
    
    DBMS_OUTPUT.PUT_LINE('Monthly interest processed successfully for all savings accounts.');
    
EXCEPTION
    WHEN OTHERS THEN
        -- Handle any unexpected errors
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('An unexpected error occurred: ' || SQLERRM);
        
        -- Optionally, log the error to an error log table
        INSERT INTO error_log (error_message, error_date)
        VALUES (SQLERRM, SYSDATE);
        
END ProcessMonthlyInterest;
