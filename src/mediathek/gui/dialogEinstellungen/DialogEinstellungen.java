/*    
 *    MediathekView
 *    Copyright (C) 2008   W. Xaver
 *    W.Xaver[at]googlemail.com
 *    http://zdfmediathk.sourceforge.net/
 *    
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package mediathek.gui.dialogEinstellungen;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import mediathek.MediathekGui;
import mediathek.daten.Daten;
import mediathek.gui.PanelVorlage;
import mediathek.gui.dialog.PanelAbout;
import mediathek.tool.EscBeenden;
import mediathek.tool.GuiFunktionen;
import mediathek.tool.Konstanten;
import mediathek.tool.Log;

public class DialogEinstellungen extends javax.swing.JFrame {

    Daten ddaten;
    public boolean ok = false;
    private PanelEinstellungen panelEinstellungen;
    private PanelEinstellungenErweitert panelEinstellungenErweitert;
    private PanelEinstellungenNetz panelEinstellungenNetz;
    private PanelFilmlisteLaden panelImportFilme;
    private PanelExportFilmliste panelExportFilmliste;
    private PanelBlacklist panelBlacklist;
    private PanelErledigteUrls panelErledigteAbos;
    private PanelErledigteUrls panelHistory;
    private PanelVorlage panelPset;
    private PanelPsetImport panelPsetVorlagen;
    // Infos
    private PanelAbout panelAbout;
    //private PanelInfoStarts panelStarts;
    private PanelMeldungenUbersicht panelMeldungenUbersicht;
    private PanelMeldungen panelMeldungenFehler;
    private PanelMeldungen panelMeldungenSystem;
    private PanelMeldungen panelMeldungenPlayer;
    private JPanel panelLeer = new JPanel();
    private Frame parentComponent;

    /**
     * @param parent
     * @param d
     */
    public DialogEinstellungen(java.awt.Frame parent, Daten d) {
        initComponents();
        setTitle("Einstellungen");
        parentComponent = parent;
        ddaten = d;
        init();
        initTree();
        GuiFunktionen.setSize(Konstanten.SYSTEM_GROESSE_EINSTELLUNGEN_NR, this, ddaten.mediathekGui);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(MediathekGui.class.getResource("/mediathek/res/MediathekView_k.gif")));
        jButtonBeenden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                beenden();
            }
        });
        new EscBeenden(this) {
            @Override
            public void beenden_() {
                beenden();
            }
        };
    }

    private void init() {
        panelEinstellungen = new PanelEinstellungen(ddaten, parentComponent);
        panelEinstellungenErweitert = new PanelEinstellungenErweitert(ddaten, parentComponent);
        panelEinstellungenNetz = new PanelEinstellungenNetz(ddaten, parentComponent);
        panelImportFilme = new PanelFilmlisteLaden(ddaten, parentComponent);
        panelExportFilmliste = new PanelExportFilmliste(ddaten, parentComponent);
        panelBlacklist = new PanelBlacklist(ddaten, parentComponent, PanelBlacklist.class.getName());
        panelHistory = new PanelErledigteUrls(ddaten, parentComponent);
        panelHistory.initHistory();
        panelErledigteAbos = new PanelErledigteUrls(ddaten, parentComponent);
        panelErledigteAbos.initAbo();
        panelPset = new PanelPset(ddaten, parentComponent);
        panelPsetVorlagen = new PanelPsetImport(ddaten, parentComponent);
        // Infos
        panelAbout = new PanelAbout(ddaten, parentComponent);
        //panelStarts = new PanelInfoStarts(ddaten);
        panelMeldungenUbersicht = new PanelMeldungenUbersicht(ddaten, parentComponent);
        panelMeldungenFehler = new PanelMeldungen(ddaten, parentComponent, Log.textFehler, Log.LOG_FEHLER, "Fehlermeldungen");
        panelMeldungenSystem = new PanelMeldungen(ddaten, parentComponent, Log.textSystem, Log.LOG_SYSTEM, "Systemmeldungen");
        panelMeldungenPlayer = new PanelMeldungen(ddaten, parentComponent, Log.textProgramm, Log.LOG_PLAYER, "Meldungen Hilfsprogramme");
    }

    private void initTree() {
        final String NAME_allgemeineEinstellungen = "Allgemein";
        final String NAME_allgemeineEinstellungenErweitert = "Erweitert";
        final String NAME_netzwerk = "Netzwerk";
        final String NAME_filmListeLaden = "Filmliste laden";
        final String NAME_filmListeExportieren = "Filmliste exportieren";
        final String NAME_blacklist = "Blacklist";
        final String NAME_programmset = "Set bearbeiten";
        final String NAME_programmsetImportieren = "Set importieren";
        // Infos
        final String NAME_programmInfos = "Programminfos";
        final String NAME_history = "History";
        final String NAME_logfile = "Erledigte Abos";
        final String NAME_logdatei = "Logdatei erstellen";
        final String NAME_systemmeldungen = "Systemmeldungen";
        final String NAME_fehlermeldungen = "Fehlermeldungen";
        final String NAME_meldungenProgramme = "Hilfsprogramme";
        //
        DefaultMutableTreeNode treeNodeStart = new DefaultMutableTreeNode(Konstanten.PROGRAMMNAME);
        // ===============================================================================
        // ######## Einstellulngen ############
        DefaultMutableTreeNode treeNodeEinstellungen = new DefaultMutableTreeNode("Einstellungen");
        // allgemeine Einstellungen
        DefaultMutableTreeNode treeNodeAllgemeineEinstellungen = new DefaultMutableTreeNode(NAME_allgemeineEinstellungen);
        treeNodeEinstellungen.add(treeNodeAllgemeineEinstellungen);
        // erweiterte Einstellungen
        DefaultMutableTreeNode treeNodeAllgemeineEinstellungenEreweitert = new DefaultMutableTreeNode(NAME_allgemeineEinstellungenErweitert);
        treeNodeEinstellungen.add(treeNodeAllgemeineEinstellungenEreweitert);
        // Netzwerk
        DefaultMutableTreeNode treeNodeNetzwerk = new DefaultMutableTreeNode(NAME_netzwerk);
        treeNodeEinstellungen.add(treeNodeNetzwerk);
        treeNodeStart.add(treeNodeEinstellungen);
        // ===============================================================================
        // ######## Filme ###############
        DefaultMutableTreeNode treeNodeFilme = new DefaultMutableTreeNode("Filmliste");
        DefaultMutableTreeNode treeNodeFilmliste = new DefaultMutableTreeNode(NAME_filmListeLaden);
        treeNodeFilme.add(treeNodeFilmliste);
        DefaultMutableTreeNode treeNodeFilmlisteExport = new DefaultMutableTreeNode(NAME_filmListeExportieren);
        treeNodeFilme.add(treeNodeFilmlisteExport);
        DefaultMutableTreeNode treeNodeBlacklist = new DefaultMutableTreeNode(NAME_blacklist);
        treeNodeFilme.add(treeNodeBlacklist);
        treeNodeStart.add(treeNodeFilme);
        // ===============================================================================
        // ########### Programme ##############
        DefaultMutableTreeNode treeNodeDownloads = new DefaultMutableTreeNode("Aufzeichnen und Abspielen");
        DefaultMutableTreeNode treeNodeProgramme = new DefaultMutableTreeNode(NAME_programmset);
        treeNodeDownloads.add(treeNodeProgramme);
        DefaultMutableTreeNode treeNodeImportProgramme = new DefaultMutableTreeNode(NAME_programmsetImportieren);
        treeNodeDownloads.add(treeNodeImportProgramme);
        treeNodeStart.add(treeNodeDownloads);
        // ===============================================================================
        // ####### Infos #########
        DefaultMutableTreeNode treeNodeInfos = new DefaultMutableTreeNode("Infos");
        DefaultMutableTreeNode treeNodeProgrammInfos = new DefaultMutableTreeNode(NAME_programmInfos);
        treeNodeInfos.add(treeNodeProgrammInfos);
        DefaultMutableTreeNode treeNodeHistory = new DefaultMutableTreeNode(NAME_history);
        treeNodeInfos.add(treeNodeHistory);
        DefaultMutableTreeNode treeNodeLogfile = new DefaultMutableTreeNode(NAME_logfile);
        treeNodeInfos.add(treeNodeLogfile);
        treeNodeStart.add(treeNodeInfos);
        // ===============================================================================
        // ############ Systemmeldungen ###############
        DefaultMutableTreeNode treeNodeSystem = new DefaultMutableTreeNode("Meldungen");
        DefaultMutableTreeNode treeNodeLogdatei = new DefaultMutableTreeNode(NAME_logdatei);
        treeNodeSystem.add(treeNodeLogdatei);
        DefaultMutableTreeNode treeNodeSystemmeldungen = new DefaultMutableTreeNode(NAME_systemmeldungen);
        treeNodeSystem.add(treeNodeSystemmeldungen);
        if (Daten.debug) {
            DefaultMutableTreeNode treeNodeFehlermeldungen = new DefaultMutableTreeNode(NAME_fehlermeldungen);
            treeNodeSystem.add(treeNodeFehlermeldungen);
        }
        DefaultMutableTreeNode treeNodeProgrammmeldungen = new DefaultMutableTreeNode(NAME_meldungenProgramme);
        treeNodeSystem.add(treeNodeProgrammmeldungen);
        treeNodeStart.add(treeNodeSystem);
        // Aufbauen
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNodeStart));
        jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree1.setRootVisible(false);
        jTree1.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
                if (node == null) {
                    // nix markiert
                    jPanelExtra.removeAll();
                    jPanelExtra.add(panelLeer);
                } else {
                    String name = node.getUserObject().toString();
                    setTitle(name);
                    if (name.equals(NAME_allgemeineEinstellungen)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelEinstellungen);
                    } else if (name.equals(NAME_allgemeineEinstellungenErweitert)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelEinstellungenErweitert);
                    } else if (name.equals(NAME_netzwerk)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelEinstellungenNetz);
                    } else if (name.equals(NAME_filmListeLaden)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelImportFilme);
                    } else if (name.equals(NAME_filmListeExportieren)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelExportFilmliste);
                    } else if (name.equals(NAME_blacklist)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelBlacklist);
                    } else if (name.equals(NAME_history)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelHistory);
                    } else if (name.equals(NAME_logfile)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelErledigteAbos);
                    } else if (name.equals(NAME_programmset)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelPset);
                    } else if (name.equals(NAME_programmsetImportieren)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelPsetVorlagen);
                    } else if (name.equals(NAME_programmInfos)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelAbout);
                    } else if (name.equals(NAME_logdatei)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelMeldungenUbersicht);
                    } else if (name.equals(NAME_systemmeldungen)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelMeldungenSystem);
                    } else if (name.equals(NAME_fehlermeldungen)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelMeldungenFehler);
                    } else if (name.equals(NAME_meldungenProgramme)) {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelMeldungenPlayer);
                    } else {
                        jPanelExtra.removeAll();
                        jPanelExtra.add(panelLeer);
                        setTitle("Programmeinstellungen");
                    }
                }
                jPanelExtra.updateUI();
            }
        });
        // und jetzt noch aufklappen
        for (int i = 0; i < jTree1.getRowCount(); ++i) {
            jTree1.expandRow(i);
        }
    }

    private void beenden() {
        ddaten.allesSpeichern();
        this.dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonBeenden = new javax.swing.JButton();
        javax.swing.JSplitPane jSplitPane1 = new javax.swing.JSplitPane();
        javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        jPanelExtra = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButtonBeenden.setText("Schließen");

        jSplitPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setOneTouchExpandable(true);

        jPanelExtra.setLayout(new java.awt.BorderLayout());
        jScrollPane2.setViewportView(jPanelExtra);

        jSplitPane1.setRightComponent(jScrollPane2);

        jTree1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jTree1.setRootVisible(false);
        jScrollPane1.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1068, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 965, Short.MAX_VALUE)
                        .addComponent(jButtonBeenden)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonBeenden)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBeenden;
    private javax.swing.JPanel jPanelExtra;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
