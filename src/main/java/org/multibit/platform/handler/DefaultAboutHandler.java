/**
 * Copyright 2011 multibit.org
 *
 * Licensed under the MIT license (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/mit-license.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.multibit.platform.handler;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.multibit.platform.listener.GenericAboutEvent;
import org.multibit.platform.listener.GenericAboutEventListener;
import org.multibit.platform.listener.GenericEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Generic event to provide the following to {@link org.multibit.platform.GenericApplication}:</p>
 * <ul>
 * <li>Provision of application specific event handling code</li>
 * </ul>
 *
 * @since 0.3.0
 *         
 */
public class DefaultAboutHandler implements GenericAboutHandler, GenericEventListener<GenericAboutEventListener> {
  private static final Logger log = LoggerFactory.getLogger(DefaultAboutHandler.class);

  // The event listeners
  private Set<GenericAboutEventListener> listeners = new HashSet<GenericAboutEventListener>();

  @Override
  public void addListeners(Collection<GenericAboutEventListener> listeners) {
    this.listeners.addAll(listeners);
  }

  /**
   * Handles the process of broadcasting the event to listeners
   * allowing this process to be decoupled
   * @param event The generic event (or it's proxy)
   */
  @Override
  public void handleAbout(GenericAboutEvent event) {
    log.debug("Called");
    if (event == null) {
      log.warn("Received a null event");
      return;
    }
    log.debug("Event class is {}", event.getClass().getSimpleName());
    log.debug("Broadcasting to {} listener(s)", listeners.size());
    for (GenericAboutEventListener listener : listeners) {
      listener.onAboutEvent(event);
    }
  }
}
