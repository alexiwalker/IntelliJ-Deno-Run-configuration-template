package RunAny;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class RunAnyConfigurationFactory extends ConfigurationFactory {
    private static final String FACTORY_NAME = "RunAny configuration factory";

    protected RunAnyConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new RunAnyRunConfiguration(project, this, "RunAny");
    }

    @NotNull
    @Override
    public String getName() {
        return FACTORY_NAME;
    }
}