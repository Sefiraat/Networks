package io.github.sefiraat.networks.managers;

import io.github.sefiraat.networks.Networks;
import io.github.sefiraat.networks.runnables.SaveConfigRunnable;
import lombok.Getter;

public class RunnableManager {

    @Getter
    public final SaveConfigRunnable saveConfigRunnable;

    public RunnableManager() {
        Networks plugin = Networks.getInstance();

        this.saveConfigRunnable = new SaveConfigRunnable();
        this.saveConfigRunnable.runTaskTimer(plugin, 1, 12000);
    }
}
