/*
 * #%L
 * OME SCIFIO package for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2005 - 2013 Open Microscopy Environment:
 *   - Board of Regents of the University of Wisconsin-Madison
 *   - Glencoe Software, Inc.
 *   - University of Dundee
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */
package ome.scifio.services;

import java.util.HashMap;
import java.util.Map;

import ome.scifio.Metadata;
import ome.scifio.Translator;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.service.AbstractService;

@Plugin(type=TranslatorService.class)
public class DefaultTranslatorService extends AbstractService implements TranslatorService {
  
  // -- Parameters --
	
  @Parameter
  private PluginAttributeService attributeService;
  
  // -- TranslatorService API Methods --

  /*
   * @see ome.scifio.TranslatorService#
   * findTranslator(ome.scifio.Metadata, ome.scifio.Metadata)
   */
  public Translator findTranslator(Metadata source, Metadata dest, boolean exact) {
    return findTranslator(source.getClass(), dest.getClass(), exact);
  }

  /*
   * @see ome.scifio.services.TranslatorService#
   * findTranslator(java.lang.Class, java.lang.Class)
   */
  public Translator findTranslator(Class<?> source, Class<?> dest, boolean exact) {
    Map<String, String> kvPairs = new HashMap<String,String>();
    kvPairs.put(Translator.SOURCE, source.getName());
    kvPairs.put(Translator.DEST, dest.getName());
    
    Translator t = attributeService.createInstance(Translator.class, kvPairs, null, exact);
    
    return t;
  }

  /*
   * @see ome.scifio.TranslatorService#
   * translate(ome.scifio.Metadata, ome.scifio.Metadata)
   */
  public boolean translate(Metadata source, Metadata dest, boolean exact) {
    Translator t = findTranslator(source, dest, exact);
    
    if (t == null) return false;
    
    t.translate(source, dest);
    
    return true;
  }
}