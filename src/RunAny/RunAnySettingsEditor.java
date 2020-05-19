package RunAny;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RunAnySettingsEditor extends SettingsEditor<RunAnyRunConfiguration> {
    private JPanel myPanel;
    private JTextField commandToRun;
    private LabeledComponent<ComponentWithBrowseButton> myMainClass;

    @Override
    protected void resetEditorFrom(@NotNull RunAnyRunConfiguration denoRunConfiguration) {

    }

    @Override
    protected void applyEditorTo(@NotNull RunAnyRunConfiguration denoRunConfiguration) throws ConfigurationException {

    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return myPanel;
    }

    private void createUIComponents() {
        myMainClass = new LabeledComponent<ComponentWithBrowseButton>();
        myMainClass.setComponent(new TextFieldWithBrowseButton());
    }

    public String getCommand(){
        return commandToRun.getText();
    }

}