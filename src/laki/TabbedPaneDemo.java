/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package laki;

/*
 * TabbedPaneDemo.java requires one additional file:
 *   images/middle.gif.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class TabbedPaneDemo extends JPanel implements ItemListener, KeyListener, MouseListener {

    private JList MainList;
    javax.swing.JScrollPane scroll;
    int b_r = 13;
    int l_r = 20;
    int t_r = 13;
    /*
     jp = new panel(new java.awt.GridBagLayout());
     scroll = new javax.swing.JScrollPane(jp);
     scroll.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
     getContentPane().add(scroll,  gridBagConstraints);
     */
    int pos = 0;
    String[][] holderLaki;
    String[][] holderBaza;
    String[][] holderTop;
    ActionListener common_listener;
    JComboBox cmb;
    JPanel jp;
    JComboBox Laki[];
    JComboBox Baza[];
    JComboBox Top[];
    JComboBox Cur[];
    JComboBox Dop[];
    int[] show = {0, 0, 0, 0};
    Integer CurB;
    Integer Cursum[];
    String sql_table;
    JButton SaveLaki;
    JButton SaveBaza;
    JButton SaveTop;
    JButton SearchLaki;
    JButton SearchBaza;
    String curLabels[];
    JButton SearchTop;
    Color GlobColor = null;
    String[] Month = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    Integer[] Days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public String lakilabels[] = {"Фирма", "Серия", "Номер", "Цвет", "Текстура",
        "Финиш", "Стойкость", "Объем", "Цена", "Страна производства",
        "День покупки", "Месяц", "Год", "Дальнейшая судьба", "Объективная оценка", "Субъективная оценка", "Время использования", "Количество покрасов"
    };
    public String lakifiles[] = {"Firm", "LakiSeria", "LakiNumber", "LakiColor", "LakiTextura",
        "LakiFiller", "LakiPersist", "LakiVolume", "LakiPrice", "LakiCountry", "LakiDay", "Month", "Year",
        "LakiFate", "LakiObEv", "LakiSubEv", "LakiCountUse", "PokrasCount"
    };
    public String bazalabels[] = {"Фирма", "Цвет", "Лечебные свойства", "Объем",
        "Цена", "Страна производства", "День покупки", "Месяц", "Год", "Объективная оценка", "Субъективная оценка"
    };
    public String bazafiles[] = {"Firm", "LakiColor", "BazaMed", "BazaVolume",
        "TopPrice", "LakiCountry", "Day", "Month", "Year", "LakiObEv", "LakiSubEv"
    };
    public String toplabels[] = {"Фирма", "Эффект", "Стойкость", "Объем",
        "Цена", "Страна производства", "День покупки", "Месяц", "Год", "Объективная оценка", "Субъективная оценка"
    };
    public String topfiles[] = {"Firm", "TopEffect", "LakiPersist", "TopVolume",
        "TopPrice", "LakiCountry", "Day", "Month", "Year", "LakiObEv", "LakiSubEv"
    };
    private JMenuBar menuBar;
    private int curType;
    private GridBagConstraints gridBagConstraints;

    public TabbedPaneDemo() {

        super(new GridLayout(1, 1));
        setPreferredSize(new Dimension(1240, 780));
        setBackground(Color.CYAN);

        listener();
        // Dop=new array();
        Dop = new combos[30];
        for (int dp = 0; dp < 30; dp++) {
            Dop[dp] = null;
        }
     // panelLaki = makeSavePanel(1);
        //add(panelLaki);

       //! add(MainList);
        //The following line enables to use scrolling tabs.
        // panelLaki.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    protected JMenuBar getMenu() {

        JMenuBar myMenuBar = new JMenuBar();

        myMenuBar.setBackground(Color.CYAN);
        JMenu addMenu = new JMenu("Добавить");
        JMenu showMenu = new JMenu("Таблица");
        JMenu searchMenu = new JMenu("Поиск по параметрам");

        addMenu.add(makeMenuItem("Лак", "addLak", 0));
        addMenu.add(makeMenuItem("База", "addBaza", 0));
        addMenu.add(makeMenuItem("Топ", "addTop", 0));
        showMenu.add(makeMenuItem("Все", "0", 1));
        showMenu.add(makeMenuItem("Лак", "1", 1));
        showMenu.add(makeMenuItem("База", "2", 1));
        showMenu.add(makeMenuItem("Топ", "3", 1));
        searchMenu.add(makeMenuItem("Лак", "searchLak", 0));
        searchMenu.add(makeMenuItem("База", "searchBaza", 0));
        searchMenu.add(makeMenuItem("Топ", "searchTop", 0));
        myMenuBar.add(addMenu);
        myMenuBar.add(showMenu);
        myMenuBar.add(searchMenu);

        return myMenuBar;
    }

    protected JComponent makeMenuItem(String name, String property, int ch) {

        if (ch == 1) {
            final JMenuItem item2 = new JCheckBoxMenuItem(name);
            JPanel pane = this;
            item2.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    int index = Integer.valueOf(property);
                    if (item2.isSelected()) {
                        show[index] = 1;//ну и отрисовку откуда php вызывается
                        //javax.swing.JOptionPane.showMessageDialog(pane, property);

                    } else {
                        show[index] = 0;
                        // javax.swing.JOptionPane.showMessageDialog(pane,property+'0');
                    }
                    allShow();
                }
            });
            return item2;
        } else {
            JMenuItem item = new JMenuItem(name);

            item.putClientProperty("id", property);
            item.addActionListener(common_listener);

            return item;
        }
    }

    public void makeScroll(String[][] source, String[] labels, int row, JPanel jp) {
        this.removeAll();
        this.revalidate();
        // jp.setLayout(new GridLayout(2,2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.gridx = 0;

        gridBagConstraints.gridy = pos;
        String title = "";
        if (labels == lakilabels) {
            title = "Лаки";
        } else if (labels == bazalabels) {
            title = "Базы"; //javax.swing.JOptionPane.showMessageDialog(this,pos);}
        } else if (labels == toplabels) {
            title = "Топы";
        }
        gridBagConstraints.ipady = 73;
        jp.add(new JLabel(title), gridBagConstraints);
        pos++;
        gridBagConstraints.ipady = 0;
        //  gridBagConstraints.gridheight=GridBagConstraints.RELATIVE;
        JTextArea textArea;
        String labeltext = "";
   //   if(row==11) javax.swing.JOptionPane.showMessageDialog(this,source);

        for (int i = 0; i < source.length; i++, pos++) {
            System.out.println(source[0][3]);
            labeltext = "";
            int u;
            for (u = 1; u <= row; u++) {

                labeltext += labels[u - 1] + " : " + source[i][u] + "; ";

            }
            labeltext += " Цвет: " + source[i][u];
            textArea = new JTextArea(labeltext, 6, 70);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = pos;
            //jp.add(textArea,BorderLayout.LINE_START);
            jp.add(textArea, gridBagConstraints);
            JButton button = new JButton("Редактировать");
            button.putClientProperty("intId", i);
            if (labels == lakilabels) {
                button.putClientProperty("id", "lakiUpdate");
            } else if (labels == bazalabels) {
                button.putClientProperty("id", "bazaUpdate");
            } else if (labels == toplabels) {
                button.putClientProperty("id", "topUpdate");
            }
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = pos;
            jp.add(button, gridBagConstraints);//,BorderLayout.CENTER);
            button.addActionListener(common_listener);

        }

       // 
    }

    public void putUpdate(String type, int index) {
        JButton button = null;
        JPanel panel = null;
        String[][] curHolder = null;
        int pan = 0;
        switch (type) {
            case "lakiUpdate":
                panel = (JPanel) makePanel(1);
                curHolder = holderLaki;
                button = SaveLaki;

                break;
            case "bazaUpdate":
                panel = (JPanel) makePanel(2);
                curHolder = holderBaza;
                button = SaveLaki;
                pan = 2;
                break;
            case "topUpdate":
                panel = (JPanel) makePanel(3);
                curHolder = holderTop;
                button = SaveLaki;
                pan = 3;
                break;

        }

        this.removeAll();
        button.putClientProperty("id", "update");
        button.putClientProperty("dbid", curHolder[index][0]);// а знать по карренту
        this.add(panel);
        this.revalidate();
        for (int i = 0; i < Cur.length; i++) {
            Cur[i].setSelectedItem(curHolder[index][i + 1]);//  для кнопки написать. прорабовать в базе запись. цвет в конце ну так его и в Laki нет. а JColorChooser убрать
        }//                this.remove(cmb);

    }

    public int selShow() {

        pos = 0;
        jp = new JPanel(new java.awt.GridBagLayout());
       //jp.setLayout(new GridLayout(2,2));

        //  scroll.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);   
        java.util.ArrayList<String> ret = null;

        ret = php("search");
        if (ret.get(0).equals("null")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Поиск не дал результатов" + ret);
            return 1;
        }

        if (sql_table.equals("Kosmetics.laki2")) {

            System.out.println(1);
            System.out.println(ret);
            holderLaki = divider(ret, l_r);//больше чем в лейбах!!!

            makeScroll(holderLaki, lakilabels, (l_r - 2), jp);

        } else if (sql_table.equals("Kosmetics.baza2")) {

            System.out.println(2);
            System.out.println(ret);
            holderBaza = divider(ret, b_r);
            makeScroll(holderBaza, bazalabels, (b_r - 2), jp);

        } else if (sql_table.equals("Kosmetics.top2")) {

            System.out.println(3);
            System.out.println(ret);
            holderTop = divider(ret, t_r);
            makeScroll(holderTop, toplabels, (t_r - 2), jp);

        }
        scroll = new javax.swing.JScrollPane(jp);
        scroll.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scroll);
        revalidate();
        pos = 0;

