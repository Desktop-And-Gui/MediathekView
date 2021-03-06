package mediathek.gui.dialogEinstellungen;

import mediathek.config.Daten;
import mediathek.config.Icons;
import mediathek.config.MVColor;
import mediathek.config.MVConfig;
import mediathek.gui.messages.FilmListImportTypeChangedEvent;
import mediathek.mainwindow.MediathekGui;
import mediathek.tool.*;
import net.engio.mbassy.listener.Handler;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@SuppressWarnings("serial")
public class PanelFilmlisteLaden extends JPanel {
    private final Daten daten;

    public PanelFilmlisteLaden(Daten d) {
        super();
        daten = d;

        daten.getMessageBus().subscribe(this);

        initComponents();
        init();

        final var config = ApplicationConfiguration.getConfiguration();
        cbSign.setSelected(config.getBoolean(ApplicationConfiguration.FILMLIST_LOAD_SIGNLANGUAGE,true));
        cbSign.addActionListener(e -> config.setProperty(ApplicationConfiguration.FILMLIST_LOAD_SIGNLANGUAGE,cbSign.isSelected()));

        cbAudio.setSelected(config.getBoolean(ApplicationConfiguration.FILMLIST_LOAD_AUDIODESCRIPTION,true));
        cbAudio.addActionListener(e -> config.setProperty(ApplicationConfiguration.FILMLIST_LOAD_AUDIODESCRIPTION,cbAudio.isSelected()));

        cbTrailer.setSelected(config.getBoolean(ApplicationConfiguration.FILMLIST_LOAD_TRAILER,true));
        cbTrailer.addActionListener(e -> config.setProperty(ApplicationConfiguration.FILMLIST_LOAD_TRAILER,cbTrailer.isSelected()));
    }

    private void init() {
        initRadio();

        final var filmeLaden = daten.getFilmeLaden();
        jButtonLoad.addActionListener(ae -> filmeLaden.loadFilmlist(""));

        jButtonDateiAuswaehlen.setIcon(Icons.ICON_BUTTON_FILE_OPEN);
        jButtonDateiAuswaehlen.addActionListener(new BeobPfad());

        jButtonFilmeLaden.addActionListener(e -> {
            if (jCheckBoxUpdate.isSelected())
                filmeLaden.updateFilmlist(jTextFieldUrl.getText());
            else
                filmeLaden.loadFilmlist(jTextFieldUrl.getText());
        });

        jRadioButtonManuell.addActionListener(new BeobOption());
        jRadioButtonAuto.addActionListener(new BeobOption());
        jTextFieldUrl.getDocument().addDocumentListener(new BeobDateiUrl());
        TextCopyPasteHandler handler = new TextCopyPasteHandler<>(jTextFieldUrl);
        jTextFieldUrl.setComponentPopupMenu(handler.getPopupMenu());
    }

    @Handler
    private void handleFilmListImportTypeChanged(FilmListImportTypeChangedEvent e) {
        SwingUtilities.invokeLater(this::initRadio);
    }

    private void initRadio() {
        switch (GuiFunktionen.getImportArtFilme()) {
            case MANUAL:
                jRadioButtonManuell.setSelected(true);
                break;

            case AUTOMATIC:
                jRadioButtonAuto.setSelected(true);
                break;
        }

        jTextFieldUrl.setText(MVConfig.get(MVConfig.Configs.SYSTEM_IMPORT_URL_MANUELL));
        setPanelTabelle(jRadioButtonManuell.isSelected());
    }

    private void setPanelTabelle(boolean manuell) {
        if (manuell) {
            jTextAreaManuell.setBackground(MVColor.FILMLISTE_LADEN_AKTIV.color);
            jTextAreaAuto.setBackground(null);
        } else {
            jTextAreaManuell.setBackground(null);
            jTextAreaAuto.setBackground(MVColor.FILMLISTE_LADEN_AKTIV.color);
        }
    }

    private class BeobOption implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (jRadioButtonManuell.isSelected())
                GuiFunktionen.setImportArtFilme(FilmListUpdateType.MANUAL);
            else
                GuiFunktionen.setImportArtFilme(FilmListUpdateType.AUTOMATIC);

