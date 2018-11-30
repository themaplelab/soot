package soot.asm;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2018 Kristen Newbury
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import org.objectweb.asm.ClassReader;

import soot.ClassSource;
import soot.FoundFile;
import soot.SootClass;
import soot.javaToJimple.IInitialResolver.Dependencies;

/**
 * Cache class source implementation.
 * 
 * @author Kristen Newbury
 */
class CacheClassSource extends ClassSource {

  private byte[] source;

  /**
   * Constructs a new Cache class source.
   * 
   * @param cls
   *          fully qualified name of the class.
   * @param data
   *          stream containing data for class.
   */
  CacheClassSource(String cls, byte[] source) {
    super(cls);
    if (source == null) {
      throw new IllegalStateException("Error: The class source must not be null.");
    }
    this.source = source;
  }

  @Override
  public Dependencies resolve(SootClass sc) {
    InputStream d = null;
    try{
      d = new ByteArrayInputStream(source);
      ClassReader clsr = new ClassReader(source);
      SootClassBuilder scb = new SootClassBuilder(sc);
      clsr.accept(scb, ClassReader.SKIP_FRAMES);
      Dependencies deps = new Dependencies();
      deps.typesToSignature.addAll(scb.deps);
      return deps;
    } catch (Exception e) {
      throw new RuntimeException("Error: Failed to create class reader from class source.", e);
    } finally {
      try {
        if (d != null) {
          d.close();
          d = null;
        }
      } catch (IOException e) {
        throw new RuntimeException("Error: Failed to close source input stream.", e);
      } finally {
        close();
      }
    }
  }

    @Override
  public void close() {
	//not applicable
  }
}