//шоу панель получает название базы - возвращает из пхп_с_гетбейсом и рисует через лабелы - 
//цвета последние их можно потом и инсертить+а дальше массив батонов с индексами массива от возвращенных 
//из пхп которые на нарисованные мейкпэнел сетселектед (только кнопку удалить и другую поставить - для апдейта)
//ну и апдейт посмотреть как
        return 1;
    }

    public int allShow() {
        this.removeAll();
        this.revalidate();
        pos = 0;
        jp = new JPanel(new java.awt.GridBagLayout());
       //jp.setLayout(new GridLayout(2,2));

        //  scroll.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);   
        java.util.ArrayList<String> ret = null;
        //javax.swing.JOptionPane.showMessageDialog(this,ret);
        if (show[0] == 1) {
            ret = php("Kosmetics.laki2");
            System.out.println(1);
            System.out.println(ret);
            holderLaki = divider(ret, l_r);//больше чем в лейбах!!!

            makeScroll(holderLaki, lakilabels, (l_r - 2), jp);
            ret = php("Kosmetics.baza2");
            System.out.println(2);
            System.out.println(ret);
            holderBaza = divider(ret, b_r);
            makeScroll(holderBaza, bazalabels, (b_r - 2), jp);

            ret = php("Kosmetics.top2");
            System.out.println(3);
            System.out.println(ret);
            holderTop = divider(ret, t_r);
            makeScroll(holderTop, toplabels, (t_r - 2), jp);
            scroll = new javax.swing.JScrollPane(jp);
            scroll.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            this.add(scroll);
            revalidate();
            pos = 0;
            return 1;
        } else {
            if (show[1] == 1) {
                ret = php("Kosmetics.laki2");
                System.out.println(1);
                System.out.println(ret);
                holderLaki = divider(ret, l_r);//больше чем в лейбах!!!

                makeScroll(holderLaki, lakilabels, (l_r - 2), jp);

            }
            if (show[2] == 1) {

                ret = php("Kosmetics.baza2");
                System.out.println(2);
                System.out.println(ret);
                holderBaza = divider(ret, b_r);
                makeScroll(holderBaza, bazalabels, (b_r - 2), jp);

            }
            if (show[3] == 1) {
                ret = php("Kosmetics.top2");
                System.out.println(3);
                System.out.println(ret);
                holderTop = divider(ret, t_r);
                makeScroll(holderTop, toplabels, (t_r - 2), jp);

            }
            scroll = new javax.swing.JScrollPane(jp);
            scroll.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            this.add(scroll);
            revalidate();
            pos = 0;
        }

//шоу панель получает название базы - возвращает из пхп_с_гетбейсом и рисует через лабелы - 
//цвета последние их можно потом и инсертить+а дальше массив батонов с индексами массива от возвращенных 
//из пхп которые на нарисованные мейкпэнел сетселектед (только кнопку удалить и другую поставить - для апдейта)
//ну и апдейт посмотреть как
        return 1;
    }

    public void getColors(List<String> xString, List<Color> xColor) {
        File file;

        // Laki[i].addItemListener(this);
        file = new File("colors");

//Laki[9].addItem("Не указан");
//Если требуемого файла не существует.
        if (!file.exists()) {
            //Создаем его.
            try {
                file.createNewFile();
            } catch (java.io.IOException sqle) {

            }
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;

            while ((str = br.readLine()) != null) {

                String[] split = str.split("#");
                String strColor = "0x" + split[1];
                strColor = strColor.trim();
                Color color = Color.decode(strColor);
                xString.add(split[0]);
                xColor.add(color);
  //javax.swing.JOptionPane.showMessageDialog(this,split[0]+"-"+split[1]);

            }
            br.close();

        } catch (java.io.IOException sqle) {
        }

    }

    protected JComponent makePanel(int type) {
        curType = type;
      
        List<String> xString = new ArrayList<String>();
        List<Color> xColor = new ArrayList<Color>();
        Color colors[] = null;
        String items[] = null;
        cmb = null;

        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(21, 2, 10, 10));

        switch (type) {
            case 1:

                int i2;
                Integer[] sum1 = {7, 8, 10, 11, 14, 15, 16, 17};
                Cursum = sum1;
                sql_table = "Kosmetics.laki2";

                Laki = new combos[18];
                Cur = Laki;
                curLabels = lakilabels;
                CurB = 10;

                for (int i = 0; i <= 17; i++) {
                    Laki[i] = new combos(lakilabels[i], lakifiles[i]);//лейблс возможно не нужен.
                    Laki[i].putClientProperty("id", lakifiles[i]);

                    if (i != 10 && i != 11 && i != 12) {
                        Laki[i].getEditor().getEditorComponent().addKeyListener(this);//другой
                        Laki[i].setEditable(true);
                    } else {
                        Laki[i].addItemListener(this);
                    }
        //  if(i==11) for(i2=1989;i2<2016;i2++) Laki[i].addItem(i2);
                    //Laki[i].addItem("Не указан");  Laki[10].addItem("Не указан");
                    if (i == 11) {
                        Laki[i].addItem("Не указан");
                        Laki[10].addItem("Не указан");
                        for (i2 = 0; i2 < 12; i2++) {
                            Laki[i].addItem(Month[i2]);
                        }
                    }
                    if (i == 12) {
                        for (i2 = 1989; i2 < 2016; i2++) {
                            Laki[i].addItem(i2);
                        }
                    }

                    File file;

                    // Laki[i].addItemListener(this);
                    file = new File(lakifiles[i]);

//Laki[9].addItem("Не указан");
//Если требуемого файла не существует.
                    if (!file.exists()) {
                        //Создаем его.
                        try {
                            file.createNewFile();
                        } catch (java.io.IOException sqle) {

                        }
                    }
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String str;

                        while ((str = br.readLine()) != null) {
                            Laki[i].addItem(str);

                        }
                        br.close();
                        if (i == 3) {
                            getColors(xString, xColor);
                            colors = xColor.toArray(new Color[xColor.size()]);
                            items = xString.toArray(new String[xString.size()]);
                            cmb = new JComboBox(items);
                            ComboBoxRenderer renderer = new ComboBoxRenderer(cmb);

                            renderer.setColors(colors);
                            renderer.setStrings(items);

                            cmb.setRenderer(renderer);
    // javax.swing.JOptionPane.showMessageDialog(this,cmb);
                            // this.add(cmb);
                            // Laki[i]=(combos)cmb;

                        }
                    } catch (java.io.IOException sqle) {

                    }
                    if (i == 4) {
                        panel.add(new JLabel("Выбор цвета", SwingConstants.RIGHT));
                        JButton ColorChose = new JButton("Здесь");
                        ColorChose.putClientProperty("id", "ColorChose");
                        ColorChose.addActionListener(common_listener);
                        panel.add(ColorChose);
                     
                    }
                    panel.add(new JLabel(lakilabels[i], SwingConstants.RIGHT));
                    panel.add(Laki[i]);
                    if (i == 3) {
                        panel.add(new JLabel("Оттенок"));
                        panel.add(cmb);
                    }
                }
                SaveLaki = new JButton();
                SaveLaki.putClientProperty("id", "SaveLaki");
                SaveLaki.setText("Сохранить в базе");
                SaveLaki.addActionListener(common_listener);
                panel.add(SaveLaki);

                break;
            case 2:
                sql_table = "Kosmetics.baza2";
                Integer[] sum2 = {3, 4, 6, 7, 8, 9, 10};
                Cursum = sum2;
                curLabels = bazalabels;
                panel = (JPanel) panelMaker(2, 11, 6, 1, bazalabels, bazafiles, Baza, SaveBaza);
                break;

            case 3:
                sql_table = "Kosmetics.top2";
                curLabels = toplabels;
                Integer[] sum3 = {3, 4, 6, 7, 8, 9, 10};
                Cursum = sum3;

                panel = (JPanel) panelMaker(3, 11, 6, -1, toplabels, topfiles, Top, SaveTop);
                break;
            case 4:
                sql_table = "Kosmetics.laki2";
                curLabels = lakilabels;
                Integer[] sum4 = {7, 8, 10, 11, 12, 14, 15, 16, 17};
                Cursum = sum4;

                panel = (JPanel) panelMaker(4, 18, 10, 4, lakilabels, lakifiles, Laki, SearchLaki);
                break;
            case 5:
                sql_table = "Kosmetics.baza2";
                curLabels = bazalabels;
                Integer[] sum5 = {3, 4, 6, 7, 8, 9, 10};
                Cursum = sum5;

                panel = (JPanel) panelMaker(5, 11, 6, 1, bazalabels, bazafiles, Baza, SaveBaza);
                break;

            case 6:
                sql_table = "Kosmetics.top2";
                curLabels = toplabels;
                Integer[] sum6 = {3, 4, 6, 7, 8, 9, 10};
                Cursum = sum6;

                panel = (JPanel) panelMaker(6, 11, 6, -1, toplabels, topfiles, Top, SaveTop);
                break;
        }
        return panel;
    }

    public JComponent panelMaker(int type, int sum, int dateBegin, int colorBegin, String[] labels, String[] files, JComboBox[] comboz, JButton Save) {
        curType = type;
        List<String> xString = new ArrayList<String>();
        List<Color> xColor = new ArrayList<Color>();
        Color colors[] = null;
        String items[] = null;
        cmb = null;
     
        int localPos = 0;
        CurB = dateBegin;
        JPanel panel = new JPanel(new java.awt.GridBagLayout());
        /*panel.setLayout(new GridLayout(15,2,10,10));
         if (type==4 ) panel.setLayout(new GridLayout(30,2,1,1));
         else if (type==5 )panel.setLayout(new GridLayout(21,2,1,1));
         else if (type==6 )panel.setLayout(new GridLayout(21,2,1,1));*/
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weightx=0.1;
        gridBagConstraints.weighty=0.1;
        gridBagConstraints.insets=new Insets(3, 1, 1, 33);
     

       
        int i2;
        comboz = new combos[sum];
        Dop = new combos[sum];
        Cur = comboz;
        for (int i = 0; i <= (sum - 1); i++, localPos++) {
             gridBagConstraints.gridy = localPos;
            Dop[i] = null;
            comboz[i] = new combos(labels[i], files[i]);//лейблс возможно не нужен.
            if (type == 4 || type == 5 || type == 6) {
                comboz[i].addItem("Не выбрано");
            }
            if ((type == 4 || type == 5 || type == 6) && (Arrays.asList(Cursum).contains(i))) {
                Dop[i] = new combos(labels[i], files[i]);
                if (type == 4 || type == 5 || type == 6) {
                    Dop[i].addItem("Не выбрано");
                }
                Dop[i].putClientProperty("id", files[i]);
            }

            comboz[i].putClientProperty("id", files[i]);

            if (i != dateBegin && i != (dateBegin + 1) && i != (dateBegin + 2)) {
                comboz[i].getEditor().getEditorComponent().addKeyListener(this);//другой
                comboz[i].setEditable(true);
                if ((type == 4 || type == 5 || type == 6) && (Arrays.asList(Cursum).contains(i))) {
                    Dop[i].getEditor().getEditorComponent().addKeyListener(this);//другой
                    Dop[i].setEditable(true);
                }

            } else {
                if ((type == 4 || type == 5 || type == 6) && (Arrays.asList(Cursum).contains(i))) {
                    Dop[i].addItemListener(this);
                }
                comboz[i].addItemListener(this);
            }
        //  if(i==11) for(i2=1989;i2<2016;i2++) Laki[i].addItem(i2);

            if (i == (dateBegin + 1)) {
              //  comboz[i].addItem("Не указан");
                //comboz[dateBegin].addItem("Не указан");
                for (i2 = 0; i2 < 12; i2++) {
                    comboz[i].addItem(Month[i2]);
                }
                if ((type == 4 || type == 5 || type == 6) && (Arrays.asList(Cursum).contains(i))) {
                    //   Dop[i].addItem("Не указан");  Dop[dateBegin].addItem("Не указан");
                    for (i2 = 0; i2 < 12; i2++) {
                        Dop[i].addItem(Month[i2]);
                    }
                }
            }
            if (i == dateBegin + 2) {
                for (i2 = 1989; i2 < 2016; i2++) {
                    comboz[i].addItem(i2);
                }
                if ((type == 4 || type == 5 || type == 6) && (Arrays.asList(Cursum).contains(i))) {
                    for (i2 = 1989; i2 < 2016; i2++) {
                        Dop[i].addItem(i2);
                    }
                }

            }

            File file;

         // Laki[i].addItemListener(this);
            // if(!(type==4 && i==3) && !(type==2 && i==1) && !(type==5 && i==1)){ file = new File("colors");}    
            file = new File(files[i]);
//Laki[9].addItem("Не указан");
//Если требуемого файла не существует.
            if (!file.exists()) {
                //Создаем его.
                try {
                    file.createNewFile();
                } catch (java.io.IOException sqle) {

                }
            }
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String str;
                while ((str = br.readLine()) != null) {
                    comboz[i].addItem(str);
                    if (!(type == 4 && i == 3) && !(type == 2 && i == 1) && !(type == 5 && i == 1)) {

                        if ((type == 4 || type == 5 || type == 6) && (Arrays.asList(Cursum).contains(i))) {
                            Dop[i].addItem(str);
                        }
                    }

                }
                br.close();
            } catch (java.io.IOException sqle) {

            }
            if ((type == 4 && i == 3) || (type == 2 && i == 1) || (type == 5 && i == 1)) {
                getColors(xString, xColor);
                colors = xColor.toArray(new Color[xColor.size()]);
                items = xString.toArray(new String[xString.size()]);
                cmb = new JComboBox(items);
                ComboBoxRenderer renderer = new ComboBoxRenderer(cmb);

                renderer.setColors(colors);
                renderer.setStrings(items);

                cmb.setRenderer(renderer);
            }
            if (i == colorBegin) {
                gridBagConstraints.gridx = 0;
                

                panel.add(new JLabel("Выбор цвета", SwingConstants.RIGHT),gridBagConstraints);
                JButton ColorChose = new JButton("Здесь");
                ColorChose.putClientProperty("id", "ColorChose");
                ColorChose.addActionListener(common_listener);
                gridBagConstraints.gridx = 1;

                panel.add(ColorChose,gridBagConstraints);
                localPos++;
                 gridBagConstraints.gridy = localPos;
                
            }
            if ((type == 4 || type == 5 || type == 6) && (Arrays.asList(Cursum).contains(i))) {
                String txt = labels[i] + " от";
                gridBagConstraints.gridx = 0;
                panel.add(new JLabel(txt, SwingConstants.RIGHT),gridBagConstraints);
            } else {
                
gridBagConstraints.gridx = 0;
                panel.add(new JLabel(labels[i], SwingConstants.RIGHT),gridBagConstraints);
            }
            gridBagConstraints.gridx = 1;
            panel.add(comboz[i],gridBagConstraints);
            if ((type == 4 || type == 5 || type == 6) && (Arrays.asList(Cursum).contains(i))) {
                //  labels[i]="до";
                gridBagConstraints.gridx = 2;
               gridBagConstraints.anchor = GridBagConstraints.LINE_START;
               JLabel doo = new JLabel("до ", SwingConstants.RIGHT);
             doo.setSize(20, 20);
                panel.add(doo,gridBagConstraints);
                 gridBagConstraints.gridx = 3;
                 
                panel.add(Dop[i],gridBagConstraints);

            }
            if ((type == 4 && i == 3) || (type == 2 && i == 1) || (type == 5 && i == 1)) {
                 gridBagConstraints.gridx = 0;
                 localPos++;
                 gridBagConstraints.gridy = localPos;
                panel.add(new JLabel("Оттенок", SwingConstants.RIGHT),gridBagConstraints);
                 gridBagConstraints.gridx = 1;
                panel.add(cmb,gridBagConstraints);
               
            }
            
        }
        SaveLaki = new JButton();
        SaveLaki.setText("Сохранить в базе");
        if (type != 4 && type != 5 && type != 6) {
            SaveLaki.putClientProperty("id", "SaveLaki");
        } else {
            SaveLaki.putClientProperty("id", "SearchLaki");
            SaveLaki.setText("Отыскать в базе");
        }

        SaveLaki.addActionListener(common_listener);
        
                 gridBagConstraints.gridy = 0;
                  gridBagConstraints.gridheight=3;
           gridBagConstraints.gridx=3;  
          
        panel.add(SaveLaki,gridBagConstraints );
        return panel;
    }

    public void listener() {

        JPanel pane = this;
        common_listener = new java.awt.event.ActionListener() {
            JComponent panelLaki;

            public void actionPerformed(java.awt.event.ActionEvent evt) {

                JComponent u = (JComponent) evt.getSource();
                String id = (String) u.getClientProperty("id");
                 // javax.swing.JOptionPane.showMessageDialog(pane,id);
                //if(id==en.INPUT && evt.getActionCommand())

                switch (id) {
                    case "SaveLaki":
                        php("save");
                        break;
                    case "SearchLaki":
                        selShow();

                        break;
                    case "addLak":
                        panelLaki = makePanel(1);
                        pane.removeAll();
                        pane.add(panelLaki);
                        pane.revalidate();
                        break;
                    case "addBaza":
                        panelLaki = makePanel(2);
                        pane.removeAll();//А кстати
                        pane.add(panelLaki);

                        pane.revalidate();
                        break;
                    case "addTop":
                        panelLaki = makePanel(3);
                        pane.removeAll();//А кстати
                        pane.add(panelLaki);

                        pane.revalidate();
                        break;
                    case "searchLak":
                        panelLaki = makePanel(4);
                        pane.removeAll();//А кстати
                        pane.add(panelLaki);

                        pane.revalidate();
                        break;
                    case "searchBaza":
                        panelLaki = makePanel(5);
                        pane.removeAll();//А кстати
                        pane.add(panelLaki);

                        pane.revalidate();
                        break;
                    case "searchTop":
                        panelLaki = makePanel(6);
                        pane.removeAll();//А кстати
                        pane.add(panelLaki);

                        pane.revalidate();
                        break;
                    case "ColorChose":
                        Color MyColor = JColorChooser.showDialog(pane,
                                "Choose background color", Color.white);
                        if (MyColor != null) {
                            GlobColor = MyColor;

                            u.setBackground(MyColor);

                        } else {
                            GlobColor = Color.WHITE;
                        }
                        break;
                    case "lakiUpdate":
                    case "bazaUpdate":
                    case "topUpdate":
                        Integer index = (Integer) u.getClientProperty("intId");
                        String type = (String) u.getClientProperty("id");
                        putUpdate(type, index);
                        break;
                    case "update":
                        php("update");
                        javax.swing.JOptionPane.showMessageDialog(pane, "Запись изменена!");
                        break;
                }

            }
        };
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     *
     * @param path
     */
    /**
     * Returns an ImageIcon, or null if the path was invalid.
     *
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = TabbedPaneDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.

        JFrame frame = new JFrame("TabbedPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TabbedPaneDemo pane = new TabbedPaneDemo();
        JMenuBar myMenuBar = pane.getMenu();
      //JMenu myMenu = pane.getMenu();
        //myMenu.addItemListener(pane);
        //myMenuBar.add(myMenu);
        frame.setJMenuBar(myMenuBar);
        //Add content to the window.
        frame.add(pane, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        JComboBox sel = (JComboBox) e.getSource();
        String id = (String) sel.getClientProperty("id");

        Integer index = Cur[CurB + 1].getSelectedIndex();
        if ((id == "Month" || id == "Year") && index != 0) {
            Cur[CurB].removeAllItems();
            if (SaveLaki.getClientProperty("id").equals("SaveLaki")) {
                Cur[CurB].addItem("Не указан");
            } else {
                Cur[CurB].addItem("Не выбрано");
            }
            for (int i = 1; i <= Days[index - 1]; i++) {
                Cur[CurB].addItem(i);
                if (Dop[CurB] != null) {
                    Dop[CurB].addItem(i);
                }
            }
            Integer year = null;
            if (!Cur[CurB + 2].getSelectedItem().equals("Не выбрано")) {
                year = (Integer) Cur[CurB + 2].getSelectedItem();
            }
            // Integer year= Integer.parseInt(tmp); 
            if (index == 2 && year % 4 == 0) {
                Cur[CurB].addItem(29);
            }
            if (Dop[CurB] != null) {
                Dop[CurB].addItem(29);
            }
            revalidate();
        }

    }
   // javax.swing.JOptionPane.showMessageDialog(this,e.getItem()+(String)sel.getClientProperty("id"));

//JMenu myMenu = (JMenu) e.getSource();
//javax.swing.JOptionPane.showMessageDialog(this,myMenu.getText());
    //  System.out.println("Menu deselected: "+myMenu.getText());
    @Override
    public void keyTyped(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
       // int key = e.getKeyCode();
        // javax.swing.JOptionPane.showMessageDialog(this,key);
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        JComponent u = (JComponent) e.getSource();
        javax.swing.JTextField j = (javax.swing.JTextField) u;
        JComponent fl = (JComponent) e.getSource();
        JComboBox cb = (JComboBox) fl.getParent();
        String file = (String) cb.getClientProperty("id");

        if (key == KeyEvent.VK_ENTER) {

     //   JTextField tfListText=(JTextField)s.getEditor().getEditorComponent();
            //javax.swing.JOptionPane.showMessageDialog(this, j.getText());
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Добавить параметр " + j.getText() + "?", "Warning", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                    bw.write(j.getText());
                    bw.write("\r\n");
                    bw.close();
                } catch (java.io.IOException sqle) {
                }
                removeAll();
                add(makePanel(curType));
                revalidate();
            }
        } else if (key == KeyEvent.VK_F1) {

     //   JTextField tfListText=(JTextField)s.getEditor().getEditorComponent();
            //javax.swing.JOptionPane.showMessageDialog(this, j.getText());
            int dialogButton2 = JOptionPane.YES_NO_OPTION;
            int dialogResult2 = JOptionPane.showConfirmDialog(null, "Удалить параметр " + j.getText() + "?", "Warning", dialogButton2);
            if (dialogResult2 == JOptionPane.YES_OPTION) {

                File inputFile = new File(file);
                File tempFile = new File("myTempFile.txt");

                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(inputFile));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TabbedPaneDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(tempFile));
                } catch (IOException ex) {
                    Logger.getLogger(TabbedPaneDemo.class.getName()).log(Level.SEVERE, null, ex);
                }

                String lineToRemove = j.getText();

                String currentLine;

                try {
                    while ((currentLine = reader.readLine()) != null) {
                        // trim newline when comparing with lineToRemove
                        String trimmedLine = currentLine.trim();
                        if (trimmedLine.equals(lineToRemove)) {
                            continue;
                        }
                        writer.write(currentLine + System.getProperty("line.separator"));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TabbedPaneDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(TabbedPaneDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(TabbedPaneDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
                boolean successful = tempFile.renameTo(inputFile);
                /* try{
                 BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
                 bw.write(j.getText());
                 bw.write("\r\n");
                 bw.close();
                 }catch(java.io.IOException sqle){}*/

                removeAll();
                add(makePanel(curType));
                revalidate();

            }
        }
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //   javax.swing.JOptionPane.showMessageDialog(this,"2!");
        switch (e.getModifiers()) {

            case InputEvent.BUTTON1_MASK: {
                // System.out.println("That's the LEFT button");     
                break;
            }
            case InputEvent.BUTTON2_MASK: {
                //System.out.println("That's the MIDDLE button");     
                break;
            }
            case InputEvent.BUTTON3_MASK: {
                //System.out.println("That's the RIGHT button");     
                JComboBox sel = (JComboBox) e.getSource();
                // javax.swing.JOptionPane.showMessageDialog(this,sel.getSelectedItem()+"-"+sel.getClientProperty("id"));
                break;
            }
        }
    }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    @Override
    public void mousePressed(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class combos extends JComboBox {

        String file;
        String label;

        public combos(String file, String label) {
            this.file = file;
            this.label = label;
        }
    }

    public java.util.ArrayList<String> php(String arg) {
        int i;
        java.sql.Connection c;
        c = null;

        java.sql.PreparedStatement pst;
        java.sql.Statement stmt = null;
        java.sql.ResultSet prs;
        java.util.ArrayList<String> ret = new java.util.ArrayList<String>();
        //java.util.ArrayList<String><String> ret2 = new java.util.ArrayList<String><String>();
        //java.util.List<java.util.List<String>> ret_double = new java.util.ArrayList<>();
        try {

            Properties properties = new Properties();
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "UTF-8");
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/?"
                    + "user=root&password=1";
            c = DriverManager.getConnection(connectionUrl, properties);
            c.setAutoCommit(true);
            stmt = c.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS Kosmetics";
            stmt.executeUpdate(sql);
            if (arg.equals("save")) {
                sql = "create table IF NOT EXISTS " + sql_table + "(" + "id integer primary key auto_increment,";
                for (i = 0; i < Cur.length; i++) {
                    /*
                     {"Серия","Номер","Цвет", "Текстура",
                     "Филлер", "Стойкость","Объем","Цена","Страна производства",
                     "Дата покупки", "Дальнейшая судьба",  "Объективная оценка",  "Субъективная оценка",  "Время использования",
                     };
                     */

                    JComboBox item = Cur[i];

                    sql += item.getClientProperty("id");
                    // 
                    if (Arrays.asList(Cursum).contains(i)) {
                        sql += " integer, ";
                    } // else if(i==9) sql+=" date, ";
                    else {
                        sql += " text, ";
                    }
                }
                sql += "rgb integer";
                sql += ") CHARACTER SET utf8";
                stmt.executeUpdate(sql);
                javax.swing.JOptionPane.showMessageDialog(this, sql);

                sql = "INSERT INTO " + sql_table + "(";
                for (i = 0; i < Cur.length; i++) {

                    JComboBox item = Cur[i];
                    if (item.getSelectedItem() == null) {
                        javax.swing.JOptionPane.showMessageDialog(this, curLabels[i] + " - поле не может быть пустым");
                        return null;
                    }

                    sql += item.getClientProperty("id");
                    sql += ",";

                }
                sql += "rgb) VALUES(";
                for (i = 0; i < Cur.length; i++) {

                    JComboBox item = Cur[i];
                    sql += "'";
                    String id = item.getClientProperty("id").toString();
                    //  if(id.equals("LakiPrice")) javax.swing.JOptionPane.showMessageDialog(this,item.getSelectedItem());
                    if ((i == (CurB + 1) || i == CurB) && item.getSelectedItem().equals("Не указан")) {
                        sql += "-1";
                    } else if (i == (CurB + 1) || id.equals("LakiPrice") || id.equals("LakiSubEv")) {
                        sql += item.getSelectedIndex();
                    } else if (Arrays.asList(Cursum).contains(i) && !item.getSelectedItem().toString().matches("^-?\\d+$")) {
                        javax.swing.JOptionPane.showMessageDialog(this, curLabels[i] + "-поле должно быть числом а не " + item.getSelectedItem().toString());
                        return null;
                    } else {
                        sql += item.getSelectedItem();
                    }
                    sql += "'";
                    sql += ",";

                }
                if (GlobColor != null) {
                    sql += GlobColor.getRGB();
                } else {
                    sql += "0";
                }
                sql += ")";
                // stmt.executeUpdate(sql);
                javax.swing.JOptionPane.showMessageDialog(this, sql);

                pst = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//хм - а тут возможно перепишет
                pst.executeUpdate();
                c.close();
            } else if (arg.equals("update")) {
                sql = "UPDATE " + sql_table + " SET ";
                for (i = 0; i < Cur.length; i++) {

                    JComboBox item = Cur[i];
                    if (item.getSelectedItem() == null) {
                        javax.swing.JOptionPane.showMessageDialog(this, curLabels[i] + " - поле не может быть пустым");
                        return null;
                    }

                    sql += item.getClientProperty("id");
                    sql += " = ";

                    item = Cur[i];
                    sql += "'";
                    String id = item.getClientProperty("id").toString();
                    //  if(id.equals("LakiPrice")) javax.swing.JOptionPane.showMessageDialog(this,item.getSelectedItem());
                    if ((i == (CurB + 1) || i == CurB) && item.getSelectedItem().equals("Не указан")) {
                        sql += "-1";
                    } else if (i == (CurB + 1) || id.equals("LakiPrice") || id.equals("LakiSubEv")) {
                        sql += item.getSelectedIndex();
                    } else if (Arrays.asList(Cursum).contains(i) && !item.getSelectedItem().toString().matches("^-?\\d+$")) {
                        javax.swing.JOptionPane.showMessageDialog(this, curLabels[i] + "-поле должно быть числом а не " + item.getSelectedItem().toString());
                        return null;
                    } else {
                        sql += item.getSelectedItem();
                    }
                    sql += "'";
                    sql += ", ";

                }
                sql += "rgb=";
                if (GlobColor != null) {
                    sql += GlobColor.getRGB();
                } else {
                    sql += "0";
                }
                sql += " WHERE id=" + SaveLaki.getClientProperty("dbid");
    //sql+=")";
                // stmt.executeUpdate(sql);
                javax.swing.JOptionPane.showMessageDialog(this, sql);

                pst = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//хм - а тут возможно перепишет
                pst.executeUpdate();

                c.close();

            } else if (arg.equals("search")) {
                int localFirst = 0;
                int localMonth = 0;
                int localMonth2 = 0;
                sql = "SELECT * FROM " + sql_table;
                String sql2 = "SELECT * FROM " + sql_table;
                for (i = 0; i < Cur.length; i++) {

                    JComboBox item = Cur[i];
                    if (!"Не выбрано".equals(item.getSelectedItem())) { //) && !(localMonth==1 &&((i == (CurB + 1)) || (i == (CurB + 2))) )

                        if (localFirst == 0) {
                            localFirst = 1;
                            sql += " WHERE ";
                        } else if (!((i == CurB || i == (CurB + 1) || i == (CurB + 2)) && (localMonth2 == 2 || localMonth2 == 2))) {
                            sql += " AND ";
                        }

                        if (i == CurB && !Dop[i].getSelectedItem().equals("Не выбрано")
                                && !Dop[i + 1].getSelectedItem().equals("Не выбрано")
                                && !Dop[i + 2].getSelectedItem().equals("Не выбрано")) {
                            sql += " ((" + Dop[i + 2].getClientProperty("id") + "<" + Dop[i + 2].getSelectedItem() + ") OR ";
                            sql += " (" + Dop[i + 1].getClientProperty("id") + "<" + Dop[i + 1].getSelectedIndex() + " AND "
                                    + Dop[i + 2].getClientProperty("id") + "=" + Dop[i + 2].getSelectedItem() + ") OR ("
                                    + Dop[i].getClientProperty("id") + "<" + Dop[i].getSelectedItem() + " AND "
                                    + Dop[i + 1].getClientProperty("id") + "=" + Dop[i + 1].getSelectedIndex() + " AND "
                                    + Dop[i + 2].getClientProperty("id") + "=" + Dop[i + 2].getSelectedItem() + "))";
                            localMonth2 = 2;

                        } else if (i == CurB && !Dop[i + 1].getSelectedItem().equals("Не выбрано")
                                && !Dop[i + 2].getSelectedItem().equals("Не выбрано")) {
                            sql += " ((" + Dop[i + 2].getClientProperty("id") + "<" + Dop[i + 2].getSelectedItem() + ") OR ";
                            sql += " (" + Dop[i + 1].getClientProperty("id") + "<" + Dop[i + 1].getSelectedIndex() + " AND "
                                    + Dop[i + 2].getClientProperty("id") + "=" + Dop[i + 2].getSelectedItem() + "))";
                            localMonth2 = 2;

                        }

                        if (i == CurB && !Cur[i].getSelectedItem().equals("Не выбрано")
                                && !Cur[i + 1].getSelectedItem().equals("Не выбрано")
                                && !Cur[i + 2].getSelectedItem().equals("Не выбрано")) {
                            if (localMonth2 == 2) {
                                sql += " AND ";
                            }
                            sql += " ((" + Cur[i + 2].getClientProperty("id") + ">" + Cur[i + 2].getSelectedItem() + ") OR ";
                            sql += " (" + Cur[i + 1].getClientProperty("id") + ">" + Cur[i + 1].getSelectedIndex() + " AND "
                                    + Cur[i + 2].getClientProperty("id") + "=" + Cur[i + 2].getSelectedItem() + ") OR ("
                                    + Cur[i].getClientProperty("id") + ">" + Cur[i].getSelectedItem() + " AND "
                                    + Cur[i + 1].getClientProperty("id") + "=" + Cur[i + 1].getSelectedIndex() + " AND "
                                    + Cur[i + 2].getClientProperty("id") + "=" + Cur[i + 2].getSelectedItem() + "))";
                            localMonth = 1;

                        } else if (i == CurB && !Cur[i + 1].getSelectedItem().equals("Не выбрано")
                                && !Cur[i + 2].getSelectedItem().equals("Не выбрано")) {
                            if (localMonth2 == 2) {
                                sql += " AND ";
                            }
                            sql += " ((" + Cur[i + 2].getClientProperty("id") + ">" + Cur[i + 2].getSelectedItem() + ") OR ";
                            sql += " (" + Cur[i + 1].getClientProperty("id") + ">" + Cur[i + 1].getSelectedIndex() + " AND "
                                    + Cur[i + 2].getClientProperty("id") + "=" + Cur[i + 2].getSelectedItem() + "))";
                            localMonth = 1;

                        }

                        if (!((i == CurB || i == (CurB + 1) || i == (CurB + 2)) && localMonth == 1)) {
                            sql += item.getClientProperty("id");
                        }
                        if (Arrays.asList(Cursum).contains(i)) {
                            if (!((i == CurB || i == (CurB + 1) || i == (CurB + 2)) && localMonth == 1)) {
                                sql += ">=";

                                if (i != (CurB + 1)) {
                                    sql += "'" + item.getSelectedItem() + "'";
                                } else {
                                    sql += item.getSelectedIndex();
                                }
                            }
                            if (Dop[i] != null && !Dop[i].getSelectedItem().equals("Не выбрано")) {
                                if (!((i == CurB || i == (CurB + 1) || i == (CurB + 2)) && localMonth2 == 2)) {
         // javax.swing.JOptionPane.showMessageDialog(this, localMonth2+"-"+i);

                                    sql += " AND ";//
                                    sql += item.getClientProperty("id");
                                    sql += "<=";
                                    if (i != (CurB + 1)) {
                                        sql += Dop[i].getSelectedItem();
                                    } else {
                                        sql += Dop[i].getSelectedIndex();
                                    }
                                }
                            }
// if(i<(Cur.length-1)) sql+=" AND ";
                        } else {
                            sql += " = ";
                            sql += "'" + item.getSelectedItem() + "'";
                            // if(i<(Cur.length-1)) sql+=" AND ";  
                        }
                    }
                    /*if (Dop[i]!=null && !Dop[i].getSelectedItem().equals("Не выбрано")){
                     if(!( ( i==CurB ||i==(CurB+1) || i==(CurB+2) ) && localMonth2==2 ) ){
                     sql+=item.getClientProperty("id");
                     sql+="<=";
                     if(i!=(CurB+1)) sql+=Dop[i].getSelectedItem();//ошибка в курбе и анд
                     else sql+=Dop[i].getSelectedIndex();}
                     }*/
                }
                if (GlobColor != null && GlobColor.getRGB() != -1) {
                    if (localFirst != 0) {
                        sql += " AND ";
                    } else {
                        sql += " WHERE ";
                    }
                    sql += " rgb=";
                    sql += GlobColor.getRGB();
                }
 //  sql2="SELECT * FROM Kosmetics.laki2 WHERE firm='Golden Rose'";
                pst = c.prepareStatement(sql);
                javax.swing.JOptionPane.showMessageDialog(this, sql);
                prs = pst.executeQuery();
                int rows = 1;
                switch (sql_table) {
                    case "Kosmetics.laki2":
                        rows = l_r;
                        break;
                    case "Kosmetics.baza2":
                        rows = b_r;
                        break;
                    case "Kosmetics.top2":
                        rows = t_r;
                        break;

                }

                get_base(ret, prs, rows);

//тут какое то умолчание?- н
                //sql+=")";
                //  pst = c.prepareStatement("SELECT * FROM Kosmetics.laki");
//prs = pst.executeQuery();
//javax.swing.JOptionPane.showMessageDialog(this,prs.getString(0));
/* try{
    
                 String ar="";
                 while(prs.next()){
              
   
                 for(i=1;i<=18;i++) ar=ar+"-"+prs.getString(i);
                 // javax.swing.JOptionPane.showMessageDialog(this,prs.getString(i));
                 //    ret_double.add(prs.getString("book_id").toString());//,prs.getString("name"));
               
                 }
  
                 // javax.swing.JOptionPane.showMessageDialog(this,ar);
                 }catch (java.sql.SQLException sqle)
                 {
                 System.out.println(sqle);

                 }*/
                javax.swing.JOptionPane.showMessageDialog(this, sql);
            } else if (arg.equals("Kosmetics.laki2") || arg.equals("Kosmetics.baza2") || arg.equals("Kosmetics.top2")) {
                sql = "SELECT * FROM " + arg;
                pst = c.prepareStatement(sql);

                prs = pst.executeQuery();
                int rows = 1;
                switch (arg) {
                    case "Kosmetics.laki2":
                        rows = l_r;
                        break;
                    case "Kosmetics.baza2":
                        rows = b_r;
                        break;
                    case "Kosmetics.top2":
                        rows = t_r;
                        break;

                }
                get_base(ret, prs, rows);
            }
//pst.executeUpdate();
//ResultSet r=pst.getGeneratedKeys();
 /*       
             connectionUrl = "jdbc:mysql://localhost/SVOD";
             String user="root";
             String pass="1";
             c = DriverManager.getConnection(connectionUrl,user,pass);
             stmt = c.createStatement();
             sql= "create table IF NOT EXISTS SVOD. books("
             + "Book_id integer primary key auto_increment,"
             + "book_title text,"
             + "author_out_id integer,"
             + "book_type text,"
             + "sbornik text,"
             + "str text" + ");";
             stmt.executeUpdate(sql);*/
        //SQLiteDatabase db = glob.dbHelper.getWritableDatabase();
       /* if((args[0].get("aim")).equals("delete_book")){
             //db.delete("books","book_id ="+args[0].get("id"),null);
             pst = c.prepareStatement("DELETE   FROM books WHERE book_id =?");
             pst.setInt(1,(int) args[0].get("id"));
             pst.executeUpdate();



             }
             else if((args[0].get("aim")).equals("delete_author")){
             //db.delete("books","author_out_id ="+args[0].get("id"),null);
             pst = c.prepareStatement("DELETE   FROM books WHERE author_out_id =?");
             pst.setInt(1, (int) args[1].get("author_out_id"));//тут и так инт а просто айди в стрингвом массиве
             pst.executeUpdate();
             //db.delete("authors","author_id ="+args[0].get("id"),null);
             pst = c.prepareStatement("DELETE   FROM books WHERE author_id =?");
             pst.setInt(1, Integer.parseInt(args[0].get("id").toString()));
             pst.executeUpdate();
             }
             else if((args[0].get("aim")).equals("return_book"))
             {

             //cursor=db.query("books",null,"book_id ="+args[0].get("id"),null,null,null,null);
             pst = c.prepareStatement("SELECT *   FROM books WHERE book_id =?");
             pst.setInt(1, Integer.parseInt(args[0].get("id").toString()));
             prs = pst.executeQuery();
             //get_base(ret,prs);


             }
             else if((args[0].get("aim")).equals("return_author"))
             {


             //  cursor=db.query("authors",null,"author_id ="+args[0].get("id"),null,null,null,null);
          
             pst = c.prepareStatement("SELECT *  FROM authors WHERE author_id =?");
             //javax.swing.JOptionPane.showMessageDialog(getContentPane(),glob.check_author[glob.position][0]+"pos"+Integer.parseInt(args[0].get("id").toString()));
             pst.setInt(1, Integer.parseInt(args[0].get("id").toString()));
             prs = pst.executeQuery();
             //get_base(ret,prs);
             //  cursor=db.query("books",null,"author_out_id ="+args[0].get("id"),null,null,null,null);
             pst = c.prepareStatement("SELECT *  FROM books WHERE author_out_id =?");//хм - а тут возможно перепишет
             pst.setInt(1, Integer.parseInt(args[0].get("id").toString()));
             prs = pst.executeQuery();
             //get_base(ret,prs);
             //javax.swing.JOptionPane.showMessageDialog(getContentPane(),ret);        

             }
        
             else if((args[0].get("aim")).equals("check_author_books"))
             {
            

             // rs=db.query("books",null,"author_out_id =? and book_title=?",new String[]{args[0].get("author").toString(),args[0].get("title").toString()},null,null,null);


             //cursor=db.query("books",null,"book_title=?",new String[]{args[0].get("title").toString()},null,null,null);
             pst = c.prepareStatement("SELECT *  FROM books WHERE author_out_id =? AND book_title=?");//хм - а тут возможно перепишет
             pst.setString(1, args[0].get("author").toString());
             pst.setString(2, args[0].get("title").toString());
             prs = pst.executeQuery();
             // get_base(ret,prs);
             //javax.swing.JOptionPane.showMessageDialog(getContentPane(),ret);
             /*
             Log.i("preret",preret.get(2));
             int tmp;
             for(tmp=2;tmp<preret.size();tmp=tmp+6){
             cursor=db.query("books",new String[]{"book_id", "book_title", "book_type"},"author_out_id="+preret.get(tmp),null,null,null,null);
             ret.addAll(BaseGetter(cursor));//потом разбить по 12

             cursor.close();
             }
             */

            /* }
             else if((args[0].get("aim")).equals("check_author"))
             {


             //cursor=db.query("authors",null,"surname =? and name=?",new String[] {args[0].get("surname").toString(),args[0].get("name").toString()},null,null,null);
             pst = c.prepareStatement("SELECT *  FROM authors WHERE surname =? AND name=?");//хм - а тут возможно перепишет
             pst.setString(1, args[0].get("surname").toString());
             pst.setString(2, args[0].get("name").toString());
             prs = pst.executeQuery();

             //get_base(ret,prs);


             }
             else if((args[0].get("aim")).equals("update_author"))
             {
        


             //    db.update("authors",args[0],"author_id ="+id,null);//если будет ругаться на айди придется во второй лисинг все кроме него
             pst = c.prepareStatement("UPDATE authors SET name=?, surname=?,workplace=?,stepen=?, zvanie=? WHERE author_id=?");//хм - а тут возможно перепишет
	

             pst.setString(1, args[0].get("name").toString());
             pst.setString(2, args[0].get("surname").toString());
             pst.setString(3, args[0].get("workplace").toString());
             pst.setString(4, args[0].get("stepen").toString());
             pst.setString(5, args[0].get("zvanie").toString());
             pst.setInt(6, (int) args[0].get("id"));
             pst.executeUpdate();


             }
             else if((args[0].get("aim")).equals("update_book"))
             {
          
             //  alert("ok"+id);


             //db.update("books",args[1],"book_id ="+id,null);//тут надо внимательно буктайтл и буктайп также как далее и в скрибе

             pst = c.prepareStatement("UPDATE books SET book_title=?, sbornik=?,str=?,book_type=? WHERE book_id=?");//хм - а тут возможно перепишет
	

             pst.setString(1, args[1].get("book_title").toString());
             pst.setString(2, args[1].get("sbornik").toString());
             pst.setString(3, args[1].get("str").toString());
             pst.setString(4, args[1].get("book_type").toString());
             pst.setInt(5, (int)args[0].get("id"));

             pst.executeUpdate();


             }
        
             else if((args[0].get("aim")).equals("scribe"))
             {

   
             long inc=0;
             String name=(String) args[0].get("name");
             String book_title=(String) args[1].get("book_title");//тут один

             if(name.indexOf("existo-")==-1){//name!!!

             // inc=db.insert("authors",null,args[0]);//с правильными названиями- СКОРЕЕ ВСЕГО НАДО БУДЕТ РАЗБИТЬ НА ДВЕ ЧАСТИ
             ///  args[0].put("author_out_id",inc);//тут лонг String strLong = Long.toString(longNumber);String s = String.valueOf(date);
             pst = c.prepareStatement("INSERT INTO authors(name,surname,workplace,stepen,zvanie) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);//хм - а тут возможно перепишет
	

             pst.setString(1, args[0].get("name").toString());
             pst.setString(2, args[0].get("surname").toString());
             pst.setString(3, args[0].get("workplace").toString());
             pst.setString(4, args[0].get("stepen").toString());
             pst.setString(5, args[0].get("zvanie").toString());

             pst.executeUpdate();
             ResultSet r=pst.getGeneratedKeys();
             if (r.next()) args[1].put("author_out_id",r.getInt(1));

             }
             //тут надо внимательно буктайтл и буктайп также как далее и в скрибе
             if(book_title.indexOf("existo-")==-1){
             //{
             if(name.indexOf("existo-")!=-1)
             {
             // Log.i("inc","scribe");
             String[] split = name.split("-");

             args[1].put("author_out_id",split[1]);//тут стринга аутор оут айди и так не существует-тут 1
             pst = c.prepareStatement("INSERT INTO books(book_title,author_out_id,book_type,sbornik,str) VALUES (?,?,?,?,?)");//хм - а тут возможно перепишет
	

             pst.setString(1, args[1].get("book_title").toString());
             pst.setInt(2, (int) Integer.parseInt(args[1].get("author_out_id").toString()));
             pst.setString(3, args[1].get("book_type").toString());
             pst.setString(4, args[1].get("sbornik").toString());
             pst.setString(5, args[1].get("str").toString());



             pst.executeUpdate();

             }

             else
             {
             
            

         
             // inc=db.insert("books",null,args[1]);
             pst = c.prepareStatement("INSERT INTO books(book_title,author_out_id,sbornik,str,book_type) VALUES (?,?,?,?,?)");//хм - а тут возможно перепишет
	
             pst.setString(1, args[1].get("book_title").toString());
             pst.setInt(2, (int) args[1].get("author_out_id"));
             pst.setString(3, args[1].get("book_type").toString());
             pst.setString(4, args[1].get("sbornik").toString());
             pst.setString(5, args[1].get("str").toString());


             pst.executeUpdate();
             }
             }

             }



             else if((args[0].get("aim")).equals("tips"))
             {


             String poisk= args[0].get("data").toString().replace("'", "''");
             //  alert(String.valueOf(poisk)+"&");
             switch((Integer) args[0].get("pos")){
             case 1:
             String where="SELECT * FROM authors where surname like ('%"+poisk+"%')";
             java.sql.Statement st = c.createStatement();
             java.sql.ResultSet rs = st.executeQuery(where);

             while(rs.next()){
                         
             ret.add(rs.getString("surname")+" "+rs.getString("name"));
               
             }
                
             break;
             case 2:
             // javax.swing.JOptionPane.showMessageDialog(getContentPane(),"2!!!");
             // alert(String.valueOf(poisk)+"&@");
             String where2="SELECT * FROM books where book_title like ('%"+poisk+"%')";
                    
             java.sql.Statement st2 = c.createStatement();
             java.sql.ResultSet rs2 = st2.executeQuery(where2);
             while(rs2.next()){
                         
             ret.add(rs2.getString("book_title"));
               
             }

             break;


             }
             }
        
             */
        } catch (java.sql.SQLException sqle) {
            System.out.println(sqle);
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: " + cE.toString());
        } finally {
            //Обязательно необходимо закрыть соединение
            try {
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return ret;
    }

    public void get_base(java.util.ArrayList<String> ret, java.sql.ResultSet prs, int rows) {
        try {
            if (!prs.isBeforeFirst()) {
                ret.add("null");
            }
            while (prs.next()) {

                int i;
                for (i = 1; i <= rows; i++) {
                    ret.add(prs.getString(i));
                }
                //    ret_double.add(prs.getString("book_id").toString());//,prs.getString("name"));

            }

        } catch (java.sql.SQLException sqle) {
            System.out.println(sqle);
            javax.swing.JOptionPane.showMessageDialog(this, sqle);
        }

    }

    public String[][] divider(java.util.ArrayList<String> victim, int divider) {

        int i, i2, i3;

        String[][] ret = new String[victim.size() / divider][divider];
        //  javax.swing.JOptionPane.showMessageDialog(this,victim.size()+"-"+divider);
        for (i = 0, i2 = 0; i < victim.size(); i = i + divider, i2++) {
            for (i3 = 0; i3 < divider; i3++) {
                ret[i2][i3] = victim.get(i + i3);
            }
        }

        return ret;
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }
}
