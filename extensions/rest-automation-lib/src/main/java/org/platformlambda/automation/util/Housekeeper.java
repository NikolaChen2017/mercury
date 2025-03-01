/*

    Copyright 2018-2021 Accenture Technology

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 */

package org.platformlambda.automation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Housekeeper extends Thread {
    private static final Logger log = LoggerFactory.getLogger(Housekeeper.class);

    private static final long ONE_MINUTE = 60 * 1000;
    private static final long TEN_MINUTE = 10 * ONE_MINUTE;
    private boolean normal = true;

    private File dir;

    public Housekeeper(File dir) {
        this.dir = dir;
    }

    @Override
    public void run() {
        log.info("Started");
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        long t0 = System.currentTimeMillis();
        while (normal) {
            long now = System.currentTimeMillis();
            if (now - t0 > ONE_MINUTE) {
                t0 = now;
                removeExpiredFiles();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // yield to the operating system
            }
        }
        log.info("Stopped");
    }

    private void removeExpiredFiles() {
        /*
         * The temporary download directory is used as a buffer to relay incoming files from HTTP requests.
         * They are removed immediately after relay.
         *
         * This housekeeper is designed as a "catch-all" mechanism to enable zero-maintenance.
         */
        long now = System.currentTimeMillis();
        List<File> expired = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File f: files) {
                if (f.isFile()) {
                    if (now - f.lastModified() > TEN_MINUTE) {
                        expired.add(f);
                    }
                }
            }
            if (!expired.isEmpty()) {
                for (File f: expired) {
                    f.delete();
                    log.warn("Removing expired file {}", f);
                }
            }
        }
    }

    private void shutdown() {
        normal = false;
    }

}
