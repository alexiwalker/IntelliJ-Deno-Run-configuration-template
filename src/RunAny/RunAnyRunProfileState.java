package RunAny;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunAnyRunProfileState extends CommandLineState {

    String executionString;

    public RunAnyRunProfileState(ExecutionEnvironment env, String executionString) {
        super(env);
        this.executionString = executionString;
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {

        GeneralCommandLine commandLine = new GeneralCommandLine();
        String[] params = this.executionString.split(" ");
        List<String> pars = Arrays.asList(params);
        String exename = pars.get(0);

        commandLine.setExePath(exename);

        for (int i =1;i<pars.size();i++){
            commandLine.addParameter(pars.get(i));
        }

        commandLine.setWorkDirectory(this.getEnvironment().getProject().getBasePath());

        return new ColoredProcessHandler(
                commandLine
        );

    }
}
