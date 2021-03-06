package org.coode.owlviz.command;

import org.coode.owlviz.ui.OWLVizIcons;
import org.coode.owlviz.ui.OWLVizViewI;
import org.coode.owlviz.ui.exportwizard.SelectFormatPage;
import org.coode.owlviz.ui.exportwizard.SpecifyFileNamePage;
import org.coode.owlviz.ui.exportwizard.SpecifyHierarchyPage;
import org.coode.owlviz.util.graph.export.ExportFormat;
import org.coode.owlviz.util.graph.ui.GraphComponent;
import org.coode.owlviz.util.wizard.Wizard;
import org.coode.owlviz.util.wizard.WizardPage;
import org.protege.editor.owl.ui.view.OWLSelectionViewAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 20, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ExportCommand extends OWLSelectionViewAction {



    private Wizard wizard;

    private SelectFormatPage formatPage;

    private SpecifyFileNamePage fileNamePage;

    private SpecifyHierarchyPage hierarchyPage;

    private OWLVizViewI view;

    public ExportCommand(OWLVizViewI view) {
        super("Export", OWLVizIcons.getIcon(OWLVizIcons.EXPORT_ICON));
        this.view = view;
        this.putValue(AbstractAction.SHORT_DESCRIPTION, "Export To Image");
    }

    private void createWizard() {
        wizard = new Wizard("Export Image");
        hierarchyPage = new SpecifyHierarchyPage(view);
        formatPage = new SelectFormatPage();
        fileNamePage = new SpecifyFileNamePage();
        wizard.setPages(new WizardPage[]{hierarchyPage, formatPage, fileNamePage});
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        export();
    }


    /**
     * Pops up the export Wizard, and gathers data.  If
     * the Wizard returns an 'approve' option then
     * the export process is executed.
     */
    protected void export() {
        createWizard();
        if (wizard.showWizard() == Wizard.OPTION_APPROVE) {
            // The user elected to export the graph
            // to an image file.  Get the format
            // and the file name.
            ExportFormat format = formatPage.getSelectedExportFormat();
            String fileName = fileNamePage.getFileName();

            // Create an export stream based on the
            //specified file name.
            OutputStream os = createOutputStream(fileName);
            if (os != null && format != null) {
                doExport(os, format);
                try {
                    os.close();
                } catch (IOException ioEx) {
                    ioEx.printStackTrace();
                }
            }
        }
    }


    /**
     * Creates an export stream given a file path name.
     *
     * @param fileName The path name.
     * @return The out stream corresponding to the path
     * name, or <code>null</code> if there was a problem
     * creating the stream.
     */
    protected OutputStream createOutputStream(String fileName) {
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException fnfEx) {
            fnfEx.printStackTrace();
        }
        return fos;
    }


    /**
     * Exports the graph in the specified format to the specified
     * export stream.
     *
     * @param os     The <code>OutputStream</code> that the image/export
     *               data should be written to.
     * @param format The <code>ExportFormat</code> for the data.
     */
    protected void doExport(OutputStream os,
                            ExportFormat format) {
        ExportFormat exportFormat = formatPage.getSelectedExportFormat();
        GraphComponent graphComponent = hierarchyPage.getSelectedGraphComponent();
        exportFormat.export(graphComponent.getController(), os);
    }


    public void updateState() {
        setEnabled(true);
    }


    public void dispose() {
        // do nothing
    }
}

