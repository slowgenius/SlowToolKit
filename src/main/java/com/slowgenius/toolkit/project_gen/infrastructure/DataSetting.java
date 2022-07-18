package com.slowgenius.toolkit.project_gen.infrastructure;


import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.slowgenius.toolkit.project_gen.domain.model.ProjectConfigVO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "DataSetting",storages = @Storage("plugin.xml"))
public class DataSetting implements PersistentStateComponent<DataState> {

    private DataState state = new DataState();

    public static DataSetting getInstance() {
        return ApplicationManager.getApplication().getService(DataSetting.class);
    }

    @Nullable
    @Override
    public DataState getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull DataState state) {
        this.state = state;
    }

     public ProjectConfigVO getProjectConfig(){
        return state.getProjectConfigVO();
     }

}
