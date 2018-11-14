import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.set.Set;
import components.set.Set2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Put your name here
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to statement string of body of
     *          instruction at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        // dequeue instruction (assert already checks)
        tokens.dequeue();
        String name = tokens.dequeue();

        //check/dequeue is
        String temp = tokens.dequeue();
        boolean is = temp.equals("IS");
        Reporter.assertElseFatalError(is,
                "Error: Invalid instruction. Expected: " + "\"IS\" token");

        body.parseBlock(tokens);

        //check/dequeue end
        temp = tokens.dequeue();
        boolean end = temp.equals("END");
        Reporter.assertElseFatalError(end,
                "Error: Invalid instruction. Expected: " + "\"END\" token");

        //check/dequeue end name
        temp = tokens.dequeue();
        boolean sameName = temp.equals(name);
        Reporter.assertElseFatalError(sameName,
                "Error: Expected name to match");

        return name;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";
        Set<String> names = new Set2<String>();
        Map<String, Statement> context = this.newContext();
        Statement body = this.newBody();

        //check/dequeue program
        String temp = tokens.dequeue();
        boolean isProgram = temp.equals("PROGRAM");
        Reporter.assertElseFatalError(isProgram,
                "Error: Invalid instruction. Expected: " + "\"PROGRAM\" token");

        //check/dequeue name
        String name = tokens.dequeue();
        boolean isName = Tokenizer.isIdentifier(name);
        Reporter.assertElseFatalError(isName, "Error: Expected identifier");

        //check/dequeue is
        temp = tokens.dequeue();
        boolean is = temp.equals("IS");
        Reporter.assertElseFatalError(is,
                "Error: Invalid instruction. Expected: " + "\"IS\" token");

        boolean isInstruction = tokens.front().equals("INSTRUCTION");

        //parsing instructions
        while (isInstruction) {
            Statement bodyBlock = body.newInstance();
            String instructionName = parseInstruction(tokens, bodyBlock);
            boolean validInstruction = (!(instructionName.equals("move")
                    || instructionName.equals("turnright")
                    || instructionName.equals("turnleft")
                    || instructionName.equals("skip")
                    || instructionName.equals("infect")));

            Reporter.assertElseFatalError(validInstruction,
                    "Error: Invalid Instruction");

            if (names.contains(instructionName)) {
                Reporter.assertElseFatalError(false, "Error: ");
            } else {
                names.add(instructionName);
            }
            context.add(instructionName, bodyBlock);
            isInstruction = tokens.front().equals("INSTRUCTION");

        }

        //parsing body

        tokens.dequeue();
        body.parseBlock(tokens);

        //check/dequeue end
        String end = tokens.dequeue();
        boolean isEnd = end.equals("END");
        Reporter.assertElseFatalError(isEnd, "Error: Expected END");

        String endName = tokens.dequeue();
        boolean sameName = name.equals(endName);
        Reporter.assertElseFatalError(sameName, "Error: Names do not match");

        //reassemble
        this.replaceBody(body);
        this.replaceContext(context);
        this.replaceName(name);

        //check that tokens is at end
        boolean isOver = tokens.front().equals(Tokenizer.END_OF_INPUT);
        Reporter.assertElseFatalError(isOver, "Error: Invalid ending syntax");
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
