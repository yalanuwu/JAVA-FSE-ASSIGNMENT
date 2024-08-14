DECLARE
    -- Define cursor to select transactions for the current month
    CURSOR c_transactions IS
        SELECT customer_id, transaction_date, transaction_amount, transaction_type
        FROM transactions
        WHERE EXTRACT(MONTH FROM transaction_date) = EXTRACT(MONTH FROM SYSDATE)
          AND EXTRACT(YEAR FROM transaction_date) = EXTRACT(YEAR FROM SYSDATE)
        ORDER BY customer_id, transaction_date;

    v_customer_id         transactions.customer_id%TYPE;
    v_transaction_date    transactions.transaction_date%TYPE;
    v_transaction_amount  transactions.transaction_amount%TYPE;
    v_transaction_type    transactions.transaction_type%TYPE;

    v_current_customer_id transactions.customer_id%TYPE := NULL;

BEGIN
    -- Open the cursor
    OPEN c_transactions;

    LOOP
        FETCH c_transactions INTO v_customer_id, v_transaction_date, v_transaction_amount, v_transaction_type;
        EXIT WHEN c_transactions%NOTFOUND;

        -- Check if we're processing a new customer
        IF v_current_customer_id IS NULL OR v_current_customer_id != v_customer_id THEN
            IF v_current_customer_id IS NOT NULL THEN
                DBMS_OUTPUT.PUT_LINE('----------------------');
            END IF;
            DBMS_OUTPUT.PUT_LINE('Statement for Customer ID: ' || v_customer_id);
            DBMS_OUTPUT.PUT_LINE('----------------------');
            v_current_customer_id := v_customer_id;
        END IF;

        -- Print the transaction details
        DBMS_OUTPUT.PUT_LINE('Date: ' || TO_CHAR(v_transaction_date, 'DD-MON-YYYY'));
        DBMS_OUTPUT.PUT_LINE('Type: ' || v_transaction_type);
        DBMS_OUTPUT.PUT_LINE('Amount: ' || TO_CHAR(v_transaction_amount, 'FM999G999G999D00'));

    END LOOP;

    -- Close the cursor
    CLOSE c_transactions;

    DBMS_OUTPUT.PUT_LINE('Monthly statements generated successfully.');

EXCEPTION
    WHEN OTHERS THEN
        -- Handle unexpected errors
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);

END;
