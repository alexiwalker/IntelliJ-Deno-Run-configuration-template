package RunAny;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;

public class RunAnyRunProfileState extends CommandLineState {

    String executionString;

    public RunAnyRunProfileState(ExecutionEnvironment env, String executionString) {
        super(env);
        this.executionString = executionString;
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        try {
            return new ColoredProcessHandler(
                    Runtime.getRuntime().exec(this.executionString),
                    "Running commandline configuration: "+ this.executionString, Charset.defaultCharset()
            );
        } catch (Exception e) {
            throw new ExecutionException("");
        }
    }
}
