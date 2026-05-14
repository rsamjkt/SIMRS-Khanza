package uz.ncipro.calendar.plaf.metal;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;
import uz.ncipro.calendar.plaf.basic.BasicJDateTimePickerPopup;
import uz.ncipro.calendar.JCalendar;

/**
 * <p>Title: MetalJDateTimePickerUI</p>
 * <p>Copyright: Copyright (c) 2001-2003</p>
 * <p>Company: NCI Projects</p>
 * @author Vadim Crits
 * @version 1.1
 */

public class MetalJDateTimePickerUI extends MetalComboBoxUI
{
  protected JCalendar popupEditor;

  public static ComponentUI createUI(JComponent c)
  {
    return new MetalJDateTimePickerUI();
  }

  protected ComboPopup createPopup()
  {
    popupEditor = new JCalendar();
    BasicJDateTimePickerPopup popup = new BasicJDateTimePickerPopup(comboBox, popupEditor);
    popup.getAccessibleContext().setAccessibleParent(comboBox);
    return popup;
  }

  public JCalendar getPopupEditor()
  {
    return popupEditor;
  }
}