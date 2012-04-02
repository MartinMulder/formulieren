package nl.voorbeeldsite.formulieren;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wizard {

    private static final String WIZARD_STEP_KEY = "wizard_step";

    public enum Step {
        WELKOM,
        PERSOONSGEGEVENS,
        AANVRAAG,
        CONTROLE,
        BEVESTIGING;
    }

    private final List<Step> steps = new ArrayList<Step>();
    private final Step step;

    private Wizard() {
        Collections.addAll(steps, Step.values());
        step = Step.values()[0];
    }

    private Wizard(Step step) {
        Collections.addAll(steps, Step.values());
        this.step = step;
    }

    private static Wizard checkIfStepAndMakeWizard(Object attribute) {
        if (attribute instanceof Step) {
            return new Wizard((Step) attribute);
        } else {
            return new Wizard();
        }
    }

    public static Wizard makeWizard(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(WIZARD_STEP_KEY);
        if (attribute instanceof Step) {
            return new Wizard((Step) attribute);
        } else {
            return new Wizard();
        }
    }

    public static Wizard makeWizard(FacesContext facesContext) {
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
        return checkIfStepAndMakeWizard(request.getSession().getAttribute(WIZARD_STEP_KEY));
    }

    public String nextStep() {
        int currentPosition = step.ordinal();
        if (currentPosition < steps.size() - 1) {
            return gotoStep(steps.get(++currentPosition));
        } else {
            return gotoStep(step);
        }
    }

    public String previousStep() {
        int currentPosition = step.ordinal();
        if (currentPosition != 0) {
            return gotoStep(steps.get(--currentPosition));
        } else {
            return gotoStep(step);
        }
    }

    public String gotoStep(Step step) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WIZARD_STEP_KEY, step);
        return getViewId(step) + "?faces-redirect=true";
    }

    public Step currentStep() {
        return step;
    }

    public boolean isFinalPage() {
        return steps.size() == step.ordinal();
    }
    
    public String getCurrentViewPrettyUrl() {
        if (step.ordinal() == 0) {
            return "/";
        } else {
            return "/" + step.name().toLowerCase() + "/";
        }
    }

    public String getViewId(Step step) {
        return step.name().toLowerCase() + ".xhtml";
    }

    public String getCurrentViewId() {
        return getViewId(step);
    }
}
