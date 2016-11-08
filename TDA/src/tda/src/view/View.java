package tda.src.view;
import tda.src.model.Model;

public class View {
/**
 * <pre>
 *           1..1     1..1
 * View ------------------------> Model
 *           view        &gt;       model
 * </pre>
 */
private Model model;

public void setModel(Model value) {
   this.model = value;
}

public Model getModel() {
   return this.model;
}

}
