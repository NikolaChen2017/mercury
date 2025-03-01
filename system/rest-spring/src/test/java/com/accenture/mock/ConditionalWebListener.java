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

package com.accenture.mock;

import org.platformlambda.core.annotations.OptionalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
@OptionalService("no.such.parameter")
public class ConditionalWebListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(MockWebListener.class);

    public void contextInitialized(ServletContextEvent event) {
        log.info("Initialized {}", event);
    }

    public void contextDestroyed(ServletContextEvent event) {
        log.info("Destroyed {}", event);
    }

}
