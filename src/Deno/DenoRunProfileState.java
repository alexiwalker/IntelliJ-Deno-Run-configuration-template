package Deno;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.NotNull;

public class DenoRunProfileState extends CommandLineState {

    String executionString;

    public DenoRunProfileState(ExecutionEnvironment env, String executionString) {
        super(env);
        this.executionString = executionString;
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {

        GeneralCommandLine commandLine = new GeneralCommandLine();

        commandLine.setExePath("deno");

        commandLine.addParameter("run");

        String[] params = this.executionString.split(" ");
        for (String param : params) {
            commandLine.addParameter(param);
        }

        commandLine.setWorkDirectory(this.getEnvironment().getProject().getBasePath());

        return new ColoredProcessHandler(
                commandLine
        );

    }
}
