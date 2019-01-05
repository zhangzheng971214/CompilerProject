package main;

import org.antlr.v4.runtime.*;

public class SyntaxErrorListener extends BaseErrorListener {
    private ExceptionHandler _exceptionHandler;

    SyntaxErrorListener(ExceptionHandler exceptionHandler) {
        super();
        _exceptionHandler = exceptionHandler;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        _exceptionHandler.addException((Token) offendingSymbol,msg);
    }

}
