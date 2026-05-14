package uz.ncipro.calendar.plaf.basic;

import java.text.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboPopup;
import uz.ncipro.calendar.*;

/**
 * <p>Title: BasicJDateTimePickerPopup</p>
 * <p>Copyright: Copyright (c) 2001-2003</p>
 * <p>Company: NCI Projects</p>
 * @author Vadim Crits
 * @version 1.1
 */

public class BasicJDateTimePickerPopup extends BasicComboPopup
{
  protected JCalendar popupEditor;

  public BasicJDateTimePickerPopup(JComboBox comboBox, JCalendar popupEditor)
  {
    super(comboBox);
    this.popupEditor = popupEditor;
    setFocusEnabled(popupEditor, false);
    add(popupEditor);
  }

  protected void setFocusEnabled(Component component, boolean flag)
  {
    if (component == null)
      return;
    if (component instanceof JComponent)
    {
      JComponent jcomponent = (JComponent)component;
      if (flag != jcomponent.isRequestFocusEnabled())
        jcomponent.setRequestFocusEnabled(flag);
    }
    if (component instanceof Container)
    {
      Component components[] = ((Container)component).getComponents();
      for (int i = 0; i < components.length; i++)
        setFocusEnabled(components[i], flag);
    }
  }

  protected void configurePopup()
  {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorderPainted(true);
    setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),
                                                 BorderFactory.createEmptyBorder(8, 8, 8, 8)));
    setOpaque(false);
    setDoubleBuffered(true);
    setRequestFocusEnabled(false);
  }

  public void show()
  {
    if (popupEditor == null)
      return;
    syncPopupDataWithPickerData();
    popupEditor.setPreferredSize(null);
    Dimension popupSize = popupEditor.getPreferredSize();
    popupSize.setSize(popupSize.width, popupSize.width + 16);
    Rectangle popupBounds = computePopupBounds(0, comboBox.getBounds().height,
                                               popupSize.width, popupSize.height);
    popupEditor.setMaximumSize(popupBounds.getSize());
    popupEditor.setPreferredSize(popupBounds.getSize());
    popupEditor.setMinimumSize(popupBounds.getSize());
    setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
    show(comboBox, popupBounds.x, popupBounds.y);
  }

  void syncPopupDataWithPickerData()
  {
    Locale l = comboBox.getLocale();
    if (!popupEditor.getLocale().equals(l))
      popupEditor.setLocale(l);
    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.applyPattern(((JDateTimePicker)comboBox).getDisplayFormat());
    if (!sdf.format(popupEditor.getDate()).equals(comboBox.getSelectedItem()))
      comboBox.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
  }
}