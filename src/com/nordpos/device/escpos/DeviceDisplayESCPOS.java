//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.
package com.nordpos.device.escpos;

import com.nordpos.device.display.DeviceDisplaySerial;
import com.nordpos.device.util.StringUtils;
import com.nordpos.device.traslator.UnicodeTranslator;
import com.nordpos.device.writter.Writter;
import javax.swing.JComponent;

public class DeviceDisplayESCPOS extends DeviceDisplaySerial {

    private final UnicodeTranslator trans;

    private static final byte[] INIT = {0x1B, 0x40};
    private static final byte[] SELECT_DISPLAY = {0x1B, 0x3D, 0x02};

//    public static final byte[] VISOR_HIDE_CURSOR = {0x1F, 0x43, 0x00};
//    public static final byte[] VISOR_SHOW_CURSOR = {0x1F, 0x43, 0x01};

    public static final byte[] VISOR_HIDE_CURSOR = {0x1B, 0x5F, 0x00};
    public static final byte[] VISOR_SHOW_CURSOR = {0x1B, 0x5F, 0x01};

    public static final byte[] VISOR_HOME = {0x0B};
    public static final byte[] VISOR_CLEAR = {0x0C};

    public DeviceDisplayESCPOS(Writter display, UnicodeTranslator trans) {
        this.trans = trans;
        init(display);
    }

    @Override
    public void initVisor() {
        display.init(INIT);
        display.write(SELECT_DISPLAY); // Al visor
        display.write(trans.getCodeTable());
//        display.write(VISOR_HIDE_CURSOR);
        display.write(VISOR_CLEAR);
        display.write(VISOR_HOME);
        display.flush();
    }

    @Override
    public void repaintLines() {
        display.init(INIT);
        display.write(SELECT_DISPLAY);
        display.write(VISOR_CLEAR);
        display.write(VISOR_HOME);
        display.write(trans.transString(StringUtils.alignLeft(m_displaylines.getLine1(), 20)));
        display.write(trans.transString(StringUtils.alignLeft(m_displaylines.getLine2(), 20)));
        display.flush();
    }

    @Override
    public JComponent getDisplayComponent() {
        return null;
    }
}
