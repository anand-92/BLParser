import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code Statement}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class StatementTest {

    /**
     * The name of a file containing a sequence of BL statements.
     */
    private static final String FILE_NAME_1 = "test/statement1.bl",
            FILE_NAME_2 = "test/statement2.bl" , FILE_NAME_3 = "test/statement1a.bl",
                    FILE_NAME_4 = "test/statement2a.bl" , FILE_NAME_5 = "test/statement1b.bl",
                            FILE_NAME_6 = "test/statement2b.bl";

    /**
     * Invokes the {@code Statement} constructor for the implementation under
     * test and returns the result.
     *
     * @return the new statement
     * @ensures constructorTest = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorTest();

    /**
     * Invokes the {@code Statement} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new statement
     * @ensures constructorRef = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorRef();

    /**
     * Test of parse on syntactically valid input.
     */
    @Test
    public final void testParseValidExample() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_1);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_1);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test of parse on syntactically invalid input.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorExample() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_2);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }

    // TODO - add more test cases for valid inputs for both parse and parseBlock
    
    /**
     * Test of parse on syntactically valid input.
     */
    @Test
    public final void testParseValidExample2() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_3);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_3);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }
    
    /**
     * Test of parse on syntactically valid input.
     */
    @Test
    public final void testParseValidExample3() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();
        SimpleReader file = new SimpleReader1L(FILE_NAME_5);
        Queue<String> tokens = Tokenizer.tokens(file);
        sRef.parse(tokens);
        file.close();
        Statement sTest = this.constructorTest();
        file = new SimpleReader1L(FILE_NAME_5);
        tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call
         */
        sTest.parse(tokens);
        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }
    // TODO - add more test cases for as many distinct syntax errors as possible
    //        for both parse and parseBlock

    /**
     * Test of parse on syntactically invalid input.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorExample2() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_4);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }
    
    /**
     * Test of parse on syntactically invalid input.
     */
    @Test(expected = RuntimeException.class)
    public final void testParseErrorExample3() {
        /*
         * Setup
         */
        Statement sTest = this.constructorTest();
        SimpleReader file = new SimpleReader1L(FILE_NAME_6);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        /*
         * The call--should result in an error being caught
         */
        sTest.parse(tokens);
    }
}
