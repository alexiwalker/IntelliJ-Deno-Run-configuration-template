import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl;
import com.intellij.execution.process.BaseOSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ExecutionEnvironmentBuilder;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.ExecutionConsole;
import com.intellij.ide.actions.runAnything.execution.RunAnythingRunProfileState;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.terminal.TerminalExecutionConsole;
import org.assertj.core.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import sun.nio.cs.UTF_32;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DenoRunConfiguration extends RunConfigurationBase {
    protected DenoRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    private DenoSettingsEditor dse = null;
    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        if(dse == null)
        dse = new DenoSettingsEditor();

        return dse;
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
        if(dse==null)
            return;

        if(dse.getRootPath().equals(""))
            throw new RuntimeConfigurationException("No entry point for configuration");

        if (!new File(dse.getRootPath()).exists())
            throw new RuntimeConfigurationException("Entry point file does not exist");
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        return new CommandLineState(executionEnvironment) {
            @NotNull
            @Override
            protected ProcessHandler startProcess() throws ExecutionException {
                List<String> flags = dse.getFlags();
                String execpath = dse.getRootPath();
                try {
                    return new BaseOSProcessHandler(
                            Runtime.getRuntime().exec(
                                    String.format(
                                            "Deno run %s %s", String.join(" ",flags), execpath
                                            )
                            ), "Starting Deno App at {place}", Charset.defaultCharset());
                } catch (IOException e) {
                    throw new ExecutionException(e.getMessage());
                }
            }
        };
    }


}