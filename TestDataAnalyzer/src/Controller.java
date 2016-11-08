import java.util.Set;
import java.util.HashSet;

public class Controller {
public void setModel(Model value) {
this.model = value;
}

	/**
	 * <pre>
	 *           1..11..1	 * Controller ------------------------> Model
	 *           controller        &gt;       model
	 * </pre>
	 */
private Model model;

public Model getModel() {
   return this.model;
}


	/**
	 * <pre>
	 *           1..1     1..1
	 * Controller ------------------------> View
	 *           controller        &gt;       view
	 * </pre>
	 */
	private View view;

	public void setView(View value) {
		this.view = value;
	}

	public View getView() {
		return this.view;
	}

	public void showView() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}
