CREATE OR REPLACE FUNCTION CalculateAge (
    p_date_of_birth DATE
) RETURN NUMBER
IS
    v_age NUMBER;
BEGIN
    -- Calculate the age in years
    v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, p_date_of_birth) / 12);
    
    RETURN v_age;
    
EXCEPTION
    WHEN OTHERS THEN
        -- Handle any unexpected errors
        DBMS_OUTPUT.PUT_LINE('An error occurred while calculating age: ' || SQLERRM);
        RETURN NULL;
END CalculateAge;
