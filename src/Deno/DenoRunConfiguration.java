package Deno;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.assertj.core.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DenoRunConfiguration extends RunConfigurationBase {
    String allowWriteKey = "allow-write";
    String allowWriteFlag = "--allow-write";
    String allowReadKey = "allow-read";
    String AllowReadFlag = "--allow-read";
    String ExecutionScriptKey = "entry";
    String allowNetKey = "allow-net";
    String allowNetFlag = "--allow-net";
    private DenoSettingsEditor denoSettingsEditor = null;

    protected DenoRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {

        if (denoSettingsEditor == null)
            denoSettingsEditor = new DenoSettingsEditor();

        return denoSettingsEditor;
    }

    String compressAllowFlag(String flag, @NotNull JSONArray entries) throws RuntimeConfigurationException, JSONException {

        JSONArray wildcard = new JSONArray();
        wildcard.put("*");

        if (entries.get(0).equals("*")) {
            return flag;
        }

        List<String> allowedFolders = new ArrayList<>();
        for (int i = 0; i < entries.length(); i++) {
            Object entry;
            try {
                entry = entries.get(i);
            } catch (JSONException e) {
                throw new RuntimeConfigurationException("Error: " + e.getMessage());
            }
            if (entry instanceof String) {
                allowedFolders.add((String) entry);
            } else {
                throw new RuntimeConfigurationException("Entry provided in Allow-write is not a string");
            }
        }

        return flag + "=" + String.join(",", allowedFolders);
    }

    Map<String, String> ParseDenoConfJson(String jsonLocation) throws RuntimeConfigurationException {
        Map<String, String> flags = new HashMap<>();

        try {
            Path p = Paths.get(jsonLocation);

            String content = String.join("\n", Files.readAllLines(p));
            JSONObject jobj = new JSONObject(content);

            String mainTS = (String) jobj.get(ExecutionScriptKey);
            flags.put(ExecutionScriptKey, mainTS);


            if (jobj.has(allowWriteKey)) {
                Object AllowWriteEntry = jobj.get(allowWriteKey);

                if (AllowWriteEntry instanceof JSONArray) {

                    flags.put(allowWriteKey, compressAllowFlag(allowWriteFlag, (JSONArray) AllowWriteEntry));

                } else if (AllowWriteEntry instanceof String) {
                    if (AllowWriteEntry.equals("*")) {
                        flags.put(allowWriteKey, allowWriteFlag);
                    }
                }
            }

            if (jobj.has(allowReadKey)) {
                Object AllowReadEntry = jobj.get(allowReadKey);

                if (AllowReadEntry instanceof JSONArray) {

                    flags.put(allowReadKey, compressAllowFlag(AllowReadFlag, (JSONArray) AllowReadEntry));

                } else if (AllowReadEntry instanceof String) {
                    if (jobj.get(allowReadKey).equals("*")) {
                        flags.put(allowReadKey, AllowReadFlag);
                    }
                }
            }

            if (jobj.has(allowNetKey)) {
                if (jobj.get(allowNetKey).equals(true))
                    flags.put(allowNetKey, allowNetFlag);
            }

        } catch (JSONException e) {
            throw new RuntimeConfigurationException("Json Error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeConfigurationException("denoconf.json could not be read");

        } catch (Exception e) {
            throw new RuntimeConfigurationException("Error: " + e.getMessage());

        }

        return flags;
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
        if (denoSettingsEditor == null)
            return;
        String projectRoot = getProject().getBasePath();
        String confFile = projectRoot + File.separator + "denoconf.json";

        if (!new File(confFile).exists()) {
            throw new RuntimeConfigurationException("Deno configuration file (denoconf.json) not found");

        }

        Map<String, String> flags = ParseDenoConfJson(confFile);

        if (!flags.containsKey(ExecutionScriptKey)) {

            throw new RuntimeConfigurationException("No script entry point provided");
        }

        if (!new File(flags.get(ExecutionScriptKey)).exists()) {
            System.out.println(flags.get(ExecutionScriptKey));
            throw new RuntimeConfigurationException("Entry point file does not exist");
        }

    }

    public String getCommand() throws RuntimeConfigurationException {
        String projectRoot = getProject().getBasePath();
        String confFile = projectRoot + File.separator + "denoconf.json";


        Map<String, String> flags = ParseDenoConfJson(confFile);

        StringBuilder sb = new StringBuilder();
        if (flags.containsKey(allowReadKey)) {
            sb.append(flags.get(allowReadKey));
            sb.append(" ");
        }

        if (flags.containsKey(allowWriteKey)) {
            sb.append(flags.get(allowWriteKey));
            sb.append(" ");

        }

        if (flags.containsKey(allowNetKey)) {
            sb.append(flags.get(allowNetKey));
            sb.append(" ");

        }

        if (flags.containsKey(ExecutionScriptKey)) {
            sb.append(flags.get(ExecutionScriptKey));
        }
        return sb.toString();
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {

        try {
            return new DenoRunProfileState(executionEnvironment, getCommand());
        } catch (RuntimeConfigurationException e) {
            throw new ExecutionException(e.getMessage());
        }

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
//        };
    }
}