package Deno;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DenoSettingsEditor extends SettingsEditor<DenoRunConfiguration> {
    private JPanel myPanel;
    private JTextField RootPathFile;
    private JCheckBox allowWriteCheckBox;
    private JCheckBox allowReadCheckBox;
    private JCheckBox allowNetCheckBox;
    private LabeledComponent<ComponentWithBrowseButton> myMainClass;

    @Override
    protected void resetEditorFrom(@NotNull DenoRunConfiguration denoRunConfiguration) {

    }

    @Override
    protected void applyEditorTo(@NotNull DenoRunConfiguration denoRunConfiguration) throws ConfigurationException {

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

    public List<String> getFlags(){
        List<String> flags = new ArrayList<>();
        if(allowNetCheckBox.isSelected()) flags.add("--allow-net");
        if(allowReadCheckBox.isSelected()) flags.add("--allow-read");
        if(allowWriteCheckBox.isSelected()) flags.add("--allow-write");

        return flags;
    }

    public String getRootPath(){
        return RootPathFile.getText();
    }
}