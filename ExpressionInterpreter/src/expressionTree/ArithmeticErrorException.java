package expressionTree;

public class ArithmeticErrorException extends Exception
{
    /**
     * default constructor for ScanErrorObjects
     */
    public ArithmeticErrorException()
    {
        super();
    }
    /**
     * Constructor for ScanErrorObjects that includes a reason for the error
     * @param reason
     */
    public ArithmeticErrorException(String reason)
    {
        super(reason);
    }
}

