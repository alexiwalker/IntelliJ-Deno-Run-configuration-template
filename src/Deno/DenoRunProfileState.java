package Deno;

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
        for (int i =0;i<params.length;i++){
            commandLine.addParameter(params[i]);
        }

        commandLine.setWorkDirectory(this.getEnvironment().getProject().getBasePath());

        return new ColoredProcessHandler(
                commandLine
        );

        //        return new CommandLineState(executionEnvironment) {
//            @NotNull
//            @Override
//            protected ProcessHandler startProcess() throws ExecutionException {
//
//                try {
//                    return new ColoredProcessHandler(
//                            Runtime.getRuntime().exec(
//                                    String.format(
//                                            "Deno run %s", getCommand()
//                                    )
//                            ), "Starting Web Deno App", Charset.defaultCharset());
//                } catch (Exception e) {
//                    throw new ExecutionException("");
//                }
//            }

    }
}
