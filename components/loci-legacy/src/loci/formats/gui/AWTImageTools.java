/*
 * #%L
 * OME SCIFIO package for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2005 - 2012 Open Microscopy Environment:
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

package loci.formats.gui;

import io.scif.ByteArrayPlane;
import io.scif.Reader;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;

import net.imglib2.display.ColorTable16;
import net.imglib2.display.ColorTable8;

import ome.xml.model.primitives.PositiveInteger;

import loci.formats.FormatAdapter;
import loci.formats.FormatException;
import loci.formats.FormatTools;
import loci.formats.IFormatReader;
import loci.formats.MetadataTools;
import loci.formats.meta.MetadataRetrieve;

/**
 * A legacy delegator class for io.scif.gui.AWTImageTools
 * 
 * <dl><dt><b>Source code:</b></dt>
 * <dd><a href="http://trac.openmicroscopy.org.uk/ome/browser/bioformats.git/components/bio-formats/src/loci/formats/gui/AWTImageTools.java">Trac</a>,
 * <a href="http://git.openmicroscopy.org/?p=bioformats.git;a=blob;f=components/bio-formats/src/loci/formats/gui/AWTImageTools.java;hb=HEAD">Gitweb</a></dd></dl>
 *
 * @author Curtis Rueden ctrueden at wisc.edu
 * 
 * @deprecated see io.scif.gui.AWTImageTools
 */
@Deprecated
public final class AWTImageTools {

  // -- Constants --

  // -- Constructor --

  private AWTImageTools() { }

  // -- Image construction - from 1D (single channel) data arrays --

