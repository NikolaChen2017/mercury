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

import org.platformlambda.automation.models.AsyncContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class AsyncTimeoutHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(AsyncTimeoutHandler.class);

    private final ConcurrentMap<String, AsyncContextHolder> contexts;
    private boolean normal = true;

    public AsyncTimeoutHandler(ConcurrentMap<String, AsyncContextHolder> contexts) {
        this.contexts = contexts;
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    @Override
    public void run() {
        log.info("Async HTTP timeout handler started");
        while (normal) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // ok to ignore
            }
            // check async context timeout
            if (!contexts.isEmpty()) {
                List<String> list = new ArrayList<>(contexts.keySet());
                long now = System.currentTimeMillis();
                for (String id : list) {
                    AsyncContextHolder holder = contexts.get(id);
                    long t1 = holder.lastAccess;
                    if (now - t1 > holder.timeout) {
                        log.warn("Async HTTP Context {} timeout for {} ms", id, now - t1);
                        SimpleHttpUtility httpUtil = SimpleHttpUtility.getInstance();
                        httpUtil.sendError(id, holder.request, 408,
                                "Timeout for " + (holder.timeout / 1000) + " seconds");
                    }
                }
            }
        }
        log.info("Async HTTP timeout handler stopped");
    }

    private void shutdown() {
        normal = false;
    }
}

