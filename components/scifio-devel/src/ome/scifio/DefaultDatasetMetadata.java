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
package ome.scifio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import net.imglib2.meta.Axes;
import net.imglib2.meta.AxisType;
import ome.scifio.util.FormatTools;

/**
 * Default implementation of {@link DatasetMetadata}.
 * <p>
 * Serves as a collection of {@link DefaultImageMetadata} objects.
 * </p>
 * 
 * @author Mark Hiner
 *
 */
public class DefaultDatasetMetadata extends AbstractMetadata 
  implements DatasetMetadata {

  // -- Fields --

  /** Contains metadata key, value pairs for this dataset */
  private Hashtable<String, Object> datasetMeta;

  /** Contains a list of metadata objects for each image in this dataset */
  @Field(label = "imageMeta", isList = true)
  private List<DefaultImageMetadata> imageMeta;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // -- Constructors --

  public DefaultDatasetMetadata() {
    this(null);
  }

  public DefaultDatasetMetadata(final SCIFIO ctx) {
    super(ctx);
    datasetMeta = new Hashtable<String, Object>();
    imageMeta = new ArrayList<DefaultImageMetadata>();
  }
  
  public DefaultDatasetMetadata(final List<ImageMetadata> list, final SCIFIO ctx) {
    this(ctx);
    
    for(int i = 0; i < list.size(); i++) {
      ImageMetadata core = list.get(i);
      imageMeta.add(new DefaultImageMetadata(core));
    }
  }
  
  public DefaultDatasetMetadata(final DatasetMetadata copy, final SCIFIO ctx) {
    super(ctx);
    
    datasetMeta = (Hashtable<String, Object>) copy.getDatasetMetadata().clone();
    imageMeta = new ArrayList<DefaultImageMetadata>();
    
    for(int i = 0; i < copy.getImageCount(); i++) {
      ImageMetadata core = copy.get(i);
      imageMeta.add(new DefaultImageMetadata(core));
    }
  }

  // -- Getters --

  public Object getMetadataValue(final String field) {
    return datasetMeta.get(field);
  }

  public Object getImageMetadataValue(final int imageIndex, final String field)
  {
    return imageMeta.get(imageIndex).getImageMetadata().get(field);
  }

  public Hashtable<String, Object> getDatasetMetadata() {
    return datasetMeta;
  }

  public Hashtable<String, Object> getImageMetadata(final int imageIndex) {
    return imageMeta.get(imageIndex).getImageMetadata();
  }
  
  public DefaultImageMetadata get(int imageIndex) {
    return imageMeta.get(imageIndex);
  }

  public int getImageCount() {
    return imageMeta.size();
  }

  public int getPlaneCount(final int imageIndex) {
    return imageMeta.get(imageIndex).getPlaneCount();
  }

  public boolean isInterleaved(final int imageIndex) {
    return imageMeta.get(imageIndex).isInterleaved();
  }

  public int getPixelType(final int imageIndex) {
    return imageMeta.get(imageIndex).getPixelType();
  }

  public int getEffectiveSizeC(final int imageIndex) {
    final int sizeZT =
      getAxisLength(imageIndex, Axes.Z) * getAxisLength(imageIndex, Axes.TIME);
    if (sizeZT == 0) return 0;
    return getPlaneCount(imageIndex) / sizeZT;
  }

  public int getRGBChannelCount(final int imageIndex) {
    if(!isRGB(imageIndex)) return 1;
    
    final int effSizeC = getEffectiveSizeC(imageIndex);
    if (effSizeC == 0) return 0;
    return getAxisLength(imageIndex, Axes.CHANNEL) / effSizeC;
  }

  public boolean isLittleEndian(final int imageIndex) {
    return imageMeta.get(imageIndex).isLittleEndian();
  }

  public boolean isIndexed(final int imageIndex) {
    return imageMeta.get(imageIndex).isIndexed();
  }

  public int getBitsPerPixel(final int imageIndex) {
    return imageMeta.get(imageIndex).getBitsPerPixel();
  }

  public boolean isRGB(final int imageIndex) {
    return imageMeta.get(imageIndex).isRGB();
  }

  public boolean isFalseColor(final int imageIndex) {
    return imageMeta.get(imageIndex).isFalseColor();
  }

  public int[] getChannelDimLengths(final int imageIndex) {
    if (imageMeta.get(imageIndex).getChannelLengths() == null)
      return new int[] {getAxisLength(imageIndex, Axes.CHANNEL)};
    return imageMeta.get(imageIndex).getChannelLengths();
  }

  public String[] getChannelDimTypes(final int imageIndex) {
    if (imageMeta.get(imageIndex).getChannelTypes() == null)
      return new String[] {FormatTools.CHANNEL};
    return imageMeta.get(imageIndex).getChannelTypes();
  }

  public int getThumbSizeX(final int imageIndex) {
    if (imageMeta.get(imageIndex).getThumbSizeX() == 0) {
      final int sx = getAxisLength(imageIndex, Axes.X);
      final int sy = getAxisLength(imageIndex, Axes.Y);
      int thumbSizeX = 0;
      if (sx > sy) thumbSizeX = FormatTools.THUMBNAIL_DIMENSION;
      else if (sy > 0) thumbSizeX = sx * FormatTools.THUMBNAIL_DIMENSION / sy;
      if (thumbSizeX == 0) thumbSizeX = 1;
      return thumbSizeX;
    }
    return imageMeta.get(imageIndex).getThumbSizeX();
  }

  public int getThumbSizeY(final int imageIndex) {
    if (imageMeta.get(imageIndex).getThumbSizeX() == 0) {
      final int sx = getAxisLength(imageIndex, Axes.X);
      final int sy = getAxisLength(imageIndex, Axes.Y);
      int thumbSizeY = 1;
      if (sy > sx) thumbSizeY = FormatTools.THUMBNAIL_DIMENSION;
      else if (sx > 0) thumbSizeY = sy * FormatTools.THUMBNAIL_DIMENSION / sx;
      if (thumbSizeY == 0) thumbSizeY = 1;
      return thumbSizeY;
    }
    return imageMeta.get(imageIndex).getThumbSizeY();
  }

  /**
   * Returns the number of axes (planes) in the
   * specified image.
   * 
   * @param imageIndex - index for multi-image files
   * @return The axis/plane count
   */
  public int getAxisCount(final int imageIndex) {
    return imageMeta.get(imageIndex).getAxisLengths().length;
  }

  /**
   * Gets the type of the (zero-indexed) specified plane.
   * 
   * @param imageIndex - index for multi-image files
   * @param planeIndex - index of the desired plane within the specified image
   * @return Type of the desired plane.
   */
  public AxisType getAxisType(final int imageIndex, final int planeIndex) {
    return imageMeta.get(imageIndex).getAxisType(planeIndex);
  }

  /**
   * Gets the length of the (zero-indexed) specified plane.
   * 
   * @param imageIndex - index for multi-image files
   * @param planeIndex - index of the desired plane within the specified image
   * @return Length of the desired plane.
   */
  public int getAxisLength(final int imageIndex, final int planeIndex) {
    return imageMeta.get(imageIndex).getAxisLength(planeIndex);
  }
  
  /**
   * A convenience method for looking up the length of an axis
   * based on its type. No knowledge of plane ordering is necessary.
   * 
   * @param imageIndex - index for multi-image files
   * @param t - desired axis type
   * @return
   */
  public int getAxisLength(final int imageIndex, final AxisType t) {
    return imageMeta.get(imageIndex).getAxisLength(t);
  }

  /**
   * Returns the array index for the specified AxisType. This index
   * can be used in other Axes methods for looking up lengths, etc...
   * </br></br>
   * This method can also be used as an existence check for the
   * targe AxisType.
   * 
   * @param imageIndex - index for multi-image files
   * @param type - axis type to look up
   * @return The index of the desired axis or -1 if not found.
   */
  public int getAxisIndex(final int imageIndex, final AxisType type) {
    return imageMeta.get(imageIndex).getAxisIndex(type);
  }
  
  /**
   * Returns an array of the types for axes associated with
   * the specified image index. Order is consistent with the
   * axis length (int) array returned by 
   * {@link DatasetMetadata#getAxesLengths(int)}.
   * </br></br>
   * AxisType order is sorted and represents order within the image.
   * 
   * @param imageIndex - index for multi-image sources
   * @return An array of AxisTypes in the order they appear.
   */
  public AxisType[] getAxes(int imageIndex) {
    return imageMeta.get(imageIndex).getAxes();
  }
  
  /**
   * Returns an array of the lengths for axes associated with
   * the specified image index.
   * 
   * Ordering is consistent with the 
   * AxisType array returned by {@link DatasetMetadata#getAxes(int)}.
   * 
   * @param imageIndex
   * @return
   */
  public int[] getAxesLengths(int imageIndex) {
    return imageMeta.get(imageIndex).getAxesLengths();
  }

  /**
   * Appends the provided AxisType to the current AxisType array
   * and creates corresponding length = 0 entry in the axis lengths
   * array.
   * 
   * @param imageIndex
   * @param type
   */
  public void addAxis(final int imageIndex, final AxisType type) {
    imageMeta.get(imageIndex).addAxis(type);
  }

  /**
   * Appends the provided AxisType to the current AxisType array
   * and creates a corresponding entry with the specified value in
   * axis lengths.
   * 
   * @param imageIndex
   * @param type
   * @param value
   */
  public void addAxis(final int imageIndex, final AxisType type, final int value)
  {
    imageMeta.get(imageIndex).addAxis(type, value);
  }

  public boolean isOrderCertain(final int imageIndex) {
    return imageMeta.get(imageIndex).isOrderCertain();
  }

  public boolean isThumbnailImage(final int imageIndex) {
    return imageMeta.get(imageIndex).isThumbnail();
  }

  public boolean isMetadataComplete(final int imageIndex) {
    return imageMeta.get(imageIndex).isMetadataComplete();
  }

  // -- Setters --
  
  public void putDatasetMeta(String key, Object value) {
    datasetMeta.put(key, value);
  }
  
  public void putImageMeta(final int imageIndex, String key, Object value) {
    imageMeta.get(imageIndex).getImageMetadata().put(key, value);
  }

  public void setThumbSizeX(final int imageIndex, final int thumbX) {
    imageMeta.get(imageIndex).setThumbSizeX(thumbX);
  }

  public void setThumbSizeY(final int imageIndex, final int thumbY) {
    imageMeta.get(imageIndex).setThumbSizeY(thumbY);
  }

  public void setPixelType(final int imageIndex, final int type) {
    imageMeta.get(imageIndex).setPixelType(type);
  }

  public void setBitsPerPixel(final int imageIndex, final int bpp) {
    imageMeta.get(imageIndex).setBitsPerPixel(bpp);
  }

  public void setChannelDimLengths(final int imageIndex, final int[] cLengths) {
    imageMeta.get(imageIndex).setChannelLengths(cLengths);
  }

  public void setChannelDimTypes(final int imageIndex, final String[] cTypes) {
    imageMeta.get(imageIndex).setChannelTypes(cTypes);
  }

  public void setOrderCertain(final int imageIndex, final boolean orderCertain)
  {
    imageMeta.get(imageIndex).setOrderCertain(orderCertain);
  }

  public void setRGB(final int imageIndex, final boolean rgb) {
    imageMeta.get(imageIndex).setRGB(rgb);
  }

  public void setLittleEndian(final int imageIndex, final boolean littleEndian)
  {
    imageMeta.get(imageIndex).setLittleEndian(littleEndian);
  }

  public void setInterleaved(final int imageIndex, final boolean interleaved) {
    imageMeta.get(imageIndex).setInterleaved(interleaved);
  }

  public void setIndexed(final int imageIndex, final boolean indexed) {
    imageMeta.get(imageIndex).setIndexed(indexed);
  }

  public void setFalseColor(final int imageIndex, final boolean falseC) {
    imageMeta.get(imageIndex).setFalseColor(falseC);
  }

  public void setMetadataComplete(final int imageIndex,
    final boolean metadataComplete)
  {
    imageMeta.get(imageIndex).setMetadataComplete(metadataComplete);
  }

  public void setImageMetadata(final int imageIndex,
    final Hashtable<String, Object> meta)
  {
    imageMeta.get(imageIndex).setImageMetadata(meta);
  }

  public void setThumbnailImage(final int imageIndex, final boolean thumbnail) {
    imageMeta.get(imageIndex).setThumbnail(thumbnail);
  }

  public void setAxisTypes(final int imageIndex, final AxisType[] axisTypes) {
    imageMeta.get(imageIndex).setAxisTypes(axisTypes);
  }
  
  public void setAxisType(final int imageIndex, final int axisIndex, final AxisType axis) {
    imageMeta.get(imageIndex).setAxisType(axisIndex, axis);
  }

  public void setAxisLengths(final int imageIndex, final int[] axisLengths) {
    imageMeta.get(imageIndex).setAxisLengths(axisLengths);
  }
  
  public void setAxisLength(final int imageIndex, final AxisType axis, final int length) {
    imageMeta.get(imageIndex).setAxisLength(axis, length);
  }
  
  // -- Helper Methods --

  public void reset() {
    super.reset(this.getClass());
    datasetMeta = new Hashtable<String, Object>();
    imageMeta = new ArrayList<DefaultImageMetadata>();
  }
  
  public void add(final DefaultImageMetadata meta) {
    imageMeta.add(meta);
  }
  
  public Collection<DefaultImageMetadata> getImageMetadata() {
    return Collections.unmodifiableCollection(imageMeta);
  }

  /*
  public boolean isSingleFile() {
    return this.size() <= 1;
  }
  
  public boolean hasCompanionFiles() {
    return false;
  }
  */
}