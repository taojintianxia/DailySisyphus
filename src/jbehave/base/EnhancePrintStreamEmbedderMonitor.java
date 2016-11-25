package jbehave.base;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.embedder.PrintStreamEmbedderMonitor;

/**
 * 
 * @author nianjun
 *
 */

public class EnhancePrintStreamEmbedderMonitor extends PrintStreamEmbedderMonitor {

    private List<String> failedPaths = new ArrayList<>();

    public List<String> getFailedPaths() {
        return failedPaths;
    }

    @Override
    public void storyFailed(String path, Throwable cause) {
        super.storyFailed(path, cause);
        failedPaths.add(path);
    }
}
