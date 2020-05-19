package RunAny;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.assertj.core.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class RunAnyRunConfiguration extends RunConfigurationBase {
    private RunAnySettingsEditor SettingsEditor = null;

    protected RunAnyRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {

        if (SettingsEditor == null)
            SettingsEditor = new RunAnySettingsEditor();

        return SettingsEditor;
    }


    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {


    }


    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {

        return new RunAnyRunProfileState(executionEnvironment, SettingsEditor.getCommand());

    }


}