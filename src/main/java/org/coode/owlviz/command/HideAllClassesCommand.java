package org.coode.owlviz.command;

import org.coode.owlviz.ui.OWLVizIcons;
import org.coode.owlviz.ui.OWLVizViewI;
import org.coode.owlviz.util.graph.ui.GraphComponent;
import org.protege.editor.owl.ui.view.OWLSelectionViewAction;

import java.awt.event.ActionEvent;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 11, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class HideAllClassesCommand extends OWLSelectionViewAction {

    /**
     *
     */
    private static final long serialVersionUID = -1129349126344279656L;

    private OWLVizViewI view;

    public HideAllClassesCommand(OWLVizViewI view) {
        super("Hide all classes", OWLVizIcons.getIcon(OWLVizIcons.HIDE_ALL_CLASSES_ICON));
        this.view = view;
    }

    public void updateState() {
        setEnabled(true);
    }

    public void dispose() {
    }


    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        for (Iterator it = view.getGraphComponents().iterator(); it.hasNext(); ) {
            GraphComponent graphComponent = (GraphComponent) it.next();
            graphComponent.getVisualisedObjectManager().hideAll();
        }
    }
}
