/*
 * #%L
 * Legacy layer preserving compatibility between legacy Bio-Formats and SCIFIO.
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

package loci.common;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/**
 * A legacy delegator class for ome.scifio.io.NIOFileunwrap().
 *
 * <dl><dt><b>Source code:</b></dt>
 * <dd><a href="http://trac.openmicroscopy.org.uk/ome/browser/bioformats.git/components/common/src/loci/common/NIOFileunwrap().java">Trac</a>,
 * <a href="http://git.openmicroscopy.org/?p=bioformats.git;a=blob;f=components/common/src/loci/common/NIOFileunwrap().java;hb=HEAD">Gitweb</a></dd></dl>
 *
 * @see IRandomAccess
 * @see java.io.RandomAccessFile
 *
 * @author Chris Allan <callan at blackcat dot ca>
 * 
 * @deprecated see ome.scifio.io.NIOFileHandle
 */
@Deprecated
public class NIOFileHandle extends AbstractNIOHandle {

  // -- Constants --

  //-- Static fields --

  // -- Fields --


  // -- Constructors --

  /**
   * Creates a random access file stream to read from, and
   * optionally to write to, the file specified by the File argument.
   */
  public NIOFileHandle(File file, String mode, int bufferSize) 
    throws IOException {
    super(new ome.scifio.io.NIOFileHandle(file, mode, bufferSize));
  }

  /**
   * Creates a random access file stream to read from, and
   * optionally to write to, the file specified by the File argument.
   */
  public NIOFileHandle(File file, String mode) throws IOException {
    super(new ome.scifio.io.NIOFileHandle(file, mode));
  }

  /**
   * Creates a random access file stream to read from, and
   * optionally to write to, a file with the specified name.
   */
  public NIOFileHandle(String name, String mode) throws IOException {
    super(new ome.scifio.io.NIOFileHandle(name, mode));
  }

  // -- NIOFileHandle API methods --

  /**
   * Set the default buffer size for read-only files.
   *
   * Subsequent uses of the NIOFileHandle(String, String) and
   * NIOFileHandle(File, String) constructors will use this buffer size.
   */
  public static void setDefaultBufferSize(int size) {
    ome.scifio.io.NIOFileHandle.setDefaultBufferSize(size);
  }

  /**
   * Set the default buffer size for read/write files.
   *
   * Subsequent uses of the NIOFileHandle(String, String) and
   * NIOFileHandle(File, String) constructors will use this buffer size.
   */
  public static void setDefaultReadWriteBufferSize(int size) {
    ome.scifio.io.NIOFileHandle.setDefaultReadWriteBufferSize(size);
  }

  // -- FileHandle and Channel API methods --

  /** Gets the random access file object backing this Fileunwrap(). */
  public RandomAccessFile getRandomAccessFile() { return ((ome.scifio.io.NIOFileHandle)unwrap()).getRandomAccessFile(); }

  /** Gets the FileChannel from this Fileunwrap(). */
  public FileChannel getFileChannel() { return ((ome.scifio.io.NIOFileHandle)unwrap()).getFileChannel(); }

  /** Gets the current buffer size. */
  public int getBufferSize() { return ((ome.scifio.io.NIOFileHandle)unwrap()).getBufferSize(); }

  // -- AbstractNIOHandle API methods --

  /* @see AbstractNIOunwrap().setLength(long) */
  public void setLength(long length) throws IOException {
    ((ome.scifio.io.NIOFileHandle)unwrap()).setLength(length);
  }

  // -- IRandomAccess API methods --

  /* @see IRandomAccess.close() */
  public void close() throws IOException {
    unwrap().close();
  }

  /* @see IRandomAccess.getFilePointer() */
  public long getFilePointer() throws IOException {
    return unwrap().getFilePointer();
  }

  /* @see IRandomAccess.length() */
  public long length() throws IOException {
    return unwrap().length();
  }

  /* @see IRandomAccess.getOrder() */
  public ByteOrder getOrder() {
    return unwrap().getOrder();
  }

  /* @see IRandomAccess.setOrder(ByteOrder) */
  public void setOrder(ByteOrder order) {
    unwrap().setOrder(order);
  }

  /* @see IRandomAccess.read(byte[]) */
  public int read(byte[] b) throws IOException {
    return unwrap().read(b);
  }

  /* @see IRandomAccess.read(byte[], int, int) */
  public int read(byte[] b, int off, int len) throws IOException {
    return unwrap().read(b, off, len);
  }

  /* @see IRandomAccess.read(ByteBuffer) */
  public int read(ByteBuffer buf) throws IOException {
    return unwrap().read(buf);
  }

  /* @see IRandomAccess.read(ByteBuffer, int, int) */
  public int read(ByteBuffer buf, int off, int len) throws IOException {
    return unwrap().read(buf, off, len);
  }

