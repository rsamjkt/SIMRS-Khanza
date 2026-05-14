package uz.ncipro.calendar;

import java.awt.Image;
import java.beans.SimpleBeanInfo;

/**
 * <p>Title: JDateTimePickerBeanInfo</p>
 * <p>Copyright: Copyright (c) 2001-2003</p>
 * <p>Company: NCI Projects</p>
 * @author Vadim Crits
 * @version 1.1
 */

public class JDateTimePickerBeanInfo extends SimpleBeanInfo
{
  /**
   * A 16x16 color icon for the JavaBean.
   */
  Image iconColor16x16;

  /**
   * A 32x32 color icon for the JavaBean.
   */
  Image iconColor32x32;

  /**
   * A 16x16 monochromatic icon for the JavaBean.
   */
  Image iconMono16x16;

  /**
   * A 32x32 monochromatic icon for the JavaBean.
   */
  Image iconMono32x32;

  public JDateTimePickerBeanInfo()
  {
    iconColor16x16 = loadImage("images/JDateTimePickerColor16.gif");
    iconColor32x32 = loadImage("images/JDateTimePickerColor32.gif");
    iconMono16x16 = loadImage("images/JDateTimePickerMono16.gif");
    iconMono32x32 = loadImage("images/JDateTimePickerMono32.gif");
  }

  public Image getIcon(int iconKind)
  {
    switch (iconKind)
    {
      case ICON_COLOR_16x16: return iconColor16x16;
      case ICON_COLOR_32x32: return iconColor32x32;
      case ICON_MONO_16x16: return iconMono16x16;
      case ICON_MONO_32x32: return iconMono32x32;
    }
    return null;
  }
}