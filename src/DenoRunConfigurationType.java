import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DenoRunConfigurationType implements ConfigurationType {
    @NotNull
    @Override
    public String getDisplayName() {
        return "Deno WebApp";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Deno Run Configuration";
    }

    @Override
    public Icon getIcon() {
        return AllIcons.General.Web;
    }

    @NotNull
    @Override
    public String getId() {
        return "DENO_RUN_CONFIGURATION";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new DenoConfigurationFactory(this)};
    }
}