package Deno;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class DenoConfigurationFactory extends ConfigurationFactory {
    private static final String FACTORY_NAME = "Deno configuration factory";

    protected DenoConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new DenoRunConfiguration(project, this, "Deno");
    }

    @NotNull
    @Override
    public String getName() {
        return FACTORY_NAME;
    }
}