  /* @see IRandomAccess.seek(long) */
  public void seek(long pos) throws IOException {
    unwrap().seek(pos);
  }

  /* @see java.io.DataInput.readBoolean() */
  public boolean readBoolean() throws IOException {
    return unwrap().readBoolean();
  }

  /* @see java.io.DataInput.readByte() */
  public byte readByte() throws IOException {
    return unwrap().readByte();
  }

  /* @see java.io.DataInput.readChar() */
  public char readChar() throws IOException {
    return unwrap().readChar();
  }

  /* @see java.io.DataInput.readDouble() */
  public double readDouble() throws IOException {
    return unwrap().readDouble();
  }

  /* @see java.io.DataInput.readFloat() */
  public float readFloat() throws IOException {
    return unwrap().readFloat();
  }

  /* @see java.io.DataInput.readFully(byte[]) */
  public void readFully(byte[] b) throws IOException {
    unwrap().readFully(b);
  }

  /* @see java.io.DataInput.readFully(byte[], int, int) */
  public void readFully(byte[] b, int off, int len) throws IOException {
    unwrap().readFully(b, off, len);
  }

  /* @see java.io.DataInput.readInt() */
  public int readInt() throws IOException {
    return unwrap().readInt();
  }

  /* @see java.io.DataInput.readLine() */
  public String readLine() throws IOException {
    return unwrap().readLine();
  }

  /* @see java.io.DataInput.readLong() */
  public long readLong() throws IOException {
    return unwrap().readLong();
  }

  /* @see java.io.DataInput.readShort() */
  public short readShort() throws IOException {
    return unwrap().readShort();
  }

  /* @see java.io.DataInput.readUnsignedByte() */
  public int readUnsignedByte() throws IOException {
    return unwrap().readUnsignedByte();
  }

  /* @see java.io.DataInput.readUnsignedShort() */
  public int readUnsignedShort() throws IOException {
    return unwrap().readUnsignedShort();
  }

  /* @see java.io.DataInput.readUTF() */
  public String readUTF() throws IOException {
    return unwrap().readUTF();
  }

  /* @see java.io.DataInput.skipBytes(int) */
  public int skipBytes(int n) throws IOException {
    return unwrap().skipBytes(n);
  }

  // -- DataOutput API methods --

  /* @see java.io.DataOutput.write(byte[]) */
  public void write(byte[] b) throws IOException {
    unwrap().write(b);
  }

  /* @see java.io.DataOutput.write(byte[], int, int) */
  public void write(byte[] b, int off, int len) throws IOException {
    unwrap().write(b, off, len);
  }

  /* @see IRandomAccess.write(ByteBuffer) */
  public void write(ByteBuffer buf) throws IOException {
    unwrap().write(buf);
  }

  /* @see IRandomAccess.write(ByteBuffer, int, int) */
  public void write(ByteBuffer buf, int off, int len) throws IOException {
    unwrap().write(buf, off, len);
  }

  /* @see java.io.DataOutput.write(int b) */
  public void write(int b) throws IOException {
    unwrap().write(b);
  }

  /* @see java.io.DataOutput.writeBoolean(boolean) */
  public void writeBoolean(boolean v) throws IOException {
    unwrap().writeBoolean(v);
  }

  /* @see java.io.DataOutput.writeByte(int) */
  public void writeByte(int v) throws IOException {
    unwrap().writeByte(v);
  }

  /* @see java.io.DataOutput.writeBytes(String) */
  public void writeBytes(String s) throws IOException {
    unwrap().writeBytes(s);
  }

  /* @see java.io.DataOutput.writeChar(int) */
  public void writeChar(int v) throws IOException {
    unwrap().writeChar(v);
  }

  /* @see java.io.DataOutput.writeChars(String) */
  public void writeChars(String s) throws IOException {
    unwrap().writeChars(s);
  }

  /* @see java.io.DataOutput.writeDouble(double) */
  public void writeDouble(double v) throws IOException {
    unwrap().writeDouble(v);
  }

  /* @see java.io.DataOutput.writeFloat(float) */
  public void writeFloat(float v) throws IOException {
    unwrap().writeFloat(v);
  }

  /* @see java.io.DataOutput.writeInt(int) */
  public void writeInt(int v) throws IOException {
    unwrap().writeInt(v);
  }

  /* @see java.io.DataOutput.writeLong(long) */
  public void writeLong(long v) throws IOException {
    unwrap().writeLong(v);
  }

  /* @see java.io.DataOutput.writeShort(int) */
  public void writeShort(int v) throws IOException {
    unwrap().writeShort(v);
  }

  /* @see java.io.DataOutput.writeUTF(String)  */
  public void writeUTF(String str) throws IOException {
    unwrap().writeUTF(str);
  }

}