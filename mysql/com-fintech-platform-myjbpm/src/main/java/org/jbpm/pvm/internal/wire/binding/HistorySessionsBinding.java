/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jbpm.pvm.internal.wire.binding;

import org.jbpm.pvm.internal.wire.descriptor.CollectionDescriptor;
import org.jbpm.pvm.internal.wire.descriptor.HistorySessionDescriptor;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Element;

/**
 * @author Tom Baeyens
 */
public class HistorySessionsBinding extends WireDescriptorBinding {
  
  private static final String KEY_HISTORY_SESSIONS_DESCRIPTOR = "historySessionsDescriptor";
  private static final ListBinding LIST_BINDING = new ListBinding();

  public HistorySessionsBinding() {
    super("history-sessions");
  }

  public Object parse(Element element, Parse parse, Parser parser) {
    HistorySessionDescriptor historySessionsDescriptor = (HistorySessionDescriptor) parse.contextMapGet(KEY_HISTORY_SESSIONS_DESCRIPTOR); 
    if (historySessionsDescriptor==null) {
      historySessionsDescriptor = new HistorySessionDescriptor();
      parse.contextMapPut(KEY_HISTORY_SESSIONS_DESCRIPTOR, historySessionsDescriptor);
    }
    
    CollectionDescriptor listDescriptor = (CollectionDescriptor) LIST_BINDING.parse(element, parse, parser);
    historySessionsDescriptor.add(listDescriptor);

    return historySessionsDescriptor;
  }

}
