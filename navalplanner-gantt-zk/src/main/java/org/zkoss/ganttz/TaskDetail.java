package org.zkoss.ganttz;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.ganttz.util.TaskBean;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

public class TaskDetail extends HtmlMacroComponent implements AfterCompose {


    private static final Log LOG = LogFactory.getLog(TaskDetail.class);

    private final TaskBean taskBean;

    public TaskBean getTaskBean() {
        return taskBean;
    }

    private Textbox nameBox;

    private Textbox startDateTextBox;

    private Textbox endDateTextBox;

    public Textbox getNameBox() {
        return nameBox;
    }

    public void setNameBox(Textbox nameBox) {
        this.nameBox = nameBox;
    }

    public Datebox getStartDateBox() {
        return startDateBox;
    }

    public void setStartDateBox(Datebox startDateBox) {
        this.startDateBox = startDateBox;
        this.startDateBox.setCompact(true);
        this.startDateBox.setFormat("dd/MM/yyyy");
    }

    public Datebox getEndDateBox() {
        return endDateBox;
    }

    public void setEndDateBox(Datebox endDateBox) {
        this.endDateBox = endDateBox;
        this.endDateBox.setFormat("dd/MM/yyyy");
    }

    private Datebox startDateBox;

    private Datebox endDateBox;

    public static TaskDetail create(TaskBean bean) {
        return new TaskDetail(bean);
    }

    private DateFormat dateFormat;

    private TaskDetail(TaskBean task) {
        this.taskBean = task;
        this.dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locales
                .getCurrent());
    }

    public TaskBean getData() {
        return taskBean;
    }

    /**
     * When a text box associated to a datebox receives focus, the corresponding
     * datebox is shown
     * @param component
     *            the component that has received focus
     */
    public void hasReceivedFocus(Component component) {
        if (component == startDateTextBox) {
            showDateBox(startDateBox, startDateTextBox);
        }
        if (component == endDateTextBox) {
            showDateBox(endDateBox, endDateTextBox);
        }
    }

    private void showDateBox(Datebox dateBox, Textbox associatedTextBox) {
        associatedTextBox.setVisible(false);
        dateBox.setVisible(true);
        dateBox.setFocus(true);
    }

    /**
     * When the dateBox loses focus the corresponding textbox is shown instead.
     * @param dateBox
     *            the component that has lost focus
     */
    public void dateBoxHasLostFocus(Datebox dateBox) {
        if (dateBox == startDateBox) {
            hideDateBox(startDateBox, startDateTextBox);
        }
        if (dateBox == endDateBox) {
            hideDateBox(endDateBox, endDateTextBox);
        }
    }

    private void hideDateBox(Datebox dateBoxToDissapear,
            Textbox associatedTextBox) {
        dateBoxToDissapear.setVisible(false);
        associatedTextBox.setVisible(true);
    }

    @Override
    public void afterCompose() {
        super.afterCompose();
        updateComponents();
        taskBean.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateComponents();
            }
        });
    }

    public void updateBean() {
        if (getEndDateBox().getValue().before(getStartDateBox().getValue())) {
            updateComponents();
            return;
        }
        taskBean.setName(getNameBox().getValue());
        taskBean.setBeginDate(getStartDateBox().getValue());
        taskBean.setEndDate(getEndDateBox().getValue());
    }

    private void updateComponents() {
        getNameBox().setValue(taskBean.getName());
        getStartDateBox().setValue(taskBean.getBeginDate());
        getEndDateBox().setValue(taskBean.getEndDate());
        getStartDateTextBox().setValue(asString(taskBean.getBeginDate()));
        getEndDateTextBox().setValue(asString(taskBean.getEndDate()));
    }

    private String asString(Date date) {
        return dateFormat.format(date);
    }

    public Textbox getStartDateTextBox() {
        return startDateTextBox;
    }

    public void setStartDateTextBox(Textbox startDateTextBox) {
        this.startDateTextBox = startDateTextBox;
    }

    public Textbox getEndDateTextBox() {
        return endDateTextBox;
    }

    public void setEndDateTextBox(Textbox endDateTextBox) {
        this.endDateTextBox = endDateTextBox;
    }


}
