package RunAny;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RunAnyRunConfigurationType implements ConfigurationType {
    @NotNull
    @Override
    public String getDisplayName() {
        return "Raw CLI";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Empty CLI Configuration";
    }

    @Override
    public Icon getIcon() {
        return AllIcons.General.ExternalTools;
    }

    @NotNull
    @Override
    public String getId() {
        return "CLI_RUN_CONFIGURATION";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new RunAnyConfigurationFactory(this)};
    }
}