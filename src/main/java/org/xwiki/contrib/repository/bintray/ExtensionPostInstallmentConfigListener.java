/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.repository.bintray;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.xwiki.component.phase.Initializable;
import org.xwiki.component.annotation.Component;
import org.xwiki.observation.EventListener;
import org.xwiki.observation.event.Event;

/**
 * This listener is only for execution configuration logic on installment.
 * It's a bit of workaround. Every listener is initialized by Observation Manager after registering it.
 *
 * @version $Id: 81a55f3a16b33bcf2696d0cac493b25c946b6ee4 $
 * @since 1.0
 */
@Component
@Named("BintrayRepositoryExtensionPostInstallmentConfigListener")
@Singleton
public class ExtensionPostInstallmentConfigListener implements EventListener, Initializable
{
    @Inject
    private Logger logger;

    @Override public void initialize()
    {
        logger.info(getName() + " registered");
    }

    @Override public String getName()
    {
        return "BintrayRepositoryExtensionPostInstallmentConfigListener";
    }

    @Override public List<Event> getEvents()
    {
        return Collections.emptyList();
    }

    @Override public void onEvent(Event event, Object o, Object o1)
    {
    }
}
