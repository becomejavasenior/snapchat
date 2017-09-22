package com.snapchat.jobs;

import com.snapchat.image.Image;
import com.snapchat.image.ImageHolder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by apolonxviii on 22.09.17.
 */
public class ValidateTimeJob implements Job {

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        Set<Map.Entry<String, Image>> entrySet = ImageHolder.getMap().entrySet();
        entrySet.forEach(new Consumer<Map.Entry<String, Image>>() {
            @Override
            public void accept(Map.Entry<String, Image> stringImageEntry) {
                if (stringImageEntry.getValue().getTime() <= System.currentTimeMillis()) {
                    String path = stringImageEntry.getValue().getPath();
                    ImageHolder.remove(path);
                    System.out.println("Image " + path + " was deleted");
                }
            }
        });
    }
}