  /**
   * Creates an image from the given single-channel byte data.
   *
   * @param data Array containing image data.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param signed Whether the byte values should be treated as signed
   *   (-128 to 127) instead of unsigned (0 to 255).
   */
  public static BufferedImage makeImage(byte[] data,
    int w, int h, boolean signed)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, signed);
  }

  /**
   * Creates an image from the given single-channel short data.
   *
   * @param data Array containing image data.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param signed Whether the short values should be treated as signed
   *   (-32768 to 32767) instead of unsigned (0 to 65535).
   */
  public static BufferedImage makeImage(short[] data,
    int w, int h, boolean signed)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, signed);
  }

  /**
   * Creates an image from the given single-channel int data.
   *
   * @param data Array containing image data.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param signed Whether the int values should be treated as signed
   *   (-2^31 to 2^31-1) instead of unsigned (0 to 2^32-1).
   */
  public static BufferedImage makeImage(int[] data,
    int w, int h, boolean signed)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, signed);
  }

  /**
   * Creates an image from the given single-channel float data.
   *
   * @param data Array containing image data.
   * @param w Width of image plane.
   * @param h Height of image plane.
   */
  public static BufferedImage makeImage(float[] data, int w, int h) {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h);
  }

  /**
   * Creates an image from the given single-channel double data.
   *
   * @param data Array containing image data.
   * @param w Width of image plane.
   * @param h Height of image plane.
   */
  public static BufferedImage makeImage(double[] data, int w, int h) {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h);
  }

  // -- Image construction - from 1D (interleaved or banded) data arrays --

  /**
   * Creates an image from the given byte data.
   *
   * @param data Array containing image data.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param c Number of channels.
   * @param interleaved If set, the channels are assumed to be interleaved;
   *   otherwise they are assumed to be sequential.
   *   For example, for RGB data, the pattern "RGBRGBRGB..." is interleaved,
   *   while "RRR...GGG...BBB..." is sequential.
   * @param signed Whether the byte values should be treated as signed
   *   (-128 to 127) instead of unsigned (0 to 255).
   */
  public static BufferedImage makeImage(byte[] data,
    int w, int h, int c, boolean interleaved, boolean signed)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, c, interleaved, 
      signed);
  }

  /**
   * Creates an image from the given short data.
   *
   * @param data Array containing image data.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param c Number of channels.
   * @param interleaved If set, the channels are assumed to be interleaved;
   *   otherwise they are assumed to be sequential.
   *   For example, for RGB data, the pattern "RGBRGBRGB..." is interleaved,
   *   while "RRR...GGG...BBB..." is sequential.
   * @param signed Whether the short values should be treated as signed
   *   (-32768 to 32767) instead of unsigned (0 to 65535).
   */
  public static BufferedImage makeImage(short[] data,
    int w, int h, int c, boolean interleaved, boolean signed)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, c, interleaved, signed);
  }

  /**
   * Creates an image from the given int data.
   *
   * @param data Array containing image data.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param c Number of channels.
   * @param interleaved If set, the channels are assumed to be interleaved;
   *   otherwise they are assumed to be sequential.
   *   For example, for RGB data, the pattern "RGBRGBRGB..." is interleaved,
   *   while "RRR...GGG...BBB..." is sequential.
   * @param signed Whether the int values should be treated as signed
   *   (-2^31 to 2^31-1) instead of unsigned (0 to 2^32-1).
   */
  public static BufferedImage makeImage(int[] data,
    int w, int h, int c, boolean interleaved, boolean signed)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, c, interleaved, signed);
  }

  /**
   * Creates an image from the given float data.
   *
   * @param data Array containing image data.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param c Number of channels.
   * @param interleaved If set, the channels are assumed to be interleaved;
   *   otherwise they are assumed to be sequential.
   *   For example, for RGB data, the pattern "RGBRGBRGB..." is interleaved,
   *   while "RRR...GGG...BBB..." is sequential.
   */
  public static BufferedImage makeImage(float[] data,
    int w, int h, int c, boolean interleaved)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, c, interleaved);
  }

  /**
   * Creates an image from the given double data.
   *
   * @param data Array containing image data.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param c Number of channels.
   * @param interleaved If set, the channels are assumed to be interleaved;
   *   otherwise they are assumed to be sequential.
   *   For example, for RGB data, the pattern "RGBRGBRGB..." is interleaved,
   *   while "RRR...GGG...BBB..." is sequential.
   */
  public static BufferedImage makeImage(double[] data,
    int w, int h, int c, boolean interleaved)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, c, interleaved);
  }

  // -- Image construction - from 2D (banded) data arrays --

  /**
   * Creates an image from the given byte data.
   *
   * @param data Array containing image data.
   *   It is assumed that each channel corresponds to one element of the array.
   *   For example, for RGB data, data[0] is R, data[1] is G, and data[2] is B.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param signed Whether the byte values should be treated as signed
   *   (-128 to 127) instead of unsigned (0 to 255).
   */
  public static BufferedImage makeImage(byte[][] data,
    int w, int h, boolean signed)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, signed);
  }

  /**
   * Creates an image from the given short data.
   *
   * @param data Array containing image data.
   *   It is assumed that each channel corresponds to one element of the array.
   *   For example, for RGB data, data[0] is R, data[1] is G, and data[2] is B.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param signed Whether the short values should be treated as signed
   *   (-32768 to 32767) instead of unsigned (0 to 65535).
   */
  public static BufferedImage makeImage(short[][] data,
    int w, int h, boolean signed)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, signed);
  }

  /**
   * Creates an image from the given int data.
   *
   * @param data Array containing image data.
   *   It is assumed that each channel corresponds to one element of the array.
   *   For example, for RGB data, data[0] is R, data[1] is G, and data[2] is B.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param signed Whether the int values should be treated as signed
   *   (-2^31 to 2^31-1) instead of unsigned (0 to 2^32-1).
   */
  public static BufferedImage makeImage(int[][] data,
    int w, int h, boolean signed)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, signed);
  }

  /**
   * Creates an image from the given single-precision floating point data.
   *
   * @param data Array containing image data.
   *   It is assumed that each channel corresponds to one element of the array.
   *   For example, for RGB data, data[0] is R, data[1] is G, and data[2] is B.
   * @param w Width of image plane.
   * @param h Height of image plane.
   */
  public static BufferedImage makeImage(float[][] data, int w, int h) {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h);
  }

  /**
   * Creates an image from the given double-precision floating point data.
   *
   * @param data Array containing image data.
   *   It is assumed that each channel corresponds to one element of the array.
   *   For example, for RGB data, data[0] is R, data[1] is G, and data[2] is B.
   * @param w Width of image plane.
   * @param h Height of image plane.
   */
  public static BufferedImage makeImage(double[][] data, int w, int h) {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h);
  }

  // -- Image construction - with type conversion --

  /**
   * Creates an image from the given raw byte array, obtaining the
   * dimensional parameters from the specified metadata object.
   *
   * @param data Array containing image data.
   * @param interleaved If set, the channels are assumed to be interleaved;
   *   otherwise they are assumed to be sequential.
   *   For example, for RGB data, the pattern "RGBRGBRGB..." is interleaved,
   *   while "RRR...GGG...BBB..." is sequential.
   * @param meta Metadata object containing dimensional parameters.
   * @param series Relevant image series number of metadata object.
   */
  public static BufferedImage makeImage(byte[] data, boolean interleaved,
    MetadataRetrieve meta, int series) throws FormatException
  {
    MetadataTools.verifyMinimumPopulated(meta, series);

    int width = meta.getPixelsSizeX(series).getValue().intValue();
    int height = meta.getPixelsSizeY(series).getValue().intValue();
    String pixelType = meta.getPixelsType(series).toString();
    int type = FormatTools.pixelTypeFromString(pixelType);
    PositiveInteger nChannels = meta.getChannelSamplesPerPixel(series, 0);
    if (nChannels == null) {
      //LOGGER.warn("SamplesPerPixel is null; it is assumed to be 1.");
    }
    int channels = nChannels == null ? 1 : nChannels.getValue();
    boolean littleEndian =
      !meta.getPixelsBinDataBigEndian(series, 0).booleanValue();
    return makeImage(data, width, height, channels,
      interleaved, FormatTools.getBytesPerPixel(type),
      FormatTools.isFloatingPoint(type), littleEndian,
      FormatTools.isSigned(type));
  }

  /**
   * Creates an image from the given raw byte array,
   * performing any necessary type conversions.
   *
   * @param data Array containing image data.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param c Number of channels.
   * @param interleaved If set, the channels are assumed to be interleaved;
   *   otherwise they are assumed to be sequential.
   *   For example, for RGB data, the pattern "RGBRGBRGB..." is interleaved,
   *   while "RRR...GGG...BBB..." is sequential.
   * @param bpp Denotes the number of bytes in the returned primitive type
   *   (e.g. if bpp == 2, we should return an array of type short).
   * @param fp If set and bpp == 4 or bpp == 8, then return floats or doubles.
   * @param little Whether byte array is in little-endian order.
   * @param signed Whether the data values should be treated as signed
   *   instead of unsigned.
   */
  public static BufferedImage makeImage(byte[] data, int w, int h, int c,
    boolean interleaved, int bpp, boolean fp, boolean little, boolean signed)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, c, interleaved,
      bpp, fp, little, signed);
  }

  /**
   * Creates an image from the given raw byte array,
   * performing any necessary type conversions.
   *
   * @param data Array containing image data, one channel per element.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param bpp Denotes the number of bytes in the returned primitive type
   *   (e.g. if bpp == 2, we should return an array of type short).
   * @param fp If set and bpp == 4 or bpp == 8, then return floats or doubles.
   * @param little Whether byte array is in little-endian order.
   * @param signed Whether the data values should be treated as signed
   *   instead of unsigned.
   */
  public static BufferedImage makeImage(byte[][] data,
    int w, int h, int bpp, boolean fp, boolean little, boolean signed)
  {
    return io.scif.gui.AWTImageTools.makeImage(data, w, h, bpp, fp,
      little, signed);
  }

  public static BufferedImage makeRGBImage(byte[] data, int c, int w, int h,
    boolean interleaved)
  {
    return io.scif.gui.AWTImageTools.makeRGBImage(data, c, w, h, interleaved);
  }

  // -- Image construction - miscellaneous --

  /**
   * Creates a blank image with the given dimensions and transfer type.
   * @param w Width of image plane.
   * @param h Height of image plane.
   * @param c Number of channels.
   * @param type One of the following types:<ul>
   *   <li>FormatTools.INT8 <b>** unsupported for now **</b></li>
   *   <li>FormatTools.UINT8</li>
   *   <li>FormatTools.INT16</li>
   *   <li>FormatTools.UINT16</li>
   *   <li>FormatTools.INT32</li>
   *   <li>FormatTools.UINT32 <b>** unsupported for now **</b></li>
   *   <li>FormatTools.FLOAT</li>
   *   <li>FormatTools.DOUBLE</li>
   * </ul>
   */
  public static BufferedImage blankImage(int w, int h, int c, int type) {
    return io.scif.gui.AWTImageTools.blankImage(w, h, c, type);
  }

  /** Creates an image with the given DataBuffer. */
  public static BufferedImage constructImage(int c, int type, int w,
    int h, boolean interleaved, boolean banded, DataBuffer buffer)
  {
    return io.scif.gui.AWTImageTools.constructImage(c, type, w, h, 
      interleaved, banded, buffer);
  }

  /** Creates an image with the given DataBuffer. */
  public static BufferedImage constructImage(int c, int type, int w,
    int h, boolean interleaved, boolean banded, DataBuffer buffer,
    ColorModel colorModel)
  {
    return io.scif.gui.AWTImageTools.constructImage(c, type, w, h, interleaved,
      banded, buffer, colorModel);
  }

  /**
   * Creates an image from the given byte array, using the given
   * IFormatReader to retrieve additional information.
   */
  public static BufferedImage openImage(byte[] buf, IFormatReader r,
    int w, int h) throws FormatException, IOException
  {
    try {
      if (!BufferedImageReader.class.isAssignableFrom(r.getClass()))
        return null;
      
      Reader scReader = FormatAdapter.get(r);
      
      ByteArrayPlane plane = new ByteArrayPlane(scReader.getContext());
      plane.populate(scReader.getMetadata().get(r.getSeries()), buf, 0, 0, w, h);
      
      if (r.get8BitLookupTable() != null) {
    	  plane.setColorTable(new ColorTable8(r.get8BitLookupTable()));
      }
      else if (r.get16BitLookupTable() != null) {
    	  plane.setColorTable(new ColorTable16(r.get16BitLookupTable()));
      }
      
      return io.scif.gui.AWTImageTools.openImage(plane, scReader, w, h, r.getSeries());
    }
    catch (io.scif.FormatException e) {
      throw new FormatException(e);
    }
  }

  // -- Data extraction --

  /**
   * Gets the image's pixel data as arrays of primitives, one per channel.
   * The returned type will be either byte[][], short[][], int[][], float[][]
   * or double[][], depending on the image's transfer type.
   */
  public static Object getPixels(BufferedImage image) {
    return io.scif.gui.AWTImageTools.getPixels(image);
  }

  /**
   *
   * Gets the image's pixel data as arrays of primitives, one per channel.
   * The returned type will be either byte[][], short[][], int[][], float[][]
   * or double[][], depending on the image's transfer type.
   */
  public static Object getPixels(BufferedImage image, int x, int y,
    int w, int h)
  {
    return io.scif.gui.AWTImageTools.getPixels(image, x, y, w, h);
  }

  /**
   * Gets the raster's pixel data as arrays of primitives, one per channel.
   * The returned type will be either byte[][], short[][], int[][], float[][]
   * or double[][], depending on the raster's transfer type.
   */
  public static Object getPixels(WritableRaster raster) {
    return io.scif.gui.AWTImageTools.getPixels(raster);
  }

  /**
   * Gets the raster's pixel data as arrays of primitives, one per channel.
   * The returned type will be either byte[][], short[][], int[][], float[][]
   * or double[][], depending on the raster's transfer type.
   */
  public static Object getPixels(WritableRaster raster, int x, int y,
    int w, int h)
  {
    return io.scif.gui.AWTImageTools.getPixels(raster, x, y, w, h);
  }

  /** Extracts pixel data as arrays of unsigned bytes, one per channel. */
  public static byte[][] getBytes(BufferedImage image) {
    return io.scif.gui.AWTImageTools.getBytes(image);
  }

  /** Extracts pixel data as arrays of unsigned bytes, one per channel. */
  public static byte[][] getBytes(WritableRaster r) {
    return io.scif.gui.AWTImageTools.getBytes(r);
  }

  /** Extracts pixel data as arrays of unsigned bytes, one per channel. */
  public static byte[][] getBytes(WritableRaster r, int x, int y, int w, int h)
  {
    return io.scif.gui.AWTImageTools.getBytes(r, x, y, w, h);
  }

  /** Extracts pixel data as arrays of unsigned shorts, one per channel. */
  public static short[][] getShorts(BufferedImage image) {
    return io.scif.gui.AWTImageTools.getShorts(image);
  }

  /** Extracts pixel data as arrays of unsigned shorts, one per channel. */
  public static short[][] getShorts(WritableRaster r) {
    return io.scif.gui.AWTImageTools.getShorts(r);
  }

  /** Extracts pixel data as arrays of unsigned shorts, one per channel. */
  public static short[][] getShorts(WritableRaster r, int x, int y,
    int w, int h)
  {
   return io.scif.gui.AWTImageTools.getShorts(r, x, y, w, h);
  }

  /** Extracts pixel data as arrays of signed integers, one per channel. */
  public static int[][] getInts(BufferedImage image) {
    return io.scif.gui.AWTImageTools.getInts(image);
  }

  /** Extracts pixel data as arrays of signed integers, one per channel. */
  public static int[][] getInts(WritableRaster r) {
    return io.scif.gui.AWTImageTools.getInts(r);
  }

  /** Extracts pixel data as arrays of signed integers, one per channel. */
  public static int[][] getInts(WritableRaster r, int x, int y, int w, int h) {
    return io.scif.gui.AWTImageTools.getInts(r, x, y, w, h);
  }

  /** Extracts pixel data as arrays of floats, one per channel. */
  public static float[][] getFloats(BufferedImage image) {
    return io.scif.gui.AWTImageTools.getFloats(image);
  }

  /** Extracts pixel data as arrays of floats, one per channel. */
  public static float[][] getFloats(WritableRaster r) {
    return io.scif.gui.AWTImageTools.getFloats(r);
  }

  /** Extracts pixel data as arrays of floats, one per channel. */
  public static float[][] getFloats(WritableRaster r, int x, int y,
    int w, int h)
  {
    return io.scif.gui.AWTImageTools.getFloats(r, x, y, w, h);
  }

  /** Extracts pixel data as arrays of doubles, one per channel. */
  public static double[][] getDoubles(BufferedImage image) {
    return io.scif.gui.AWTImageTools.getDoubles(image);
  }

  /** Extracts pixel data as arrays of doubles, one per channel. */
  public static double[][] getDoubles(WritableRaster r) {
    return io.scif.gui.AWTImageTools.getDoubles(r);
  }

  /** Extracts pixel data as arrays of doubles, one per channel. */
  public static double[][] getDoubles(WritableRaster r, int x, int y,
    int w, int h)
  {
    return io.scif.gui.AWTImageTools.getDoubles(r, x, y, w, h);
  }

  /**
   * Return a 2D array of bytes representing the image.  If the transfer type
   * is something other than DataBuffer.TYPE_BYTE, then each pixel value is
   * converted to the appropriate number of bytes.  In other words, if we
   * are given an image with 16-bit data, each channel of the resulting array
   * will have width * height * 2 bytes.
   */
  public static byte[][] getPixelBytes(BufferedImage img, boolean little) {
    return io.scif.gui.AWTImageTools.getPixelBytes(img, little);
  }

  /**
   * Return a 2D array of bytes representing the image.  If the transfer type
   * is something other than DataBuffer.TYPE_BYTE, then each pixel value is
   * converted to the appropriate number of bytes.  In other words, if we
   * are given an image with 16-bit data, each channel of the resulting array
   * will have width * height * 2 bytes.
   */
  public static byte[][] getPixelBytes(WritableRaster r, boolean little) {
    return io.scif.gui.AWTImageTools.getPixelBytes(r, little);
  }

  /**
   * Return a 2D array of bytes representing the image.  If the transfer type
   * is something other than DataBuffer.TYPE_BYTE, then each pixel value is
   * converted to the appropriate number of bytes.  In other words, if we
   * are given an image with 16-bit data, each channel of the resulting array
   * will have width * height * 2 bytes.
   */
  public static byte[][] getPixelBytes(BufferedImage img, boolean little,
    int x, int y, int w, int h)
  {
    return io.scif.gui.AWTImageTools.getPixelBytes(img, little, x, y, w, h);
  }

  /**
   * Return a 2D array of bytes representing the image.  If the transfer type
   * is something other than DataBuffer.TYPE_BYTE, then each pixel value is
   * converted to the appropriate number of bytes.  In other words, if we
   * are given an image with 16-bit data, each channel of the resulting array
   * will have width * height * 2 bytes.
   */
  public static byte[][] getPixelBytes(WritableRaster r, boolean little,
    int x, int y, int w, int h)
  {
    return io.scif.gui.AWTImageTools.getPixelBytes(r, little, x, y, w, h);
  }

  /**
   * Gets the pixel type of the given image.
   * @return One of the following types:<ul>
   *   <li>FormatReader.INT8</li>
   *   <li>FormatReader.UINT8</li>
   *   <li>FormatReader.INT16</li>
   *   <li>FormatReader.UINT16</li>
   *   <li>FormatReader.INT32</li>
   *   <li>FormatReader.UINT32</li>
   *   <li>FormatReader.FLOAT</li>
   *   <li>FormatReader.DOUBLE</li>
   *   <li>-1 (unknown type)</li>
   * </ul>
   */
  public static int getPixelType(BufferedImage image) {
    return io.scif.gui.AWTImageTools.getPixelType(image);
  }

  // -- Image conversion --

  // NB: The commented out makeType method below is broken in that it results
  // in rescaled data in some circumstances. We were using it for getBytes and
  // getShorts, but due to this problem we implemented a different solution
  // using Raster.getPixels instead. But we have left the makeType method here
  // in case we decide to explore this issue any further in the future.

  ///** Copies the given image into a result with the specified data type. */
  //public static BufferedImage makeType(BufferedImage image, int type) {
  //  WritableRaster r = image.getRaster();
  //  int w = image.getWidth(), h = image.getHeight(), c = r.getNumBands();
  //  ColorModel colorModel = makeColorModel(c, type);
  //  if (colorModel == null) return null;
  //
  //  int s = w * h;
  //  DataBuffer buf = null;
  //  if (type == DataBuffer.TYPE_BYTE) buf = new DataBufferByte(s, c);
  //  else if (type == DataBuffer.TYPE_USHORT) buf = new DataBufferUShort(s, c);
  //  else if (type == DataBuffer.TYPE_INT) buf = new DataBufferInt(s, c);
  //  else if (type == DataBuffer.TYPE_SHORT) buf = new DataBufferShort(s, c);
  //  else if (type == DataBuffer.TYPE_FLOAT) buf = new DataBufferFloat(s, c);
  //  else if (type == DataBuffer.TYPE_DOUBLE) buf = new DataBufferDouble(s, c);
  //  if (buf == null) return null;
  //
  //  SampleModel model = new BandedSampleModel(type, w, h, c);
  //  WritableRaster raster = Raster.createWritableRaster(model, buf, null);
  //  BufferedImage target = new BufferedImage(colorModel, raster, false, null);
  //  Graphics2D g2 = target.createGraphics();
  //  g2.drawRenderedImage(image, null);
  //  g2.dispose();
  //  return target;
  //}

  /**
   * Converts a java.awt.image.RenderedImage into a
   * java.awt.image.BufferedImage.
   *
   * This code was adapted from
   * <a href="http://www.jguru.com/faq/view.jsp?EID=114602">a jGuru post</a>.
   */
  public static BufferedImage convertRenderedImage(RenderedImage img) {
    return io.scif.gui.AWTImageTools.convertRenderedImage(img);
  }

  /** Get the bytes from an image, merging the channels as necessary. */
  public static byte[] getBytes(BufferedImage img, boolean separated) {
    return io.scif.gui.AWTImageTools.getBytes(img, separated);
  }

  /**
   * Converts the given BufferedImage into an image with unsigned pixel data.
   */
  public static BufferedImage makeUnsigned(BufferedImage img) {
    return io.scif.gui.AWTImageTools.makeUnsigned(img);
  }

  // -- Image manipulation --

  /** Returns a subimage of the specified image. */
  public static BufferedImage getSubimage(BufferedImage image,
    boolean littleEndian, int x, int y, int w, int h)
  {
    return io.scif.gui.AWTImageTools.getSubimage(image, littleEndian, x, y, w, h);
  }

  /** Splits the given multi-channel image into single-channel images. */
  public static BufferedImage[] splitChannels(BufferedImage image) {
    return io.scif.gui.AWTImageTools.splitChannels(image);
  }

  /** Merges the given images into a single multi-channel image. */
  public static BufferedImage mergeChannels(BufferedImage[] images) {
    return io.scif.gui.AWTImageTools.mergeChannels(images);
  }

  /**
   * Pads (or crops) the image to the given width and height.
   * The image will be centered within the new bounds.
   */
  public static BufferedImage padImage(BufferedImage img, int width, int height)
  {
    return io.scif.gui.AWTImageTools.padImage(img, width, height);
  }

  /** Perform autoscaling on the given BufferedImage. */
  public static BufferedImage autoscale(BufferedImage img) {
    return io.scif.gui.AWTImageTools.autoscale(img);
  }

  /**
   * Perform autoscaling on the given BufferedImage;
   * map min to 0 and max to 255.
   * If the BufferedImage has 8 bit data, then nothing happens.
   */
  public static BufferedImage autoscale(BufferedImage img, int min, int max) {
    return io.scif.gui.AWTImageTools.autoscale(img, min, max);
  }

  // -- Image scaling --

  /** Copies the source image into the target, applying scaling. */
  public static BufferedImage copyScaled(BufferedImage source,
    BufferedImage target, Object hint)
  {
    return io.scif.gui.AWTImageTools.copyScaled(source, target, hint);
  }

  /**
   * Scales the image using the Java2D API, with the resultant
   * image optimized for the given graphics configuration.
   */
  public static BufferedImage scale2D(BufferedImage image,
    int width, int height, Object hint, GraphicsConfiguration gc)
  {
    return io.scif.gui.AWTImageTools.scale2D(image, width, height, hint, gc);
  }

  /**
   * Scales the image using the Java2D API, with the
   * resultant image having the given color model.
   */
  public static BufferedImage scale2D(BufferedImage image,
    int width, int height, Object hint, ColorModel cm)
  {
    return io.scif.gui.AWTImageTools.scale2D(image, width, height, hint, cm);
  }

  /** Scales the image using the AWT Image API. */
  public static Image scaleAWT(BufferedImage source, int width,
    int height, int hint)
  {
    return io.scif.gui.AWTImageTools.scaleAWT(source, width, height, hint);
  }

  /**
   * Scales the image using the most appropriate API, with the resultant image
   * having the same color model as the original image.
   */
  public static BufferedImage scale(BufferedImage source,
    int width, int height, boolean pad)
  {
    return io.scif.gui.AWTImageTools.scale(source, width, height, pad);
  }

  // -- AWT images --

  /**
   * Creates a buffered image from the given AWT image object.
   * If the AWT image is already a buffered image, no new object is created.
   */
  public static BufferedImage makeBuffered(Image image) {
    return io.scif.gui.AWTImageTools.makeBuffered(image);
  }

  /**
   * Creates a buffered image possessing the given color model,
   * from the specified AWT image object. If the AWT image is already a
   * buffered image with the given color model, no new object is created.
   */
  public static BufferedImage makeBuffered(Image image, ColorModel cm) {
    return io.scif.gui.AWTImageTools.makeBuffered(image, cm);
  }

  /** Ensures the given AWT image is fully loaded. */
  public static boolean loadImage(Image image) {
    return io.scif.gui.AWTImageTools.loadImage(image);
  }

  /**
   * Gets the width and height of the given AWT image,
   * waiting for it to finish loading if necessary.
   */
  public static Dimension getSize(Image image) {
    return io.scif.gui.AWTImageTools.getSize(image);
  }

  // -- Graphics configuration --

  /**
   * Creates a buffered image compatible with the given graphics
   * configuration, using the given buffered image as a source.
   * If gc is null, the default graphics configuration is used.
   */
  public static BufferedImage makeCompatible(BufferedImage image,
    GraphicsConfiguration gc)
  {
    return io.scif.gui.AWTImageTools.makeCompatible(image, gc);
  }

  /** Gets the default graphics configuration for the environment. */
  public static GraphicsConfiguration getDefaultConfiguration() {
    return io.scif.gui.AWTImageTools.getDefaultConfiguration();
  }

  // -- Color model --

  /** Gets a color space for the given number of color components. */
  public static ColorSpace makeColorSpace(int c) {
    return io.scif.gui.AWTImageTools.makeColorSpace(c);
  }

  /** Gets a color model for the given number of color components. */
  public static ColorModel makeColorModel(int c, int dataType) {
    return io.scif.gui.AWTImageTools.makeColorModel(c, dataType);
  }

  // -- Indexed color conversion --

  /** Converts an indexed color BufferedImage to an RGB BufferedImage. */
  public static BufferedImage indexedToRGB(BufferedImage img, boolean le) {
    return io.scif.gui.AWTImageTools.indexedToRGB(img, le);
  }

  /** Converts an IndexColorModel to a 2D byte array. */
  public static byte[][] get8BitLookupTable(ColorModel model) {
    return io.scif.gui.AWTImageTools.get8BitLookupTable(model);
  }

  /** Convers an Index16ColorModel to a 2D short array. */
  public static short[][] getLookupTable(ColorModel model) {
    return io.scif.gui.AWTImageTools.getLookupTable(model);
  }

}