            daten.getMessageBus().publishAsync(new FilmListImportTypeChangedEvent());
        }
    }

    private class BeobPfad implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //we can use native chooser on Mac...
            if (SystemUtils.IS_OS_MAC_OSX) {
                FileDialog chooser = new FileDialog(MediathekGui.ui(), "Filmliste laden");
                chooser.setMode(FileDialog.LOAD);
                chooser.setVisible(true);
                if (chooser.getFile() != null) {
                    try {
                        File destination = new File(chooser.getDirectory() + chooser.getFile());
                        jTextFieldUrl.setText(destination.getAbsolutePath());
                    } catch (Exception ex) {
                        Log.errorLog(102036579, ex);
                    }
                }
            } else {
                int returnVal;
                JFileChooser chooser = new JFileChooser();
                if (!jTextFieldUrl.getText().isEmpty()) {
                    chooser.setCurrentDirectory(new File(jTextFieldUrl.getText()));
                }
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setFileHidingEnabled(false);
                returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        jTextFieldUrl.setText(chooser.getSelectedFile().getAbsolutePath());
                    } catch (Exception ex) {
                        Log.errorLog(733025319, ex);
                    }
                }
            }
        }
    }

    private class BeobDateiUrl implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            turnOnManualImport();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            turnOnManualImport();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            turnOnManualImport();
        }

        private void turnOnManualImport() {
            MVConfig.add(MVConfig.Configs.SYSTEM_IMPORT_URL_MANUELL, jTextFieldUrl.getText());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Generated using JFormDesigner non-commercial license
    private void initComponents() {
        var jPanelAuto = new JPanel();
        jTextAreaAuto = new JTextArea();
        jButtonLoad = new JButton();
        var jPanelManuel = new JPanel();
        var jLabel1 = new JLabel();
        jTextFieldUrl = new JTextField();
        jButtonDateiAuswaehlen = new JButton();
        jButtonFilmeLaden = new JButton();
        jTextAreaManuell = new JTextArea();
        jCheckBoxUpdate = new JCheckBox();
        jRadioButtonAuto = new JRadioButton();
        jRadioButtonManuell = new JRadioButton();
        var jPanel1 = new JPanel();
        cbSign = new JCheckBox();
        cbTrailer = new JCheckBox();
        cbAudio = new JCheckBox();

        //======== this ========
        setMinimumSize(new Dimension(746, 400));
        setPreferredSize(new Dimension(746, 400));
        setLayout(new MigLayout(
            new LC().insets("5").hideMode(3).gridGap("5", "5"), //NON-NLS
            // columns
            new AC()
                .fill().gap()
                .grow().fill(),
            // rows
            new AC()
                .fill().gap()
                .fill().gap()
                .fill()));

        //======== jPanelAuto ========
        {
            jPanelAuto.setBorder(new TitledBorder("Die Filmliste automatisch laden")); //NON-NLS
            jPanelAuto.setLayout(new MigLayout(
                new LC().insets("5").hideMode(3).gridGap("5", "5"), //NON-NLS
                // columns
                new AC()
                    .grow().fill().gap()
                    .grow().fill(),
                // rows
                new AC()
                    .fill().gap()
                    .fill()));

            //---- jTextAreaAuto ----
            jTextAreaAuto.setEditable(false);
            jTextAreaAuto.setColumns(20);
            jTextAreaAuto.setRows(3);
            jTextAreaAuto.setText("Die Filmliste wird beim Programmstart automatisch geladen (wenn sie \u00e4lter als 3h ist). Zus\u00e4tzlich kann sie \u00fcber den Button \"Neue Filmliste laden\" aktualisiert werden. Zum Update werden dann nur noch die Differenzlisten geladen (enthalten nur die neuen Filme)."); //NON-NLS
            jTextAreaAuto.setMargin(new Insets(4, 4, 4, 4));
            jTextAreaAuto.setWrapStyleWord(true);
            jTextAreaAuto.setLineWrap(true);
            jTextAreaAuto.setFont(jTextAreaAuto.getFont().deriveFont(jTextAreaAuto.getFont().getSize() - 1f));
            jPanelAuto.add(jTextAreaAuto, new CC().cell(0, 0, 2, 1));

            //---- jButtonLoad ----
            jButtonLoad.setText("Filme jetzt laden"); //NON-NLS
            jPanelAuto.add(jButtonLoad, new CC().cell(1, 1).alignX("trailing").growX(0)); //NON-NLS
        }
        add(jPanelAuto, new CC().cell(1, 0));

        //======== jPanelManuel ========
        {
            jPanelManuel.setBorder(new TitledBorder("Filmliste nur manuell laden")); //NON-NLS
            jPanelManuel.setLayout(new MigLayout(
                new LC().insets("5").hideMode(3).gridGap("5", "5"), //NON-NLS
                // columns
                new AC()
                    .fill().gap()
                    .grow().fill().gap()
                    .fill().gap()
                    .fill(),
                // rows
                new AC()
                    .fill().gap()
                    .fill().gap()
                    .fill()));

            //---- jLabel1 ----
            jLabel1.setText("URL/Datei:"); //NON-NLS
            jPanelManuel.add(jLabel1, new CC().cell(0, 1));
            jPanelManuel.add(jTextFieldUrl, new CC().cell(1, 1, 2, 1));

            //---- jButtonDateiAuswaehlen ----
            jButtonDateiAuswaehlen.setIcon(new ImageIcon(getClass().getResource("/mediathek/res/muster/button-file-open.png"))); //NON-NLS
            jButtonDateiAuswaehlen.setToolTipText("URL oder lokale Filmliste ausw\u00e4hlen"); //NON-NLS
            jPanelManuel.add(jButtonDateiAuswaehlen, new CC().cell(3, 1).alignX("left").growX(0).width("32:32:32").height("32:32:32")); //NON-NLS

            //---- jButtonFilmeLaden ----
            jButtonFilmeLaden.setText("Filme jetzt laden"); //NON-NLS
            jPanelManuel.add(jButtonFilmeLaden, new CC().cell(2, 2, 2, 1));

            //---- jTextAreaManuell ----
            jTextAreaManuell.setEditable(false);
            jTextAreaManuell.setColumns(20);
            jTextAreaManuell.setRows(2);
            jTextAreaManuell.setText("Die Filmliste wird nur manuell \u00fcber den Button \"Neue Filmliste laden\" geladen. Es wird dann dieser Dialog angezeigt und es kann eine URL/Datei zum Laden angegeben werden."); //NON-NLS
            jTextAreaManuell.setMargin(new Insets(4, 4, 4, 4));
            jTextAreaManuell.setWrapStyleWord(true);
            jTextAreaManuell.setLineWrap(true);
            jTextAreaManuell.setFont(jTextAreaManuell.getFont().deriveFont(jTextAreaManuell.getFont().getSize() - 1f));
            jPanelManuel.add(jTextAreaManuell, new CC().cell(0, 0, 4, 1));

            //---- jCheckBoxUpdate ----
            jCheckBoxUpdate.setText("alte Filmliste nicht l\u00f6schen, nur erweitern"); //NON-NLS
            jPanelManuel.add(jCheckBoxUpdate, new CC().cell(0, 2, 2, 1));
        }
        add(jPanelManuel, new CC().cell(1, 1));
        add(jRadioButtonAuto, new CC().cell(0, 0).alignY("top").growY(0)); //NON-NLS
        add(jRadioButtonManuell, new CC().cell(0, 1).alignY("top").growY(0)); //NON-NLS

        //======== jPanel1 ========
        {
            jPanel1.setBorder(new TitledBorder("Zus\u00e4tzliche Filmdaten laden")); //NON-NLS
            jPanel1.setToolTipText("<html>Alle nicht angew\u00e4hlten Eintr\u00e4ge werden beim Laden der Filmliste aus dem Endergebnis herausgefiltert.<br/><b>Die Eintr\u00e4ge werden dauerhaft aus der lokalen Filmliste entfernt.</b><br/>Sie werden erst wieder beim Laden einer neuen Liste vom Server hinzugef\u00fcgt wenn die Einstellungen entsprechend angepasst wurden.</html>"); //NON-NLS
            jPanel1.setLayout(new MigLayout(
                new LC().insets("5").hideMode(3).gridGap("5", "5"), //NON-NLS
                // columns
                new AC()
                    .fill().gap()
                    .fill().gap()
                    .fill(),
                // rows
                new AC()
                    .fill()));

            //---- cbSign ----
            cbSign.setText("Geb\u00e4rdensprache"); //NON-NLS
            jPanel1.add(cbSign, new CC().cell(2, 0));

            //---- cbTrailer ----
            cbTrailer.setText("Trailer/Teaser/Vorschau"); //NON-NLS
            jPanel1.add(cbTrailer, new CC().cell(0, 0));

            //---- cbAudio ----
            cbAudio.setText("H\u00f6rfassungen"); //NON-NLS
            jPanel1.add(cbAudio, new CC().cell(1, 0));
        }
        add(jPanel1, new CC().cell(1, 2));

        //---- buttonGroup1 ----
        var buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(jRadioButtonAuto);
        buttonGroup1.add(jRadioButtonManuell);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JTextArea jTextAreaAuto;
    private JButton jButtonLoad;
    private JTextField jTextFieldUrl;
    private JButton jButtonDateiAuswaehlen;
    private JButton jButtonFilmeLaden;
    private JTextArea jTextAreaManuell;
    private JCheckBox jCheckBoxUpdate;
    private JRadioButton jRadioButtonAuto;
    private JRadioButton jRadioButtonManuell;
    private JCheckBox cbSign;
    private JCheckBox cbTrailer;
    private JCheckBox cbAudio;
    // End of variables declaration//GEN-END:variables
}
